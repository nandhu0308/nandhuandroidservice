package com.limitless.services.payment.PaymentService.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.json.JSONArray;
import org.json.JSONObject;

import com.limitless.services.engage.dao.EngageSeller;
import com.limitless.services.payment.PaymentService.PaymentsSettlementResponseBean;
import com.limitless.services.payment.PaymentService.ReleaseFundsRequestBean;
import com.limitless.services.payment.PaymentService.ReleaseFundsResponseBean;
import com.limitless.services.payment.PaymentService.SettlementRequestBean;
import com.limitless.services.payment.PaymentService.SettlementResponseBean;
import com.limitless.services.payment.PaymentService.util.HibernateUtil;
import com.limitless.services.payment.PaymentService.util.PaymentConstants;
import com.limitless.services.payment.PaymentService.util.RestClientUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class PaymentSettlementManager {

	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	private static final Log log = LogFactory.getLog(PaymentTxnManager.class);
	Client client = RestClientUtil.createClient();

	public List<PaymentsSettlementResponseBean> doSettlement(int days) throws Exception {
		log.debug("Transaction settlement");
		List<PaymentsSettlementResponseBean> respBeanList = new ArrayList<PaymentsSettlementResponseBean>();
		Transaction transaction = null;
		Session session = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date now = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(now);
			calendar.add(Calendar.DATE, -days);
			String txnDateStart = sdf.format(calendar.getTime()) + " 00:00:00";
			calendar.add(Calendar.DATE, days);
			String txnDateEnd = sdf.format(calendar.getTime()) + " 00:00:00";
			log.debug("StartTime" + txnDateStart);
			log.debug("EndTime" + txnDateEnd);
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date startTime = sdf2.parse(txnDateStart);
			Date endTime = sdf2.parse(txnDateEnd);

			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			Criteria criteria = session.createCriteria(PaymentTxn.class);
			Criterion stCriterion = Restrictions.ge("txnUpdatedTime", startTime);
			Criterion etCriterion = Restrictions.lt("txnUpdatedTime", endTime);
			LogicalExpression timeExp = Restrictions.and(stCriterion, etCriterion);
			criteria.add(timeExp);
			List<PaymentTxn> txns = criteria.list();
			log.debug("Txns Size: " + txns.size());

			for (PaymentTxn txn : txns) {
				PaymentsSettlementResponseBean bean = new PaymentsSettlementResponseBean();
				SettlementRequestBean requestBean = new SettlementRequestBean();
				if (txn.getCitrusMpTxnId() != 0 && txn.getSplitId() != 0 && txn.getTxnAmount() != 0
						&& txn.getTxnStatus().equals("PAYMENT_SUCCESSFUL")) {
					requestBean.setTrans_id(txn.getCitrusMpTxnId());
					requestBean.setSettlement_ref("LimitlessCircle Pay");
					requestBean.setTrans_source("CITRUS");
					double txnAmount = txn.getTxnAmount();
					int citrusSellerId = txn.getCitrusSellerId();

					// Getting seller split percent
					Criteria criteria2 = session.createCriteria(EngageSeller.class);
					criteria2.add(Restrictions.eq("citrusSellerId", citrusSellerId));
					List<EngageSeller> sellerList = criteria2.list();
					double feePercent = 0;
					if (sellerList.size() > 0) {
						for (EngageSeller seller : sellerList) {
							feePercent = seller.getSellerSplitPercent();
						}
					}

					// Calculating settlement amount and split amount
					double feeAmount = txnAmount * (feePercent / 100);
					// round off to 2 decimal
					feeAmount = Math.round(feeAmount * 100) / 100D;

					double settlementAmount = txnAmount - feeAmount;
					log.debug("Settlement Amount: " + settlementAmount);
					requestBean.setSettlement_amount(settlementAmount);
					requestBean.setFee_amount(feeAmount);
					requestBean.setSettlement_date_time(txn.getTxnUpdatedTime().toString());

					PaymentSettlement settlement = new PaymentSettlement();

					// Doing settlement
					SettlementResponseBean settlementResponseBean = callSettlementApi(requestBean);

					if (settlementResponseBean.getMessage().equals("Success")) {
						settlement.setSettlementId(settlementResponseBean.getSettlementId());
						settlement.setTxnId(txn.getTxnId());
						settlement.setSettlementStatus("SETTLE_INITIATED");
					} else if (settlementResponseBean.getMessage().equals("Failed")) {
						settlement.setErrorIdSettle(settlementResponseBean.getErrorId());
						settlement.setErrorDescriptionSettle(settlementResponseBean.getErrorDescription());
						settlement.setTxnId(txn.getTxnId());
						settlement.setSettlementStatus("SETTLE_INITIATED");
					}

					// Doing release funds
					ReleaseFundsRequestBean fundsRequestBean = new ReleaseFundsRequestBean();
					fundsRequestBean.setSplit_id(txn.getSplitId());

					ReleaseFundsResponseBean fundsResponseBean = callReleaseFundsApi(fundsRequestBean);

					if (fundsResponseBean.getMessage().equals("Success")) {
						settlement.setReleasefundRefId(fundsResponseBean.getReleaseFundsRefId());
						settlement.setSettlementAmount(fundsResponseBean.getSettlementAmount());
						settlement.setTxnId(txn.getTxnId());
						settlement.setSettlementStatus("SETTLE_SUCCESS");
					} else if (fundsResponseBean.getMessage().equals("Failed")) {
						settlement.setErrorIdRelease(fundsResponseBean.getErrorId());
						settlement.setErrorDescriptionRelease(fundsResponseBean.getErrorDescription());
						settlement.setTxnId(txn.getTxnId());
						settlement.setSettlementStatus("SETTLE_INITIATED");
					}

					// settlement.setSettlementTime(txn.getTxnUpdatedTime());
					Criteria criteria3 = session.createCriteria(PaymentSettlement.class);
					criteria3.add(Restrictions.eq("txnId", txn.getTxnId()));
					List<PaymentSettlement> settlementList = criteria3.list();
					log.debug("Settlement List : " + settlementList);
					log.debug("Settlement Size : " + settlementList.size());
					if (settlementList.size() == 1) {
						for (PaymentSettlement settle : settlementList) {
							PaymentSettlement instance = (PaymentSettlement) session.get(
									"com.limitless.services.payment.PaymentService.dao.PaymentSettlement",
									settle.getPsId());
							if (instance.getSettlementStatus().equals("SETTLE_SUCCESS")) {
								instance.setSettlementId(instance.getSettlementId());
								instance.setReleasefundRefId(instance.getReleasefundRefId());
								instance.setSettlementAmount(instance.getSettlementAmount());
								instance.setErrorIdSettle(settlementResponseBean.getErrorId());
								instance.setErrorDescriptionSettle(settlementResponseBean.getErrorDescription());
								instance.setErrorIdRelease(fundsResponseBean.getErrorId());
								instance.setErrorDescriptionRelease(fundsResponseBean.getErrorDescription());
							} else if (instance.getSettlementStatus().equals("SETTLE_INITIATED")) {
								if (instance.getSettlementId() == 0) {
									instance.setSettlementId(settlementResponseBean.getSettlementId());
								} else {
									instance.setErrorIdSettle(settlementResponseBean.getErrorId());
									instance.setErrorDescriptionSettle(settlementResponseBean.getErrorDescription());
								}
								if (instance.getReleasefundRefId() == 0) {
									instance.setReleasefundRefId(fundsResponseBean.getReleaseFundsRefId());
									instance.setSettlementAmount(fundsResponseBean.getSettlementAmount());
								} else {
									instance.setErrorIdRelease(fundsResponseBean.getErrorId());
									instance.setErrorDescriptionRelease(fundsResponseBean.getErrorDescription());
								}
							}

							session.update(instance);

							bean.setPsId(instance.getPsId());
							bean.setSettlementId(instance.getSettlementId());
							bean.setReleasefundRefId(instance.getReleasefundRefId());
							bean.setSettlementAmount(instance.getSettlementAmount());
							bean.setErrorIdSettle(instance.getErrorIdSettle());
							bean.setErrorDescriptionSettle(instance.getErrorDescriptionSettle());
							bean.setErrorIdRelease(instance.getErrorIdRelease());
							bean.setErrorDescriptionRelease(instance.getErrorDescriptionRelease());

						}
					} else if (settlementList.isEmpty()) {
						session.persist(settlement);
						bean.setPsId(settlement.getPsId());
						bean.setReleasefundRefId(settlement.getReleasefundRefId());
						bean.setSettlementId(settlement.getSettlementId());
					}

					respBeanList.add(bean);
					bean = null;
				}
			}
			transaction.commit();

		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Transaction settlement failed", re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return respBeanList;
	}

	public SettlementResponseBean callSettlementApi(SettlementRequestBean requestBean) {
		SettlementResponseBean responseBean = new SettlementResponseBean();
		try {
			PaymentConstants constants = PaymentConstants.getInstance();
			String authToken = constants.getAuth_Token();
			WebResource webResource = client.resource("https://splitpay.citruspay.com/marketplace/pgsettlement/");
			ClientResponse clientResponse = webResource.type("application/json").accept("application/json")
					.header("auth_token", authToken).post(ClientResponse.class, requestBean);
			String settlementResponse = clientResponse.getEntity(String.class);
			log.debug("Settlement Response: " + settlementResponse);

			// JSON conversion
			try {
				JSONObject srJson = new JSONObject(settlementResponse);

				if (srJson.has("settlement_id")) {
					responseBean.setSettlementId(srJson.getInt("settlement_id"));
					responseBean.setMessage("Success");
				} else if (srJson.has("error_id")) {
					responseBean.setErrorId(srJson.getString("error_id"));
					responseBean.setErrorDescription(srJson.getString("error_description"));
					responseBean.setMessage("Failed");
				}
			} catch (Exception e) {
				JSONArray errJsonArray = new JSONArray(settlementResponse);
				for (int i = 0; i < errJsonArray.length(); i++) {
					JSONObject errJson = errJsonArray.getJSONObject(i);
					responseBean.setErrorDescription(errJson.getString("message"));
					responseBean.setMessage("Failed");
					continue;
				}
			}
		} catch (RuntimeException re) {
			log.error("Settlement API failed", re);
			throw re;
		}
		return responseBean;
	}

	public ReleaseFundsResponseBean callReleaseFundsApi(ReleaseFundsRequestBean requestBean) {
		ReleaseFundsResponseBean responseBean = new ReleaseFundsResponseBean();
		try {
			PaymentConstants constants = PaymentConstants.getInstance();
			String authToken = constants.getAuth_Token();
			WebResource webResource = client.resource("https://splitpay.citruspay.com/marketplace/funds/release/");
			ClientResponse clientResponse = webResource.type("application/json").accept("application/json")
					.header("auth_token", authToken).post(ClientResponse.class, requestBean);
			String fundsResponse = clientResponse.getEntity(String.class);
			log.debug("Release Funds Response: " + fundsResponse);

			// JSON Conversion
			JSONObject fundsJson = new JSONObject(fundsResponse);

			if (fundsJson.has("releasefund_ref")) {
				responseBean.setReleaseFundsRefId(fundsJson.getInt("releasefund_ref"));
				responseBean.setSettlementAmount(fundsJson.getDouble("amount"));
				responseBean.setMessage("Success");
			} else if (fundsJson.has("error_id")) {
				responseBean.setErrorId(fundsJson.getString("error_id"));
				responseBean.setErrorDescription(fundsJson.getString("error_description"));
				responseBean.setMessage("Failed");
			}
		} catch (RuntimeException re) {
			log.error("ReleaseFunds API failed", re);
			throw re;
		}
		return responseBean;
	}
}

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
import org.hibernate.criterion.Junction;
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
	private static final Log log = LogFactory.getLog(PaymentSettlementManager.class);
	Client client = RestClientUtil.createClient();

	public List<PaymentsSettlementResponseBean> doSettlement(int days) throws Exception {
		log.debug("Transaction settlement");
		String authToken = "";
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

			CitrusAuthToken token = (CitrusAuthToken) session
					.get("com.limitless.services.payment.PaymentService.dao.CitrusAuthToken", 1);
			authToken = token.getAuthToken();

			Criteria criteria = session.createCriteria(PaymentTxn.class);
			Junction conditionGroup = Restrictions.conjunction()
					.add(Restrictions.between("txnUpdatedTime", startTime, endTime))
					.add(Restrictions.ne("citrusMpTxnId", 0)).add(Restrictions.ne("splitId", 0))
					.add(Restrictions.ne("txnAmount", 0F)).add(Restrictions.eq("txnStatus", "PAYMENT_SUCCESSFUL"));
			criteria.add(conditionGroup);
			List<PaymentTxn> txns = criteria.list();
			log.debug("Txns Size: " + txns.size() + "Txns : " + txns.toString());

			for (PaymentTxn txn : txns) {
				PaymentsSettlementResponseBean bean = new PaymentsSettlementResponseBean();
				SettlementRequestBean requestBean = new SettlementRequestBean();
				requestBean.setTrans_id(txn.getCitrusMpTxnId());
				requestBean.setSettlement_ref("LimitlessCircle Pay");
				requestBean.setTrans_source("CITRUS");
				double txnAmount = txn.getTxnAmount();
				int sellerId = txn.getSellerId();

				// Getting seller split percent
				EngageSeller seller = (EngageSeller) session.get("com.limitless.services.engage.dao.EngageSeller",
						sellerId);
				double feePercent = seller.getSellerSplitPercent();

				// Calculating settlement amount and split amount
				double feeAmount = txnAmount * (feePercent / 100);
				// round off to 2 decimal
				feeAmount = Math.round(feeAmount * 100) / 100D;

				double settlementAmount = txnAmount - feeAmount;
				log.debug("Settlement Amount: " + settlementAmount);
				requestBean.setSettlement_amount(settlementAmount);
				requestBean.setFee_amount(feeAmount);
				requestBean.setSettlement_date_time(txn.getTxnUpdatedTime().toString());

				SettlementResponseBean settlementResponseBean = null;
				ReleaseFundsRequestBean fundsRequestBean = new ReleaseFundsRequestBean();
				ReleaseFundsResponseBean fundsResponseBean = null;
				PaymentSettlement settlement = new PaymentSettlement();
				Criteria criteria2 = session.createCriteria(PaymentSettlement.class);
				criteria2.add(Restrictions.eq("txnId", txn.getTxnId()));
				List<PaymentSettlement> settleList = criteria2.list();
				log.debug("settlePrevList Size : " + settleList.size());

				if (settleList.size() > 0) {
					for (PaymentSettlement settle1 : settleList) {
						// Doing settlement
						PaymentSettlement settleInstance = (PaymentSettlement) session.get(
								"com.limitless.services.payment.PaymentService.dao.PaymentSettlement",
								settle1.getPsId());
						try {
							if (settleInstance.getSettlementId() == 0) {
								settlementResponseBean = callSettlementApi(requestBean, authToken);
							} else {
								settlementResponseBean = new SettlementResponseBean();
								settlementResponseBean.setSettlementId(settleInstance.getSettlementId());
								settlementResponseBean.setErrorId(settleInstance.getErrorIdSettle());
								settlementResponseBean.setMessage("Success");
							}

						} catch (Exception e) {
							continue;
						}

						boolean isSettlementAlreadyPerformed = (settlementResponseBean.getErrorId() != null
								&& settlementResponseBean.getErrorId().equals("343"));
						if (settlementResponseBean.getMessage().equals("Success") || isSettlementAlreadyPerformed) {
							if (!isSettlementAlreadyPerformed) {
								settleInstance.setSettlementId(settlementResponseBean.getSettlementId());
							} else {
								settleInstance.setErrorIdSettle(settlementResponseBean.getErrorId());
							}
							settleInstance.setTxnId(txn.getTxnId());
							settleInstance.setSettlementStatus("SETTLE_SUCCESS");
							session.update(settleInstance);
							bean.setPsId(settleInstance.getPsId());
							bean.setSettlementId(settleInstance.getSettlementId());
						} else if (settlementResponseBean.getMessage().equals("Failed")
								|| settlementResponseBean.getErrorId() == null) {
							if (settlementResponseBean.getErrorId() == null) {
								settleInstance.setErrorIdSettle("NA");
							} else {
								settleInstance.setErrorIdSettle(settlementResponseBean.getErrorId());
							}
							settleInstance.setErrorDescriptionSettle(settlementResponseBean.getErrorDescription());
							settleInstance.setTxnId(txn.getTxnId());
							settleInstance.setSettlementStatus("SETTLE_FAILED");
							session.update(settleInstance);
							bean.setPsId(settleInstance.getPsId());
							bean.setErrorIdSettle(settleInstance.getErrorIdSettle());
							bean.setErrorDescriptionSettle(settleInstance.getErrorDescriptionSettle());
						}

						PaymentSettlement releaseInstance = (PaymentSettlement) session.get(
								"com.limitless.services.payment.PaymentService.dao.PaymentSettlement",
								settle1.getPsId());
						// Doing release funds
						if (releaseInstance.getSettlementId() > 0 || releaseInstance.getErrorIdSettle().equals("343")) {
							if (releaseInstance.getReleasefundRefId() == 0) {

								fundsRequestBean.setSplit_id(txn.getSplitId());
								fundsResponseBean = callReleaseFundsApi(fundsRequestBean, authToken);

								boolean isFundsReleasedAlready = (fundsResponseBean.getErrorId() != null
										&& fundsResponseBean.getErrorId().equals("361"));

								if (fundsResponseBean.getMessage().equals("Success") || isFundsReleasedAlready) {
									if (isFundsReleasedAlready) {
										releaseInstance.setErrorIdRelease(fundsResponseBean.getErrorId());
									} else {
										releaseInstance.setReleasefundRefId(fundsResponseBean.getReleaseFundsRefId());
									}
									if (fundsResponseBean.getSettlementAmount() > 0) {
										releaseInstance.setSettlementAmount(fundsResponseBean.getSettlementAmount());
									}
									releaseInstance.setTxnId(txn.getTxnId());
									releaseInstance.setSettlementStatus("RELEASE_SUCCESS");
									session.update(releaseInstance);
									bean.setPsId(releaseInstance.getPsId());
									bean.setReleasefundRefId(releaseInstance.getReleasefundRefId());
									bean.setSettlementAmount(releaseInstance.getSettlementAmount());
								} else if (fundsResponseBean.getMessage().equals("Failed")) {
									releaseInstance.setErrorIdRelease(fundsResponseBean.getErrorId());
									releaseInstance.setErrorDescriptionRelease(fundsResponseBean.getErrorDescription());
									releaseInstance.setTxnId(txn.getTxnId());
									releaseInstance.setSettlementStatus("RELEASE_FAILED");
									session.update(releaseInstance);
									bean.setPsId(releaseInstance.getPsId());
									bean.setErrorIdRelease(releaseInstance.getErrorIdRelease());
									bean.setErrorDescriptionRelease(releaseInstance.getErrorDescriptionRelease());
								}
							} else {
								releaseInstance.setSettlementStatus("RELEASE_SUCCESS");
								session.update(releaseInstance);
							}
						}
					}
				} else if (settleList.isEmpty()) {

					try {
						settlementResponseBean = callSettlementApi(requestBean, authToken);
					} catch (Exception e) {
						continue;
					}

					boolean isSettlementAlreadyPerformed = (settlementResponseBean.getErrorId() != null
							&& settlementResponseBean.getErrorId().equals("343"));

					if (settlementResponseBean.getMessage().equals("Success") || isSettlementAlreadyPerformed) {

						if (!isSettlementAlreadyPerformed) {
							settlement.setSettlementId(settlementResponseBean.getSettlementId());
						} else {
							settlement.setErrorIdSettle(settlementResponseBean.getErrorId());
						}

						settlement.setTxnId(txn.getTxnId());
						settlement.setSettlementStatus("SETTLE_SUCCESS");

						fundsRequestBean.setSplit_id(txn.getSplitId());
						fundsResponseBean = callReleaseFundsApi(fundsRequestBean, authToken);
						boolean isFundsReleasedAlready = (fundsResponseBean.getErrorId() != null
								&& fundsResponseBean.getErrorId().equals("361"));
						int settleId = settlement.getPsId();
						if (fundsResponseBean.getMessage().equals("Success") || isFundsReleasedAlready) {

							if (isFundsReleasedAlready) {
								settlement.setErrorIdRelease(fundsResponseBean.getErrorId());
							} else {
								settlement.setReleasefundRefId(fundsResponseBean.getReleaseFundsRefId());
							}
							if (fundsResponseBean.getSettlementAmount() > 0) {
								settlement.setSettlementAmount(fundsResponseBean.getSettlementAmount());
							}
							settlement.setSettlementStatus("RELEASE_SUCCESS");
							bean.setPsId(settleId);
							bean.setSettlementId(settlement.getSettlementId());
							bean.setReleasefundRefId(settlement.getReleasefundRefId());
							bean.setSettlementAmount(settlement.getSettlementAmount());
							bean.setMessage("Success");
						}
					} else if (settlementResponseBean.getMessage().equals("Failed")) {
						settlement.setErrorIdSettle(settlementResponseBean.getErrorId());
						settlement.setErrorDescriptionSettle(settlementResponseBean.getErrorDescription());
						settlement.setTxnId(txn.getTxnId());
						settlement.setSettlementStatus("SETTLE_FAILED");
						bean.setPsId(settlement.getPsId());
						bean.setErrorIdSettle(settlement.getErrorIdSettle());
						bean.setErrorDescriptionSettle(settlement.getErrorDescriptionSettle());
					}
					session.persist(settlement);

				}
				settlement = null;
				respBeanList.add(bean);
				bean = null;
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

	public SettlementResponseBean callSettlementApi(SettlementRequestBean requestBean, String authToken) {
		SettlementResponseBean responseBean = new SettlementResponseBean();
		try {
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
					responseBean.setErrorId("PE");
					responseBean.setMessage("Failed");
					continue;
				}
			}
		} catch (RuntimeException re) {
			log.error("Settlement API failed", re);
		}
		return responseBean;
	}

	public ReleaseFundsResponseBean callReleaseFundsApi(ReleaseFundsRequestBean requestBean, String authToken) {
		ReleaseFundsResponseBean responseBean = new ReleaseFundsResponseBean();
		try {
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

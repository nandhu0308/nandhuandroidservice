package com.limitless.services.payment.PaymentService.dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

// Generated Oct 19, 2016 7:49:46 PM by Hibernate Tools 3.4.0.CR1

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.limitless.services.engage.dao.EngageCustomer;
import com.limitless.services.engage.dao.EngageSeller;
import com.limitless.services.engage.dao.SellerPayamentsConfiguration;
import com.limitless.services.payment.PaymentService.CreditReminderRequestBean;
import com.limitless.services.payment.PaymentService.CreditReminderResponseBean;
import com.limitless.services.payment.PaymentService.CreditRespBean;
import com.limitless.services.payment.PaymentService.CustomerCreditResponseBean;
import com.limitless.services.payment.PaymentService.SellerCreditsResponseBean;
import com.limitless.services.payment.PaymentService.util.HibernateUtil;

/**
 * Home object for domain model class PaymentCredit.
 * 
 * @see com.limitless.PaymentCredit
 * @author Hibernate Tools
 */
public class PaymentCreditManager {

	private static final Log log = LogFactory.getLog(PaymentCreditManager.class);

	/*
	 * private final SessionFactory sessionFactory = getSessionFactory();
	 * 
	 * protected SessionFactory getSessionFactory() { try { return
	 * (SessionFactory) new InitialContext() .lookup("SessionFactory"); } catch
	 * (Exception e) { log.error("Could not locate SessionFactory in JNDI", e);
	 * throw new IllegalStateException(
	 * "Could not locate SessionFactory in JNDI"); } }
	 */

	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public void persist(PaymentCredit transientInstance) {
		log.debug("persisting PaymentCredit instance");
		Transaction tx = null;
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			tx = session.beginTransaction();
			session.persist(transientInstance);
			tx.commit();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			if (tx != null) {
				tx.rollback();
			}
			log.error("persist failed", re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	public void attachDirty(PaymentCredit instance) {
		log.debug("attaching dirty PaymentCredit instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(PaymentCredit instance) {
		log.debug("attaching clean PaymentCredit instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(PaymentCredit persistentInstance) {
		log.debug("deleting PaymentCredit instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public PaymentCredit merge(PaymentCredit detachedInstance) {
		log.debug("merging PaymentCredit instance");
		try {
			PaymentCredit result = (PaymentCredit) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public PaymentCredit findById(java.lang.Integer id) {
		log.debug("getting PaymentCredit instance with id: " + id);
		try {
			PaymentCredit instance = (PaymentCredit) sessionFactory.getCurrentSession()
					.get("com.limitless.PaymentCredit", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(PaymentCredit instance) {
		log.debug("finding PaymentCredit instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria("com.limitless.PaymentCredit")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<SellerCreditsResponseBean> sellerCreditsList(int sellerId) {
		log.debug("Getting Total credits for seller" + sellerId);
		List<SellerCreditsResponseBean> creditsList = new ArrayList<SellerCreditsResponseBean>();
		Transaction transaction = null;
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(PaymentCredit.class);
			criteria.add(Restrictions.eq("sellerId", sellerId))
					.setProjection(Projections.distinct(Projections.property("customerId")));
			List<Integer> credits = criteria.list();
			log.debug("Credits size:" + credits.size());
			if (credits != null && credits.size() > 0) {
				for (Integer credit : credits) {
					int custId = (Integer) credit;
					Query query = session.createQuery(
							"select sum(PC.creditAmount) from PaymentCredit PC where PC.customerId = :customerId");
					query.setParameter("customerId", custId);
					List amountList = query.list();
					log.debug("Amount :" + amountList.toString());
					double creditAmt = (Double) amountList.get(0);
					Query query2 = session.createQuery(
							"select sum(PC.debitAmount) from PaymentCredit PC where PC.customerId = :customerId");
					query2.setParameter("customerId", custId);
					List amountList2 = query2.list();
					log.debug("Amount :" + amountList2.toString());
					double debitAmt = (Double) amountList2.get(0);
					double netCredit = creditAmt - debitAmt;
					EngageCustomer customer = (EngageCustomer) session
							.get("com.limitless.services.engage.dao.EngageCustomer", custId);
					String customerName = customer.getCustomerName();
					String customerPhone = customer.getCustomerMobileNumber();
					SellerCreditsResponseBean bean = new SellerCreditsResponseBean();
					bean.setCustomerId(custId);
					bean.setCustomerName(customerName);
					bean.setCustomerPhone(customerPhone);
					bean.setTotalCredits(netCredit);
					creditsList.add(bean);
					bean = null;
				}
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Getting sellers credits failed");
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return creditsList;
	}

	public List<CustomerCreditResponseBean> customerCreditsList(int customerId) {

		log.debug("Getting Total credits for Customer" + customerId);
		List<CustomerCreditResponseBean> creditsList = new ArrayList<CustomerCreditResponseBean>();
		Transaction transaction = null;
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(PaymentCredit.class);
			criteria.add(Restrictions.eq("customerId", customerId))
					.setProjection(Projections.distinct(Projections.property("merchantId")));
			List<Integer> credits = criteria.list();
			log.debug("Credits size:" + credits.size());
			if (credits != null && credits.size() > 0) {
				for (Integer credit : credits) {
					int sellerId = (Integer) credit;
					Query query = session.createQuery(
							"select sum(PC.creditAmount) from PaymentCredit PC where PC.merchantId = :sellerId and PC.customerId = :customerId");
					query.setParameter("sellerId", sellerId);
					query.setParameter("customerId", customerId);
					List amountList = query.list();
					log.debug("Amount :" + amountList.toString());
					double creditAmt = (Double) amountList.get(0);
					Query query2 = session.createQuery(
							"select sum(PC.debitAmount) from PaymentCredit PC where PC.merchantId = :sellerId and PC.customerId = :customerId");
					query2.setParameter("sellerId", sellerId);
					query2.setParameter("customerId", customerId);
					List amountList2 = query2.list();
					log.debug("Amount :" + amountList2.toString());
					double debitAmt = (Double) amountList2.get(0);
					double netCredit = creditAmt - debitAmt;
					EngageSeller seller = (EngageSeller) session
							.get("com.limitless.services.engage.dao.EngageSeller",sellerId);
					String sellerName = seller.getSellerName();
					String sellerPhone = seller.getSellerMobileNumber();
					int citrusSellerId = 0;
					Criteria criteria2 = session.createCriteria(SellerPayamentsConfiguration.class);
					criteria2.add(Restrictions.eq("sellerId", sellerId));
					List<SellerPayamentsConfiguration> configList = criteria2.list();
					if(configList.size()==1){
						for(SellerPayamentsConfiguration config : configList){
							citrusSellerId = config.getCitrusSellerId();
						}
					}
					CustomerCreditResponseBean bean = new CustomerCreditResponseBean();
					bean.setSellerId(citrusSellerId);
					bean.setSellerName(sellerName);
					bean.setSellerPhone(sellerPhone);
					bean.setTotalCredits(netCredit);
					bean.setMerchantId(sellerId);
					creditsList.add(bean);
					bean = null;
				}
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Getting customer credits failed");
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return creditsList;
	}

	public CreditRespBean updateCreditDebitTrans(int txnId) {
		log.debug("Updating Credit/Debit Trans");
		CreditRespBean respBean = new CreditRespBean();
		Transaction transaction = null;
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(PaymentCredit.class);
			criteria.add(Restrictions.eq("txnId", txnId));
			List<PaymentCredit> credits = criteria.list();
			log.debug("Credits Size: " + credits.size());
			if (credits.size() > 0) {
				for (PaymentCredit credit : credits) {
					credit.setCreditAmount(credit.getCreditTemp());
					credit.setDebitAmount(credit.getDebitTemp());
					respBean.setCreditId(credit.getCreditId());
					respBean.setMessage("Success");
				}
			} else if (credits.isEmpty()) {
				respBean.setMessage("Failed");
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Updating Credit/Debit Trans failed" + re);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return respBean;
	}
	
	public CreditReminderResponseBean sendCreditReminder(CreditReminderRequestBean requestBean) throws Exception{
		log.debug("sending credit reminder");
		CreditReminderResponseBean responseBean = new CreditReminderResponseBean();
		String response = "";
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			int sellerId = requestBean.getSellerId();
			int customerId = requestBean.getCustomerId();
			
			EngageSeller seller = (EngageSeller) session
					.get("com.limitless.services.engage.dao.EngageSeller", requestBean.getSellerId());
			String sellerShopName = seller.getSellerShopName();
			String sellerMobileNumber = seller.getSellerMobileNumber();
			
			EngageCustomer customer = (EngageCustomer) session
					.get("com.limitless.services.engage.dao.EngageCustomer", requestBean.getCustomerId());
			String customerName = customer.getCustomerName();
			String customerMobileNumber = customer.getCustomerMobileNumber();
			
			Query query = session.createQuery(
					"select sum(PC.creditAmount) from PaymentCredit PC where PC.merchantId = :sellerId and PC.customerId = :customerId");
			query.setParameter("sellerId", sellerId);
			query.setParameter("customerId", customerId);
			List creditAmountList = query.list();
			log.debug("credit amount : " + creditAmountList.toString());
			
			Query query2 = session.createQuery(
					"select sum(PC.debitAmount) from PaymentCredit PC where PC.merchantId = :sellerId and PC.customerId = :customerId");
			query2.setParameter("sellerId", sellerId);
			query2.setParameter("customerId", customerId);
			List debitAmountList = query2.list();
			log.debug("debit amount : " + debitAmountList.toString());
			
			double creditAmount = (Double) creditAmountList.get(0);
			double debitAmount = (Double) debitAmountList.get(0);
			double netCredit = creditAmount - debitAmount;
			
			log.debug("Net credit amount : " + netCredit);
			
			String message = "REMINDER! "+customerName+" kindly pay Rs. "+netCredit+" to Ms. "+sellerShopName+". Thanks for going cashfree with LimitlessCircle.";
			String encoded_message = URLEncoder.encode(message);
			String authkey = "129194Aa6NwGoQsVt580d9a57";
			String mobiles = customerMobileNumber;
			String senderId = "HAAPPY";
			String route = "4";
			String mainUrl = "http://api.msg91.com/api/sendhttp.php?";
			StringBuilder sbPostData = new StringBuilder(mainUrl);
			sbPostData.append("authkey=" + authkey);
			sbPostData.append("&mobiles=" + mobiles);
			sbPostData.append("&message=" + encoded_message);
			sbPostData.append("&route=" + route);
			sbPostData.append("&sender=" + senderId);
			mainUrl = sbPostData.toString();
			URL msgUrl = new URL(mainUrl);
			URLConnection con = msgUrl.openConnection();
			con.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			while ((response = reader.readLine()) != null) {
				System.out.println(response);
			}
			reader.close();
			
			responseBean.setCustomerId(requestBean.getCustomerId());
			responseBean.setSellerId(requestBean.getSellerId());
			responseBean.setMessage("Success");
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("sending credit reminder failed: " + re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}

}

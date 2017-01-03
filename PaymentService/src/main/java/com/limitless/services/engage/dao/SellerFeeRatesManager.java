package com.limitless.services.engage.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.limitless.services.engage.SellerFeeRateRequestBean;
import com.limitless.services.engage.SellerFeeRateResponseBean;
import com.limitless.services.payment.PaymentService.util.HibernateUtil;

public class SellerFeeRatesManager {
	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	private static final Log log = LogFactory.getLog(SellerFeeRatesManager.class);
	
	public SellerFeeRateResponseBean addFeeRates(SellerFeeRateRequestBean requestBean){
		log.debug("Adding fee rated to sellers");
		SellerFeeRateResponseBean responseBean = new SellerFeeRateResponseBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			SellerFeeRates rates = new SellerFeeRates();
			rates.setSellerId(requestBean.getSellerId());
			rates.setUpto3k(requestBean.getUpto3k());
			rates.setGt3klt10k(requestBean.getGt3klt10k());
			rates.setGt10klt1lac(requestBean.getGt10klt1lac());
			rates.setGt1laclt2lac(requestBean.getGt1laclt2lac());
			rates.setGt2lac(requestBean.getGt2lac());
			
			session.persist(rates);
			responseBean.setRateId(rates.getRateId());
			responseBean.setMessage("Success");
			transaction.commit();
		}
		catch(RuntimeException re){
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("adding fee rate failed : " + re);
		}
		finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}
}

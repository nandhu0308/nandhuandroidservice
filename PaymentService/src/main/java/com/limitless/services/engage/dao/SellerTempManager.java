package com.limitless.services.engage.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.limitless.services.engage.SellerTempRequestBean;
import com.limitless.services.engage.SellerTempResponseBean;
import com.limitless.services.payment.PaymentService.util.HibernateUtil;

public class SellerTempManager {
	private static final Log log = LogFactory.getLog(SellerTempManager.class);
	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	public SellerTempResponseBean addTempDetails(SellerTempRequestBean requestBean){
		log.debug("Storing seller temp details");
		SellerTempResponseBean responseBean = new SellerTempResponseBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			SellerTemp temp = new SellerTemp();
			temp.setSellerId(requestBean.getSellerId());
			temp.setSellerAccount(requestBean.getSellerBankAccountNumber());
			temp.setSellerIfsc(requestBean.getSellerIfsc());
			temp.setSellerKycImage(requestBean.getSellerKycImage());
			temp.setSellerBankProof(requestBean.getSellerBankProof());
			temp.setStatus(requestBean.getStatus());
			
			session.persist(temp);
			
			responseBean.setStId(temp.getStId());
			responseBean.setMessage("Success");
			transaction.commit();
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("persist failed", re);
			throw re;
		}
		finally {
			if(session.isOpen() && session!=null){
				session.close();
			}
		}
		return responseBean;
	}
}

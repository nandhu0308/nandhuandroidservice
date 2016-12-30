package com.limitless.services.payment.PaymentService.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.limitless.services.payment.PaymentService.MasterTxnRequestBean;
import com.limitless.services.payment.PaymentService.MasterTxnResponseBean;
import com.limitless.services.payment.PaymentService.util.HibernateUtil;

public class MasterTxnManager {
	private static final Log log = LogFactory.getLog(MasterTxnManager.class);
	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	public MasterTxnResponseBean createMasterTxn(MasterTxnRequestBean requestBean){
		log.debug("Adding master txn");
		MasterTxnResponseBean responseBean = new MasterTxnResponseBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			MasterTxn txn = new MasterTxn();
			txn.setCitrusTxnId(requestBean.getCitrusTxnId());
			txn.setIciciUorderId(requestBean.getIciciUorderId());
			txn.setSellerId(requestBean.getSellerId());
			txn.setCustomerId(requestBean.getCustomerId());
			
			session.persist(txn);
			
			responseBean.setMasterId(txn.getId());
			responseBean.setMessage("Success");
			
			transaction.commit();
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.debug("Master Txn adding failed : " + re);
		}
		finally {
			if(session!=null & session.isOpen()){
				session.close();
			}
		}
		return responseBean;
	}
	
}

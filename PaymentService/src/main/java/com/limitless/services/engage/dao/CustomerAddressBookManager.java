package com.limitless.services.engage.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.limitless.services.engage.CustomerAddressRequestBean;
import com.limitless.services.engage.CustomerAddressResponseBean;
import com.limitless.services.payment.PaymentService.util.HibernateUtil;

public class CustomerAddressBookManager {
	private static final Log log = LogFactory.getLog(CustomerAddressBookManager.class);
	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	public CustomerAddressResponseBean addAddress(CustomerAddressRequestBean requestBean){
		log.debug("adding address");
		CustomerAddressResponseBean responseBean = new CustomerAddressResponseBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			
			
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("adding address failed : " +re);
			throw re;
		}
		finally {
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return responseBean;
	}
	
}

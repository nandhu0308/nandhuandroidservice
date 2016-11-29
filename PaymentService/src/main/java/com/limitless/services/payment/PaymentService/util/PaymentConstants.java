package com.limitless.services.payment.PaymentService.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.limitless.services.payment.PaymentService.dao.CitrusAuthToken;

public class PaymentConstants {
	private static PaymentConstants constants = new PaymentConstants();
	private static String AUTH_TOKEN = "";
	private static final String ACCESS_KEY = "96K34Q4A8I8NREH7NVOZ";
	private static final String SECRET_KEY = "bc3fb974fd550bd26083862dda1591c80cf5da8f";
	
	private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	private static final Log log = LogFactory.getLog(PaymentConstants.class);
	
	private PaymentConstants(){}
	
	public static PaymentConstants getInstance(){
		return constants;
	}
	
	public String getAuth_Token(){
		if(AUTH_TOKEN.equals("")){
			log.debug("getting auth token");
			Session session = null;
			Transaction transaction = null;
			try{
				session = sessionFactory.getCurrentSession();
				transaction = session.beginTransaction();
				
				CitrusAuthToken token = (CitrusAuthToken) session
						.get("com.limitless.services.payment.PaymentService.dao.CitrusAuthToken", 1);
				AUTH_TOKEN = token.getAuthToken();
				transaction.commit();
			}
			catch(RuntimeException re){
				if(transaction!=null){
					transaction.rollback();
				}
				log.error("Getting auth token failed " + re);
			}
			finally {
				if(session!=null && session.isOpen()){
					session.close();
				}
			}
		}
		return AUTH_TOKEN;
	}
	
	public String getAccess_Key(){
		return ACCESS_KEY;
	}
	
	public String getSecret_key(){
		return SECRET_KEY;
	}
}

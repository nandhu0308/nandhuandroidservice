package com.limitless.services.payment.PaymentService.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.json.JSONObject;

import com.limitless.services.payment.PaymentService.AuthTokenRequestBean;
import com.limitless.services.payment.PaymentService.AuthTokenResponseBean;
import com.limitless.services.payment.PaymentService.util.HibernateUtil;
import com.limitless.services.payment.PaymentService.util.PaymentConstants;
import com.limitless.services.payment.PaymentService.util.RestClientUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class CitrusAuthTokenManager {
	private static final Log log = LogFactory.getLog(CitrusAuthTokenManager.class);
	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	Client client = RestClientUtil.createClient();

	public AuthTokenResponseBean getAuthToken() {
		log.debug("Getting Citrus Auth Token");
		AuthTokenResponseBean responseBean = new AuthTokenResponseBean();
		Session session = null;
		Transaction transaction = null;
		String authToken = "";
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			AuthTokenRequestBean requestBean = new AuthTokenRequestBean();
			PaymentConstants constants = PaymentConstants.getInstance();
			requestBean.setAccess_key(constants.getAccess_Key());
			requestBean.setSecret_key(constants.getSecret_key());

			WebResource webResource = client.resource("https://splitpay.citruspay.com/marketplace/auth/");
			ClientResponse clientResponse = webResource.type("application/json").accept("application/json")
					.post(ClientResponse.class, requestBean);
			String citrusResponse = clientResponse.getEntity(String.class);

			try {
				JSONObject responseJson = new JSONObject(citrusResponse);
				if (responseJson.has("auth_token")) {
					authToken = responseJson.getString("auth_token");
					constants.setAuthToken(authToken);
				} else {
					log.debug("Citrus Response : " + citrusResponse);
				}
			} catch (Exception e) {
				log.error("Getting auth token failed :" + e);
			}

			CitrusAuthToken token = (CitrusAuthToken) session
					.get("com.limitless.services.payment.PaymentService.dao.CitrusAuthToken", 1);
			token.setAuthToken(authToken);

			session.update(token);

			CitrusAuthToken updatedToken = (CitrusAuthToken) session
					.get("com.limitless.services.payment.PaymentService.dao.CitrusAuthToken", 1);

			responseBean.setMessage("Success");
			responseBean.setUpdatedTime(updatedToken.getUpdatedTime().toString());

			transaction.commit();			

		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Getting auth token failed " + re);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}
}

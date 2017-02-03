package com.limitless.services.engage.restaurants.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.limitless.services.payment.PaymentService.util.HibernateUtil;
import com.limitless.services.payment.PaymentService.util.RestClientUtil;
import com.sun.jersey.api.client.Client;

public class RestaurantManager {
	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	Client client = RestClientUtil.createClient();
	private static final Log log = LogFactory.getLog(RestaurantManager.class);
}

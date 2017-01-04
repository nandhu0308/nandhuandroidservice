package com.limitless.services.engage.order.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.limitless.services.engage.order.OrderProductsBean;
import com.limitless.services.engage.order.OrderRequestBean;
import com.limitless.services.engage.order.OrderResponseBean;
import com.limitless.services.engage.sellers.product.dao.Product;
import com.limitless.services.payment.PaymentService.util.HibernateUtil;

public class OrdersManager {
	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	private static final Log log = LogFactory.getLog(OrdersManager.class);
	
	public OrderResponseBean addOrder(OrderRequestBean requestBean){
		log.debug("Adding order");
		OrderResponseBean responseBean = new OrderResponseBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			double totalAmount = 0;
			List<OrderProductsBean> productList = requestBean.getOrderList();
			for(OrderProductsBean products : productList){
				Product product = (Product) session
						.get("com.limitless.services.engage.sellers.product.dao.Product", products.getProductId());
				double totalPrice = products.getQuantity() * product.getProductPrice();
				totalAmount +=totalPrice;
			}
			
			Orders order = new Orders();
			order.setCustomerId(requestBean.getCustomerId());
			order.setSellerId(requestBean.getSellerId());
			order.setTotalAmount(totalAmount);
			order.setOrderStatus("ORDER_PLACED");
			session.persist(order);
			
			int orderId = order.getOrderId();
			List<OrderProductsBean> productList2 = requestBean.getOrderList();
			for(OrderProductsBean products2 : productList){
				Product product = (Product) session
						.get("com.limitless.services.engage.sellers.product.dao.Product", products2.getProductId());
				double totalPrice = products2.getQuantity() * product.getProductPrice();
				OrderDetails details = new OrderDetails();
				details.setOrderId(orderId);
				details.setProductId(products2.getProductId());
				details.setQuantity(products2.getQuantity());
				details.setUnitPrice(totalPrice);
				session.persist(details);
			}
			responseBean.setOrderId(orderId);
			responseBean.setTotalAmount(totalAmount);
			responseBean.setMessage("Success");
			transaction.commit();
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("Adding order failed : " + re);
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

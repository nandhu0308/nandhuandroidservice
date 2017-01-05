package com.limitless.services.engage.order.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.limitless.services.engage.dao.EngageCustomer;
import com.limitless.services.engage.dao.EngageSeller;
import com.limitless.services.engage.order.OrderDetailResponseBean;
import com.limitless.services.engage.order.OrderProductsBean;
import com.limitless.services.engage.order.OrderProductsListBean;
import com.limitless.services.engage.order.OrderRequestBean;
import com.limitless.services.engage.order.OrderResponseBean;
import com.limitless.services.engage.order.OrderStatusResponseBean;
import com.limitless.services.engage.order.OrderSummaryResponseBean;
import com.limitless.services.engage.order.OrdersListBean;
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
			order.setOrderStatus("ORDER_INITIATED");
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
	
	public OrderStatusResponseBean orderStatusUpdate(int orderId, int statusCode){
		log.debug("updating the order status : " + orderId);
		OrderStatusResponseBean responseBean = new OrderStatusResponseBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			Orders order = (Orders) session
					.get("com.limitless.services.engage.order.dao.Orders", orderId);
			if(statusCode == 1){
				responseBean.setPrevStatus(order.getOrderStatus());
				order.setOrderStatus("ORDER_RECEIVED");
			}
			else if(statusCode == 2){
				responseBean.setPrevStatus(order.getOrderStatus());
				order.setOrderStatus("ORDER_PROCESSED");
			}
			else if(statusCode == 3){
				responseBean.setPrevStatus(order.getOrderStatus());
				order.setOrderStatus("ORDER_DELIVERED");
			}
			else if(statusCode == 4){
				responseBean.setPrevStatus(order.getOrderStatus());
				order.setOrderStatus("ORDER_CANCELED");
			}
			else if(statusCode == 5){
				responseBean.setPrevStatus(order.getOrderStatus());
				order.setOrderStatus("PROCESS_FAILED");
			}
			session.update(order);
			Orders order2 = (Orders) session
					.get("com.limitless.services.engage.order.dao.Orders", orderId);
			responseBean.setOrderId(orderId);
			responseBean.setCurrentStatus(order2.getOrderStatus());
			responseBean.setMessage("Success");
			responseBean.setUpdatedTime(order2.getOrderUpdatedTime().toString());
			transaction.commit();
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("updating the order status failed : " + re);
		}
		finally {
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return responseBean;
	}
	
	public OrderSummaryResponseBean sellerOrderSummary(int sellerId){
		log.debug("getting seller order summary");
		OrderSummaryResponseBean responseBean = new OrderSummaryResponseBean();
		List<OrdersListBean> ordersList = new ArrayList<OrdersListBean>();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			EngageSeller seller = (EngageSeller) session
					.get("com.limitless.services.engage.dao.EngageSeller", sellerId);
			if(seller!=null){
				String sellerName = seller.getSellerShopName();

				Criteria criteria = session.createCriteria(Orders.class);
				criteria.add(Restrictions.eq("sellerId", sellerId));
				criteria.addOrder(Order.desc("orderId"));
				List<Orders> orderList = criteria.list();
				log.debug("order size : " + orderList.size());
				if (orderList.size() > 0) {
					for (Orders order : orderList) {
						OrdersListBean listBean = new OrdersListBean();
						listBean.setOrderId(order.getOrderId());
						listBean.setCustomerId(order.getCustomerId());
						EngageCustomer customer = (EngageCustomer) session
								.get("com.limitless.services.engage.dao.EngageCustomer", order.getCustomerId());
						listBean.setCustomerName(customer.getCustomerName());
						listBean.setSellerId(sellerId);
						listBean.setSellerName(sellerName);
						listBean.setTotalAmount(order.getTotalAmount());
						listBean.setTime(order.getOrderCreatedTime().toString());
						listBean.setOrderStatus(order.getOrderStatus());
						ordersList.add(listBean);
						listBean = null;
					}
					responseBean.setOrdersList(ordersList);
					responseBean.setMessage("Success");
				} else if (orderList.isEmpty()) {
					responseBean.setMessage("No Record Found");
				}
			}
			else{
				responseBean.setMessage("Failed");
			}
			transaction.commit();
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("getting seller order summary failed : " + re);
			throw re;
		}
		finally {
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return responseBean;
	}
	
	public OrderSummaryResponseBean customerOrderSummary(int customerId){
		log.debug("getting customer order summary");
		OrderSummaryResponseBean responseBean = new OrderSummaryResponseBean();
		List<OrdersListBean> ordersList = new ArrayList<OrdersListBean>();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			EngageCustomer customer = (EngageCustomer) session
					.get("com.limitless.services.engage.dao.EngageCustomer", customerId);
			if(customer!=null){
				String customerName = customer.getCustomerName();
				
				Criteria criteria = session.createCriteria(Orders.class);
				criteria.add(Restrictions.eq("customerId", customerId));
				criteria.addOrder(Order.desc("orderId"));
				List<Orders> orderList = criteria.list();
				log.debug("orderlist size : " + orderList.size());
				if(orderList.size()>0){
					for(Orders order : orderList){
						OrdersListBean listBean = new OrdersListBean();
						listBean.setOrderId(order.getOrderId());
						listBean.setCustomerId(order.getCustomerId());
						listBean.setCustomerName(customerName);
						listBean.setSellerId(order.getSellerId());
						EngageSeller seller = (EngageSeller) session
								.get("com.limitless.services.engage.dao.EngageSeller", order.getSellerId());
						listBean.setSellerName(seller.getSellerShopName());
						listBean.setTotalAmount(order.getTotalAmount());
						listBean.setTime(order.getOrderCreatedTime().toString());
						listBean.setOrderStatus(order.getOrderStatus());
						ordersList.add(listBean);
						listBean = null;
					}
					responseBean.setOrdersList(ordersList);
					responseBean.setMessage("Success");
				}
				else if(orderList.isEmpty()){
					responseBean.setMessage("No Record Found");
				}
			}
			else{
				responseBean.setMessage("Failed");
			}
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("getting seller order summary failed : " + re);
			throw re;
		}
		finally {
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return responseBean;
	}
	
	public OrderDetailResponseBean getOrderById(int orderId){
		log.debug("getting order : " + orderId);
		OrderDetailResponseBean responseBean = new OrderDetailResponseBean();
		List<OrderProductsListBean> productsList = new ArrayList<OrderProductsListBean>();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(OrderDetails.class);
			criteria.add(Restrictions.eq("orderId", orderId));
			List<OrderDetails> detailsList = criteria.list();
			log.debug("order details size : " + detailsList.size());
			if(detailsList.size()>0){
				for(OrderDetails details : detailsList){
					OrderProductsListBean listBean = new OrderProductsListBean();
					listBean.setProductId(details.getProductId());
					Product product = (Product) session
							.get("com.limitless.services.engage.sellers.product.dao.Product", details.getProductId());
					listBean.setProductName(product.getProductName());
					listBean.setQuantity(details.getQuantity());
					listBean.setTotalPrice(details.getUnitPrice());
					productsList.add(listBean);
					listBean = null;
				}
				responseBean.setOrderId(orderId);
				responseBean.setProductsBean(productsList);
				responseBean.setMessage("Success");
			}
			else if(detailsList.isEmpty()){
				responseBean.setMessage("Failed");
			}
		}
		catch (RuntimeException re) {
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("getting order failed : " + re);
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

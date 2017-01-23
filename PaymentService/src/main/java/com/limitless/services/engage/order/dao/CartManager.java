package com.limitless.services.engage.order.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.limitless.services.engage.CustomerAddressResponseBean;
import com.limitless.services.engage.dao.CustomerAddressBook;
import com.limitless.services.engage.order.CartItemsListBean;
import com.limitless.services.engage.order.CartRequestBean;
import com.limitless.services.engage.order.CartResponseBean;
import com.limitless.services.payment.PaymentService.util.HibernateUtil;

public class CartManager {
	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	private static final Log log = LogFactory.getLog(CartManager.class);
	
	public CartResponseBean addCart(CartRequestBean requestBean){
		log.debug("adding cart");
		CartResponseBean responseBean = new CartResponseBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			Cart cart = new Cart();
			cart.setCustomerId(requestBean.getCustomerId());
			cart.setSellerId(requestBean.getSellerId());
			session.persist(cart);
			
			int cartId = cart.getCartId();
			List<CartItemsListBean> listBean = requestBean.getItemList();
			for(CartItemsListBean bean : listBean){
				CartDetails details = new CartDetails();
				details.setCartId(cartId);
				details.setProductId(bean.getProductId());
				details.setQuantity(bean.getProductQuantity());
				session.persist(details);
			}
			responseBean.setCartId(cartId);
			responseBean.setMessage("Success");
			transaction.commit();
		}
		catch (RuntimeException re) {
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("adding cart failed : " +re);
			throw re;
		}
		finally {
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return responseBean;
	}
	
	public CartResponseBean deleteItemFromCart(int itemId){
		log.debug("deleting item from the cart..");
		CartResponseBean responseBean = new CartResponseBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			CartDetails item = (CartDetails) session
					.get("com.limitless.services.engage.order.dao.CartDetails", itemId);
			if(item!=null){
				session.delete(item);
				responseBean.setMessage("Success");
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
			log.error("deleting the product failed : " +re);
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

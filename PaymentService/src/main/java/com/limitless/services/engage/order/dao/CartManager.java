package com.limitless.services.engage.order.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

import com.limitless.services.engage.CustomerAddressResponseBean;
import com.limitless.services.engage.dao.CustomerAddressBook;
import com.limitless.services.engage.dao.EngageCustomer;
import com.limitless.services.engage.dao.EngageSeller;
import com.limitless.services.engage.order.CartBean;
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
			cart.setIsDeleted(0);
			session.persist(cart);
			
			int cartId = cart.getCartId();
			List<CartItemsListBean> listBean = requestBean.getItemList();
			for(CartItemsListBean bean : listBean){
				CartDetails details = new CartDetails();
				details.setCartId(cartId);
				details.setProductId(bean.getProductId());
				details.setQuantity(bean.getProductQuantity());
				details.setProductPricesMapperId(bean.getProductPricesMapperId());
				details.setIsRemoved(0);
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
				item.setIsRemoved(1);
				session.update(item);
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
	
	public CartResponseBean removeCart(int cartId){
		log.debug("removing cart");
		CartResponseBean responseBean = new CartResponseBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			Cart cart = (Cart) session
					.get("com.limitless.services.engage.order.dao.Cart", cartId);
			if(cart!=null){
				Criteria criteria = session.createCriteria(CartDetails.class);
				criteria.add(Restrictions.eq("cartId", cartId));
				List<CartDetails> detailsList = criteria.list();
				log.debug("Cart items size : " + detailsList.size());
				if(detailsList.size()>0){
					for(CartDetails details : detailsList){
						CartDetails item = (CartDetails) session
								.get("com.limitless.services.engage.order.dao.CartDetails", details.getCartDetailsId());
						if(item!=null){
							item.setIsRemoved(1);
							session.update(item);
						}
					}
					cart.setIsDeleted(1);
					session.update(cart);
					responseBean.setMessage("Success");
					responseBean.setCartId(cartId);
				}
				else if(detailsList.isEmpty()){
					cart.setIsDeleted(0);
					session.update(cart);
					responseBean.setMessage("Success");
					responseBean.setCartId(cartId);
				}
			}
			else{
				responseBean.setMessage("Failed");
				responseBean.setCartId(cartId);
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
	
	public CartResponseBean itemToCart(int cartId, CartItemsListBean itemBean){
		log.debug("adding item to cart");
		CartResponseBean responseBean = new CartResponseBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			CartDetails details = new CartDetails();
			details.setCartId(cartId);
			details.setIsRemoved(0);
			details.setProductId(itemBean.getProductId());
			details.setQuantity(itemBean.getProductQuantity());
			
			session.persist(details);
			responseBean.setCartId(cartId);
			responseBean.setMessage("Success");
			transaction.commit();
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("adding item to cart failed : " +re);
			throw re;
		}
		finally {
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return responseBean;
	}
	
	public CartBean getCart(int cartId){
		log.debug("getting cart");
		CartBean bean = new  CartBean();
		List<CartItemsListBean> itemsList = new ArrayList<CartItemsListBean>();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			Cart cart = (Cart) session
					.get("com.limitless.services.engage.order.dao.Cart", cartId);
			if(cart!=null){
				bean.setCartId(cartId);
				bean.setCustomerId(cart.getCustomerId());
				EngageCustomer customer = (EngageCustomer) session
						.get("com.limitless.services.engage.dao.EngageCustomer", cart.getCustomerId());
				if(customer!=null){
					bean.setCustomerName(customer.getCustomerName());
					bean.setCustomerMobileNumber(customer.getCustomerMobileNumber());
				}
				bean.setSellerId(cart.getSellerId());
				EngageSeller seller = (EngageSeller) session
						.get("com.limitless.services.engage.dao.EngageSeller", cart.getSellerId());
				if(seller!=null){
					bean.setSellerShopName(seller.getSellerShopName());
					bean.setSellerMobileNumber(seller.getSellerMobileNumber());
				}
				Criteria criteria = session.createCriteria(CartDetails.class);
				Junction condition = Restrictions.conjunction().add(Restrictions.eq("cartId", cartId))
						.add(Restrictions.eq("isRemoved", 0));
				criteria.add(condition);
				List<CartDetails> detailsList = criteria.list();
				log.debug("Details size : " + detailsList.size());
				if(detailsList.size()>0){
					for(CartDetails details : detailsList){
						CartItemsListBean item = new CartItemsListBean();
						item.setCartDetailId(details.getCartDetailsId());
						item.setProductId(details.getProductId());
						item.setProductPricesMapperId(details.getProductPricesMapperId());
						item.setProductQuantity(details.getQuantity());
						itemsList.add(item);
						item = null;
					}
					bean.setCartItemsList(itemsList);
				}
				bean.setMessage("Success");
			}
			else if(cart == null){
				bean.setMessage("Failed");
			}
			transaction.commit();
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("getting cart failed : " +re);
			throw re;
		}
		finally {
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return bean;
	}
	
}

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
import com.limitless.services.engage.order.CartItemResponseBean;
import com.limitless.services.engage.order.CartItemsListBean;
import com.limitless.services.engage.order.CartListBean;
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
			cart.setIsCheckedOut(0);
			session.persist(cart);
			
			int cartId = cart.getCartId();
			List<CartItemsListBean> listBean = requestBean.getItemList();
			for(CartItemsListBean bean : listBean){
				CartDetails details = new CartDetails();
				details.setCartId(cartId);
				details.setProductId(bean.getProductId());
				details.setQuantity(bean.getProductQuantity());
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
						.add(Restrictions.eq("isRemoved", 0))
						.add(Restrictions.eq("isCheckedOut", 0));
				criteria.add(condition);
				List<CartDetails> detailsList = criteria.list();
				log.debug("Details size : " + detailsList.size());
				if(detailsList.size()>0){
					for(CartDetails details : detailsList){
						CartItemsListBean item = new CartItemsListBean();
						item.setCartDetailId(details.getCartDetailsId());
						item.setProductId(details.getProductId());
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
	
	public CartListBean getCustomerCart(int customerId){
		log.debug("getting customer cart list");
		CartListBean listBean = new CartListBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			EngageCustomer customer = (EngageCustomer) session
					.get("com.limitless.services.engage.dao.EngageCustomer", customerId);
			if(customer!=null){
				listBean.setCustomerId(customerId);
				listBean.setCustomerName(customer.getCustomerName());
				Criteria criteria = session.createCriteria(Cart.class);
				Junction condition = Restrictions.conjunction()
						.add(Restrictions.eq("customerId", customerId))
						.add(Restrictions.eq("isDeleted", 0))
						.add(Restrictions.eq("isCheckedOut", 0));
				criteria.add(condition);
				List<Cart> cartList = criteria.list();
				log.debug("cart list size : " + cartList.size());
				if(cartList.size()>0){
					List<CartBean> beanList = new ArrayList<CartBean>();
					for(Cart cart : cartList){
						CartBean bean = new CartBean();
						bean.setCustomerId(customerId);
						bean.setCustomerName(customer.getCustomerName());
						bean.setCustomerMobileNumber(customer.getCustomerMobileNumber());
						
						EngageSeller seller = (EngageSeller) session
								.get("com.limitless.services.engage.dao.EngageSeller", cart.getSellerId());
						if(seller!=null){
							bean.setSellerId(cart.getSellerId());
							bean.setSellerShopName(seller.getSellerShopName());
							bean.setSellerMobileNumber(seller.getSellerMobileNumber());
						}
						
						Criteria criteria2 = session.createCriteria(CartDetails.class);
						criteria2.add(Restrictions.eq("cartId", cart.getCartId()));
						List<CartDetails> detailsList = criteria2.list();
						log.debug("details size : " + detailsList.size());
						bean.setCartItemsCount(detailsList.size());
						
						beanList.add(bean);
						bean = null;
					}
					listBean.setCartBean(beanList);
					listBean.setMessage("Success");
				}
				else if(cartList.isEmpty()){
					listBean.setMessage("Failed");
				}
			}
			else{
				listBean.setMessage("Failed");
			}
			transaction.commit();
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("getting customer cart failed : " +re);
			throw re;
		}
		finally {
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return listBean;
	}
	
	public CartBean getCustomerSellerCart(int customerId, int sellerId){
		log.debug("getting customer and seller cart");
		CartBean bean = new CartBean();
		List<CartItemsListBean> itemsList = new ArrayList<CartItemsListBean>();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			bean.setCustomerId(customerId);
			EngageCustomer customer = (EngageCustomer) session
					.get("com.limitless.services.engage.dao.EngageCustomer", customerId);
			if (customer != null) {
				bean.setCustomerName(customer.getCustomerName());
				bean.setCustomerMobileNumber(customer.getCustomerMobileNumber());

				bean.setSellerId(sellerId);
				EngageSeller seller = (EngageSeller) session.get("com.limitless.services.engage.dao.EngageSeller",
						sellerId);
				if (seller != null) {
					bean.setSellerShopName(seller.getSellerShopName());
					bean.setSellerMobileNumber(seller.getSellerMobileNumber());

					Criteria criteria = session.createCriteria(Cart.class);
					Junction condition = Restrictions.conjunction().add(Restrictions.eq("customerId", customerId))
							.add(Restrictions.eq("sellerId", sellerId)).add(Restrictions.eq("isDeleted", 0))
							.add(Restrictions.eq("isCheckedOut", 0));
					criteria.add(condition);
					List<Cart> cartList = criteria.list();
					log.debug("cart size : " + cartList.size());
					if (cartList.size() == 1) {
						for (Cart cart : cartList) {
							bean.setCartId(cart.getCartId());
							Criteria criteria2 = session.createCriteria(CartDetails.class);
							Junction condition2 = Restrictions.conjunction()
									.add(Restrictions.eq("cartId", cart.getCartId()))
									.add(Restrictions.eq("isRemoved", 0));
							criteria2.add(condition2);
							List<CartDetails> detailsList = criteria2.list();
							log.debug("details size : " + detailsList.size());
							if(detailsList.size()>0){
								bean.setCartItemsCount(detailsList.size());
								for(CartDetails details : detailsList){
									CartItemsListBean item = new CartItemsListBean();
									item.setCartDetailId(details.getCartDetailsId());
									item.setProductId(details.getProductId());
									item.setProductQuantity(details.getQuantity());
									itemsList.add(item);
									item = null;
								}
								bean.setCartItemsList(itemsList);
							}
						}
						bean.setMessage("Success");
					} else {
						bean.setMessage("Not Found");
					}
				} else {
					bean.setMessage("Not Found");
				}
			} else {
				bean.setMessage("Not Found");
			}
			transaction.commit();
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("getting customer and seller cart failed : " +re);
			throw re;
		}
		finally {
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return bean;
	}
	
	public CartItemResponseBean cartItemUpdate(CartItemsListBean itemBean){
		log.debug("updating cart items");
		CartItemResponseBean responseBean = new CartItemResponseBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			CartDetails detail = (CartDetails) session
					.get("com.limitless.services.engage.order.dao.CartDetails", itemBean.getCartDetailId());
			if(detail!=null){
				detail.setProductId(itemBean.getProductId());
				detail.setQuantity(itemBean.getProductQuantity());
				
				session.update(detail);
				responseBean.setCartDetailsId(itemBean.getCartDetailId());
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
			log.error("updating cart items failed : " +re);
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

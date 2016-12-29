package com.limitless.services.engage.upi.dao;

import java.util.ArrayList;

// Generated Oct 24, 2016 8:29:02 PM by Hibernate Tools 3.4.0.CR1

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.limitless.services.engage.dao.EngageCustomer;
import com.limitless.services.engage.dao.EngageSeller;
import com.limitless.services.engage.upi.UpiCustomerTxnHistoryBean;
import com.limitless.services.engage.upi.UpiTxnHistoryBean;
import com.limitless.services.payment.PaymentService.util.HibernateUtil;

/**
 * Home object for domain model class UpiOrder.
 * @see com.limitless.UpiOrder
 * @author Hibernate Tools
 */
public class UpiOrderManager {

	private static final Log log = LogFactory.getLog(UpiOrderManager.class);

	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public void persist(UpiOrder transientInstance) {
		log.debug("persisting UpiOrder instance");
		Transaction tx = null;
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			tx = session.beginTransaction();
			session.persist(transientInstance);
			tx.commit();
		} catch (RuntimeException re) {
			if(tx!=null){
				tx.rollback();
			}
			log.error("persist failed", re);
			throw re;
		} finally{
			if(session != null && session.isOpen()){
				session.close();
			}
		}
	}

	public void attachDirty(UpiOrder instance) {
		log.debug("attaching dirty UpiOrder instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(UpiOrder instance) {
		log.debug("attaching clean UpiOrder instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(UpiOrder persistentInstance) {
		log.debug("deleting UpiOrder instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public UpiOrder merge(UpiOrder detachedInstance) {
		log.debug("merging UpiOrder instance");
		try {
			UpiOrder result = (UpiOrder) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public UpiOrder findById(java.lang.Integer id) {
		log.debug("getting UpiOrder instance with id: " + id);
		Transaction tx = null;
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			tx = session.beginTransaction();
			UpiOrder instance = (UpiOrder)session.get("com.limitless.services.engage.upi.dao.UpiOrder", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			tx.commit();
			return instance;
		} catch (RuntimeException re) {
			if(tx!=null){
				tx.rollback();
			}
			log.error("get failed", re);
			throw re;
		} finally{
			if(session != null && session.isOpen()){
				session.close();
			}
		}
	}

	public List findByExample(UpiOrder instance) {
		log.debug("finding UpiOrder instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("com.limitless.UpiOrder")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	public UpiOrder updateUPIOrderSuccess(int orderId, String iciciTxnNo, String iciciTxnTime, String orderStatus, String orderPaymentType) {
		Transaction transaction = null;
		Session session = null;
		try {

			// TODO

			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			UpiOrder instance = (UpiOrder) session
					.get("com.limitless.services.engage.upi.dao.UpiOrder", orderId);

			instance.setIciciTxnNo(iciciTxnNo);
			instance.setIciciTxnTime(iciciTxnTime);
			instance.setOrderStatus(orderStatus);
			instance.setOrderPaymentType(orderPaymentType);

			session.update(instance);

			UpiOrder upiOrder = (UpiOrder) session
					.get("com.limitless.services.engage.upi.dao.UpiOrder", orderId);
			
			transaction.commit();
			return upiOrder;
			
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("merge failed", re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}
	
	public UpiOrder updateUPIOrderFailure(int orderId, String orderStatus, String errorCode) {
		Transaction transaction = null;
		Session session = null;
		try {

			// TODO

			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			UpiOrder instance = (UpiOrder) session
					.get("com.limitless.services.engage.upi.dao.UpiOrder", orderId);

			instance.setOrderErrorCode(errorCode);
			instance.setOrderStatus(orderStatus);

			session.update(instance);

			UpiOrder upiOrder = (UpiOrder) session
					.get("com.limitless.services.engage.upi.dao.UpiOrder", orderId);
			
			transaction.commit();
			return upiOrder;
			
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("merge failed", re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}
	
	public UpiCustomerTxnHistoryBean getCustomerTransactions(int customerId){
		log.debug("Getting customer txns : " + customerId);
		UpiCustomerTxnHistoryBean historyBean = new UpiCustomerTxnHistoryBean();
		List<UpiTxnHistoryBean> txnBean = new ArrayList<UpiTxnHistoryBean>();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			EngageCustomer customer = (EngageCustomer) session
					.get("com.limitless.services.engage.dao.EngageCustomer", customerId);
			String customerName = "";
			if(customer!=null){
				customerName = customer.getCustomerName();
			}
			
			Criteria criteria = session.createCriteria(UpiOrder.class);
			criteria.add(Restrictions.eq("customerId", customerId));
			criteria.setMaxResults(10);
			criteria.addOrder(Order.desc("orderId"));
			List<UpiOrder> ordersList = criteria.list();
			log.debug("Orders Size : " + ordersList.size());
			if(ordersList.size()>0){
				for(UpiOrder order : ordersList){
					UpiTxnHistoryBean bean = new UpiTxnHistoryBean();
					bean.setCustomerId(customerId);
					bean.setCustomerName(customerName);
					bean.setOrderId(order.getOrderId());
					bean.setSellerId(order.getSellerId());
					EngageSeller seller = (EngageSeller) session
							.get("com.limitless.services.engage.dao.EngageSeller", order.getSellerId());
					bean.setSellerName(seller.getSellerShopName());
					bean.setOrderAmount(order.getOrderAmount());
					bean.setTxnNotes(order.getTxnNotes());
					bean.setIciciTxnNo(order.getIciciTxnNo());
					bean.setIciciTxnTime(order.getIciciTxnTime());
					bean.setOrderStatus(order.getOrderStatus());
					bean.setPaymentType(order.getOrderPaymentType());
					bean.setOrderTime(order.getOrderCreatedTime().toString());
					txnBean.add(bean);
					bean = null;
				}
				historyBean.setMessage("Success");
				historyBean.setTxnList(txnBean);
			}
			else if(ordersList.isEmpty()){
				historyBean.setMessage("No Record Found");
			}
			transaction.commit();
		}
		catch(RuntimeException re){
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("getting txn history failed", re);
			throw re;
		}
		finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return historyBean;
	}
	
	public UpiCustomerTxnHistoryBean getCustomerTransactionsPagination(int customerId, int firstTxnId){
		log.debug("Getting customer txns : " + customerId + "Last Txn Id : " + firstTxnId );
		UpiCustomerTxnHistoryBean historyBean = new UpiCustomerTxnHistoryBean();
		List<UpiTxnHistoryBean> txnBean = new ArrayList<UpiTxnHistoryBean>();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			EngageCustomer customer = (EngageCustomer) session
					.get("com.limitless.services.engage.dao.EngageCustomer", customerId);
			String customerName = "";
			if(customer!=null){
				customerName = customer.getCustomerName();
			}
			
			Criteria criteria = session.createCriteria(UpiOrder.class);
			Criterion cIdCriterion = Restrictions.eq("customerId", customerId);
			Criterion txnIdCriterion = Restrictions.lt("orderId", firstTxnId);
			LogicalExpression logExp = Restrictions.and(cIdCriterion, txnIdCriterion);
			criteria.add(logExp);
			criteria.addOrder(Order.desc("orderId"));
			criteria.setMaxResults(10);
			List<UpiOrder> ordersList = criteria.list();
			log.debug("Orders Size : " + ordersList.size());
			if(ordersList.size()>0){
				for(UpiOrder order : ordersList){
					UpiTxnHistoryBean bean = new UpiTxnHistoryBean();
					bean.setCustomerId(customerId);
					bean.setCustomerName(customerName);
					bean.setOrderId(order.getOrderId());
					bean.setSellerId(order.getSellerId());
					EngageSeller seller = (EngageSeller) session
							.get("com.limitless.services.engage.dao.EngageSeller", order.getSellerId());
					bean.setSellerName(seller.getSellerShopName());
					bean.setOrderAmount(order.getOrderAmount());
					bean.setTxnNotes(order.getTxnNotes());
					bean.setIciciTxnNo(order.getIciciTxnNo());
					bean.setIciciTxnTime(order.getIciciTxnTime());
					bean.setOrderStatus(order.getOrderStatus());
					bean.setPaymentType(order.getOrderPaymentType());
					bean.setOrderTime(order.getOrderCreatedTime().toString());
					txnBean.add(bean);
					bean = null;
				}
				historyBean.setMessage("Success");
				historyBean.setTxnList(txnBean);
			}
			else if(ordersList.isEmpty()){
				historyBean.setMessage("No Record Found");
			}
			transaction.commit();
		}
		catch(RuntimeException re){
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("getting txn history failed", re);
			throw re;
		}
		finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return historyBean;
	}
}

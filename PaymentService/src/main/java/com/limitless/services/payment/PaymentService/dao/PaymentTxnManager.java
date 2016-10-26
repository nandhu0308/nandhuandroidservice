package com.limitless.services.payment.PaymentService.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/*
 * @author veejay.developer@gmail.com
 * ©www.limitlesscircle.com 
 */

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.limitless.services.engage.dao.EngageCustomer;
import com.limitless.services.engage.dao.EngageCustomerManager;
import com.limitless.services.payment.PaymentService.PaymentTxnBean.TxnStatus;
import com.limitless.services.payment.PaymentService.SellerTxnHistoryBean;
import com.limitless.services.payment.PaymentService.TxnHistoryBean;
import com.limitless.services.payment.PaymentService.util.HibernateUtil;

/**
 * Home object for domain model class PaymentTxn.
 * @see com.limitless.services.payment.PaymentService.dao.PaymentTxn
 * @author Hibernate Tools
 */
public class PaymentTxnManager {

	private static final Log log = LogFactory.getLog(PaymentTxnManager.class);

	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	/*protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext()
					.lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}*/

	public void persist(PaymentTxn transientInstance) {
		log.debug("persisting PaymentTxn instance");
		Transaction tx = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			tx = session.beginTransaction();
			session.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		} finally{
			tx.commit();
		}
	}

/*	public void attachDirty(PaymentTxn instance) {
		log.debug("attaching dirty PaymentTxn instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(PaymentTxn instance) {
		log.debug("attaching clean PaymentTxn instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}*/

	public void delete(PaymentTxn persistentInstance) {
		log.debug("deleting PaymentTxn instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public PaymentTxn merge(PaymentTxn detachedInstance) {
		log.debug("merging PaymentTxn instance");
		try {
			PaymentTxn result = (PaymentTxn) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}
	
	public PaymentTxn updateSplitId(int txnId, int citrusMpTxnId, int splitId, String txnStatus) {
		Transaction tx = null;
		try {
			
			//TODO
			
			Session session = sessionFactory.getCurrentSession();
			tx = session.beginTransaction();
			PaymentTxn instance = (PaymentTxn)session.get("com.limitless.services.payment.PaymentService.dao.PaymentTxn",
					txnId);
			
			instance.setCitrusMpTxnId(citrusMpTxnId);
			instance.setSplitId(splitId);
			instance.setTxnStatus(txnStatus);
			
			session.update(instance);
			
			PaymentTxn paymentTxn = (PaymentTxn)session.get("com.limitless.services.payment.PaymentService.dao.PaymentTxn",
					txnId);
			
			return paymentTxn;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		} finally {
			tx.commit();
		}
	}
	
	public PaymentTxn updateTxn(int txnId, String txnStatus) {
		Transaction tx = null;
		try {
			
			//TODO
			
			Session session = sessionFactory.getCurrentSession();
			tx = session.beginTransaction();
			PaymentTxn instance = (PaymentTxn)session.get("com.limitless.services.payment.PaymentService.dao.PaymentTxn",
					txnId);
			
			instance.setTxnStatus(txnStatus);
			
			session.update(instance);
			
			PaymentTxn paymentTxn = (PaymentTxn)session.get("com.limitless.services.payment.PaymentService.dao.PaymentTxn",
					txnId);
			
			return paymentTxn;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		} finally {
			tx.commit();
		}
	}

	public PaymentTxn findById(int id) {
		log.debug("getting PaymentTxn instance with id: " + id);
		Transaction tx = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			tx = session.beginTransaction();
			PaymentTxn instance = (PaymentTxn)session.get("com.limitless.services.payment.PaymentService.dao.PaymentTxn",
					id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		} finally{
			tx.commit();
		}
	}

	public List findByExample(PaymentTxn instance) {
		log.debug("finding PaymentTxn instance by example");
		try {
			List results = sessionFactory
					.getCurrentSession()
					.createCriteria(
							"com.limitless.services.payment.PaymentService.dao.PaymentTxn")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	public List<PaymentTxn> getTxnHistory(int customerId){
		log.debug("Getting History");
		Transaction tx = null;
		try{
			Session session = sessionFactory.getCurrentSession();
			tx = session.beginTransaction();
			Query query = session.createQuery("from PaymentTxn where engageCustomerId = :customerId order by txnId desc");
			query.setParameter("customerId", customerId);
			query.setMaxResults(10);
			List<PaymentTxn> paymentHistory = query.list();
			return paymentHistory;
		}
		catch(RuntimeException re){
			log.error("Getting History Failed", re);
			throw re;
		} finally{
			tx.commit();
		}
	}
	
	public List<PaymentTxn> getTxnHistoryPagination(int customerId, int firstTxnId){
		log.debug("Getting History");
		Transaction tx = null;
		try{
			Session session = sessionFactory.getCurrentSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(PaymentTxn.class);
			Criterion custIdCriterion = Restrictions.eq("engageCustomerId", customerId);
			Criterion txnIdCriterion = Restrictions.lt("txnId", firstTxnId);
			LogicalExpression logicalExpression = Restrictions.and(custIdCriterion, txnIdCriterion);
			criteria.add(logicalExpression);
			criteria.addOrder(Order.desc("txnId"));
			criteria.setMaxResults(10);
			List<PaymentTxn> paymentTxnsList = criteria.list();
			return paymentTxnsList;
		}
		catch(RuntimeException re){
			log.error("Getting History Failed", re);
			throw re;
		}
		finally{
			tx.commit();
		}
	}
	
	public double getCreditAmount(int txnId){
		log.debug("getting credit amount");
		Transaction transaction = null;
		double creditAmount = 0;
		try{
			Session session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(PaymentCredit.class);
			criteria.add(Restrictions.eq("txnId", txnId));
			List<PaymentCredit> creditsList = criteria.list();
			if(creditsList!=null && creditsList.size()>0){
				for(PaymentCredit credit : creditsList){
					creditAmount = credit.getCreditAmount();
				}
			}
		}
		catch(RuntimeException re){
			log.error("Getting credit amount Failed", re);
			throw re;
		}
		finally{
			transaction.commit();
		}
		return creditAmount;
	}
	
	public SellerTxnHistoryBean getSellerTxns(int citrusSellerId) throws Exception{
		log.debug("Getting Seller Txn History");
		Transaction transaction = null;
		SellerTxnHistoryBean sthBean = new SellerTxnHistoryBean();
		try{
			Session session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(PaymentTxn.class);
			criteria.add(Restrictions.eq("citrusSellerId", citrusSellerId));
			criteria.addOrder(Order.desc("txnUpdatedTime"));
			criteria.setMaxResults(10);
			List<PaymentTxn> paymentList = criteria.list();
			if(paymentList.size() > 0){
				log.debug("Size: "+paymentList.size());
				sthBean.setMessage("Success");
				List<TxnHistoryBean> historyBeanList = new ArrayList<TxnHistoryBean>();
				for(PaymentTxn payment : paymentList){
					TxnHistoryBean bean = new TxnHistoryBean();
					bean.setTxnId(payment.getTxnId());
					int txnId = payment.getTxnId();
					Criteria criteria2 = session.createCriteria(PaymentCredit.class);
					criteria2.add(Restrictions.eq("txnId", txnId));
					List<PaymentCredit> creditList = criteria2.list();
					if(creditList != null || creditList.size() == 0){
						for(PaymentCredit credit : creditList){
							bean.setCreditAmount(credit.getCreditAmount());
						}
					}
					bean.setCustomerId(payment.getEngageCustomerId());
					int customerId = payment.getEngageCustomerId();
					EngageCustomer customer = (EngageCustomer) session.get("com.limitless.services.engage.dao.EngageCustomer", customerId);
					bean.setCustomerName(customer.getCustomerName());
					bean.setSellerId(payment.getSellerId());
					bean.setSellerName(payment.getSellerName());
					bean.setTxtAmount(payment.getTxnAmount());
					bean.setCitrusMpTxnId(payment.getCitrusMpTxnId());
					bean.setSplitId(payment.getSplitId());
					bean.setTxtStatus(payment.getTxnStatus());
					String gmtTime = payment.getTxnUpdatedTime().toString();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date = sdf.parse(gmtTime);
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					calendar.add(Calendar.HOUR, 5);
					calendar.add(Calendar.MINUTE, 30);
					String localTime = sdf.format(calendar.getTime());
					bean.setTxnTime(localTime);
					historyBeanList.add(bean);
					bean = null;
				}
				sthBean.setHistoryBean(historyBeanList);
			}
			else{
				log.debug("Size: "+paymentList.size());
				sthBean.setMessage("No Record Found");				
			}
		}
		catch(RuntimeException re){
			log.error("Getting History Failed", re);
			throw re;
		}
		finally{
			transaction.commit();
		}
		return sthBean;
	}
	
	public SellerTxnHistoryBean sellerTxnHistoryPagination(int citrusSellerId, int firstTxnId) throws Exception{
		log.debug("Getting Seller Txn History");
		Transaction transaction = null;
		SellerTxnHistoryBean sthBean = new SellerTxnHistoryBean();
		try{
			Session session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(PaymentTxn.class);
			Criterion txnIdCriterion = Restrictions.lt("txnId", firstTxnId);
			Criterion csidCriterion = Restrictions.eq("citrusSellerId", citrusSellerId);
			LogicalExpression logicalExpression = Restrictions.and(txnIdCriterion, csidCriterion);
			criteria.add(logicalExpression);
			criteria.addOrder(Order.desc("txnUpdatedTime"));
			criteria.setMaxResults(10);
			List<PaymentTxn> paymentList = criteria.list();
			if(paymentList.size() > 0){
				log.debug("Size: "+paymentList.size());
				sthBean.setMessage("Success");
				List<TxnHistoryBean> historyBeanList = new ArrayList<TxnHistoryBean>();
				for(PaymentTxn payment : paymentList){
					TxnHistoryBean bean = new TxnHistoryBean();
					bean.setTxnId(payment.getTxnId());
					int txnId = payment.getTxnId();
					Criteria criteria2 = session.createCriteria(PaymentCredit.class);
					criteria2.add(Restrictions.eq("txnId", txnId));
					List<PaymentCredit> creditList = criteria2.list();
					if(creditList != null || creditList.size() == 0){
						for(PaymentCredit credit : creditList){
							bean.setCreditAmount(credit.getCreditAmount());
						}
					}
					bean.setCustomerId(payment.getEngageCustomerId());
					int customerId = payment.getEngageCustomerId();
					EngageCustomer customer = (EngageCustomer) session.get("com.limitless.services.engage.dao.EngageCustomer", customerId);
					bean.setCustomerName(customer.getCustomerName());
					bean.setSellerId(payment.getSellerId());
					bean.setSellerName(payment.getSellerName());
					bean.setTxtAmount(payment.getTxnAmount());
					bean.setCitrusMpTxnId(payment.getCitrusMpTxnId());
					bean.setSplitId(payment.getSplitId());
					bean.setTxtStatus(payment.getTxnStatus());
					String gmtTime = payment.getTxnUpdatedTime().toString();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date = sdf.parse(gmtTime);
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					calendar.add(Calendar.HOUR, 5);
					calendar.add(Calendar.MINUTE, 30);
					String localTime = sdf.format(calendar.getTime());
					bean.setTxnTime(localTime);
					historyBeanList.add(bean);
					bean = null;
				}
				sthBean.setHistoryBean(historyBeanList);
			}
			else{
				log.debug("Size: "+paymentList.size());
				sthBean.setMessage("No Record Found");				
			}
		}
		catch(RuntimeException re){
			log.error("Getting History Failed", re);
			throw re;
		}
		finally{
			transaction.commit();
		}
		return sthBean;
	}
	
	public static void main(String[] args) {
		PaymentTxnManager manager = new PaymentTxnManager();
		//manager.updateSplitId(946706, 28698, 31747, TxnStatus.PAYMENT_SUCCESSFUL.toString());
		manager.findById(946707);
	}
}

package com.limitless.services.engage.dao;

// Generated Sep 25, 2016 10:49:31 PM by Hibernate Tools 3.4.0.CR1

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;

import com.limitless.services.engage.LoginResponseBean;
import com.limitless.services.engage.PasswdResponseBean;
import com.limitless.services.payment.PaymentService.util.HibernateUtil;

/**
 * Home object for domain model class EngageCustomer.
 * @see com.limitless.services.payment.PaymentService.dao.EngageCustomer
 * @author Hibernate Tools
 */
public class EngageCustomerManager {

	private static final Log log = LogFactory.getLog(EngageCustomerManager.class);

	/*private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext()
					.lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}*/
	
	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public void persist(EngageCustomer transientInstance) {
		log.debug("persisting EngageCustomer instance");
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

	public void attachDirty(EngageCustomer instance) {
		log.debug("attaching dirty EngageCustomer instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(EngageCustomer instance) {
		log.debug("attaching clean EngageCustomer instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(EngageCustomer persistentInstance) {
		log.debug("deleting EngageCustomer instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public EngageCustomer merge(EngageCustomer detachedInstance) {
		log.debug("merging EngageCustomer instance");
		try {
			EngageCustomer result = (EngageCustomer) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public EngageCustomer findById(java.lang.Integer id) {
		log.debug("getting EngageCustomer instance with id: " + id);
		Transaction tx = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			tx = session.beginTransaction();
			EngageCustomer instance = (EngageCustomer) session
					.get("com.limitless.services.engage.dao.EngageCustomer",
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
	
	public PasswdResponseBean changePassword(int customerId, String oldPassword, String newPassword){
		log.debug("Changing password for user");
		PasswdResponseBean bean = new PasswdResponseBean();
		Transaction transaction = null;
		try{
			Session session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			EngageCustomer instance = (EngageCustomer) session
					.get("com.limitless.services.engage.dao.EngageCustomer", customerId);
			if(instance != null){
				instance.setCustomerPasswd99(newPassword);
				session.update(instance);
				EngageCustomer customer = (EngageCustomer) session
						.get("com.limitless.services.engage.dao.EngageCustomer", customerId);
				bean.setCustomerId(customerId);
				bean.setPasswd(customer.getCustomerPasswd99());
				bean.setMessage("Success");
			}
			else{
				bean.setMessage("Failed");
				bean.setCustomerId(customerId);
			}
		}
		catch(RuntimeException re){
			log.error("Changing password failed");
			throw re;
		}
		finally{
			transaction.commit();
		}
		return bean;
	}
	
	public boolean checkDuplicateEmail(String customerEmail){
		 log.debug("Checking Email whether already exist");
		 Transaction transaction = null;
		 try{
			 boolean emailExist = false;
			 Session session = sessionFactory.getCurrentSession();
			 transaction = session.beginTransaction();
			 Query query = session.createQuery("from EngageCustomer where customerEmail99 = :customerEmail");
			 query.setParameter("customerEmail", customerEmail);
			 List<EngageCustomer> customer = query.list();
			 if(customer != null && customer.size() > 0){
				 emailExist = true;
			 }
			 return emailExist;
		 }
		 catch(RuntimeException re){
			 log.error("Checking Email failed");
			 throw re;
		 }
		 finally{
			 transaction.commit();
		 }
	}
	
	public boolean checkDuplicateMobile(String customerMobile){
		log.debug("Checking Mobile whether already exist");
		Transaction transaction = null;
		try{
			boolean mobileExist = false;
			Session session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			Query query = session.createQuery("from EngageCustomer where customerMobileNumber = :customerMobile");
			query.setParameter("customerMobile", customerMobile);
			List<EngageCustomer> customer = query.list();
			if(customer != null && customer.size() > 0){
				mobileExist = true;
			}
			return mobileExist;
		}
		catch(RuntimeException re){
			log.error("Checking mobile failed");
			throw re;
		}
		finally{
			 transaction.commit();
		 }
	}

	public List findByExample(EngageCustomer instance) {
		log.debug("finding EngageCustomer instance by example");
		try {
			List results = sessionFactory
					.getCurrentSession()
					.createCriteria(
							"com.limitless.services.payment.PaymentService.dao.EngageCustomer")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	public LoginResponseBean validateUser(String customerEmail99, String passwd){
		log.debug("checking login credentials");
		LoginResponseBean loginResponseBean = new LoginResponseBean();
		Transaction transaction = null;
		try{
			Session session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(EngageCustomer.class);
			Criterion emailCriterion = Restrictions.eq("customerEmail99", customerEmail99);
			Criterion passwdCriterion = Restrictions.eq("customerPasswd99", passwd);
			LogicalExpression logicalExp = Restrictions.and(emailCriterion, passwdCriterion);
			criteria.add(logicalExp);
			List<EngageCustomer> userList = criteria.list();
			if(userList.size() > 0 && userList.size() == 1){
				log.debug("Size: " +userList.size());
				for(EngageCustomer user : userList){
					loginResponseBean.setLoginStatus(1);
					loginResponseBean.setMessage("Success");
					loginResponseBean.setCustomerId(user.getCustomerId());
					loginResponseBean.setCustomerName(user.getCustomerName());
					loginResponseBean.setCustomerMobileNumber(user.getCustomerMobileNumber());
				}
			}
			else{
				loginResponseBean.setLoginStatus(-1);
			}
		}
		catch(RuntimeException re){
			log.error("checking login credentials failed");
			throw re;
		}
		finally{
			transaction.commit();
		}
		return loginResponseBean;
	}
	
	public boolean validateCredentials(int customerId, String passwd) {
		log.debug("getting EngageCustomer instance with id: " + customerId);
		boolean isValidCredentials = false;
		Transaction tx = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			tx = session.beginTransaction();
			EngageCustomer instance = (EngageCustomer)session.get("com.limitless.services.engage.dao.EngageCustomer",
					customerId);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
				if(instance.getCustomerPasswd99().equals(passwd)){
					isValidCredentials = true;
				}
			}
			return isValidCredentials;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		} finally{
			tx.commit();
		}
	}
}

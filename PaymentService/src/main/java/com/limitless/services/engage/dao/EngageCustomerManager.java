package com.limitless.services.engage.dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

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

import com.limitless.services.engage.InviteRequestBean;
import com.limitless.services.engage.InviteResponseBean;
import com.limitless.services.engage.LoginResponseBean;
import com.limitless.services.engage.MobileResponseBean;
import com.limitless.services.engage.PasswdResponseBean;
import com.limitless.services.engage.ProfileChangeRequestBean;
import com.limitless.services.engage.ProfileChangeResponseBean;
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
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			tx = session.beginTransaction();
			session.persist(transientInstance);
			log.debug("persist successful");
			tx.commit();
		} catch (RuntimeException re) {
			if(tx!=null){
				tx.rollback();
			}
			log.error("persist failed", re);
			throw re;
		} finally{
			if(session!=null){
				session.close();
			}
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
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			tx = session.beginTransaction();
			EngageCustomer instance = (EngageCustomer) session
					.get("com.limitless.services.engage.dao.EngageCustomer",
							id);
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
			if(session!=null){
				session.close();
			}
		}
	}
	
	public PasswdResponseBean changePassword(int customerId, String oldPassword, String newPassword){
		log.debug("Changing password for user");
		PasswdResponseBean bean = new PasswdResponseBean();
		Transaction transaction = null;
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
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
			transaction.commit();
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("Changing password failed");
			throw re;
		}
		finally{
			if(session!=null){
				session.close();
			}
		}
		return bean;
	}
	
	public boolean checkDuplicateEmail(String customerEmail){
		 log.debug("Checking Email whether already exist");
		 Transaction transaction = null;
		 Session session = null;
		 try{
			 boolean emailExist = false;
			 System.out.println("Email--->"+customerEmail);
			 session = sessionFactory.getCurrentSession();
			 transaction = session.beginTransaction();
			 if(!customerEmail.equals("")){
				 Query query = session.createQuery("from EngageCustomer where customerEmail99 = :customerEmail");
				 query.setParameter("customerEmail", customerEmail);
				 List<EngageCustomer> customer = query.list();
				 log.debug("User:-->"+customer);
				 if(customer != null && customer.size() > 0){
					 emailExist = true;
				 }
			 }
			 transaction.commit();
			 return emailExist;
		 }
		 catch(RuntimeException re){
			 if(transaction!=null){
					transaction.rollback();
				}
			 log.error("Checking Email failed");
			 throw re;
		 }
		 finally{
			 if(session!=null){
					session.close();
				}
		 }
	}
	
	public boolean checkDuplicateMobile(String customerMobile){
		log.debug("Checking Mobile whether already exist");
		Transaction transaction = null;
		Session session = null;
		try{
			boolean mobileExist = false;
			System.out.println("Mobile--->"+customerMobile);
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			Query query = session.createQuery("from EngageCustomer where customerMobileNumber = :customerMobile");
			query.setParameter("customerMobile", customerMobile);
			List<EngageCustomer> customer = query.list();
			log.debug("User:-->"+customer);
			if(customer != null && customer.size() > 0){
				mobileExist = true;
			}
			transaction.commit();
			return mobileExist;
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("Checking mobile failed");
			throw re;
		}
		finally{
			if(session!=null){
				session.close();
			}
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
	
	public LoginResponseBean validateUser(String customerMobile, String passwd){
		log.debug("checking login credentials");
		LoginResponseBean loginResponseBean = new LoginResponseBean();
		Transaction transaction = null;
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(EngageCustomer.class);
			Criterion emailCriterion = Restrictions.eq("customerMobileNumber", customerMobile);
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
					loginResponseBean.setCustomerEmail(user.getCustomerEmail99());;
				}
			}
			else{
				loginResponseBean.setLoginStatus(-1);
			}
			transaction.commit();
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("checking login credentials failed");
			throw re;
		}
		finally{
			if(session!=null){
				session.close();
			}
		}
		return loginResponseBean;
	}
	
	public boolean validateCredentials(int customerId, String passwd) {
		log.debug("getting EngageCustomer instance with id: " + customerId);
		boolean isValidCredentials = false;
		Transaction tx = null;
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
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
			tx.commit();
			return isValidCredentials;
		} catch (RuntimeException re) {
			if(tx!=null){
				tx.rollback();
			}
			log.error("get failed", re);
			throw re;
		} finally{
			if(session!=null){
				session.close();
			}
		}
	}
	
	public ProfileChangeResponseBean updateCustomerProfile(ProfileChangeRequestBean requestBean){
		log.debug("Changing/updating customer details");
		ProfileChangeResponseBean responseBean = new ProfileChangeResponseBean();
		Transaction transaction = null;
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			int customerId = requestBean.getCustomerId();
			String key = requestBean.getCustomerKey();
			String value = requestBean.getCustomerValue();
			
			EngageCustomer instance = (EngageCustomer)session.get("com.limitless.services.engage.dao.EngageCustomer",
					customerId);
			
			if(instance!=null){
				if(key.equals("name")){
					String customerName = value;
					instance.setCustomerName(customerName);
					session.update(instance);
				}
				else if(key.equals("mobile")){
					String customerMobile = value;
					instance.setCustomerMobileNumber(customerMobile);
					session.update(instance);
				}
				else if(key.equals("email")){
					String customerEmailId = value;
					instance.setCustomerEmail99(customerEmailId);
					session.update(instance);
				}
				responseBean.setMessage("Success");
				responseBean.setCustomerId(customerId);
			}
			else{
				responseBean.setMessage("Failed");
				responseBean.setCustomerId(customerId);
			}
			transaction.commit();
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("Changing/updating customer details failed", re);
			throw re;
		}
		finally{
			if(session!=null){
				session.close();
			}
		}
		return responseBean;
	}
	
	public MobileResponseBean getCustomerMobileNumber(String customerMobile){
		log.debug("getting customer mobile");
		MobileResponseBean responseBean = new MobileResponseBean();
		Transaction transaction = null;
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(EngageCustomer.class);
			criteria.add(Restrictions.eq("customerMobileNumber", customerMobile));
			List<EngageCustomer> customerList = criteria.list();
			if(customerList!=null && customerList.size()>0){
				for(EngageCustomer customer : customerList){
					responseBean.setCustomerMobile(customer.getCustomerMobileNumber());
					responseBean.setCustomerId(customer.getCustomerId());
					responseBean.setCustomerEmail(customer.getCustomerEmail99());
				}
				responseBean.setMessage("Success");
			}
			else{
				responseBean.setMessage("Mobile Not Found");
			}
			transaction.commit();
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("Changing/updating customer details failed", re);
			throw re;
		}
		finally{
			if(session!=null){
				session.close();
			}
		}
		return responseBean;
	}
	
	public InviteResponseBean sendInvite(InviteRequestBean requestBean) throws Exception{
		log.debug("Sending invite");
		InviteResponseBean responseBean = new InviteResponseBean();
		Transaction transaction = null;
		Session session = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			String senderName = "";
			if(requestBean.getKey().equals("merchant")){
				EngageSeller seller = (EngageSeller) session.get("com.limitless.services.engage.dao.EngageSeller", requestBean.getSellerId());
				senderName = seller.getSellerName();
			}
			else if(requestBean.getKey().equals("customer")){
				EngageCustomer customer = (EngageCustomer) session.get("com.limitless.services.engage.dao.EngageCustomer", requestBean.getCustomerId());
				senderName = customer.getCustomerName();
			}
			String message = "LETS GO CASHLESS! "+senderName + " has invited you to join LimitlessCircle. Download the app: goo.gl/ejZrmv";
			String encoded_message = URLEncoder.encode(message);
			String authkey = "129194Aa6NwGoQsVt580d9a57";
			String mobiles = requestBean.getMobileNumbers();
			String senderId = "LLCINV";
			String route = "4";
			String mainUrl="http://api.msg91.com/api/sendhttp.php?";
			StringBuilder sbPostData= new StringBuilder(mainUrl);
            sbPostData.append("authkey="+authkey);
            sbPostData.append("&mobiles="+mobiles);
            sbPostData.append("&message="+encoded_message);
            sbPostData.append("&route="+route);
            sbPostData.append("&sender="+senderId);
            mainUrl = sbPostData.toString();
            URL msgUrl = new URL(mainUrl);
            URLConnection con = msgUrl.openConnection();
            con.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String response = "";
            while((response = reader.readLine())!=null){
            	System.out.println(response);
            }
            responseBean.setCustomerId(requestBean.getCustomerId());
            responseBean.setMerchantId(requestBean.getSellerId());
            responseBean.setMessage("Success");
            responseBean.setResponse(response);
            reader.close();
            transaction.commit();
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("Changing/updating customer details failed", re);
			throw re;
		}
		finally {
			if(session!=null){
				session.close();
			}
		}
		return responseBean;
	}
	
}

package com.limitless.services.engage.dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

// Generated Sep 25, 2016 10:49:31 PM by Hibernate Tools 3.4.0.CR1

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
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
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.json.JSONArray;
import org.json.JSONObject;

import com.limitless.services.engage.AddAccountRequestBean;
import com.limitless.services.engage.AddAccountResponseBean;
import com.limitless.services.engage.CitrusRegistrationRequestBean;
import com.limitless.services.engage.CitrusRegistrationResponseBean;
import com.limitless.services.engage.CustomerDataBean;
import com.limitless.services.engage.CustomerDeviceIdRequestBean;
import com.limitless.services.engage.CustomerDeviceIdResponseBean;
import com.limitless.services.engage.CustomerFcmRequestBean;
import com.limitless.services.engage.CustomerLogoutRequestBean;
import com.limitless.services.engage.CustomerLogoutResponseBean;
import com.limitless.services.engage.CustomerNotificationBean;
import com.limitless.services.engage.CustomerNotifyRequestBean;
import com.limitless.services.engage.CustomerNotifyResponseBean;
import com.limitless.services.engage.Guest2CustomerLoginRequestBean;
import com.limitless.services.engage.GuestLoginRequestBean;
import com.limitless.services.engage.GuestLoginResponseBean;
import com.limitless.services.engage.InviteRequestBean;
import com.limitless.services.engage.InviteResponseBean;
import com.limitless.services.engage.LoginResponseBean;
import com.limitless.services.engage.MobileResponseBean;
import com.limitless.services.engage.P2PCustomerVerificationRequestBean;
import com.limitless.services.engage.P2PCustomerVerificationResponseBean;
import com.limitless.services.engage.PasswdResponseBean;
import com.limitless.services.engage.ProfileChangeRequestBean;
import com.limitless.services.engage.ProfileChangeResponseBean;
import com.limitless.services.engage.order.dao.Cart;
import com.limitless.services.engage.sellers.CitrusSellerResponseBean;
import com.limitless.services.engage.sellers.dao.CitrusSeller;
import com.limitless.services.engage.sellers.dao.CitrusSellerManager;
import com.limitless.services.engage.sellers.dao.SellerVersion;
import com.limitless.services.payment.PaymentService.dao.CitrusAuthToken;
import com.limitless.services.payment.PaymentService.resources.CitrusSellerResource;
import com.limitless.services.payment.PaymentService.util.HibernateUtil;
import com.limitless.services.payment.PaymentService.util.RestClientUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * Home object for domain model class EngageCustomer.
 * 
 * @see com.limitless.services.payment.PaymentService.dao.EngageCustomer
 * @author Hibernate Tools
 */
public class EngageCustomerManager {

	private static final Log log = LogFactory.getLog(EngageCustomerManager.class);
	Client client = RestClientUtil.createClient();

	/*
	 * private final SessionFactory sessionFactory = getSessionFactory();
	 * 
	 * protected SessionFactory getSessionFactory() { try { return
	 * (SessionFactory) new InitialContext() .lookup("SessionFactory"); } catch
	 * (Exception e) { log.error("Could not locate SessionFactory in JNDI", e);
	 * throw new IllegalStateException(
	 * "Could not locate SessionFactory in JNDI"); } }
	 */

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
			if (tx != null) {
				tx.rollback();
			}
			log.error("persist failed", re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
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
			EngageCustomer result = (EngageCustomer) sessionFactory.getCurrentSession().merge(detachedInstance);
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
			EngageCustomer instance = (EngageCustomer) session.get("com.limitless.services.engage.dao.EngageCustomer",
					id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			tx.commit();
			return instance;
		} catch (RuntimeException re) {
			if (tx != null) {
				tx.rollback();
			}
			log.error("get failed", re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	public PasswdResponseBean changePassword(int customerId, String oldPassword, String newPassword) {
		log.debug("Changing password for user");
		PasswdResponseBean bean = new PasswdResponseBean();
		Transaction transaction = null;
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			EngageCustomer instance = (EngageCustomer) session.get("com.limitless.services.engage.dao.EngageCustomer",
					customerId);
			if (instance != null) {
				instance.setCustomerPasswd99(newPassword);
				session.update(instance);
				EngageCustomer customer = (EngageCustomer) session
						.get("com.limitless.services.engage.dao.EngageCustomer", customerId);
				bean.setCustomerId(customerId);
				bean.setPasswd(customer.getCustomerPasswd99());
				bean.setMessage("Success");
			} else {
				bean.setMessage("Failed");
				bean.setCustomerId(customerId);
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Changing password failed");
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return bean;
	}

	public boolean checkDuplicateEmail(String customerEmail) {
		log.debug("Checking Email whether already exist");
		Transaction transaction = null;
		Session session = null;
		try {
			boolean emailExist = false;
			System.out.println("Email--->" + customerEmail);
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			if (!customerEmail.equals("")) {
				Query query = session.createQuery("from EngageCustomer where customerEmail99 = :customerEmail");
				query.setParameter("customerEmail", customerEmail);
				List<EngageCustomer> customer = query.list();
				log.debug("User:-->" + customer);
				if (customer != null && customer.size() > 0) {
					emailExist = true;
				}
			}
			transaction.commit();
			return emailExist;
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Checking Email failed");
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	public boolean checkDuplicateMobile(String customerMobile) {
		log.debug("Checking Mobile whether already exist");
		Transaction transaction = null;
		Session session = null;
		try {
			boolean mobileExist = false;
			System.out.println("Mobile--->" + customerMobile);
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			Query query = session.createQuery("from EngageCustomer where customerMobileNumber = :customerMobile");
			query.setParameter("customerMobile", customerMobile);
			List<EngageCustomer> customer = query.list();
			log.debug("User:-->" + customer);
			if (customer != null && customer.size() > 0) {
				mobileExist = true;
			}
			transaction.commit();
			return mobileExist;
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Checking mobile failed");
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	public List findByExample(EngageCustomer instance) {
		log.debug("finding EngageCustomer instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("com.limitless.services.payment.PaymentService.dao.EngageCustomer")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public LoginResponseBean validateUser(String customerMobile, String passwd) throws Exception {
		log.debug("checking login credentials");
		LoginResponseBean loginResponseBean = new LoginResponseBean();
		Transaction transaction = null;
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(EngageCustomer.class);
			Criterion emailCriterion = Restrictions.eq("customerMobileNumber", customerMobile);
			Criterion passwdCriterion = Restrictions.eq("customerPasswd99", passwd);
			LogicalExpression logicalExp = Restrictions.and(emailCriterion, passwdCriterion);
			criteria.add(logicalExp);
			List<EngageCustomer> userList = criteria.list();
			if (userList.size() > 0 && userList.size() == 1) {
				log.debug("Size: " + userList.size());
				for (EngageCustomer user : userList) {
					loginResponseBean.setLoginStatus(1);
					loginResponseBean.setMessage("Success");
					loginResponseBean.setCustomerId(user.getCustomerId());
					loginResponseBean.setCustomerName(user.getCustomerName());
					loginResponseBean.setCustomerEmail(user.getCustomerEmail99());
					loginResponseBean.setDeviceId(user.getDeviceId());
					
					SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(new Date());
					calendar.add(Calendar.MINUTE, 5);
					String validDate = sdf1.format(calendar.getTime());
					log.debug("Date String : " + validDate);
					Date validTill = sdf1.parse(validDate);
					log.debug("Date String : " + validTill.toString());
					
					JSONObject sessionKeyJson = new JSONObject();
					sessionKeyJson.put("role", "customer");
					sessionKeyJson.put("key", user.getCustomerId());
					sessionKeyJson.put("value", passwd);
					
					SessionKeys sessionKeys = new SessionKeys();
					sessionKeys.setUserId(user.getCustomerId());
					sessionKeys.setSessionKey(sessionKeyJson.toString());
					sessionKeys.setKeyAlive(1);
					
					session.persist(sessionKeys);
					
					int sesssionKeyId = sessionKeys.getSessionId();
					
					String sessionKeyString = sesssionKeyId+"."+sessionKeyJson.toString();
					String sessionKeyB64 = Base64.getEncoder().encodeToString(sessionKeyString.getBytes());
					log.debug("Session Key : " +sessionKeyB64);
					
					loginResponseBean.setSessionKey(sessionKeyB64);
					loginResponseBean.setSessionId(sesssionKeyId);
				}
			} else {
				loginResponseBean.setLoginStatus(-1);
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("checking login credentials failed");
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
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
			EngageCustomer instance = (EngageCustomer) session.get("com.limitless.services.engage.dao.EngageCustomer",
					customerId);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
				if (instance.getCustomerPasswd99().equals(passwd)) {
					isValidCredentials = true;
				}
			}
			tx.commit();
			return isValidCredentials;
		} catch (RuntimeException re) {
			if (tx != null) {
				tx.rollback();
			}
			log.error("get failed", re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	public ProfileChangeResponseBean updateCustomerProfile(ProfileChangeRequestBean requestBean) {
		log.debug("Changing/updating customer details");
		ProfileChangeResponseBean responseBean = new ProfileChangeResponseBean();
		Transaction transaction = null;
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			int customerId = requestBean.getCustomerId();
			String key = requestBean.getCustomerKey();
			String value = requestBean.getCustomerValue();

			EngageCustomer instance = (EngageCustomer) session.get("com.limitless.services.engage.dao.EngageCustomer",
					customerId);

			if (instance != null) {
				if (key.equals("name")) {
					String customerName = value;
					instance.setCustomerName(customerName);
					session.update(instance);
				} else if (key.equals("mobile")) {
					String customerMobile = value;
					instance.setCustomerMobileNumber(customerMobile);
					session.update(instance);
				} else if (key.equals("email")) {
					String customerEmailId = value;
					instance.setCustomerEmail99(customerEmailId);
					session.update(instance);
				}
				responseBean.setMessage("Success");
				responseBean.setCustomerId(customerId);
			} else {
				responseBean.setMessage("Failed");
				responseBean.setCustomerId(customerId);
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Changing/updating customer details failed", re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}

	public MobileResponseBean getCustomerMobileNumber(String customerMobile) {
		log.debug("getting customer mobile");
		MobileResponseBean responseBean = new MobileResponseBean();
		Transaction transaction = null;
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(EngageCustomer.class);
			criteria.add(Restrictions.eq("customerMobileNumber", customerMobile));
			List<EngageCustomer> customerList = criteria.list();
			if (customerList != null && customerList.size() > 0) {
				for (EngageCustomer customer : customerList) {
					responseBean.setCustomerMobile(customer.getCustomerMobileNumber());
					responseBean.setCustomerId(customer.getCustomerId());
					responseBean.setCustomerEmail(customer.getCustomerEmail99());
				}
				responseBean.setMessage("Success");
			} else {
				responseBean.setMessage("Mobile Not Found");
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Changing/updating customer details failed", re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}

	public InviteResponseBean sendInvite(InviteRequestBean requestBean) throws Exception {
		log.debug("Sending invite");
		InviteResponseBean responseBean = new InviteResponseBean();
		Transaction transaction = null;
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			String senderName = "";
			String message = "";
			if (requestBean.getKey().equals("merchant")) {
				EngageSeller seller = (EngageSeller) session.get("com.limitless.services.engage.dao.EngageSeller",
						requestBean.getSellerId());
				senderName = seller.getSellerName();
				if(seller.getMobileAlias()!=null){
					message = senderName
							+ " invites you to join LimitlessCircle. "
							+ "Click here: https://goo.gl/BEv6i7?referrer=app_sms_st_"+seller.getMobileAlias()+"_lot or use code:"+seller.getMobileAlias();
				}
				else if(seller.getSellerMobileNumber()!=null){
					message = senderName
							+ " has invited you to join LimitlessCircle. "
							+ "Click here: https://goo.gl/BEv6i7?referrer=app_sms_st_"+seller.getSellerMobileNumber()+"_lot or use code:"+seller.getSellerMobileNumber();
				}
			} else if (requestBean.getKey().equals("customer")) {
				EngageCustomer customer = (EngageCustomer) session
						.get("com.limitless.services.engage.dao.EngageCustomer", requestBean.getCustomerId());
				senderName = customer.getCustomerName();
				message = "LETS GO DIGITAL! " + senderName
						+ " has invited you to join LimitlessCircle. Download the app: goo.gl/ejZrmv";
			}
			String encoded_message = URLEncoder.encode(message);
			String authkey = "129194Aa6NwGoQsVt580d9a57";
			String mobiles = requestBean.getMobileNumbers();
			String senderId = "LLCINV";
			String route = "4";
			String mainUrl = "http://api.msg91.com/api/sendhttp.php?";
			StringBuilder sbPostData = new StringBuilder(mainUrl);
			sbPostData.append("authkey=" + authkey);
			sbPostData.append("&mobiles=" + mobiles);
			sbPostData.append("&message=" + encoded_message);
			sbPostData.append("&route=" + route);
			sbPostData.append("&sender=" + senderId);
			mainUrl = sbPostData.toString();
			URL msgUrl = new URL(mainUrl);
			URLConnection con = msgUrl.openConnection();
			con.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String response = "";
			while ((response = reader.readLine()) != null) {
				System.out.println(response);
			}
			responseBean.setCustomerId(requestBean.getCustomerId());
			responseBean.setMerchantId(requestBean.getSellerId());
			responseBean.setMessage("Success");
			responseBean.setResponse(response);
			reader.close();
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Changing/updating customer details failed", re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}

	public AddAccountResponseBean moneyTransferRegister(AddAccountRequestBean requestBean) {
		log.debug("Registering for P2p");
		AddAccountResponseBean responseBean = new AddAccountResponseBean();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			Criteria criteria = session.createCriteria(CitrusSeller.class);
			Junction conditions = Restrictions.conjunction()
					.add(Restrictions.eq("sellerAccNumber", requestBean.getAccountNumber()))
					.add(Restrictions.eq("sellerIfsc", requestBean.getIfscCode()))
					.add(Restrictions.eq("sellerName", requestBean.getAccountName()));
			criteria.add(conditions);
			List<CitrusSeller> citrusSellerList = criteria.list();
			log.debug("Citrus Sellers Size: " + citrusSellerList.size());

			CitrusSellerManager csManager = new CitrusSellerManager();
			CitrusSellerResponseBean csResponseBean = csManager.getCitrusSellers(session, transaction);

			if (citrusSellerList.isEmpty()) {
				EngageCustomer oldInstance = (EngageCustomer) session
						.get("com.limitless.services.engage.dao.EngageCustomer", requestBean.getCustomerId());
				CitrusRegistrationRequestBean citrusRequestBean = new CitrusRegistrationRequestBean();
				citrusRequestBean.setSeller_name(requestBean.getAccountName());
				citrusRequestBean.setSeller_add1("Bangalore");
				citrusRequestBean.setSeller_city("Bangalore");
				citrusRequestBean.setSeller_state("KA");
				citrusRequestBean.setSeller_country("India");
				citrusRequestBean.setZip("560071");
				citrusRequestBean.setSeller_mobile(oldInstance.getCustomerMobileNumber());
				citrusRequestBean.setSeller_ifsc_code(requestBean.getIfscCode());
				citrusRequestBean.setSeller_acc_num(requestBean.getAccountNumber());
				citrusRequestBean.setActive(1);
				citrusRequestBean.setPayoutmode("NEFT");
				citrusRequestBean.setSelleremail(oldInstance.getCustomerEmail99());

				CitrusAuthToken token = (CitrusAuthToken) session
						.get("com.limitless.services.payment.PaymentService.dao.CitrusAuthToken", 1);
				String authToken = token.getAuthToken();

				CitrusRegistrationResponseBean citrusResponseBean = citrsuRegistration(citrusRequestBean, authToken);
				if (citrusResponseBean.getMessage().equals("Success")) {
					EngageCustomer newInstance = (EngageCustomer) session
							.get("com.limitless.services.engage.dao.EngageCustomer", requestBean.getCustomerId());
					newInstance.setCitrusSellerId(citrusResponseBean.getCitrusSellerId());
					session.update(newInstance);
					responseBean.setCitrusSellerId(citrusResponseBean.getCitrusSellerId());
					responseBean.setMessage("Success");
					csResponseBean = csManager.getCitrusSellers(session, transaction);
				} else {
					responseBean.setMessage(citrusResponseBean.getMessage());
				}
			} else if (citrusSellerList.size() > 0) {
				int citrus_csid = 0;
				for (CitrusSeller citrusSeller : citrusSellerList) {
					citrus_csid = citrusSeller.getCitrusId();
				}

				EngageCustomer newInstance = (EngageCustomer) session
						.get("com.limitless.services.engage.dao.EngageCustomer", requestBean.getCustomerId());
				newInstance.setCitrusSellerId(citrus_csid);
				session.update(newInstance);
				responseBean.setCitrusSellerId(citrus_csid);
				responseBean.setMessage("Success");

			} else {
				responseBean.setMessage("Failed");
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Changing/updating customer details failed", re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}

	public CitrusRegistrationResponseBean citrsuRegistration(CitrusRegistrationRequestBean requestBean,
			String authToken) {
		log.debug("Registering at citrus");
		CitrusRegistrationResponseBean responseBean = new CitrusRegistrationResponseBean();
		try {
			WebResource webResource = client.resource("https://splitpay.citruspay.com/marketplace/seller/");
			ClientResponse clientResponse = webResource.accept("application/json").type("application/json")
					.header("auth_token", authToken).post(ClientResponse.class, requestBean);
			String citrusResponse = clientResponse.getEntity(String.class);
			log.debug("Citrus Response : " + citrusResponse);

			try {
				JSONObject responseJson = new JSONObject(citrusResponse);
				if (responseJson.has("sellerid")) {
					responseBean.setCitrusSellerId(responseJson.getInt("sellerid"));
					responseBean.setMessage("Success");
				}
			} catch (Exception e) {
				JSONArray responseArray = new JSONArray(citrusResponse);
				for (int i = 0; i < responseArray.length(); i++) {
					JSONObject expJson = (JSONObject) responseArray.get(0);
					responseBean.setMessage(expJson.getString("stack"));
				}
			}
		} catch (Exception e) {
			log.debug("Registering at citrus failed :" + e);
		}
		return responseBean;
	}

	public P2PCustomerVerificationResponseBean p2pCustomerVerification(P2PCustomerVerificationRequestBean requestBean) {
		log.debug("Verifiying and updating customers for p2p");
		P2PCustomerVerificationResponseBean responseBean = new P2PCustomerVerificationResponseBean();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			Criteria criteria = session.createCriteria(EngageSeller.class);
			criteria.add(Restrictions.eq("sellerMobileNumber", requestBean.getCustomerMobileNumber()));
			List<EngageSeller> sellerList = criteria.list();
			log.debug("Seller size : " + sellerList.size());
			if (sellerList.size() > 0) {
				for (EngageSeller seller : sellerList) {
					int citrusSellerId = seller.getCitrusSellerId();

					CitrusSeller cseller = (CitrusSeller) session
							.get("com.limitless.services.engage.sellers.dao.CitrusSeller", citrusSellerId);

					if (cseller != null) {
						responseBean.setAccountName(cseller.getSellerName());
						responseBean.setAccountNumber(cseller.getSellerAccNumber());
						responseBean.setIfsc(cseller.getSellerIfsc());
						responseBean.setCitrusSellerId(citrusSellerId);
						responseBean.setMessage("Success");

						EngageCustomer customerInstance = (EngageCustomer) session
								.get("com.limitless.services.engage.dao.EngageCustomer", requestBean.getCustomerId());
						customerInstance.setCitrusSellerId(cseller.getCitrusId());
						session.update(customerInstance);
					}
				}
			} else if (sellerList.isEmpty()) {
				responseBean.setMessage("Failed");
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("P2P verification failed failed", re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}

	public String getCustomerVersion() {
		log.debug("Getting Customer Version");
		String customerVersion = "1.0.0";
		Session session = null;
		Transaction transaction = null;

		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			CustomerVersion version = (CustomerVersion) session.get("com.limitless.services.engage.dao.CustomerVersion",
					1);
			if (version != null)
				customerVersion = version.getVersion();
			transaction.commit();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			customerVersion = "1.0.0";
			log.error("Getting customer version failed :" + e);

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return customerVersion;
	}
	
	public CustomerDeviceIdResponseBean addDeviceIdForCustomer(CustomerDeviceIdRequestBean requestBean){
		log.debug("adding device id");
		CustomerDeviceIdResponseBean responseBean = new CustomerDeviceIdResponseBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			EngageCustomer customer = 	(EngageCustomer) session
					.get("com.limitless.services.engage.dao.EngageCustomer", requestBean.getCustomerId());
			if(customer!=null){
				customer.setDeviceId(requestBean.getDeviceId());
				session.update(customer);
				responseBean.setCustomerId(requestBean.getCustomerId());
				responseBean.setMessage("Success");
			}
			else if(customer == null){
				responseBean.setMessage("Failed");
			}
			transaction.commit();
		}
		catch(RuntimeException re){
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("adding device id failed : ", re);
			throw re;
		}
		finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}
	
	public CustomerNotifyResponseBean notifyAllCustomer(CustomerNotifyRequestBean requestBean){
		log.debug("sending notification");
		CustomerNotifyResponseBean responseBean = new CustomerNotifyResponseBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(EngageCustomer.class);
			criteria.add(Restrictions.isNotNull("deviceId"));
			List<EngageCustomer> customerList = criteria.list();
			log.debug("customer size : " + customerList.size());
			if(customerList.size()>0){
				for(EngageCustomer customer : customerList){
					CustomerFcmRequestBean fcmBean = new CustomerFcmRequestBean();
					fcmBean.setTo(customer.getDeviceId());
					fcmBean.setPriority("high");
					CustomerDataBean dataBean = new CustomerDataBean();
					dataBean.setImageUrl(requestBean.getImageUrl());
					dataBean.setMerchantMobile(requestBean.getMerchantMobile());
					dataBean.setTitle(requestBean.getTitle());
					dataBean.setBody(requestBean.getBody());
					fcmBean.setData(dataBean);
					
					WebResource webResource2 = client.resource("https://fcm.googleapis.com/fcm/send");
					ClientResponse clientResponse = webResource2.type("application/json")
							.header("Authorization", "key=AIzaSyAP4xJ6VMm4vpj2A1ocGDvvvwzxtUNuKI0")
							.post(ClientResponse.class, fcmBean);
					System.out.println(clientResponse.getStatus());
					System.out.println(clientResponse.getEntity(String.class));
				}
				responseBean.setMessage("Success");
			}
			else if(customerList.isEmpty()){
				responseBean.setMessage("Success");
			}
			transaction.commit();
		}
		catch(RuntimeException re){
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("adding device id failed : ", re);
			throw re;
		}
		finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}
	
	public CustomerNotifyResponseBean notifyCustomer(CustomerNotifyRequestBean requestBean){
		log.debug("sending notification");
		CustomerNotifyResponseBean responseBean = new CustomerNotifyResponseBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			EngageCustomer customer = (EngageCustomer) session
					.get("com.limitless.services.engage.dao.EngageCustomer", requestBean.getCustomerId());
			if(customer!=null){
				CustomerFcmRequestBean fcmBean = new CustomerFcmRequestBean();
				fcmBean.setTo(customer.getDeviceId());
				fcmBean.setPriority("high");
				CustomerDataBean dataBean = new CustomerDataBean();
				dataBean.setImageUrl(requestBean.getImageUrl());
				dataBean.setMerchantMobile(requestBean.getMerchantMobile());
				dataBean.setTitle(requestBean.getTitle());
				dataBean.setBody(requestBean.getBody());
				fcmBean.setData(dataBean);
				
				WebResource webResource2 = client.resource("https://fcm.googleapis.com/fcm/send");
				ClientResponse clientResponse = webResource2.type("application/json")
						.header("Authorization", "key=AIzaSyAP4xJ6VMm4vpj2A1ocGDvvvwzxtUNuKI0")
						.post(ClientResponse.class, fcmBean);
				System.out.println(clientResponse.getStatus());
				System.out.println(clientResponse.getEntity(String.class));
				if(clientResponse.getStatus()==200){
					responseBean.setCustomerId(requestBean.getCustomerId());
					responseBean.setMessage("Success");
				}
				else{
					responseBean.setMessage("Failed");
				}
			}
			else if(customer==null){
				responseBean.setMessage("Failed");
			}
			transaction.commit();
		}
		catch(RuntimeException re){
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("sending notification failed : ", re);
			throw re;
		}
		finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}
	
	public boolean authenticateCustomer(String sessionKey, int sessionId){
		log.debug("authenticating customer");
		boolean isCustomerAuthorized = false;
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			JSONObject sessionJson = new JSONObject(sessionKey);
			int customerId = sessionJson.getInt("key");
			
			SessionKeys sessionKeys = (SessionKeys) session
					.get("com.limitless.services.engage.dao.SessionKeys", sessionId);
			if(sessionKeys!=null){
				if(sessionKeys.getKeyAlive()==1 && sessionKeys.getUserId()==customerId){
					isCustomerAuthorized = true;
				}
				else{
					isCustomerAuthorized = false;
				}
			}
			else{
				isCustomerAuthorized = false;
			}
			transaction.commit();
		}
		catch(Exception e){
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("authenticating custimer failed :" + e);
		}
		finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return isCustomerAuthorized;
	}
	
	public CustomerLogoutResponseBean deacticateSessionKey(CustomerLogoutRequestBean requestBean){
		log.debug("deactivting session key");
		CustomerLogoutResponseBean responseBean = new CustomerLogoutResponseBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			SessionKeys key = (SessionKeys) session
					.get("com.limitless.services.engage.dao.SessionKeys", requestBean.getSessionId());
			if(key!=null){
				key.setKeyAlive(0);
				session.update(key);
				responseBean.setCustomerId(requestBean.getCustomerId());
				responseBean.setMessage("Success");
			}
			else{
				responseBean.setMessage("Failed");
			}
			transaction.commit();
		}
		catch(RuntimeException re){
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("deactivting session key failed :" + re);
		}
		finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}
	
	public GuestLoginResponseBean loginGuestUser(GuestLoginRequestBean requestBean){
		log.debug("guest login");
		GuestLoginResponseBean responseBean = new GuestLoginResponseBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(GuestUser.class);
			criteria.add(Restrictions.eq("deviceMac", requestBean.getDeviceMac()));
			List<GuestUser> guestList = criteria.list();
			log.debug("Guest list : " + guestList.size());
			if(guestList.size()>0){
				for(GuestUser guest : guestList){
					responseBean.setCustomerId(-1);
					responseBean.setGuestId(guest.getGuestId());
					Criteria criteria2 = session.createCriteria(SessionKeys.class);
					Criterion uidCriterion = Restrictions.eq("userId", guest.getGuestId());
					Criterion aliveCriterion = Restrictions.eq("keyAlive", 1);
					LogicalExpression logExp = Restrictions.and(uidCriterion, aliveCriterion);
					criteria2.add(logExp);
					List<SessionKeys> sessionList = criteria2.list();
					log.debug("Session list : " + sessionList.size());
					if(sessionList.size()==1){
						for(SessionKeys sessionKeys : sessionList){
							String sessionKeyString = sessionKeys.getSessionId()+"."+sessionKeys.getSessionKey();
							String sessionKeyB64 = Base64.getEncoder().encodeToString(sessionKeyString.getBytes());
							log.debug("Session Key : " +sessionKeyB64);
							
							responseBean.setSessionKey(sessionKeyB64);
							responseBean.setSessionId(sessionKeys.getSessionId());
						}
					}
				}
				responseBean.setMessage("Success");
			}
			else if(guestList.isEmpty()){
				GuestUser guestUser = new GuestUser();
				guestUser.setDeviceMac(requestBean.getDeviceMac());
				guestUser.setUtmSource(requestBean.getUtmSource());
				guestUser.setUtmMedium(requestBean.getUtmMedium());
				guestUser.setUtmCampign(requestBean.getUtmCampign());
				guestUser.setUtmTerm(requestBean.getUtmTerm());
				guestUser.setUtmContent(requestBean.getUtmContent());
				session.persist(guestUser);
				
				String guestPwd = DigestUtils.sha256Hex("guest@llc2o2o"+guestUser.getGuestId());
				log.debug("pwd hash : "+guestPwd);
				
				JSONObject sessionKeyJson = new JSONObject();
				sessionKeyJson.put("role", "customer");
				sessionKeyJson.put("key", guestUser.getGuestId());
				sessionKeyJson.put("value", guestPwd);
				
				SessionKeys sessionKeys = new SessionKeys();
				sessionKeys.setUserId(guestUser.getGuestId());
				sessionKeys.setSessionKey(sessionKeyJson.toString());
				sessionKeys.setKeyAlive(1);
				
				session.persist(sessionKeys);
				
				int sesssionKeyId = sessionKeys.getSessionId();
				
				String sessionKeyString = sesssionKeyId+"."+sessionKeyJson.toString();
				String sessionKeyB64 = Base64.getEncoder().encodeToString(sessionKeyString.getBytes());
				log.debug("Session Key : " +sessionKeyB64);
				
				responseBean.setCustomerId(-1);
				responseBean.setGuestId(guestUser.getGuestId());
				responseBean.setSessionKey(sessionKeyB64);
				responseBean.setSessionId(sesssionKeyId);
				responseBean.setMessage("Success");
			}
			transaction.commit();			
		}
		catch(RuntimeException re){
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("guest login failed :" + re);
		}
		finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}
	
	public LoginResponseBean guest2CustomerLogin(Guest2CustomerLoginRequestBean requestBean) throws Exception{
		log.debug("guest to customer login");
		LoginResponseBean responseBean = new LoginResponseBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			SessionKeys key = (SessionKeys) session
					.get("com.limitless.services.engage.dao.SessionKeys", requestBean.getSessionId());
			if(key!=null){
				if(key.getUserId() == requestBean.getGuestId()){
					key.setKeyAlive(0);
					session.update(key);
					String customerMobile = requestBean.getCustomerMobileNumber();
					String passwd = requestBean.getCustomerPasswd();
					Criteria criteria = session.createCriteria(EngageCustomer.class);
					Criterion emailCriterion = Restrictions.eq("customerMobileNumber", customerMobile);
					Criterion passwdCriterion = Restrictions.eq("customerPasswd99", passwd);
					LogicalExpression logicalExp = Restrictions.and(emailCriterion, passwdCriterion);
					criteria.add(logicalExp);
					List<EngageCustomer> userList = criteria.list();
					if (userList.size() > 0 && userList.size() == 1) {
						log.debug("Size: " + userList.size());
						for (EngageCustomer user : userList) {
							responseBean.setLoginStatus(1);
							responseBean.setMessage("Success");
							responseBean.setCustomerId(user.getCustomerId());
							responseBean.setCustomerName(user.getCustomerName());
							responseBean.setCustomerEmail(user.getCustomerEmail99());
							responseBean.setDeviceId(user.getDeviceId());
							
							SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(new Date());
							calendar.add(Calendar.MINUTE, 5);
							String validDate = sdf1.format(calendar.getTime());
							log.debug("Date String : " + validDate);
							Date validTill = sdf1.parse(validDate);
							log.debug("Date String : " + validTill.toString());
							
							JSONObject sessionKeyJson = new JSONObject();
							sessionKeyJson.put("role", "customer");
							sessionKeyJson.put("key", user.getCustomerId());
							sessionKeyJson.put("value", passwd);
							
							SessionKeys sessionKeys = new SessionKeys();
							sessionKeys.setUserId(user.getCustomerId());
							sessionKeys.setSessionKey(sessionKeyJson.toString());
							sessionKeys.setKeyAlive(1);
							
							session.persist(sessionKeys);
							
							int sesssionKeyId = sessionKeys.getSessionId();
							
							String sessionKeyString = sesssionKeyId+"."+sessionKeyJson.toString();
							String sessionKeyB64 = Base64.getEncoder().encodeToString(sessionKeyString.getBytes());
							log.debug("Session Key : " +sessionKeyB64);
							
							responseBean.setSessionKey(sessionKeyB64);
							responseBean.setSessionId(sesssionKeyId);
							
							Criteria criteria2 = session.createCriteria(Cart.class);
							criteria2.add(Restrictions.eq("customerId", requestBean.getGuestId()));
							List<Cart> cartList = criteria2.list();
							log.debug("cart list size : " + cartList.size());
							if(cartList.size()>0){
								for(Cart cart : cartList){
									Cart cartInstance = (Cart) session
											.get("com.limitless.services.engage.order.dao.Cart", cart.getCartId());
									cartInstance.setCustomerId(user.getCustomerId());
									session.update(cartInstance);
								}
							}
						}
					} else {
						responseBean.setLoginStatus(-1);
					}
				}
			}
			transaction.commit();
		}
		catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("guest login failed :" + re);
		}
		finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}
	
}

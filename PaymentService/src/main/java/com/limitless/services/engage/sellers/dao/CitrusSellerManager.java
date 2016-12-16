package com.limitless.services.engage.sellers.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;

import com.limitless.services.engage.sellers.CitrusSellerBean;
import com.limitless.services.engage.sellers.CitrusSellerRequestBean;
import com.limitless.services.engage.sellers.CitrusSellerResponseBean;
import com.limitless.services.engage.sellers.CitrusSellersBean;
import com.limitless.services.payment.PaymentService.dao.CitrusAuthToken;
import com.limitless.services.payment.PaymentService.util.HibernateUtil;
import com.limitless.services.payment.PaymentService.util.PaymentConstants;
import com.limitless.services.payment.PaymentService.util.RestClientUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class CitrusSellerManager {
	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	private static final Log log = LogFactory.getLog(CitrusSellerManager.class);
	Client client = RestClientUtil.createClient();
	
	public CitrusSellerResponseBean getCitrusSellers(){
		log.debug("Getting and updting sellers from citrus");
		CitrusSellerResponseBean responseBean = new CitrusSellerResponseBean();
		String authToken = "";
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			CitrusAuthToken token = (CitrusAuthToken) session
					.get("com.limitless.services.payment.PaymentService.dao.CitrusAuthToken", 1);
			authToken = token.getAuthToken();
			List<CitrusSellersBean> sellersList = new ArrayList<CitrusSellersBean>();
			List<CitrusSellerRequestBean> requestBeans = getSellerFromCitrus(authToken);
			log.debug("Request Beans Size : " + requestBeans.size());
			if(requestBeans.size()>0){
				for(CitrusSellerRequestBean bean : requestBeans){
					CitrusSellersBean sellersBean = new CitrusSellersBean();
					
					CitrusSeller instance = (CitrusSeller) session
							.get("com.limitless.services.engage.sellers.dao.CitrusSeller", bean.getCitrusId());
					
					if(instance == null){
						CitrusSeller seller = new CitrusSeller();
						seller.setCitrusId(bean.getCitrusId());
						seller.setSellerName(bean.getSellerName());
						seller.setSellerAddress1(bean.getSellerAddress1());
						seller.setSellerAddress2(bean.getSellerAddress2());
						seller.setSellerCity(bean.getSellerCity());
						seller.setSellerState(bean.getSellerState());
						seller.setSellerCountry(bean.getSellerCountry());
						seller.setSellerZip(bean.getSellerZip());
						seller.setSellerBurl(bean.getSellerBurl());
						seller.setSellerMail(bean.getSellerMail());
						seller.setSellerIfsc(bean.getSellerIfsc());
						seller.setSellerAccNumber(bean.getSellerAccNumber());
						seller.setSellerPayoutmode(bean.getSellerPayoutmode());
						seller.setSellerAccountId(bean.getSellerAccountId());
						seller.setSellerActive(bean.getSellerActive());
						
						session.persist(seller);
						sellersBean.setAccountId(seller.getSellerAccountId());
						sellersBean.setCitrusId(seller.getCitrusId());
						sellersList.add(sellersBean);
						sellersBean = null;
					}
					else if(instance != null){
						CitrusSeller seller = (CitrusSeller) session
								.get("com.limitless.services.engage.sellers.dao.CitrusSeller", bean.getCitrusId());
						seller.setCitrusId(bean.getCitrusId());
						seller.setSellerName(bean.getSellerName());
						seller.setSellerAddress1(bean.getSellerAddress1());
						seller.setSellerAddress2(bean.getSellerAddress2());
						seller.setSellerCity(bean.getSellerCity());
						seller.setSellerState(bean.getSellerState());
						seller.setSellerCountry(bean.getSellerCountry());
						seller.setSellerZip(bean.getSellerZip());
						seller.setSellerBurl(bean.getSellerBurl());
						seller.setSellerMail(bean.getSellerMail());
						seller.setSellerIfsc(bean.getSellerIfsc());
						seller.setSellerAccNumber(bean.getSellerAccNumber());
						seller.setSellerPayoutmode(bean.getSellerPayoutmode());
						seller.setSellerAccountId(bean.getSellerAccountId());
						seller.setSellerActive(bean.getSellerActive());
						
						session.update(seller);
						sellersBean.setAccountId(instance.getSellerAccountId());
						sellersBean.setCitrusId(instance.getCitrusId());
						sellersList.add(sellersBean);
						sellersBean = null;
					}
				}
			}
			responseBean.setMessage("Success");
			responseBean.setSellersList(sellersList);
			transaction.commit();
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("Getting and updting sellers from citrus failed : " + re);
			throw re;
		}
		finally {
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		
		return responseBean;
	}
	
	public CitrusSellerResponseBean getCitrusSellers(Session session, Transaction transaction){
		log.debug("Getting and updting sellers from citrus");
		CitrusSellerResponseBean responseBean = new CitrusSellerResponseBean();
		String authToken = "";
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			CitrusAuthToken token = (CitrusAuthToken) session
					.get("com.limitless.services.payment.PaymentService.dao.CitrusAuthToken", 1);
			authToken = token.getAuthToken();
			List<CitrusSellersBean> sellersList = new ArrayList<CitrusSellersBean>();
			List<CitrusSellerRequestBean> requestBeans = getSellerFromCitrus(authToken);
			log.debug("Request Beans Size : " + requestBeans.size());
			if(requestBeans.size()>0){
				for(CitrusSellerRequestBean bean : requestBeans){
					CitrusSellersBean sellersBean = new CitrusSellersBean();
					
					CitrusSeller instance = (CitrusSeller) session
							.get("com.limitless.services.engage.sellers.dao.CitrusSeller", bean.getCitrusId());
					
					if(instance == null){
						CitrusSeller seller = new CitrusSeller();
						seller.setCitrusId(bean.getCitrusId());
						seller.setSellerName(bean.getSellerName());
						seller.setSellerAddress1(bean.getSellerAddress1());
						seller.setSellerAddress2(bean.getSellerAddress2());
						seller.setSellerCity(bean.getSellerCity());
						seller.setSellerState(bean.getSellerState());
						seller.setSellerCountry(bean.getSellerCountry());
						seller.setSellerZip(bean.getSellerZip());
						seller.setSellerBurl(bean.getSellerBurl());
						seller.setSellerMail(bean.getSellerMail());
						seller.setSellerIfsc(bean.getSellerIfsc());
						seller.setSellerAccNumber(bean.getSellerAccNumber());
						seller.setSellerPayoutmode(bean.getSellerPayoutmode());
						seller.setSellerAccountId(bean.getSellerAccountId());
						seller.setSellerActive(bean.getSellerActive());
						
						session.persist(seller);
						sellersBean.setAccountId(seller.getSellerAccountId());
						sellersBean.setCitrusId(seller.getCitrusId());
						sellersList.add(sellersBean);
						sellersBean = null;
					}
					else if(instance != null){
						CitrusSeller seller = (CitrusSeller) session
								.get("com.limitless.services.engage.sellers.dao.CitrusSeller", bean.getCitrusId());
						seller.setCitrusId(bean.getCitrusId());
						seller.setSellerName(bean.getSellerName());
						seller.setSellerAddress1(bean.getSellerAddress1());
						seller.setSellerAddress2(bean.getSellerAddress2());
						seller.setSellerCity(bean.getSellerCity());
						seller.setSellerState(bean.getSellerState());
						seller.setSellerCountry(bean.getSellerCountry());
						seller.setSellerZip(bean.getSellerZip());
						seller.setSellerBurl(bean.getSellerBurl());
						seller.setSellerMail(bean.getSellerMail());
						seller.setSellerIfsc(bean.getSellerIfsc());
						seller.setSellerAccNumber(bean.getSellerAccNumber());
						seller.setSellerPayoutmode(bean.getSellerPayoutmode());
						seller.setSellerAccountId(bean.getSellerAccountId());
						seller.setSellerActive(bean.getSellerActive());
						
						session.update(seller);
						sellersBean.setAccountId(instance.getSellerAccountId());
						sellersBean.setCitrusId(instance.getCitrusId());
						sellersList.add(sellersBean);
						sellersBean = null;
					}
				}
			}
			responseBean.setMessage("Success");
			responseBean.setSellersList(sellersList);
		}
		catch(RuntimeException re){
			log.error("Getting and updting sellers from citrus failed : " + re);
		}
		
		return responseBean;
	}
	
	public List<CitrusSellerRequestBean> getSellerFromCitrus(String authToken){
		List<CitrusSellerRequestBean> requestBeanList = new ArrayList<CitrusSellerRequestBean>();
		try{
			WebResource webResource = client.resource("https://splitpay.citruspay.com/marketplace/seller/");
			ClientResponse clientResponse = webResource.accept("application/json")
					.header("auth_token", authToken)
					.get(ClientResponse.class);
			String citrusResponse = clientResponse.getEntity(String.class);
			log.debug("Citrus Response : " + citrusResponse);
			
			JSONArray responseJsonArray = new JSONArray(citrusResponse);
			for(int i=0; i<responseJsonArray.length();i++){
				JSONObject responseJson = responseJsonArray.getJSONObject(i);
				CitrusSellerRequestBean requestBean = new CitrusSellerRequestBean();
				requestBean.setCitrusId(responseJson.getInt("seller_id"));
				requestBean.setSellerName(responseJson.getString("seller_name"));
				requestBean.setSellerAddress1(responseJson.getString("seller_add1"));
				if(responseJson.getString("seller_add2").equals("")){
					requestBean.setSellerAddress2("NA");
				}
				else{
					requestBean.setSellerAddress2(responseJson.getString("seller_add2"));
				}
				requestBean.setSellerCity(responseJson.getString("seller_city"));
				requestBean.setSellerState(responseJson.getString("seller_state"));
				requestBean.setSellerCountry(responseJson.getString("seller_country"));
				requestBean.setSellerZip(responseJson.getString("seller_zip"));
				requestBean.setSellerBurl(responseJson.getString("business_url"));
				requestBean.setSellerMail(responseJson.getString("selleremail"));
				requestBean.setSellerIfsc(responseJson.getString("seller_ifsc_code"));
				requestBean.setSellerAccNumber(responseJson.getString("seller_acc_num"));
				requestBean.setSellerAccountId(responseJson.getInt("seller_account_id"));
				requestBean.setSellerPayoutmode(responseJson.getString("payoutmode"));
				requestBean.setSellerActive(responseJson.getInt("seller_active"));
				requestBeanList.add(requestBean);
				requestBean = null;
			}
		}
		catch(RuntimeException re){
			log.error("Getting sellers from citrus failed : " + re);
		}
		return requestBeanList;
	}
	
	public CitrusSellerBean getSellerById(int citrusSellerId){
		log.debug("Getting citrus seller");
		CitrusSellerBean sellerBean = new CitrusSellerBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			CitrusSeller seller = (CitrusSeller) session
					.get("com.limitless.services.engage.sellers.dao.CitrusSeller", citrusSellerId);
			if(seller != null){
				sellerBean.setMessage("Success");
				sellerBean.setCitrusSellerId(seller.getCitrusId());
				sellerBean.setSellerName(seller.getSellerName());
				sellerBean.setSellerBankAccountNumber(seller.getSellerAccNumber());
				sellerBean.setSellerBankIfsc(seller.getSellerIfsc());
				sellerBean.setSellerActive(seller.getSellerActive());
			}
			else{
				sellerBean.setMessage("Information Will Be Available Shortly...");
			}
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("Getting sellers from citrus failed : " + re);
		}
		finally {
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return sellerBean;
	}
}

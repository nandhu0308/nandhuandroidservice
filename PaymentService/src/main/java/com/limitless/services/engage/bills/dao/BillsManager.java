package com.limitless.services.engage.bills.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;

import com.limitless.services.engage.bills.BillBean;
import com.limitless.services.engage.bills.BillPaymentStatusUpdateResponseBean;
import com.limitless.services.engage.bills.BillRequestBean;
import com.limitless.services.engage.bills.BillResponseBean;
import com.limitless.services.engage.bills.CustomerBillsListBean;
import com.limitless.services.engage.bills.SellerBillsListBean;
import com.limitless.services.engage.dao.EngageCustomer;
import com.limitless.services.engage.dao.EngageSeller;
import com.limitless.services.payment.PaymentService.util.HibernateUtil;
import com.limitless.services.payment.PaymentService.util.RestClientUtil;
import com.sun.jersey.api.client.Client;

public class BillsManager {
	private static final Log log = LogFactory.getLog(BillsManager.class);
	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	Client client = RestClientUtil.createClient();
	
	public CustomerBillsListBean getCustomerBills(int customerId, String statusCode){
		log.debug("getting customer bills");
		CustomerBillsListBean listBean = new CustomerBillsListBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			statusCode = statusCode.toUpperCase();
			
			List<BillBean> billsList = new ArrayList<BillBean>();
			
			EngageCustomer customer = (EngageCustomer) session
					.get("com.limitless.services.engage.dao.EngageCustomer", customerId);
			if(customer!=null){
				listBean.setCustomerId(customerId);
				listBean.setCustomerName(customer.getCustomerName());
				listBean.setCustomerMobile(customer.getCustomerMobileNumber());
				Criteria criteria = session.createCriteria(Bills.class);
				Criterion cidCriterion = Restrictions.eq("customerId", customerId);
				Criterion statusCriterion = Restrictions.eq("billStatus", statusCode);
				LogicalExpression logExp = Restrictions.and(cidCriterion, statusCriterion);
				criteria.add(logExp);
				List<Bills> billList = criteria.list();
				log.debug("bill list size : " + billList.size());
				if(billList.size()>0){
					for(Bills bill : billList){
						BillBean bean = new BillBean();
						bean.setBillId(bill.getBillId());
						bean.setCustomerId(customerId);
						bean.setCustomerName(customer.getCustomerName());
						bean.setSellerId(bill.getSellerId());
						EngageSeller seller = (EngageSeller) session
								.get("com.limitless.services.engage.dao.EngageSeller", bill.getSellerId());
						if(seller!=null){
							bean.setSellerName(seller.getSellerShopName());
							bean.setCitrusSellerId(seller.getCitrusSellerId());
						}
						bean.setBillDate(bill.getBillDate());
						bean.setBillDueDate(bill.getBillDueDate());
						bean.setBillPaidDate(bill.getBillPaidDate());
						bean.setBillAmount(bill.getBillAmount());
						bean.setAdjustmentAmount(bill.getBillAdjustment());
						bean.setPayableAmount(Math.max(0, (bill.getBillAmount() - bill.getBillAdjustment())));
						bean.setBillStatus(bill.getBillStatus());
						billsList.add(bean);
						bean = null;
					}
					listBean.setBillList(billsList);
					listBean.setMessage("Success");
				}
				else if(billList.isEmpty()){
					listBean.setMessage("Failed");
				}
			}
			else if(customer==null){
				listBean.setMessage("Failed - Customer Not Found");
			}
			transaction.commit();
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
		}
		finally {
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return listBean;
	}
	
	public SellerBillsListBean getSellerBills(int sellerId){
		log.debug("getting seller bills");
		SellerBillsListBean listBean = new SellerBillsListBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			List<BillBean> billsList = new ArrayList<BillBean>();
			
			EngageSeller seller = (EngageSeller) session
					.get("com.limitless.services.engage.dao.EngageSeller", sellerId);
			if(seller!=null){
				listBean.setSellerId(sellerId);
				listBean.setSellerName(seller.getSellerName());
				listBean.setSellerShopName(seller.getSellerShopName());
				listBean.setSellerMobile(seller.getSellerMobileNumber());
				Criteria criteria = session.createCriteria(Bills.class);
				criteria.add(Restrictions.eq("sellerId", sellerId));
				List<Bills> billList = criteria.list();
				log.debug("bill size : " + billList.size());
				if(billList.size()>0){
					for(Bills bill : billList){
						BillBean bean = new BillBean();
						bean.setBillId(bill.getBillId());
						bean.setCustomerId(bill.getCustomerId());
						EngageCustomer customer = (EngageCustomer) session
								.get("com.limitless.services.engage.dao.EngageCustomer", bill.getCustomerId());
						if(customer!=null){
							bean.setCustomerName(customer.getCustomerName());
						}
						bean.setSellerId(sellerId);
						bean.setSellerName(seller.getSellerName());
						bean.setBillDate(bill.getBillDate());
						bean.setBillDueDate(bill.getBillDueDate());
						bean.setBillPaidDate(bill.getBillPaidDate());
						bean.setBillAmount(bill.getBillAmount());
						bean.setAdjustmentAmount(bill.getBillAdjustment());
						bean.setPayableAmount(Math.max(0, (bill.getBillAmount() - bill.getBillAdjustment())));
						bean.setBillStatus(bill.getBillStatus());
						billsList.add(bean);
						bean = null;
					}
					listBean.setBillList(billsList);
					listBean.setMessage("Success");
				}
			}
			else if(seller==null){
				listBean.setMessage("Failed - Seller Not Found");
			}
			transaction.commit();
		}
		catch(RuntimeException re){
			log.error("getting seller bills failed : " + re);
			if(transaction!=null){
				transaction.rollback();
			}
			throw re;
		}
		finally {
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return listBean;
	}
	
	public BillResponseBean newBill(BillRequestBean requestBean){
		log.debug("adding new bill");
		BillResponseBean responseBean = new BillResponseBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			EngageSeller seller = (EngageSeller) session
					.get("com.limitless.services.engage.dao.EngageSeller", requestBean.getSellerId());
			if(seller!=null){
				EngageCustomer customer = (EngageCustomer) session
						.get("com.limitless.services.engage.dao.EngageCustomer", requestBean.getCustomerId());
				if(customer!=null){
					Bills bill = new Bills();
					bill.setCustomerId(requestBean.getCustomerId());
					bill.setSellerId(requestBean.getSellerId());
					bill.setBillDate(requestBean.getBillDate());
					bill.setBillDueDate(requestBean.getBillDueDate());
					bill.setBillAmount(requestBean.getBillAmount());
					bill.setBillAdjustment(requestBean.getBillAdjustment());
					bill.setBillStatus("PENDING");
					session.persist(bill);
					log.debug("bill id : " + bill.getBillId());
					responseBean.setBillId(bill.getBillId());
					responseBean.setMessage("Success");
					responseBean.setCustomerId(requestBean.getCustomerId());
					responseBean.setSellerId(requestBean.getSellerId());
				}
				else if(customer==null){
					responseBean.setMessage("Failed - Customer Not Found");
				}
			}
			else if(seller==null){
				responseBean.setMessage("Failed - Seller Not Found");
			}
			transaction.commit();
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("adding new bill failed : " + re);
			throw re;
		}
		finally {
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return responseBean;
	}
	
	public BillPaymentStatusUpdateResponseBean updateBillStatus(int billId, int statusCode){
		log.debug("updating bill status");
		BillPaymentStatusUpdateResponseBean responseBean = new BillPaymentStatusUpdateResponseBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			Bills bill = (Bills) session.get("com.limitless.services.engage.bills.dao.Bills", billId);
			if(bill!=null){
				if(bill.getBillId()==billId){
					if(statusCode==1){
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						Date date = new Date();
						String billPaidDate = sdf.format(date);
						bill.setBillPaidDate(billPaidDate);
						bill.setBillStatus("PAID");
						session.update(bill);
					}
					else if(statusCode==2){
						bill.setBillStatus("PENDING");
						session.update(bill);
					}
					responseBean.setBillId(billId);
					responseBean.setMessage("Success");
				}
				else{
					responseBean.setMessage("Failed");
				}
			}
			else if(bill==null){
				responseBean.setMessage("Failed");
			}
			transaction.commit();
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("updating bill status failed : " + re);
			throw re;
		}
		finally {
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return responseBean;
	}
	
	public void sendBillMail(int billId){
		log.debug("sending mail for bill payment");
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			String customerName = "";
			String customerEmail = "";
			String sellerName = "";
			String sellerMobile = "";
			String sellerEmail = "";
			String mailContent = "";
			
			Bills bill = (Bills) session
					.get("com.limitless.services.engage.bills.dao.Bills", billId);
			if(bill!=null){
				EngageCustomer customer = (EngageCustomer) session
						.get("com.limitless.services.engage.dao.EngageCustomer", bill.getCustomerId());
				if(customer!=null){
					customerName = customer.getCustomerName();
					customerEmail = customer.getCustomerEmail99();
					EngageSeller seller = (EngageSeller) session
							.get("com.limitless.services.engage.dao.EngageSeller", bill.getSellerId());
					if(seller!=null){
						sellerName = seller.getSellerShopName();
						sellerMobile = seller.getSellerMobileNumber();
						sellerEmail = seller.getSellerEmail99();
						final String username = "transactions@limitlesscircle.com";
						final String password = "Engage@12E";
						String sendMailTo = customerEmail+","+sellerEmail;
						log.debug("Mailing to : " + sendMailTo);
						Properties properties = new Properties();
						properties.put("mail.smtp.host", "smtp.zoho.com");
						properties.put("mail.smtp.socketFactory.port", "465");
						properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
						properties.put("mail.smtp.auth", "true");
						properties.put("mail.smtp.port", "465");
						javax.mail.Session mailSession = javax.mail.Session.getDefaultInstance(properties,
								new javax.mail.Authenticator() {
							protected PasswordAuthentication getPasswordAuthentication() {
								return new PasswordAuthentication(username, password);
							}
						  });
						
						try{
							javax.mail.Message message = new MimeMessage(mailSession);
							message.setFrom(new InternetAddress("transactions@limitlesscircle.com"));
							message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendMailTo));
							message.addRecipients(Message.RecipientType.BCC, InternetAddress.parse("orders@limitlesscircle.com"));
							message.setSubject("Bill Payment Done Successfully. Reference BillId :"+billId);
							mailContent = "Hello "+customerName+",<br>"
									+ "<table>"
									+ "<tr><td>Bill Id</td><td>"+billId+"</td></tr>"
									+ "<tr><td>Seller Name</td><td>"+sellerName+"</td></tr>"
									+ "<tr><td>Bill Amount</td><td>"+bill.getBillAmount()+"</td></tr>"
									+ "<tr><td>Adjustment</td><td>"+bill.getBillAdjustment()+"</td></tr>"
									+ "<tr><td>Amount Paid</td><td>"+Math.max(0, (bill.getBillAmount() - bill.getBillAdjustment()))+"</td></tr>"
									+ "</table>"
									+ "<br>"
									+ "For other queries contact seller:"+sellerMobile;
							message.setContent(mailContent, "text/html");
							Transport.send(message, message.getAllRecipients());
						}
						catch(Exception e){
							log.error("something went wrong : "+e);
						}
					}
				}
			}
			transaction.commit();
		}
		catch(RuntimeException re){
			if(transaction!=null){
				
			}
			log.error("sending mail failed : " + re);
		}
		finally {
			if(session!=null & session.isOpen()){
				session.close();
			}
		}
	}
	
}

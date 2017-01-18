package com.limitless.services.payment.PaymentService.dao;

import java.text.DecimalFormat;
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
import org.hibernate.criterion.Restrictions;

import com.limitless.services.engage.sellers.dao.CitrusSeller;
import com.limitless.services.payment.PaymentService.ReportMailResponseBean;
import com.limitless.services.payment.PaymentService.util.HibernateUtil;

public class MailManager {
	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	private static final Log log = LogFactory.getLog(MailManager.class);
	private static DecimalFormat df2 = new DecimalFormat(".##");
	
	public ReportMailResponseBean sendReport(){
		log.debug("sending reports");
		ReportMailResponseBean responseBean = new ReportMailResponseBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			double totalSettlements = 0;
			double totalTxnAmounts = 0;
			int totalMerchants = 0;
			
			Criteria criteria = session.createCriteria(PaymentSettlement.class);
			criteria.add(Restrictions.eq("settlementStatus", "RELEASE_SUCCESS"));
			List<PaymentSettlement> settleList = criteria.list();
			log.debug("sette size : " + settleList.size());
			if(settleList.size()>0){
				for(PaymentSettlement settle : settleList){
					totalSettlements += settle.getSettlementAmount();
				}
				log.debug("Total Settlements : " + totalSettlements);
				responseBean.setTotalSettlements(totalSettlements);
			}
			
			Criteria criteria2 = session.createCriteria(CitrusSeller.class);
			List<CitrusSeller> sellerList = criteria2.list();
			log.debug("Seller size : " + sellerList.size());
			totalMerchants = sellerList.size();
			responseBean.setTotalMerchants(totalMerchants);
			
			Criteria criteria3 = session.createCriteria(PaymentTxn.class);
			criteria3.add(Restrictions.eq("txnStatus", "PAYMENT_SUCCESSFUL"));
			List<PaymentTxn> txnList = criteria3.list();
			log.debug("Txn size : " + txnList.size());
			if(txnList.size()>0){
				for(PaymentTxn txn : txnList){
					totalTxnAmounts += txn.getTxnAmount();
				}
				responseBean.setTotalTxnAmounts(totalTxnAmounts);
			}
			responseBean.setMessage("Success");
			
			final String username = "transactions@limitlesscircle.com";
			final String password = "Engage@12E";
			
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
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("chandra@limitlesscircle.com"));
				
				message.setSubject("Report for the day");
				String messageContent = "<table><tr><td><b>Total Merchants<b></td><td>:</td>"
						+ "<td>"+totalMerchants+"</td></tr>"
								+ "<tr><td><b>Total Transactions</b><sup>*</sup></td><td>:</td><td>"+df2.format(totalTxnAmounts)+"</td></tr>"
										+ "<tr><td><b>Total Settlements</b><sup>**</sup></td><td>:</td><td>"+df2.format(totalSettlements)+"</td></tr>"
						+ "</table>"
						+ "<br><sup>*</sup>Total transactions happened till this mail generated."
						+ "<br><sup>**</sup>Total settlements till day before yesterday transactions.";
				message.setContent(messageContent, "text/html");
				Transport.send(message);
			}
			catch(Exception e){
				log.error("mail error : " + e);
			}
			
			transaction.commit();
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("sending report failed : " + re);
		}
		finally {
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return responseBean;
	}
	
}

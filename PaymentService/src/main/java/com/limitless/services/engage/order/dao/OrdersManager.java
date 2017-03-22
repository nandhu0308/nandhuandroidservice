package com.limitless.services.engage.order.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.limitless.services.engage.AddressListBean;
import com.limitless.services.engage.dao.CustomerAddressBook;
import com.limitless.services.engage.dao.EngageCustomer;
import com.limitless.services.engage.dao.EngageSeller;
import com.limitless.services.engage.dao.SellerDeviceIdMapper;
import com.limitless.services.engage.order.OrderDetailResponseBean;
import com.limitless.services.engage.order.OrderFcmBean;
import com.limitless.services.engage.order.OrderMailResponseBean;
import com.limitless.services.engage.order.OrderNotificationDataBean;
import com.limitless.services.engage.order.OrderPaymentModeUpdateResponseBean;
import com.limitless.services.engage.order.OrderProductsBean;
import com.limitless.services.engage.order.OrderProductsListBean;
import com.limitless.services.engage.order.OrderRequestBean;
import com.limitless.services.engage.order.OrderResponseBean;
import com.limitless.services.engage.order.OrderStatusResponseBean;
import com.limitless.services.engage.order.OrderSummaryResponseBean;
import com.limitless.services.engage.order.OrdersListBean;
import com.limitless.services.engage.restaurants.RestaurantOrderDataBean;
import com.limitless.services.engage.restaurants.RestaurantOrderFcmRequestBean;
import com.limitless.services.engage.sellers.product.dao.Product;
import com.limitless.services.engage.sellers.product.dao.ProductInventory;
import com.limitless.services.payment.PaymentService.InventoryUpdateResponseBean;
import com.limitless.services.payment.PaymentService.util.HibernateUtil;
import com.limitless.services.payment.PaymentService.util.RestClientUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class OrdersManager {
	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	private static final Log log = LogFactory.getLog(OrdersManager.class);
	Client client = RestClientUtil.createClient();
	
	public OrderResponseBean addOrder(OrderRequestBean requestBean){
		log.debug("Adding order");
		OrderResponseBean responseBean = new OrderResponseBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			double totalAmount = 0;
			List<OrderProductsBean> productList = requestBean.getOrderList();
			for(OrderProductsBean products : productList){
				Product product = (Product) session
						.get("com.limitless.services.engage.sellers.product.dao.Product",products.getProductId());
				float discountedPrice = (float) ((Float) product.getProductPrice()
						- (product.getProductPrice() * (product.getDiscountRate() / 100)));
				double totalPrice = products.getQuantity() * discountedPrice;
				totalAmount += totalPrice;
			}
			
			Orders order = new Orders();
			order.setCustomerId(requestBean.getCustomerId());
			order.setSellerId(requestBean.getSellerId());
			order.setTotalAmount(totalAmount);
			order.setOrderStatus("ORDER_INITIATED");
			order.setPaymentMode(requestBean.getPaymentMode());
			order.setDeliveryAddress(requestBean.getAddressId());
			session.persist(order);
			
			int orderId = order.getOrderId();
			List<OrderProductsBean> productList2 = requestBean.getOrderList();
			for(OrderProductsBean products2 : productList){
				double totalPrice = 0;

				Product product = (Product) session.get("com.limitless.services.engage.sellers.product.dao.Product",
						products2.getProductId());
				float discountedPrice = (float) ((Float) product.getProductPrice()
						- (product.getProductPrice() * (product.getDiscountRate() / 100)));
				totalPrice = products2.getQuantity() * discountedPrice;

				OrderDetails details = new OrderDetails();
				details.setOrderId(orderId);
				details.setProductId(products2.getProductId());
				details.setQuantity(products2.getQuantity());
				details.setUnitPrice(totalPrice);
				session.persist(details);
			}
			responseBean.setOrderId(orderId);
			responseBean.setTotalAmount(totalAmount);
			responseBean.setMessage("Success");
			transaction.commit();
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("Adding order failed : " + re);
			throw re;
		}
		finally {
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return responseBean;
	}
	
	public OrderPaymentModeUpdateResponseBean updatePaymentMode(int orderId, String paymentMode){
		log.debug("updating payment mode");
		OrderPaymentModeUpdateResponseBean responseBean = new OrderPaymentModeUpdateResponseBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			Orders order = (Orders) session
					.get("com.limitless.services.engage.order.dao.Orders", orderId);
			if(order!=null){
				if(paymentMode.equals("PAID")){
					order.setPaymentMode(paymentMode);
					session.update(order);
				}
				else if(paymentMode.equals("POD")){
					order.setPaymentMode(paymentMode);
					session.update(order);
				}
				else if(paymentMode.equals("FAILED")){
					order.setPaymentMode(paymentMode);
					session.update(order);
				}
				responseBean.setOrderId(orderId);
				responseBean.setPaymentMode(paymentMode);
				responseBean.setMessage("Success");
			}
			transaction.commit();
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("updating payment mode failed : " + re);
			//throw re;
		}
		finally {
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return responseBean;
	}
	
	public OrderStatusResponseBean orderStatusUpdate(int orderId, int statusCode){
		log.debug("updating the order status : " + orderId);
		OrderStatusResponseBean responseBean = new OrderStatusResponseBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			Orders order = (Orders) session
					.get("com.limitless.services.engage.order.dao.Orders", orderId);
			if(statusCode == 1){
				responseBean.setPrevStatus(order.getOrderStatus());
				if(order.getPaymentMode()!=null && order.getPaymentMode().equals("PAID") && order.getOrderStatus().equals("ORDER_PROCESSED")){
					order.setOrderStatus("ORDER_DELIVERED");
				}
				else{
					order.setOrderStatus("ORDER_RECEIVED");
				}
			}
			else if(statusCode == 2){
				responseBean.setPrevStatus(order.getOrderStatus());
				order.setOrderStatus("ORDER_PROCESSED");
			}
			else if(statusCode == 3){
				responseBean.setPrevStatus(order.getOrderStatus());
				order.setOrderStatus("ORDER_DELIVERED");
			}
			else if(statusCode == 4){
				responseBean.setPrevStatus(order.getOrderStatus());
				order.setOrderStatus("ORDER_CANCELED");
			}
			else if(statusCode == 5){
				responseBean.setPrevStatus(order.getOrderStatus());
				order.setOrderStatus("PROCESS_FAILED");
			}
			session.update(order);
			Orders order2 = (Orders) session
					.get("com.limitless.services.engage.order.dao.Orders", orderId);
			responseBean.setOrderId(orderId);
			responseBean.setCurrentStatus(order2.getOrderStatus());
			responseBean.setMessage("Success");
			responseBean.setUpdatedTime(order2.getOrderUpdatedTime().toString());
			transaction.commit();
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("updating the order status failed : " + re);
		}
		finally {
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return responseBean;
	}
	
	public OrderSummaryResponseBean sellerOrderSummary(int sellerId) throws Exception{
		log.debug("getting seller order summary");
		OrderSummaryResponseBean responseBean = new OrderSummaryResponseBean();
		List<OrdersListBean> ordersList = new ArrayList<OrdersListBean>();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			EngageSeller seller = (EngageSeller) session
					.get("com.limitless.services.engage.dao.EngageSeller", sellerId);
			if(seller!=null){
				String sellerName = seller.getSellerShopName();
				String sellerMobileNumber = seller.getSellerMobileNumber();
				int citrusSellerId = seller.getCitrusSellerId();

				Criteria criteria = session.createCriteria(Orders.class);
				Criterion sidCriterion = Restrictions.eq("sellerId", sellerId);
				Criterion statusCriterion = Restrictions.ne("orderStatus", "ORDER_INITIATED");
				LogicalExpression logicalExpression = Restrictions.and(sidCriterion, statusCriterion);
				criteria.add(logicalExpression);
				criteria.addOrder(Order.desc("orderId"));
				List<Orders> orderList = criteria.list();
				log.debug("order size : " + orderList.size());
				if (orderList.size() > 0) {
					for (Orders order : orderList) {
						OrdersListBean listBean = new OrdersListBean();
						listBean.setOrderId(order.getOrderId());
						listBean.setCustomerId(order.getCustomerId());
						EngageCustomer customer = (EngageCustomer) session
								.get("com.limitless.services.engage.dao.EngageCustomer", order.getCustomerId());
						listBean.setCustomerName(customer.getCustomerName());
						listBean.setCustomerMobileNumber(customer.getCustomerMobileNumber());
						listBean.setSellerId(sellerId);
						listBean.setSellerName(sellerName);
						listBean.setCitrusSellerId(citrusSellerId);
						listBean.setSellerMobileNumber(sellerMobileNumber);
						listBean.setTotalAmount(order.getTotalAmount());
						listBean.setPaymentMode(order.getPaymentMode());
						String gmtTime = order.getOrderCreatedTime().toString();
						SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date dateTxn = sdf3.parse(gmtTime);
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(dateTxn);
						calendar.add(Calendar.HOUR, 5);
						calendar.add(Calendar.MINUTE, 30);
						String localTime = sdf3.format(calendar.getTime());
						listBean.setTime(localTime);
						listBean.setOrderStatus(order.getOrderStatus());
						CustomerAddressBook address = (CustomerAddressBook) session
								.get("com.limitless.services.engage.dao.CustomerAddressBook", order.getDeliveryAddress());
						if(address!=null){
							AddressListBean addressBean = new AddressListBean();
							addressBean.setCadId(address.getCabId());
							addressBean.setReceiverName(address.getReceiverName());
							addressBean.setCustomerId(address.getCustomerId());
							addressBean.setCustomerAddress1(address.getCustomerAddress1());
							addressBean.setCustomerAddress2(address.getCustomerAddress2());
							addressBean.setCustomerCity(address.getCustomerCity());
							addressBean.setCustomerState(address.getCustomerState());
							addressBean.setCustomerZip(address.getCustomerZip());
							addressBean.setCustomerLandmark(address.getCustomerLandmark());
							addressBean.setCustomerDeliveryMobile(address.getCustomerDeliveryMobile());
							listBean.setAddressBean(addressBean);
						}
						ordersList.add(listBean);
						listBean = null;
					}
					responseBean.setOrdersList(ordersList);
					responseBean.setMessage("Success");
				} else if (orderList.isEmpty()) {
					responseBean.setMessage("No Record Found");
				}
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
			log.error("getting seller order summary failed : " + re);
			throw re;
		}
		finally {
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return responseBean;
	}
	
	public OrderSummaryResponseBean customerOrderSummary(int customerId) throws Exception{
		log.debug("getting customer order summary");
		OrderSummaryResponseBean responseBean = new OrderSummaryResponseBean();
		List<OrdersListBean> ordersList = new ArrayList<OrdersListBean>();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			EngageCustomer customer = (EngageCustomer) session
					.get("com.limitless.services.engage.dao.EngageCustomer", customerId);
			if(customer!=null){
				String customerName = customer.getCustomerName();
				String customerMobileNumber = customer.getCustomerMobileNumber();
				
				Criteria criteria = session.createCriteria(Orders.class);
				Criterion cidCriterion = Restrictions.eq("customerId", customerId);
				Criterion statusCriterion = Restrictions.ne("orderStatus", "ORDER_INITIATED");
				LogicalExpression logicalExpression = Restrictions.and(cidCriterion, statusCriterion);
				criteria.add(logicalExpression);
				criteria.addOrder(Order.desc("orderId"));
				List<Orders> orderList = criteria.list();
				log.debug("orderlist size : " + orderList.size());
				if(orderList.size()>0){
					for(Orders order : orderList){
						OrdersListBean listBean = new OrdersListBean();
						listBean.setOrderId(order.getOrderId());
						listBean.setCustomerId(order.getCustomerId());
						listBean.setCustomerName(customerName);
						listBean.setCustomerMobileNumber(customerMobileNumber);
						listBean.setSellerId(order.getSellerId());
						EngageSeller seller = (EngageSeller) session
								.get("com.limitless.services.engage.dao.EngageSeller", order.getSellerId());
						listBean.setSellerName(seller.getSellerShopName());
						listBean.setSellerMobileNumber(seller.getSellerMobileNumber());
						listBean.setCitrusSellerId(seller.getCitrusSellerId());
						listBean.setTotalAmount(order.getTotalAmount());
						listBean.setPaymentMode(order.getPaymentMode());
						String gmtTime = order.getOrderCreatedTime().toString();
						SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date dateTxn = sdf3.parse(gmtTime);
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(dateTxn);
						calendar.add(Calendar.HOUR, 5);
						calendar.add(Calendar.MINUTE, 30);
						String localTime = sdf3.format(calendar.getTime());
						listBean.setTime(localTime);
						listBean.setOrderStatus(order.getOrderStatus());
						CustomerAddressBook address = (CustomerAddressBook) session
								.get("com.limitless.services.engage.dao.CustomerAddressBook", order.getDeliveryAddress());
						if(address!=null){
							AddressListBean addressBean = new AddressListBean();
							addressBean.setCadId(address.getCabId());
							addressBean.setReceiverName(address.getReceiverName());
							addressBean.setCustomerId(address.getCustomerId());
							addressBean.setCustomerAddress1(address.getCustomerAddress1());
							addressBean.setCustomerAddress2(address.getCustomerAddress2());
							addressBean.setCustomerCity(address.getCustomerCity());
							addressBean.setCustomerState(address.getCustomerState());
							addressBean.setCustomerZip(address.getCustomerZip());
							addressBean.setCustomerLandmark(address.getCustomerLandmark());
							addressBean.setCustomerDeliveryMobile(address.getCustomerDeliveryMobile());
							listBean.setAddressBean(addressBean);
						}
						ordersList.add(listBean);
						listBean = null;
					}
					responseBean.setOrdersList(ordersList);
					responseBean.setMessage("Success");
				}
				else if(orderList.isEmpty()){
					responseBean.setMessage("No Record Found");
				}
			}
			else{
				responseBean.setMessage("Failed");
			}
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("getting seller order summary failed : " + re);
			throw re;
		}
		finally {
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return responseBean;
	}
	
	public OrderDetailResponseBean getOrderById(int orderId){
		log.debug("getting order : " + orderId);
		OrderDetailResponseBean responseBean = new OrderDetailResponseBean();
		List<OrderProductsListBean> productsList = new ArrayList<OrderProductsListBean>();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(OrderDetails.class);
			criteria.add(Restrictions.eq("orderId", orderId));
			List<OrderDetails> detailsList = criteria.list();
			log.debug("order details size : " + detailsList.size());
			if(detailsList.size()>0){
				for(OrderDetails details : detailsList){
					OrderProductsListBean listBean = new OrderProductsListBean();
					listBean.setProductId(details.getProductId());
					Product product = (Product) session
							.get("com.limitless.services.engage.sellers.product.dao.Product", details.getProductId());
					listBean.setProductName(product.getProductName());
					listBean.setQuantity(details.getQuantity());
					listBean.setTotalPrice(details.getUnitPrice());
					listBean.setProductColor(product.getProductColor());
					listBean.setProductSize(product.getProductSizeText());
					productsList.add(listBean);
					listBean = null;
				}
				responseBean.setOrderId(orderId);
				responseBean.setProductsBean(productsList);
				responseBean.setMessage("Success");
			}
			else if(detailsList.isEmpty()){
				responseBean.setMessage("Failed");
			}
		}
		catch (RuntimeException re) {
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("getting order failed : " + re);
			throw re;
		}
		finally {
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return responseBean;
	}
	
	public OrderMailResponseBean sendMailOrderTxn(int orderId) throws Exception{
		log.debug("Sending email");
		OrderMailResponseBean responseBean = new OrderMailResponseBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			//getting order
			Orders order = (Orders) session
					.get("com.limitless.services.engage.order.dao.Orders", orderId);
			int customerId = order.getCustomerId();
			int sellerId = order.getSellerId();
			int addressId = order.getDeliveryAddress();
			//getting customer
			EngageCustomer customer = (EngageCustomer) session
					.get("com.limitless.services.engage.dao.EngageCustomer", customerId);
			String customerEmail = customer.getCustomerEmail99();
			String customerMobile = customer.getCustomerMobileNumber();
			log.debug("customer mail : " + customerEmail);
			//getting seller
			EngageSeller seller = (EngageSeller) session
					.get("com.limitless.services.engage.dao.EngageSeller", sellerId);	
			String sellerEmail = seller.getSellerEmail99();
			String sellerMobile = seller.getSellerMobileNumber();
			log.debug("seller mail : " + sellerEmail);
			String sellerExtraEmail = "";
			if(seller.getExtraEmails()!= null){
				sellerExtraEmail = ","+seller.getExtraEmails();
			}
			
			String receiverName = "";
			String addressLine1 = "";
			String addressLine2 = "";
			String city = "";
			String state = "";
			String zip = "";
			String landmark = "";
			String receiverMobile = "";
			
			CustomerAddressBook address = (CustomerAddressBook) session
					.get("com.limitless.services.engage.dao.CustomerAddressBook", addressId);
			if(address!=null){
				receiverName = address.getReceiverName();
				addressLine1 = address.getCustomerAddress1();
				addressLine2 = address.getCustomerAddress2();
				city = address.getCustomerCity();
				state = address.getCustomerState();
				zip = address.getCustomerZip();
				landmark = address.getCustomerLandmark();
				receiverMobile = address.getCustomerDeliveryMobile();
			}
			
			final String username = "transactions@limitlesscircle.com";
			final String password = "Engage@12E";
			String sendMailTo = customerEmail+","+sellerEmail+sellerExtraEmail;
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
				if(!order.getOrderStatus().equals("PROCESS_FAILED")){
					message.setFrom(new InternetAddress("transactions@limitlesscircle.com"));
					message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendMailTo));
					message.addRecipients(Message.RecipientType.BCC, InternetAddress.parse("orders@limitlesscircle.com"));
					double totalAmount = order.getTotalAmount();
					String mailContent = "";
					
					if(order.getOrderStatus().equals("ORDER_RECEIVED")){
						message.setSubject("Order Received Successfully. Order Reference Id : "+orderId);
					}
					else if(order.getOrderStatus().equals("ORDER_PROCESSED")){
						message.setSubject("Order Processed Successfully. Order Reference Id : "+orderId);
					}
					else if(order.getOrderStatus().equals("ORDER_DELIVERED")){
						message.setSubject("Order Delivered Successfully. Order Reference Id : "+orderId);
					}
					
					Criteria criteria = session.createCriteria(OrderDetails.class);
					criteria.add(Restrictions.eq("orderId", orderId));
					List<OrderDetails> detailsList = criteria.list();
					if(detailsList.size()>0){
						for(OrderDetails detail : detailsList){
							int quantity = detail.getQuantity();
							double totalPrice = detail.getUnitPrice();
							Product product = (Product) session
									.get("com.limitless.services.engage.sellers.product.dao.Product", detail.getProductId());
							String productName = product.getProductName();
							String productImage = product.getProduct_image();
							String productColor = "";
							if(product.getProductColor()!=null){
								productColor = product.getProductColor();
							}
							String productSize = "";
							if(product.getProductSizeText()!=null){
								productSize = product.getProductSizeText();
							}
							double productPrice = product.getProductPrice();
							float discountedPrice = (float) ((Float) product.getProductPrice() - (product.getProductPrice()*(product.getDiscountRate()/100)));
							
							mailContent += "<table><tr><td rowspan=3>"
									+"<img src="+productImage+" height=100 width=100>"
									+"</td><td><b>"+productName+"</b>&nbsp;MRP Rs:"+productPrice+"</td>"
									+"<td>Specs:<br>Color:"+productColor+"<br>Size:"+productSize
									+"<td>Quantity:&nbsp;"+quantity+"</td>"
									+"<td>Discounted Price:&nbsp;"+discountedPrice+"</td>"
									+"<td>Unit's Total Amount:&nbsp;"+totalPrice+"</td></tr></table>";
						}
					}
					if(order.getPaymentMode()!=null && order.getPaymentMode().equals("PAID")){
						mailContent +="<br><h2>Total Amount Paid Rs. "+totalAmount+"</h2>";
					}
					else if(order.getPaymentMode()!=null && order.getPaymentMode().equals("POD")){
						mailContent +="<br><h2>Total Amount To Be Paid Rs. "+totalAmount+"<sup>*</sup></h2>"
								+ "<br>";
					}
					mailContent += "<br>"
							+ "<b>Delivery Address</b><br>";
					if(order.getDeliveryAddress() == 0){
						mailContent += "NA / IN-SHOP - DELIVERY AT THE SHOP";
					}
					else if(order.getDeliveryAddress() != 0){
						mailContent += receiverName+"<br>"+addressLine1
								+ "<br>"+addressLine2+"<br>"+city+"<br>"+state+"<br>ZIP:"+zip+"<br>Mobile:"+receiverMobile+"<sup>**</sup>";
					}
					mailContent += "<br><br><font color=red><sup>*</sup>You have opted for Pay-On-Delivery.</font>"
							+ "<br>"
							+ "<font color=red><sup>**</sup>For delivery related queries to customer call this number.</font>";
					message.setContent(mailContent,"text/html");
				}
				else if(order.getOrderStatus().equals("PROCESS_FAILED") || order.getOrderStatus().equals("ORDER_CANCELED")){
					message.setFrom(new InternetAddress("transactions@limitlesscircle.com"));
					message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(customerEmail));
					message.addRecipients(Message.RecipientType.BCC, InternetAddress.parse("orders@limitlesscircle.com"));
					double totalAmount = order.getTotalAmount();
					String mailContent = "";
					if(order.getOrderStatus().equals("PROCESS_FAILED")){
						message.setSubject("Order Failed. Order Reference Id : "+orderId);
					}
					else if(order.getOrderStatus().equals("ORDER_CANCELED")){
						message.setSubject("Order Canceled. Order Reference Id : "+orderId);
					}
					Criteria criteria = session.createCriteria(OrderDetails.class);
					criteria.add(Restrictions.eq("orderId", orderId));
					List<OrderDetails> detailsList = criteria.list();
					if(detailsList.size()>0){
						for(OrderDetails detail : detailsList){
							int quantity = detail.getQuantity();
							double totalPrice = detail.getUnitPrice();
							Product product = (Product) session
									.get("com.limitless.services.engage.sellers.product.dao.Product", detail.getProductId());
							String productName = product.getProductName();
							String productImage = product.getProduct_image();
							double productPrice = product.getProductPrice();
							
							mailContent += "<table><tr><td rowspan=3>"
									+"<img src="+productImage+" height=100 width=100>"
									+ "</td><td><b>"+productName+"</b>&nbsp;Price Rs:"+productPrice+"</td>"
											+ "<td>Quantity:&nbsp;"+quantity+"</td>"
													+ "<td>Unit's Total Amount:&nbsp;"+totalPrice+"</td></tr></table>";	
						}
					}
					message.setContent(mailContent,"text/html");
				}
				Transport.send(message, message.getAllRecipients());
				responseBean.setOrderId(orderId);
				responseBean.setMessage("Success");
			}
			catch(Exception e){
				log.error("mail error : " + e);
				throw e;
			}
			transaction.commit();
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("sending email failed : " + re);
			throw re;
		}
		finally {
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return responseBean;
	}
	
	public InventoryUpdateResponseBean updateInventory(int orderId){
		log.debug("updating product inventory");
		InventoryUpdateResponseBean responseBean = new InventoryUpdateResponseBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(OrderDetails.class);
			criteria.add(Restrictions.eq("orderId", orderId));
			List<OrderDetails> detailsList = criteria.list();
			log.debug("orders list : "+detailsList.size());
			if(detailsList.size()>0){
				for(OrderDetails details : detailsList){
					ProductInventory inventory = (ProductInventory) session
							.get("com.limitless.services.engage.sellers.product.dao.ProductInventory", details.getProductId());
					if(inventory!=null){
						inventory.setProductSold(details.getQuantity() + inventory.getProductSold());
						inventory.setProductStock(inventory.getProductStock() - details.getQuantity());
						session.update(inventory);
						responseBean.setOrderId(orderId);
						responseBean.setMessage("Success");
					}
				}
			}
			else if(detailsList.isEmpty()){
				responseBean.setMessage("Failed");
			}
			transaction.commit();
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("updating inventory failed : " + re);
		}
		finally {
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return responseBean;
	}
	
	public void notificationToSeller(int orderId){
		log.debug("sending notification to seller");
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			Orders order = (Orders) session
					.get("com.limitless.services.engage.order.dao.Orders", orderId);
			if(order!=null){
				int customerId = order.getCustomerId();
				int sellerId =  order.getSellerId();
				EngageCustomer customer = (EngageCustomer) session
						.get("com.limitless.services.engage.dao.EngageCustomer", customerId);
				if(customer!=null){
					String customerName = customer.getCustomerName();
					String customerMobile = customer.getCustomerMobileNumber();
					
					EngageSeller seller = (EngageSeller) session
							.get("com.limitless.services.engage.dao.EngageSeller", sellerId);
					if(seller!=null){
						Criteria criteria = session.createCriteria(SellerDeviceIdMapper.class);
						criteria.add(Restrictions.eq("sellerId", sellerId));
						List<SellerDeviceIdMapper> mapperList = criteria.list();
						log.debug("mapperlist size : " + mapperList.size());
						if(mapperList.size()>0){
							for(SellerDeviceIdMapper mapper : mapperList){
								String to = mapper.getSellerDeviceId();
								RestaurantOrderFcmRequestBean fcmBean = new RestaurantOrderFcmRequestBean();
								fcmBean.setTo(to);
								fcmBean.setPriority("high");
								RestaurantOrderDataBean data = new RestaurantOrderDataBean();
								data.setBussinessType("restaurant");
								data.setTitle("Order Received Successfully");
								data.setBody(customerName + " placed order for Rs." + order.getTotalAmount());
								data.setOrderId(orderId);
								data.setCustomerMobile(customerMobile);
								fcmBean.setData(data);
								WebResource webResource2 = client.resource("https://fcm.googleapis.com/fcm/send");
								ClientResponse clientResponse = webResource2.type("application/json")
										.header("Authorization", "key=AIzaSyCE49LX2u8Op-LbqidMJfcKlH4Bh5opUos")
										.post(ClientResponse.class, fcmBean);
								System.out.println(clientResponse.getStatus());
								System.out.println(clientResponse.getEntity(String.class));
							}
						}
					}
				}
			}
			transaction.commit();
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("sending notification to seller failed : " + re);
		}
		finally {
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
	}
	
	public void notificationToCustomer(int orderId){
		log.debug("sending notification to customer");
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			Orders order = (Orders) session
					.get("com.limitless.services.engage.order.dao.Orders", orderId);
			if(order!=null){
				int customerId = order.getCustomerId();
				int sellerId =  order.getSellerId();
				EngageCustomer customer = (EngageCustomer) session
						.get("com.limitless.services.engage.dao.EngageCustomer", customerId);
				if(customer!=null){
					String customerName = customer.getCustomerName();
					String customerMobile = customer.getCustomerMobileNumber();
					
					EngageSeller seller = (EngageSeller) session
							.get("com.limitless.services.engage.dao.EngageSeller", sellerId);
					if(seller!=null){
						String to = customer.getDeviceId();
						String sellerShopName = seller.getSellerShopName();
						OrderFcmBean fcmBean = new OrderFcmBean();
						fcmBean.setTo(to);
						fcmBean.setPriority("high");
						OrderNotificationDataBean data = new OrderNotificationDataBean();
						data.setTitle("Order Placed Successfully");
						data.setBody("You placed for Rs."+order.getTotalAmount()+" to "+sellerShopName);
						data.setBussinessType("eCommerce");
						data.setOrderId(orderId);
						data.setCustomerName(customerName);
						data.setCustomerMobile(customerMobile);
						fcmBean.setData(data);
						WebResource webResource2 = client.resource("https://fcm.googleapis.com/fcm/send");
						ClientResponse clientResponse = webResource2.type("application/json")
								.header("Authorization", "key=AIzaSyAP4xJ6VMm4vpj2A1ocGDvvvwzxtUNuKI0")
								.post(ClientResponse.class, fcmBean);
						System.out.println(clientResponse.getStatus());
						System.out.println(clientResponse.getEntity(String.class));
					}
				}
			}
			transaction.commit();
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("sending notification to customer failed : " + re);
		}
		finally {
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
	}
	
}

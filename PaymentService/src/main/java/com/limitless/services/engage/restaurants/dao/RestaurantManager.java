package com.limitless.services.engage.restaurants.dao;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.limitless.services.engage.AddressListBean;
import com.limitless.services.engage.SellerRestaurantListBean;
import com.limitless.services.engage.dao.CustomerAddressBook;
import com.limitless.services.engage.dao.EngageCustomer;
import com.limitless.services.engage.dao.EngageSeller;
import com.limitless.services.engage.dao.SellerDeviceIdMapper;
import com.limitless.services.engage.dao.SellerPayamentsConfiguration;
import com.limitless.services.engage.restaurants.NewRestaurantCategoryRequestBean;
import com.limitless.services.engage.restaurants.NewRestaurantCategoryResponseBean;
import com.limitless.services.engage.restaurants.NewRestaurantItemRequestBean;
import com.limitless.services.engage.restaurants.NewRestaurantItemResponseBean;
import com.limitless.services.engage.restaurants.NewRestaurantSubcategoryRequestBean;
import com.limitless.services.engage.restaurants.NewRestaurantSubcategoryResponseBean;
import com.limitless.services.engage.restaurants.RestaurantBean;
import com.limitless.services.engage.restaurants.RestaurantCategoryListBean;
import com.limitless.services.engage.restaurants.RestaurantItemListBean;
import com.limitless.services.engage.restaurants.RestaurantOrderBean;
import com.limitless.services.engage.restaurants.RestaurantOrderDataBean;
import com.limitless.services.engage.restaurants.RestaurantOrderDetailsResponseBean;
import com.limitless.services.engage.restaurants.RestaurantOrderFcmRequestBean;
import com.limitless.services.engage.restaurants.RestaurantOrderItemsBean;
import com.limitless.services.engage.restaurants.RestaurantOrderItemsListBean;
import com.limitless.services.engage.restaurants.RestaurantOrderListBean;
import com.limitless.services.engage.restaurants.RestaurantOrderRequestBean;
import com.limitless.services.engage.restaurants.RestaurantOrderResponseBean;
import com.limitless.services.engage.restaurants.RestaurantOrderStatusUpdateRequestBean;
import com.limitless.services.engage.restaurants.RestaurantOrderStatusUpdateResponseBean;
import com.limitless.services.engage.restaurants.RestaurantSellerResponseBean;
import com.limitless.services.engage.restaurants.RestaurantSubcategoryListBean;
import com.limitless.services.payment.PaymentService.util.HibernateUtil;
import com.limitless.services.payment.PaymentService.util.RestClientUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class RestaurantManager {
	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	Client client = RestClientUtil.createClient();
	private static final Log log = LogFactory.getLog(RestaurantManager.class);

	public RestaurantBean getRestaurantDetails(int restaurantId) {
		log.debug("getting restaurant details");
		RestaurantBean bean = null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			Restaurants restaurant = (Restaurants) session
					.get("com.limitless.services.engage.restaurants.dao.Restaurants", restaurantId);
			if (restaurant != null) {
				List<RestaurantCategoryListBean> categorysList = new ArrayList<RestaurantCategoryListBean>();
				bean = new RestaurantBean();
				EngageSeller seller = (EngageSeller) session.get("com.limitless.services.engage.dao.EngageSeller",
						restaurant.getSellerId());
				if (seller != null) {
					Criteria criteria2 = session.createCriteria(SellerPayamentsConfiguration.class);
					criteria2.add(Restrictions.eq("sellerId", seller.getSellerId()));
					List<SellerPayamentsConfiguration> configList = criteria2.list();
					if (configList.size() == 1) {
						for (SellerPayamentsConfiguration config : configList) {
							bean.setRestaurantSellerCitrusId(config.getCitrusSellerId());
						}
					}
					bean.setRestaurantSellerName(seller.getSellerName());
					bean.setRestaurantSellerEmail(seller.getSellerEmail99());
				}
				bean.setRestaurantId(restaurantId);
				bean.setRestaurantName(restaurant.getRestaurantName());
				bean.setRestaurantCity(restaurant.getRestaurantCity());
				bean.setRestaurantPhone(restaurant.getRestaurantPhone());
				bean.setRestaurantOrderStyle(restaurant.getRestaurantOrderStyle());
				bean.setEatInPod(restaurant.getEatInPod());
				bean.setTakeAwayPod(restaurant.getTakeAwayPod());
				bean.setHomeDeliveryPod(restaurant.getHomeDeliveryPod());
				Criteria criteria = session.createCriteria(RestaurantCategory.class);
				criteria.add(Restrictions.eq("restaurantId", restaurantId));
				List<RestaurantCategory> categorys = criteria.list();
				log.debug("category list size : " + categorys.size());
				if (categorys.size() > 0) {
					for (RestaurantCategory category : categorys) {
						RestaurantCategoryListBean cBean = new RestaurantCategoryListBean();
						cBean.setCategoryId(category.getCategoryId());
						cBean.setCategoryName(category.getCategoryName());
						cBean.setRestaurantId(category.getRestaurantId());
						List<RestaurantSubcategoryListBean> subcategorysList = new ArrayList<RestaurantSubcategoryListBean>();
						Criteria criteria2 = session.createCriteria(RestaurantSubCategory.class);
						criteria2.add(Restrictions.eq("categoryId", category.getCategoryId()));
						List<RestaurantSubCategory> subcategorys = criteria2.list();
						log.debug("sub category list size : " + subcategorys.size());
						if (subcategorys.size() > 0) {
							for (RestaurantSubCategory subcategory : subcategorys) {
								List<RestaurantItemListBean> itemsList1 = new ArrayList<RestaurantItemListBean>();
								RestaurantSubcategoryListBean scBean = new RestaurantSubcategoryListBean();
								scBean.setCategoryId(subcategory.getCategoryId());
								scBean.setSubCategoryId(subcategory.getSubCategoryId());
								scBean.setSubCategoryName(subcategory.getSubcategoryName());

								Criteria criteria3 = session.createCriteria(RestaurantItems.class);
								criteria3.add(Restrictions.eq("subcategoryId", subcategory.getSubCategoryId()));
								List<RestaurantItems> items1 = criteria3.list();
								log.debug("subcategory items size : " + items1.size());
								if (items1.size() > 0) {
									for (RestaurantItems item1 : items1) {
										RestaurantItemListBean itemBean = new RestaurantItemListBean();
										itemBean.setItemId(item1.getItemId());
										itemBean.setItemName(item1.getItemName());
										itemBean.setItemPrice(item1.getItemPrice());
										itemBean.setItemType(item1.getItemType());
										itemBean.setItemImage(item1.getItemImage());
										itemBean.setItemAvailable(item1.getAvailable());
										itemBean.setItemChefSpl(item1.getChefSpl());
										itemBean.setItemRecommended(item1.getRecommended());
										itemBean.setItemLunch(item1.getLunch());
										itemBean.setItemBreakfast(item1.getBreakfast());
										itemBean.setItemDinner(item1.getDinner());
										itemBean.setItemDiscountRate(item1.getDiscountRate());

										itemsList1.add(itemBean);
										itemBean = null;
									}
									scBean.setItemsList(itemsList1);
								}
								subcategorysList.add(scBean);
								scBean = null;
							}
							List<RestaurantItemListBean> itemsList3 = new ArrayList<RestaurantItemListBean>();
							Criteria criteria6 = session.createCriteria(RestaurantItems.class);
							Criterion cidCriterion = Restrictions.eq("categoryId", category.getCategoryId());
							Criterion scidCriterion = Restrictions.eq("subcategoryId", 0);
							LogicalExpression logExp = Restrictions.and(cidCriterion, scidCriterion);
							criteria6.add(logExp);
							List<RestaurantItems> items4 = criteria6.list();
							log.debug("items list size : " + items4.size());
							if (items4.size() > 0) {
								for (RestaurantItems item4 : items4) {
									RestaurantItemListBean itemBean = new RestaurantItemListBean();
									itemBean.setItemId(item4.getItemId());
									itemBean.setItemName(item4.getItemName());
									itemBean.setItemPrice(item4.getItemPrice());
									itemBean.setItemType(item4.getItemType());
									itemBean.setItemImage(item4.getItemImage());
									itemBean.setItemAvailable(item4.getAvailable());
									itemBean.setItemChefSpl(item4.getChefSpl());
									itemBean.setItemRecommended(item4.getRecommended());
									itemBean.setItemLunch(item4.getLunch());
									itemBean.setItemBreakfast(item4.getBreakfast());
									itemBean.setItemDinner(item4.getDinner());
									itemBean.setItemDiscountRate(item4.getDiscountRate());

									itemsList3.add(itemBean);
									itemBean = null;
								}
								cBean.setItemsList(itemsList3);
							}
							cBean.setSubcategorysList(subcategorysList);
							categorysList.add(cBean);
						} else if (subcategorys.isEmpty()) {
							List<RestaurantItemListBean> itemsList2 = new ArrayList<RestaurantItemListBean>();
							Criteria criteria4 = session.createCriteria(RestaurantItems.class);
							criteria4.add(Restrictions.eq("categoryId", category.getCategoryId()));
							List<RestaurantItems> items2 = criteria4.list();
							log.debug("category items size : " + items2.size());
							if (items2.size() > 0) {
								for (RestaurantItems item2 : items2) {
									RestaurantItemListBean itemBean = new RestaurantItemListBean();
									itemBean.setItemId(item2.getItemId());
									itemBean.setItemName(item2.getItemName());
									itemBean.setItemPrice(item2.getItemPrice());
									itemBean.setItemType(item2.getItemType());
									itemBean.setItemImage(item2.getItemImage());
									itemBean.setItemAvailable(item2.getAvailable());
									itemBean.setItemChefSpl(item2.getChefSpl());
									itemBean.setItemRecommended(item2.getRecommended());
									itemBean.setItemLunch(item2.getLunch());
									itemBean.setItemBreakfast(item2.getBreakfast());
									itemBean.setItemDinner(item2.getDinner());
									itemBean.setItemDiscountRate(item2.getDiscountRate());

									itemsList2.add(itemBean);
									itemBean = null;
								}
								cBean.setItemsList(itemsList2);
							}
							categorysList.add(cBean);
						}

						bean.setCategorysList(categorysList);
					}
				} else if (categorys.isEmpty()) {
					List<RestaurantItemListBean> itemsList3 = new ArrayList<RestaurantItemListBean>();
					Criteria criteria5 = session.createCriteria(RestaurantItems.class);
					criteria5.add(Restrictions.eq("restaurantId", restaurantId));
					List<RestaurantItems> items3 = criteria5.list();
					log.debug("items size : " + items3.size());
					if (items3.size() > 0) {
						for (RestaurantItems item3 : items3) {
							RestaurantItemListBean itemBean = new RestaurantItemListBean();
							itemBean.setItemId(item3.getItemId());
							itemBean.setItemName(item3.getItemName());
							itemBean.setItemPrice(item3.getItemPrice());
							itemBean.setItemType(item3.getItemType());
							itemBean.setItemImage(item3.getItemImage());
							itemBean.setItemAvailable(item3.getAvailable());
							itemBean.setItemChefSpl(item3.getChefSpl());
							itemBean.setItemRecommended(item3.getRecommended());
							itemBean.setItemLunch(item3.getLunch());
							itemBean.setItemBreakfast(item3.getBreakfast());
							itemBean.setItemDinner(item3.getDinner());
							itemBean.setItemDiscountRate(item3.getDiscountRate());

							itemsList3.add(itemBean);
							itemBean = null;
						}
						bean.setItemsList(itemsList3);
					}
				}
			} else {
				bean = null;
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("getting restaurant failed : " + re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return bean;
	}

	public List<SellerRestaurantListBean> getSellerRestaurants(int sellerId) {
		log.debug("getting seller restaurants");
		List<SellerRestaurantListBean> restaurantsList = new ArrayList<SellerRestaurantListBean>();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			Criteria criteria = session.createCriteria(Restaurants.class);
			criteria.add(Restrictions.eq("sellerId", sellerId));
			List<Restaurants> restaurants = criteria.list();
			log.debug("restaurants size : " + restaurants.size());
			if (restaurants.size() > 0) {
				for (Restaurants restaurant : restaurants) {
					SellerRestaurantListBean bean = new SellerRestaurantListBean();
					bean.setRestaurantId(restaurant.getRestaurantId());
					bean.setRestaurantName(restaurant.getRestaurantName());
					bean.setRestaurantCity(restaurant.getRestaurantCity());
					bean.setEatInPod(restaurant.getEatInPod());
					bean.setTakeAwayPod(restaurant.getTakeAwayPod());
					bean.setHomeDeliveryPod(restaurant.getHomeDeliveryPod());
					restaurantsList.add(bean);
					bean = null;
				}
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("getting restaurant failed : " + re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return restaurantsList;
	}

	public RestaurantOrderResponseBean createOrder(RestaurantOrderRequestBean requestBean) {
		log.debug("new order");
		RestaurantOrderResponseBean responseBean = null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			String sellerName = "";
			int citrusSellerId = 0;
			String sellerEmail = "";
			String sellerMobileNumber = "";

			float totalAmount = 0;
			Restaurants restaurants = (Restaurants) session
					.get("com.limitless.services.engage.restaurants.dao.Restaurants", requestBean.getRestaurantId());
			if (restaurants != null) {
				int sellerId = restaurants.getSellerId();
				for (RestaurantOrderItemsBean items : requestBean.getItemsList()) {
					RestaurantItems item = (RestaurantItems) session
							.get("com.limitless.services.engage.restaurants.dao.RestaurantItems", items.getItemId());
					if (item != null) {
						float totalPrice = item.getItemPrice() * items.getItemQuantity();
						totalAmount += totalPrice;
					}
				}
				log.debug("total amount : " + totalAmount);

				RestaurantOrder order = new RestaurantOrder();
				order.setCustomerId(requestBean.getCustomerId());
				order.setRestaurantId(requestBean.getRestaurantId());
				order.setOrderType(requestBean.getOrderStyle());
				order.setDeliveryAddressId(requestBean.getDeliveryAddressId());
				if (requestBean.getPaymentMode() != null && requestBean.getPaymentMode().equals("POD")) {
					order.setOrderStatus("ORDER_RECIEVED");
					order.setPaymentMode(requestBean.getPaymentMode());
				}
				order.setOrderStatus("ORDER_INITIATED");
				order.setTotalAmount(totalAmount);
				order.setDeliveryFee(requestBean.getOrderDeliveryFee());
				order.setPackagingFee(requestBean.getPackagingFee());
				session.persist(order);
				log.debug("order id : " + order.getOrderId());
				int orderId = order.getOrderId();

				for (RestaurantOrderItemsBean items : requestBean.getItemsList()) {
					RestaurantOrderDetails orderDetails = new RestaurantOrderDetails();
					orderDetails.setItemId(items.getItemId());
					orderDetails.setOrderId(orderId);
					orderDetails.setQuantity(items.getItemQuantity());
					RestaurantItems item = (RestaurantItems) session
							.get("com.limitless.services.engage.restaurants.dao.RestaurantItems", items.getItemId());
					if (item != null) {
						float totalPrice = item.getItemPrice() * items.getItemQuantity();
						orderDetails.setItemPrice(item.getItemPrice());
						orderDetails.setTotalPrice(totalPrice);
					}
					session.persist(orderDetails);
				}
				if (orderId > 0) {
					EngageSeller seller = (EngageSeller) session.get("com.limitless.services.engage.dao.EngageSeller",
							sellerId);
					if (seller != null) {
						sellerName = seller.getSellerName();
						sellerEmail = seller.getSellerEmail99();
						sellerMobileNumber = seller.getSellerMobileNumber();
						Criteria criteria2 = session.createCriteria(SellerPayamentsConfiguration.class);
						criteria2.add(Restrictions.eq("sellerId", seller.getSellerId()));
						List<SellerPayamentsConfiguration> configList = criteria2.list();
						if (configList.size() == 1) {
							for (SellerPayamentsConfiguration config : configList) {
								citrusSellerId = config.getCitrusSellerId();
							}
						}
					}
					responseBean = new RestaurantOrderResponseBean();
					responseBean.setRestaurantOrderId(orderId);
					responseBean.setTotalAmount(totalAmount);
					responseBean.setOrderDeliveryFee(requestBean.getOrderDeliveryFee());
					if (requestBean.getPaymentMode() != null) {
						responseBean.setPaymentMode(requestBean.getPaymentMode());
					}
					responseBean.setSellerId(sellerId);
					responseBean.setCitrusSellerId(citrusSellerId);
					responseBean.setSellerEmail(sellerEmail);
					responseBean.setSellerName(sellerName);
					responseBean.setSellerMobileName(sellerMobileNumber);
				}
			}

			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("new order failed : " + re);
			// throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}

	public RestaurantOrderStatusUpdateResponseBean orderStatusUpdate(
			RestaurantOrderStatusUpdateRequestBean requestBean) {
		log.debug("updating order status");
		RestaurantOrderStatusUpdateResponseBean responseBean = null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			Restaurants restaurant = (Restaurants) session
					.get("com.limitless.services.engage.restaurants.dao.Restaurants", requestBean.getRestaurantId());
			if (restaurant != null) {
				RestaurantOrder order = (RestaurantOrder) session
						.get("com.limitless.services.engage.restaurants.dao.RestaurantOrder", requestBean.getOrderId());
				if (order != null && requestBean.getRestaurantId() == order.getRestaurantId()) {
					responseBean = new RestaurantOrderStatusUpdateResponseBean();
					responseBean.setOrderId(requestBean.getOrderId());
					responseBean.setRestaurantId(requestBean.getRestaurantId());
					responseBean.setPreviousStatus(order.getOrderStatus());
					order.setOrderStatus(requestBean.getOrderStatusString());
					responseBean.setCurrentStatus(requestBean.getOrderStatusString());

					session.update(order);
				}
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("updating order status failed : " + re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}

	public RestaurantOrderStatusUpdateResponseBean orderStatusUpdate(int orderId, int status) {
		log.debug("upadting order status");
		RestaurantOrderStatusUpdateResponseBean responseBean = new RestaurantOrderStatusUpdateResponseBean();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			RestaurantOrder order = (RestaurantOrder) session
					.get("com.limitless.services.engage.restaurants.dao.RestaurantOrder", orderId);
			if (order != null) {
				if (status == 1) {
					order.setOrderStatus("ORDER_RECIEVED");
					order.setPaymentMode("PAID");
					session.update(order);
					responseBean.setOrderId(orderId);
					responseBean.setCurrentStatus("ORDER_RECIEVED");
				} else if (status == 2) {
					order.setOrderStatus("PROCESS_FAILED");
					order.setPaymentMode("FAILED");
					session.update(order);
					responseBean.setOrderId(orderId);
					responseBean.setCurrentStatus("PROCESS_FAILED");
				} else if (status == 3) {
					order.setOrderStatus("ORDER_RECIEVED");
					session.update(order);
					responseBean.setOrderId(orderId);
					responseBean.setCurrentStatus("ORDER_RECIEVED");
				}
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("updating order status failed : " + re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}

	public RestaurantOrderBean getCustomerOrderSummary(int customerId) throws Exception {
		log.debug("getting customer order summary");
		RestaurantOrderBean bean = null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			EngageCustomer customer = (EngageCustomer) session.get("com.limitless.services.engage.dao.EngageCustomer",
					customerId);
			if (customer != null) {
				String customerName = customer.getCustomerName();
				String customerPhone = customer.getCustomerMobileNumber();

				Criteria criteria = session.createCriteria(RestaurantOrder.class);
				Junction condition = Restrictions.conjunction().add(Restrictions.eq("customerId", customerId))
						.add(Restrictions.ne("orderStatus", "ORDER_INITIATED"));
				criteria.add(condition);
				criteria.addOrder(Order.desc("orderId"));
				List<RestaurantOrder> orders = criteria.list();
				log.debug("order list size : " + orders.size());
				if (orders.size() > 0) {
					List<RestaurantOrderListBean> orderList = new ArrayList<RestaurantOrderListBean>();
					for (RestaurantOrder order : orders) {
						RestaurantOrderListBean listBean = new RestaurantOrderListBean();
						listBean.setCustomerId(customerId);
						listBean.setOrderId(order.getOrderId());
						listBean.setOrderStyle(order.getOrderType());
						listBean.setOrderTotalAmount((float) order.getTotalAmount());
						listBean.setOrderDeliveryFee(order.getDeliveryFee());
						listBean.setPackagingFee(order.getPackagingFee());
						listBean.setCustomerId(order.getCustomerId());
						listBean.setCustomerName(customerName);
						listBean.setCustomerMobileNumber(customerPhone);
						listBean.setRestaurantOrderStatus(order.getOrderStatus());
						if (order.getPaymentMode() != null) {
							listBean.setPaymentMode(order.getPaymentMode());
						}
						// String gmtTime = order.getOrderTime().toString();
						SimpleDateFormat sdf3 = new SimpleDateFormat(" dd MMM yyyy HH:mm:ss");
						Date gmtDate = order.getOrderTime();
						// Date dateTxn = sdf3.parse(gmtDate);
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(gmtDate);
						calendar.add(Calendar.HOUR, 5);
						calendar.add(Calendar.MINUTE, 30);
						String localTime = sdf3.format(calendar.getTime());
						listBean.setRestaurantOrderTime(localTime);
						listBean.setRestaurantId(order.getRestaurantId());
						int sellerId = 0;
						Restaurants restaurant = (Restaurants) session.get(
								"com.limitless.services.engage.restaurants.dao.Restaurants", order.getRestaurantId());
						if (restaurant != null) {
							listBean.setRestaurantName(restaurant.getRestaurantName());
							listBean.setRestaurantCity(restaurant.getRestaurantCity());
							listBean.setRestaurantMobileNumber(restaurant.getRestaurantPhone());
							sellerId = restaurant.getSellerId();
						}

						Criteria criteria3 = session.createCriteria(RestaurantOrderDetails.class);
						criteria3.add(Restrictions.eq("orderId", order.getOrderId()));
						List<RestaurantOrderDetails> detailsList = criteria3.list();
						log.debug("details size : " + detailsList.size());
						if (detailsList.size() > 0) {
							String itemNames = "";
							for (RestaurantOrderDetails details : detailsList) {
								RestaurantItems item = (RestaurantItems) session.get(
										"com.limitless.services.engage.restaurants.dao.RestaurantItems",
										details.getItemId());
								if (item != null) {
									itemNames = itemNames + item.getItemName() + "[" + details.getQuantity() + "]. ";
								}
							}
							listBean.setOrderItemNames(itemNames);
						}

						EngageSeller seller = (EngageSeller) session
								.get("com.limitless.services.engage.dao.EngageSeller", sellerId);
						if (seller != null) {
							listBean.setSellerId(sellerId);
							listBean.setSellerName(seller.getSellerName());
							listBean.setRestaurantAddress(seller.getSellerAddress());
							Criteria criteria2 = session.createCriteria(SellerPayamentsConfiguration.class);
							criteria2.add(Restrictions.eq("sellerId", seller.getSellerId()));
							List<SellerPayamentsConfiguration> configList = criteria2.list();
							log.debug("configs size : " + configList.size());
							if (configList.size() > 0) {
								for (SellerPayamentsConfiguration config : configList) {
									listBean.setCitrusSellerId(config.getCitrusSellerId());
									if (config.getPayOnDelivery() == 1) {
										listBean.setPodAvailable(true);
									} else if (config.getPayOnDelivery() == 0) {
										listBean.setPodAvailable(false);
									}
									listBean.setDeliveryMinAmount(config.getDevliveryMInAmt());
									listBean.setDeliveryFee(config.getConvenienceFee());
									listBean.setDeliveryRadius(config.getDeliveryRadius());
									if (config.getConvenienceFee() == 1) {
										listBean.setConvenienceFee(true);
									} else if (config.getConvenienceFee() == 0) {
										listBean.setConvenienceFee(false);
									}
								}
							}
						}

						CustomerAddressBook address = (CustomerAddressBook) session.get(
								"com.limitless.services.engage.dao.CustomerAddressBook", order.getDeliveryAddressId());
						if (address != null) {
							AddressListBean addressBean = new AddressListBean();
							addressBean.setCadId(address.getCabId());
							addressBean.setReceiverName(address.getReceiverName());
							addressBean.setCustomerAddress1(address.getCustomerAddress1());
							addressBean.setCustomerAddress2(address.getCustomerAddress2());
							addressBean.setCustomerCity(address.getCustomerCity());
							addressBean.setCustomerState(address.getCustomerState());
							addressBean.setCustomerZip(address.getCustomerZip());
							addressBean.setCustomerDeliveryMobile(address.getCustomerDeliveryMobile());
							addressBean.setCustomerLandmark(address.getCustomerLandmark());
							listBean.setDeliveryAddress(addressBean);
						}
						orderList.add(listBean);
					}
					bean = new RestaurantOrderBean();
					bean.setCustomerId(customerId);
					bean.setOrderList(orderList);
				}
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("getting customer order summary failed : " + re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return bean;
	}

	public RestaurantOrderBean getRestaurantOrderSummary(int restaurantId) throws Exception {
		log.debug("getting restaurant order summary");
		RestaurantOrderBean bean = null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			Restaurants restaurant = (Restaurants) session
					.get("com.limitless.services.engage.restaurants.dao.Restaurants", restaurantId);
			if (restaurant != null) {
				String restaurantName = restaurant.getRestaurantName();
				String restaurantCity = restaurant.getRestaurantCity();
				String restaurantPhone = restaurant.getRestaurantPhone();
				int sellerId = restaurant.getSellerId();
				String sellerName = "";
				String restaurantAddress = "";
				int citrusSellerId = 0;
				EngageSeller seller = (EngageSeller) session.get("com.limitless.services.engage.dao.EngageSeller",
						sellerId);
				if (seller != null) {
					sellerName = seller.getSellerName();
					restaurantAddress = seller.getSellerAddress();
					Criteria criteria2 = session.createCriteria(SellerPayamentsConfiguration.class);
					criteria2.add(Restrictions.eq("sellerId", seller.getSellerId()));
					List<SellerPayamentsConfiguration> configList = criteria2.list();
					if (configList.size() == 1) {
						for (SellerPayamentsConfiguration config : configList) {
							citrusSellerId = config.getCitrusSellerId();
						}
					}
				}

				Criteria criteria = session.createCriteria(RestaurantOrder.class);
				Junction condition = Restrictions.conjunction().add(Restrictions.eq("restaurantId", restaurantId))
						.add(Restrictions.ne("orderStatus", "ORDER_INITIATED"));
				criteria.add(condition);
				criteria.addOrder(Order.desc("orderId"));
				List<RestaurantOrder> orders = criteria.list();
				log.debug("order list size : " + orders.size());
				if (orders.size() > 0) {
					List<RestaurantOrderListBean> orderList = new ArrayList<RestaurantOrderListBean>();
					for (RestaurantOrder order : orders) {
						RestaurantOrderListBean listBean = new RestaurantOrderListBean();
						listBean.setRestaurantId(restaurantId);
						listBean.setOrderId(order.getOrderId());
						listBean.setOrderStyle(order.getOrderType());
						listBean.setRestaurantOrderStatus(order.getOrderStatus());
						if (order.getPaymentMode() != null) {
							listBean.setPaymentMode(order.getPaymentMode());
						}
						SimpleDateFormat sdf3 = new SimpleDateFormat(" dd MMM yyyy HH:mm:ss");
						Date gmtDate = order.getOrderTime();
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(gmtDate);
						calendar.add(Calendar.HOUR, 5);
						calendar.add(Calendar.MINUTE, 30);
						String localTime = sdf3.format(calendar.getTime());
						listBean.setRestaurantOrderTime(localTime);
						listBean.setOrderTotalAmount((float) order.getTotalAmount());
						listBean.setOrderDeliveryFee(order.getDeliveryFee());
						listBean.setPackagingFee(order.getPackagingFee());
						EngageCustomer customer = (EngageCustomer) session
								.get("com.limitless.services.engage.dao.EngageCustomer", order.getCustomerId());
						if (customer != null) {
							listBean.setCustomerId(order.getCustomerId());
							listBean.setCustomerName(customer.getCustomerName());
							listBean.setCustomerMobileNumber(customer.getCustomerMobileNumber());
						}
						listBean.setRestaurantId(order.getRestaurantId());
						listBean.setRestaurantName(restaurantName);
						listBean.setRestaurantCity(restaurantCity);
						listBean.setRestaurantAddress(restaurantAddress);
						listBean.setRestaurantMobileNumber(restaurantPhone);
						listBean.setCitrusSellerId(citrusSellerId);
						listBean.setSellerId(sellerId);
						listBean.setSellerName(sellerName);
						CustomerAddressBook address = (CustomerAddressBook) session.get(
								"com.limitless.services.engage.dao.CustomerAddressBook", order.getDeliveryAddressId());
						if (address != null) {
							AddressListBean addressBean = new AddressListBean();
							addressBean.setCadId(address.getCabId());
							addressBean.setReceiverName(address.getReceiverName());
							addressBean.setCustomerAddress1(address.getCustomerAddress1());
							addressBean.setCustomerAddress2(address.getCustomerAddress2());
							addressBean.setCustomerCity(address.getCustomerCity());
							addressBean.setCustomerState(address.getCustomerState());
							addressBean.setCustomerZip(address.getCustomerZip());
							addressBean.setCustomerDeliveryMobile(address.getCustomerDeliveryMobile());
							addressBean.setCustomerLandmark(address.getCustomerLandmark());
							listBean.setDeliveryAddress(addressBean);
						}
						orderList.add(listBean);
					}
					bean = new RestaurantOrderBean();
					bean.setRestaurantId(restaurantId);
					bean.setOrderList(orderList);
				}
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("getting restaurant order summary failed : " + re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return bean;
	}

	public RestaurantOrderDetailsResponseBean getOrderById(int orderId) {
		log.debug("getting order details");
		RestaurantOrderDetailsResponseBean responseBean = null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			RestaurantOrder order = (RestaurantOrder) session
					.get("com.limitless.services.engage.restaurants.dao.RestaurantOrder", orderId);
			if (order != null) {
				Criteria criteria = session.createCriteria(RestaurantOrderDetails.class);
				criteria.add(Restrictions.eq("orderId", orderId));
				List<RestaurantOrderDetails> detailsList = criteria.list();
				log.debug("details size : " + detailsList.size());
				if (detailsList.size() > 0) {
					responseBean = new RestaurantOrderDetailsResponseBean();
					responseBean.setOrderId(orderId);
					List<RestaurantOrderItemsListBean> itemsList = new ArrayList<RestaurantOrderItemsListBean>();
					for (RestaurantOrderDetails detail : detailsList) {
						RestaurantOrderItemsListBean items = new RestaurantOrderItemsListBean();
						items.setItemId(detail.getItemId());
						items.setItemQuantity(detail.getQuantity());
						items.setItemTotalPrice(detail.getTotalPrice());
						RestaurantItems item = (RestaurantItems) session.get(
								"com.limitless.services.engage.restaurants.dao.RestaurantItems", detail.getItemId());
						if (item != null) {
							items.setItemName(item.getItemName());
							items.setItemPrice(item.getItemPrice());
						}
						itemsList.add(items);
						items = null;
					}
					responseBean.setItemsList(itemsList);
				}
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("getting order details failed : " + re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}

	public RestaurantSellerResponseBean getRestaurantSeller(int restaurantId) {
		log.debug("getting restaurant seller");
		RestaurantSellerResponseBean responseBean = null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			Restaurants restaurant = (Restaurants) session
					.get("com.limitless.services.engage.restaurants.dao.Restaurants", restaurantId);
			if (restaurant != null) {
				EngageSeller seller = (EngageSeller) session.get("com.limitless.services.engage.dao.EngageSeller",
						restaurant.getSellerId());
				if (seller != null) {
					responseBean = new RestaurantSellerResponseBean();
					responseBean.setRestaurantId(restaurantId);
					responseBean.setSellerId(seller.getSellerId());
					responseBean.setSellerName(seller.getSellerName());
					responseBean.setSellerMobile(seller.getSellerMobileNumber());
					responseBean.setSellerEmail(seller.getSellerEmail99());
					responseBean.setSellerDeviceId(seller.getSellerDeviceId());
					Criteria criteria2 = session.createCriteria(SellerPayamentsConfiguration.class);
					criteria2.add(Restrictions.eq("sellerId", seller.getSellerId()));
					List<SellerPayamentsConfiguration> configList = criteria2.list();
					if (configList.size() == 1) {
						for (SellerPayamentsConfiguration config : configList) {
							responseBean.setCitrusSellerId(config.getCitrusSellerId());
						}
					}
				}
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("getting restaurant seller failed : " + re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}

	public void sendOrderMail(int orderId) throws Exception {
		log.debug("sending mail for order");
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			RestaurantOrder order = (RestaurantOrder) session
					.get("com.limitless.services.engage.restaurants.dao.RestaurantOrder", orderId);
			if (order != null) {
				int restaurantId = order.getRestaurantId();
				String restaurantName = "";
				String restaurantCity = "";
				String restaurantPhone = "";
				int sellerId = 0;
				String sellerName = "";
				String sellerEmailId = "";
				String sellerCity = "";
				String sellerMobile = "";
				String customerName = "";
				String customerMobile = "";
				String customerCity = "";
				String customerEmail = "";
				Restaurants restaurant = (Restaurants) session
						.get("com.limitless.services.engage.restaurants.dao.Restaurants", restaurantId);
				if (restaurant != null) {
					restaurantName = restaurant.getRestaurantName();
					restaurantPhone = restaurant.getRestaurantPhone();
					restaurantCity = restaurant.getRestaurantCity();
					sellerId = restaurant.getSellerId();
					EngageSeller seller = (EngageSeller) session.get("com.limitless.services.engage.dao.EngageSeller",
							sellerId);
					if (seller != null) {
						sellerName = seller.getSellerName();
						sellerEmailId = seller.getSellerEmail99();
						sellerCity = seller.getSellerCity();
						sellerMobile = seller.getSellerMobileNumber();
					}
					EngageCustomer customer = (EngageCustomer) session
							.get("com.limitless.services.engage.dao.EngageCustomer", order.getCustomerId());
					if (customer != null) {
						customerName = customer.getCustomerName();
						customerMobile = customer.getCustomerMobileNumber();
						customerCity = customer.getCustomerCity();
						customerEmail = customer.getCustomerEmail99();
					}

					final String username = "transactions@limitlesscircle.com";
					final String password = "Engage@12E";
					String sendMailTo = sellerEmailId + "," + customerEmail;
					Properties properties = new Properties();
					properties.put("mail.smtp.host", "smtp.zoho.com");
					properties.put("mail.smtp.socketFactory.port", "465");
					properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
					properties.put("mail.smtp.auth", "true");
					properties.put("mail.smtp.port", "465");

					javax.mail.Session mailSession = javax.mail.Session.getDefaultInstance(properties,
							new javax.mail.Authenticator() {
								protected PasswordAuthentication getPasswordAuthentication() {
									return new PasswordAuthentication(username, password);
								}
							});

					try {
						javax.mail.Message message = new MimeMessage(mailSession);
						if (!order.getOrderStatus().equals("ORDER_INITIATED")
								|| !order.getOrderStatus().equals("PROCESS_FAILED")) {
							message.setFrom(new InternetAddress("transactions@limitlesscircle.com"));
							message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendMailTo));
							message.addRecipients(Message.RecipientType.BCC,
									InternetAddress.parse("orders@limitlesscircle.com"));
							double totalAmount = order.getTotalAmount();
							String mailContent = "";
							message.setSubject("Order Recieved Successfully. Order Reference Id: " + orderId);
							Criteria criteria = session.createCriteria(RestaurantOrderDetails.class);
							criteria.add(Restrictions.eq("orderId", orderId));
							List<RestaurantOrderDetails> detailsList = criteria.list();
							log.debug("details size : " + detailsList.size());
							if (detailsList.size() > 0) {
								for (RestaurantOrderDetails details : detailsList) {
									int quantity = details.getQuantity();
									double itemTotalPrice = details.getTotalPrice();
									RestaurantItems item = (RestaurantItems) session.get(
											"com.limitless.services.engage.restaurants.dao.RestaurantItems",
											details.getItemId());
									String itemName = item.getItemName();
									float itemPrice = item.getItemPrice();
									String itemType = item.getItemType();

									mailContent += "<table>" + "<tr>" + "<td>" + itemName + "-" + itemType + "</td>"
											+ "<td>&nbsp;</td>" + "<td>MRP:" + itemPrice + "</<td>" + "<td>&nbsp;</td>"
											+ "<td>Quantity:" + quantity + "</td>" + "<td>&nbsp;</td>"
											+ "<td>Total Price: " + itemTotalPrice + "</td>" + "</tr>" + "</table>"
											+ "<br>";

								}
								if (order.getPaymentMode().equals("POD")) {
									mailContent += "<h2><b>Total Amount To Be Paid: " + totalAmount + "</b></h2>";
									message.setContent(mailContent, "text/html");
								} else {
									mailContent += "<h2><b>Total Amount : " + totalAmount + "</b></h2>";
									message.setContent(mailContent, "text/html");
								}
							}
						} else if (order.getOrderStatus().equals("PROCESS_FAILED")) {
							message.setFrom(new InternetAddress("transactions@limitlesscircle.com"));
							message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendMailTo));
							message.addRecipients(Message.RecipientType.BCC,
									InternetAddress.parse("orders@limitlesscircle.com"));
							double totalAmount = order.getTotalAmount();
							String mailContent = "";
							message.setSubject("Order Failed. Order Reference Id: " + orderId);
							Criteria criteria = session.createCriteria(RestaurantOrderDetails.class);
							criteria.add(Restrictions.eq("orderId", orderId));
							List<RestaurantOrderDetails> detailsList = criteria.list();
							log.debug("details size : " + detailsList.size());
							if (detailsList.size() > 0) {
								for (RestaurantOrderDetails details : detailsList) {
									int quantity = details.getQuantity();
									double itemTotalPrice = details.getTotalPrice();
									RestaurantItems item = (RestaurantItems) session.get(
											"com.limitless.services.engage.restaurants.dao.RestaurantItems",
											details.getItemId());
									String itemName = item.getItemName();
									float itemPrice = item.getItemPrice();
									String itemType = item.getItemType();

									mailContent += "<table>" + "<tr>" + "<td>" + itemName + "-" + itemType + "</td>"
											+ "<td>&nbsp;</td>" + "<td>MRP:" + itemPrice + "</<td>" + "<td>&nbsp;</td>"
											+ "<td>Quantity:" + quantity + "</td>" + "<td>&nbsp;</td>"
											+ "<td>Total Price: " + itemTotalPrice + "</td>" + "</tr>" + "</table>"
											+ "<br>";

								}
								message.setContent(mailContent, "text/html");
							}
						}
						Transport.send(message, message.getAllRecipients());
					} catch (Exception e) {
						log.error("sending mail failed : " + e);
						throw e;
					}
				}
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("sending mail failed");
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	public void notificationToRestaurant(int orderId) {
		log.debug("sending notification to restaurant");
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			RestaurantOrder order = (RestaurantOrder) session
					.get("com.limitless.services.engage.restaurants.dao.RestaurantOrder", orderId);
			if (order != null) {
				int customerId = order.getCustomerId();
				int restaurantId = order.getRestaurantId();

				String customerName = "";
				String customerMobile = "";

				EngageCustomer customer = (EngageCustomer) session
						.get("com.limitless.services.engage.dao.EngageCustomer", customerId);
				if (customer != null) {
					customerName = customer.getCustomerName();
					customerMobile = customer.getCustomerMobileNumber();
				}

				Restaurants restaurants = (Restaurants) session
						.get("com.limitless.services.engage.restaurants.dao.Restaurants", restaurantId);
				if (restaurants != null) {
					int sellerId = restaurants.getSellerId();
					log.debug("seller id : " + sellerId);
					EngageSeller seller = (EngageSeller) session.get("com.limitless.services.engage.dao.EngageSeller",
							sellerId);
					if (seller != null) {
						Criteria criteria = session.createCriteria(SellerDeviceIdMapper.class);
						criteria.add(Restrictions.eq("sellerId", sellerId));
						List<SellerDeviceIdMapper> mapperList = criteria.list();
						log.debug("mapperlist size : " + mapperList.size());
						if (mapperList.size() > 0) {
							for (SellerDeviceIdMapper mapper : mapperList) {
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
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("sending notification to restaurant failed");
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	public void notificationToCustomer(int orderId) {
		log.debug("sending notification to customer");
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			RestaurantOrder order = (RestaurantOrder) session
					.get("com.limitless.services.engage.restaurants.dao.RestaurantOrder", orderId);
			if (order != null) {
				int customerId = order.getCustomerId();
				int restaurantId = order.getRestaurantId();

				EngageCustomer customer = (EngageCustomer) session
						.get("com.limitless.services.engage.dao.EngageCustomer", customerId);
				if (customer != null) {
					String customerName = customer.getCustomerName();
					String customerMobile = customer.getCustomerMobileNumber();
					String to = customer.getDeviceId();
					Restaurants restaurants = (Restaurants) session
							.get("com.limitless.services.engage.restaurants.dao.Restaurants", restaurantId);
					if (restaurants != null) {
						String restaurantName = restaurants.getRestaurantName();
						String restaurantPhone = restaurants.getRestaurantPhone();

						RestaurantOrderFcmRequestBean fcmBean = new RestaurantOrderFcmRequestBean();
						fcmBean.setTo(to);
						fcmBean.setPriority("high");
						RestaurantOrderDataBean data = new RestaurantOrderDataBean();
						data.setBussinessType("restaurant");
						data.setTitle("Order Placed Successfully");
						data.setBody(restaurantName + " received your orders worth Rs." + order.getTotalAmount());
						data.setOrderId(orderId);
						data.setCustomerMobile(customerMobile);
						data.setCustomerName(customerName);
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
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("sending notification to customer failed");
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	public NewRestaurantCategoryResponseBean addNewCategory(NewRestaurantCategoryRequestBean requestBean) {
		log.debug("adding new category");
		NewRestaurantCategoryResponseBean responseBean = null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			EngageSeller seller = (EngageSeller) session.get("com.limitless.services.engage.dao.EngageSeller",
					requestBean.getSellerId());
			if (seller != null) {
				Restaurants restaurants = (Restaurants) session.get(
						"com.limitless.services.engage.restaurants.dao.Restaurants", requestBean.getRestaurantId());
				if (restaurants != null) {
					if (restaurants.getSellerId() == requestBean.getSellerId()) {
						RestaurantCategory category = new RestaurantCategory();
						category.setCategoryName(requestBean.getCategoryName());
						category.setRestaurantId(requestBean.getRestaurantId());
						session.persist(category);
						log.debug("category id : " + category.getCategoryId());

						if (!requestBean.getSubcategoryRequestList().isEmpty()
								|| requestBean.getSubcategoryRequestList() != null) {
							List<NewRestaurantSubcategoryRequestBean> subcategoryRequestList = requestBean
									.getSubcategoryRequestList();
							for (NewRestaurantSubcategoryRequestBean reqBean : subcategoryRequestList) {
								RestaurantSubCategory subCategory = new RestaurantSubCategory();
								subCategory.setSubcategoryName(reqBean.getSubcategoryName());
								subCategory.setCategoryId(category.getCategoryId());
								session.persist(subCategory);
								log.debug("sub category id : " + subCategory.getCategoryId());
							}
						}
						responseBean = new NewRestaurantCategoryResponseBean();
						responseBean.setCategoryId(category.getCategoryId());
						responseBean.setRestaurantId(requestBean.getRestaurantId());
						responseBean.setSellerId(requestBean.getSellerId());
					}
				}
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("adding new category failed");
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}

	public NewRestaurantSubcategoryResponseBean addNewSubcategory(NewRestaurantSubcategoryRequestBean requestBean) {
		log.debug("adding new subcategory");
		NewRestaurantSubcategoryResponseBean responseBean = null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			RestaurantCategory category = (RestaurantCategory) session.get(
					"com.limitless.services.engage.restaurants.dao.RestaurantCategory", requestBean.getCategoryId());
			if (category != null) {
				RestaurantSubCategory subCategory = new RestaurantSubCategory();
				subCategory.setSubcategoryName(requestBean.getSubcategoryName());
				subCategory.setSubCategoryId(requestBean.getCategoryId());
				session.persist(subCategory);

				responseBean = new NewRestaurantSubcategoryResponseBean();
				responseBean.setCategoryId(requestBean.getCategoryId());
				responseBean.setSubcategoryId(subCategory.getSubCategoryId());
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("adding new subcategory failed : " + re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}

	public NewRestaurantItemResponseBean addNewItem(NewRestaurantItemRequestBean requestBean) {
		log.debug("adding new item");
		NewRestaurantItemResponseBean responseBean = null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			Restaurants restaurants = (Restaurants) session
					.get("com.limitless.services.engage.restaurants.dao.Restaurants", requestBean.getRestaurantId());
			if (restaurants != null) {
				RestaurantCategory category = (RestaurantCategory) session.get(
						"com.limitless.services.engage.restaurants.dao.RestaurantCategory",
						requestBean.getCategoryId());
				if (category != null) {
					RestaurantSubCategory subCategory = (RestaurantSubCategory) session.get(
							"com.limitless.services.engage.restaurants.dao.RestaurantSubCategory",
							requestBean.getSubcategoryId());
					if (subCategory != null) {
						RestaurantItems item = new RestaurantItems();
						item.setRestaurantId(requestBean.getRestaurantId());
						item.setCategoryId(requestBean.getCategoryId());
						item.setSubcategoryId(requestBean.getSubcategoryId());
						item.setItemName(requestBean.getItemName());
						item.setItemImage(requestBean.getItemImage());
						item.setItemType(requestBean.getItemType());
						item.setItemPrice(requestBean.getItemPrice());
						item.setAvailable(requestBean.getItemAvailable());
						item.setBreakfast(requestBean.getItemBreakfast());
						item.setLunch(requestBean.getItemLunch());
						item.setDinner(requestBean.getItemDinner());
						item.setChefSpl(requestBean.getItemChefSpl());
						item.setRecommended(requestBean.getItemRecommended());
						item.setDiscountRate(requestBean.getItemDiscountRate());
						session.persist(item);
						responseBean = new NewRestaurantItemResponseBean();
						responseBean.setItemId(item.getItemId());
						responseBean.setRestaurantId(item.getRestaurantId());
						responseBean.setCategoryId(item.getCategoryId());
						responseBean.setSubcategoryId(item.getSubcategoryId());
						responseBean.setMessage("Success");
					}
				}
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.commit();
			}
			log.error("adding item failed : " + re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}

}

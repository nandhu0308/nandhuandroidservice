package com.limitless.services.engage.restaurants.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.limitless.services.engage.SellerRestaurantListBean;
import com.limitless.services.engage.restaurants.RestaurantBean;
import com.limitless.services.engage.restaurants.RestaurantCategoryListBean;
import com.limitless.services.engage.restaurants.RestaurantItemListBean;
import com.limitless.services.engage.restaurants.RestaurantSubcategoryListBean;
import com.limitless.services.payment.PaymentService.util.HibernateUtil;
import com.limitless.services.payment.PaymentService.util.RestClientUtil;
import com.sun.jersey.api.client.Client;

public class RestaurantManager {
	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	Client client = RestClientUtil.createClient();
	private static final Log log = LogFactory.getLog(RestaurantManager.class);
	
	public RestaurantBean getRestaurantDetails(int restaurantId){
		log.debug("getting restaurant details");
		RestaurantBean bean = null;
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			Restaurants restaurant = (Restaurants) session
					.get("com.limitless.services.engage.restaurants.dao.Restaurants", restaurantId);
			if(restaurant != null){
				List<RestaurantCategoryListBean> categorysList = new ArrayList<RestaurantCategoryListBean>();
				List<RestaurantSubcategoryListBean> subcategorysList = new ArrayList<RestaurantSubcategoryListBean>();
				bean = new RestaurantBean();
				bean.setRestaurantId(restaurantId);
				bean.setRestaurantName(restaurant.getRestaurantName());
				bean.setRestaurantCity(restaurant.getRestaurantCity());
				bean.setRestaurantOrderStyle(restaurant.getRestaurantOrderStyle());
				
				Criteria criteria = session.createCriteria(RestaurantCategory.class);
				criteria.add(Restrictions.eq("restaurantId", restaurantId));
				List<RestaurantCategory> categorys = criteria.list();
				log.debug("category list size : " +categorys.size());
				if(categorys.size()>0){
					for(RestaurantCategory category : categorys){
						RestaurantCategoryListBean cBean = new RestaurantCategoryListBean();
						cBean.setCategoryId(category.getCategoryId());
						cBean.setCategoryName(category.getCategoryName());
						cBean.setRestaurantId(category.getRestaurantId());
						
						Criteria criteria2 = session.createCriteria(RestaurantSubCategory.class);
						criteria2.add(Restrictions.eq("categoryId", category.getCategoryId()));
						List<RestaurantSubCategory> subcategorys = criteria2.list();
						log.debug("sub category list size : " +subcategorys.size());
						if(subcategorys.size()>0){
							for(RestaurantSubCategory subcategory : subcategorys){
								List<RestaurantItemListBean> itemsList1 = new ArrayList<RestaurantItemListBean>();
								RestaurantSubcategoryListBean scBean = new RestaurantSubcategoryListBean();
								scBean.setCategoryId(subcategory.getCategoryId());
								scBean.setSubCategoryId(subcategory.getSubCategoryId());
								scBean.setSubCategoryName(subcategory.getSubcategoryName());
								
								Criteria criteria3 = session.createCriteria(RestaurantItems.class);
								criteria3.add(Restrictions.eq("subcategoryId", subcategory.getSubCategoryId()));
								List<RestaurantItems> items1 = criteria3.list();
								log.debug("subcategory items size : " + items1.size());
								if(items1.size()>0){
									for(RestaurantItems item1 : items1){
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
							cBean.setSubcategorysList(subcategorysList);
							categorysList.add(cBean);
						}
						else if(subcategorys.isEmpty()){
							List<RestaurantItemListBean> itemsList2 = new ArrayList<RestaurantItemListBean>();
							Criteria criteria4 = session.createCriteria(RestaurantItems.class);
							criteria4.add(Restrictions.eq("categoryId", category.getCategoryId()));
							List<RestaurantItems> items2 = criteria4.list();
							log.debug("category items size : " + items2.size());
							if(items2.size()>0){
								for(RestaurantItems item2 : items2){
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
				}
				else if(categorys.isEmpty()){
					List<RestaurantItemListBean> itemsList3 = new ArrayList<RestaurantItemListBean>();
					Criteria criteria5 = session.createCriteria(RestaurantItems.class);
					criteria5.add(Restrictions.eq("restaurantId", restaurantId));
					List<RestaurantItems> items3 = criteria5.list();
					log.debug("items size : " + items3.size());
					if(items3.size()>0){
						for(RestaurantItems item3 : items3){
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
			}
			else{
				bean = null;
			}
			transaction.commit();
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("getting restaurant failed : " +re);
			throw re;
		}
		finally {
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return bean;
	}
	
	public List<SellerRestaurantListBean> getSellerRestaurants(int sellerId){
		log.debug("getting seller restaurants");
		List<SellerRestaurantListBean> restaurantsList = new ArrayList<SellerRestaurantListBean>();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(Restaurants.class);
			criteria.add(Restrictions.eq("sellerId", sellerId));
			List<Restaurants> restaurants = criteria.list();
			log.debug("restaurants size : " + restaurants.size());
			if(restaurants.size()>0){
				for(Restaurants restaurant : restaurants){
					SellerRestaurantListBean bean = new SellerRestaurantListBean();
					bean.setRestaurantId(restaurant.getRestaurantId());
					bean.setRestaurantName(restaurant.getRestaurantName());
					bean.setRestaurantCity(restaurant.getRestaurantCity());
					restaurantsList.add(bean);
					bean = null;
				}
			}
			transaction.commit();
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("getting restaurant failed : " +re);
			throw re;
		}
		finally {
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return restaurantsList;
	}
	
}

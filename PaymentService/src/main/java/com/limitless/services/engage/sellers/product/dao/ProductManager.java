package com.limitless.services.engage.sellers.product.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;

import com.limitless.services.engage.dao.EngageSeller;
import com.limitless.services.engage.sellers.NewProductsRequestBean;
import com.limitless.services.engage.sellers.NewProductsResponseBean;
import com.limitless.services.engage.sellers.ProductBean;
import com.limitless.services.engage.sellers.ProductInventoryRequestBean;
import com.limitless.services.engage.sellers.ProductInventoryResponseBean;
import com.limitless.services.engage.sellers.ProductCSCListBean;
import com.limitless.services.engage.sellers.ProductCategoryRequestBean;
import com.limitless.services.engage.sellers.ProductCategoryResponseBean;
import com.limitless.services.engage.sellers.ProductModelsBean;
import com.limitless.services.engage.sellers.ProductResponseBean;
import com.limitless.services.engage.sellers.ProductSubCategoryListBean;
import com.limitless.services.engage.sellers.ProductSubcategoryRequestBean;
import com.limitless.services.engage.sellers.ProductsCategoryListBean;
import com.limitless.services.engage.sellers.ProductsListRequestBean;
import com.limitless.services.engage.sellers.ProductsListResponeBean;
import com.limitless.services.payment.PaymentService.util.HibernateUtil;

public class ProductManager {

	private static final Log log = LogFactory.getLog(ProductManager.class);

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

	public List<ProductBean> getAllProducts(int sellerId) {
		log.debug("Getting all product ids for a seller");
		Transaction transaction = null;
		Session session = null;
		Set<Integer> productIds = new HashSet<Integer>();

		List<ProductBean> productsList = new ArrayList<ProductBean>();

		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			Criteria criteria = session.createCriteria(SellerProduct.class);
			criteria.add(Restrictions.eq("sellerId", sellerId));
			List<SellerProduct> sellerProductList = criteria.list();
			log.debug("Size : " + sellerProductList.size());
			if (sellerProductList.size() > 0) {

				// Populate the Product Ids from Seller_Product Table
				for (SellerProduct sellerProduct : sellerProductList) {
					productIds.add(sellerProduct.getProductId());
				}

				// Get All products
				Criteria productcriteria = session.createCriteria(Product.class);
				productcriteria.add(Restrictions.in("productId", productIds));
				List<Product> products = productcriteria.list();
				log.debug("Size : " + products.size());
				if (products.size() > 0) {
					for (Product product : products) {
						ProductBean bean = new ProductBean();
						bean.setProductId(product.getProductId());
						bean.setProductName(product.getProductName());
						bean.setProductDescription(product.getProductDescription());
						bean.setProductPrice(product.getProductPrice());
						bean.setDiscountRate(product.getDiscountRate());
						float discountedPrice = (float) ((Float) product.getProductPrice()
								- (product.getProductPrice() * (product.getDiscountRate() / 100)));
						bean.setDiscountedPrice(discountedPrice);
						bean.setProduct_image(product.getProduct_image());
						bean.setProductInStock(product.getProductInStock());
						bean.setCategoryId(product.getCategoryId());
						bean.setIsDefault(product.getIsDefault());
						bean.setProductColor(product.getProductColor());
						bean.setProductSizeText(product.getProductSizeText());
						bean.setProductSizeNumber(product.getProductSizeNumber());
						bean.setImage1(product.getImage1());
						bean.setImage2(product.getImage2());
						bean.setImage3(product.getImage3());
						bean.setImage4(product.getImage4());
						bean.setImage5(product.getImage5());
						bean.setImage6(product.getImage6());
						bean.setImage7(product.getImage7());
						bean.setImage8(product.getImage8());
						bean.setImage9(product.getImage9());
						bean.setImage10(product.getImage10());
						bean.setPod(product.getPod());
						bean.setAddToCart(product.isAddToCart());
						productsList.add(bean);
						bean = null;
					}
				}
			}
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Finding deviceId failed");
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return productsList;
	}

	public NewProductsResponseBean addNewProducts(ProductBean bean) {
		log.debug("adding new products");
		NewProductsResponseBean responseBean = new NewProductsResponseBean();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			Product product = new Product();
			product.setSellerId(bean.getSellerId());
			product.setProductName(bean.getProductName());
			product.setProductPrice(bean.getProductPrice());
			product.setProductDescription(bean.getProductDescription());
			product.setProduct_image(bean.getProduct_image());
			product.setProductInStock(1);
			product.setProductColor(bean.getProductColor());
			product.setProductSizeText(bean.getProductSizeText());
			product.setProductSizeNumber(bean.getProductSizeNumber());
			product.setCategoryId(bean.getCategoryId());
			product.setSubcategoryId(bean.getSubcategoryId());
			product.setIsDefault(bean.getIsDefault());
			product.setImage1(bean.getImage1());
			product.setImage2(bean.getImage2());
			product.setImage3(bean.getImage3());
			product.setImage4(bean.getImage4());
			product.setImage5(bean.getImage5());
			product.setImage6(bean.getImage6());
			product.setImage7(bean.getImage7());
			product.setImage8(bean.getImage8());
			product.setImage9(bean.getImage9());
			product.setImage10(bean.getImage10());
			product.setPod(bean.getPod());
			product.setAddToCart(bean.isAddToCart());
			product.setGroupId(bean.getGroupId());
			session.persist(product);

			ProductInventory inventory = new ProductInventory();
			inventory.setProductId(product.getProductId());
			inventory.setProductStock(bean.getProductInventory());
			inventory.setProductSold(0);

			session.persist(inventory);

			responseBean.setProductId(product.getProductId());
			responseBean.setMessage("Success");

			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("adding product failed");
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}

	public ProductResponseBean updateProduct(ProductBean bean) {
		log.debug("updating product");
		ProductResponseBean responseBean = new ProductResponseBean();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			Product product = (Product) session.get("com.limitless.services.engage.sellers.product.dao.Product",
					bean.getProductId());
			if (product != null) {
				product.setProductName(bean.getProductName());
				product.setProductPrice(bean.getProductPrice());
				product.setProductDescription(bean.getProductDescription());
				product.setProductInStock(1);
				product.setProductColor(bean.getProductColor());
				product.setProductSizeText(bean.getProductSizeText());
				product.setProductSizeNumber(bean.getProductSizeNumber());
				product.setCategoryId(bean.getCategoryId());
				product.setSubcategoryId(bean.getSubcategoryId());
				product.setIsDefault(bean.getIsDefault());
				product.setImage1(bean.getImage1());
				product.setImage2(bean.getImage2());
				product.setImage3(bean.getImage3());
				product.setImage4(bean.getImage4());
				product.setImage5(bean.getImage5());
				product.setImage6(bean.getImage6());
				product.setImage7(bean.getImage7());
				product.setImage8(bean.getImage8());
				product.setImage9(bean.getImage9());
				product.setImage10(bean.getImage10());
				product.setPod(bean.getPod());
				product.setAddToCart(bean.isAddToCart());
				product.setGroupId(bean.getGroupId());
				session.update(product);

				responseBean.setProductId(bean.getProductId());
				responseBean.setMessage("Success");
			} else {
				responseBean.setMessage("Failed");
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("updating product failed");
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}

	public ProductInventoryResponseBean addProductInventory(ProductInventoryRequestBean requestBean) {
		log.debug("adding inventory");
		ProductInventoryResponseBean responseBean = new ProductInventoryResponseBean();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			ProductInventory inventoryInstance = (ProductInventory) session.get(
					"com.limitless.services.engage.sellers.product.dao.ProductInventory", requestBean.getProductId());
			if (inventoryInstance != null) {
				inventoryInstance.setProductStock(inventoryInstance.getProductStock() + requestBean.getProductStock());
				session.update(inventoryInstance);
				responseBean.setProductId(requestBean.getProductId());
				responseBean.setMessage("Success");
			} else if (inventoryInstance == null) {
				ProductInventory inventory = new ProductInventory();
				inventory.setProductId(requestBean.getProductId());
				inventory.setProductStock(requestBean.getProductStock());
				inventory.setProductSold(0);
				session.persist(inventory);
				responseBean.setProductId(requestBean.getProductId());
				responseBean.setMessage("Success");
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("updating product failed");
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}

	public ProductBean getProductById(int productId) {
		log.debug("Getting product by id");
		ProductBean productBean = null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			Product product = (Product) session.get("com.limitless.services.engage.sellers.product.dao.Product",
					productId);
			if (product != null && product.getIsRemoved() != 1) {
				productBean = new ProductBean();
				productBean.setProductId(productId);
				productBean.setProductName(product.getProductName());
				productBean.setProduct_image(product.getProduct_image());
				productBean.setProductDescription(product.getProductDescription());
				productBean.setProductPrice(product.getProductPrice());
				productBean.setProductInStock(product.getProductInStock());
				float discountedPrice = (float) ((Float) product.getProductPrice()
						- (product.getProductPrice() * (product.getDiscountRate() / 100)));
				productBean.setDiscountRate(product.getDiscountRate());
				productBean.setDiscountedPrice(discountedPrice);
				productBean.setImage1(product.getImage1());
				productBean.setImage2(product.getImage2());
				productBean.setImage3(product.getImage3());
				productBean.setImage4(product.getImage4());
				productBean.setImage5(product.getImage5());
				productBean.setImage6(product.getImage6());
				productBean.setImage7(product.getImage7());
				productBean.setImage8(product.getImage8());
				productBean.setImage9(product.getImage9());
				productBean.setImage10(product.getImage10());
				productBean.setCategoryId(product.getCategoryId());
				productBean.setSubcategoryId(product.getSubcategoryId());
				productBean.setProductSizeText(product.getProductSizeText());
				productBean.setProductSizeNumber(product.getProductSizeNumber());
				productBean.setProductColor(product.getProductColor());
				productBean.setPod(product.getPod());
				productBean.setAddToCart(product.isAddToCart());
				productBean.setGroupId(product.getGroupId());
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("getting product failed : " + re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return productBean;
	}

	public ProductCSCListBean getSellerProductsV2(int sellerId) {
		log.debug("getting seller products");
		ProductCSCListBean listBean = new ProductCSCListBean();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			EngageSeller seller = (EngageSeller) session.get("com.limitless.services.engage.dao.EngageSeller",
					sellerId);
			if (seller != null) {
				listBean.setSellerId(sellerId);
				listBean.setCitrusSellerId(seller.getCitrusSellerId());
				listBean.setSellerName(seller.getSellerShopName());
				listBean.setSellerCity(seller.getSellerCity());
				List<ProductsCategoryListBean> categoryProductList = new ArrayList<ProductsCategoryListBean>();
				Criteria criteria = session.createCriteria(ProductCategory.class);
				criteria.add(Restrictions.eq("sellerId", sellerId));
				List<ProductCategory> categoryList = criteria.list();
				log.debug("category size : " + categoryList.size());
				if (categoryList.size() > 0) {
					for (ProductCategory category : categoryList) {
						ProductsCategoryListBean categoryBean = new ProductsCategoryListBean();
						categoryBean.setCategoryId(category.getProductCategoryId());
						categoryBean.setProductCategoryName(category.getProductCategoryName());
						categoryBean.setCategoryImageUrl(category.getProdcuctCategoryImage());
						Criteria criteria2 = session.createCriteria(ProductSubcategory.class);
						criteria2.add(Restrictions.eq("productCategoryId", category.getProductCategoryId()));
						List<ProductSubcategory> subcategoryList = criteria2.list();
						log.debug("sub category size : " + subcategoryList.size());
						if (subcategoryList.size() > 0) {
							List<ProductSubCategoryListBean> scListBean = new ArrayList<ProductSubCategoryListBean>();
							for (ProductSubcategory subcategory : subcategoryList) {
								ProductSubCategoryListBean subCategoryBean = new ProductSubCategoryListBean();
								subCategoryBean.setCategoryId(subcategory.getProductCategoryId());
								subCategoryBean.setSubcategoryId(subcategory.getProductScId());
								subCategoryBean.setSubcategoryName(subcategory.getProductScName());
								subCategoryBean.setSubCategoryImageUrl(subcategory.getProductSubcategoryImage());
								// List<ProductBean> subcategoryProductList =
								// new ArrayList<ProductBean>();
								// Criteria criteria3 =
								// session.createCriteria(Product.class);
								// criteria3.add(Restrictions.eq("subcategoryId",
								// subcategory.getProductScId()));
								// List<Product> productList = criteria3.list();
								// log.debug("products list size : " +
								// productList.size());
								// if (productList.size() > 0) {
								// for (Product product : productList) {
								// ProductBean bean = new ProductBean();
								// bean.setProductId(product.getProductId());
								// bean.setParentProductId(product.getParentProductId());
								// bean.setProductName(product.getProductName());
								// bean.setProductDescription(product.getProductDescription());
								// bean.setProductPrice(product.getProductPrice());
								// bean.setDiscountRate(product.getDiscountRate());
								// float discountedPrice = (float) ((Float)
								// product.getProductPrice()
								// - (product.getProductPrice() *
								// (product.getDiscountRate() / 100)));
								// bean.setDiscountedPrice(discountedPrice);
								// bean.setProduct_image(product.getProduct_image());
								// bean.setProductInStock(product.getProductInStock());
								// bean.setCategoryId(product.getCategoryId());
								// bean.setGroupId(product.getGroupId());
								// bean.setIsDefault(product.getIsDefault());
								// bean.setProductColor(product.getProductColor());
								// bean.setProductSizeText(product.getProductSizeText());
								// bean.setProductSizeNumber(product.getProductSizeNumber());
								// bean.setImage1(product.getImage1());
								// bean.setImage2(product.getImage2());
								// bean.setImage3(product.getImage3());
								// bean.setImage4(product.getImage4());
								// bean.setImage5(product.getImage5());
								// bean.setImage6(product.getImage6());
								// bean.setImage7(product.getImage7());
								// bean.setImage8(product.getImage8());
								// bean.setImage9(product.getImage9());
								// bean.setImage10(product.getImage10());
								// subcategoryProductList.add(bean);
								// bean = null;
								// }
								// subCategoryBean.setProductsList(subcategoryProductList);
								// }
								scListBean.add(subCategoryBean);
								subCategoryBean = null;
							}
							categoryBean.setSubcategoryList(scListBean);
						} else if (subcategoryList.isEmpty()) {
							// List<ProductBean> productList = new
							// ArrayList<ProductBean>();
							// Criteria criteria4 =
							// session.createCriteria(Product.class);
							// criteria4.add(Restrictions.eq("catergoryId",
							// category.getProductCategoryId()));
							// List<Product> productList2 = criteria4.list();
							// log.debug("products list size : " +
							// productList2.size());
							// if (productList2.size() > 0) {
							// for (Product product : productList2) {
							// ProductBean bean = new ProductBean();
							// bean.setProductId(product.getProductId());
							// bean.setParentProductId(product.getParentProductId());
							// bean.setProductName(product.getProductName());
							// bean.setProductDescription(product.getProductDescription());
							// bean.setProductPrice(product.getProductPrice());
							// bean.setDiscountRate(product.getDiscountRate());
							// float discountedPrice = (float) ((Float)
							// product.getProductPrice()
							// - (product.getProductPrice() *
							// (product.getDiscountRate() / 100)));
							// bean.setDiscountedPrice(discountedPrice);
							// bean.setProduct_image(product.getProduct_image());
							// bean.setProductInStock(product.getProductInStock());
							// bean.setCategoryId(product.getCategoryId());
							// bean.setGroupId(product.getGroupId());
							// bean.setIsDefault(product.getIsDefault());
							// bean.setProductColor(product.getProductColor());
							// bean.setProductSizeText(product.getProductSizeText());
							// bean.setProductSizeNumber(product.getProductSizeNumber());
							// bean.setImage1(product.getImage1());
							// bean.setImage2(product.getImage2());
							// bean.setImage3(product.getImage3());
							// bean.setImage4(product.getImage4());
							// bean.setImage5(product.getImage5());
							// bean.setImage6(product.getImage6());
							// bean.setImage7(product.getImage7());
							// bean.setImage8(product.getImage8());
							// bean.setImage9(product.getImage9());
							// bean.setImage10(product.getImage10());
							// productList.add(bean);
							// bean = null;
							// }
							// categoryBean.setProductsList(productList);
							// }
						}
						categoryProductList.add(categoryBean);
						categoryBean = null;
					}
					listBean.setCategoryList(categoryProductList);
				} else if (categoryList.isEmpty()) {
					// List<ProductBean> beanList = new
					// ArrayList<ProductBean>();
					// Criteria criteria5 =
					// session.createCriteria(Product.class);
					// criteria5.add(Restrictions.eq("sellerId", sellerId));
					// List<Product> productList = criteria5.list();
					// log.debug("product size :" + productList.size());
					// if(productList.size()>0){
					// for(Product product : productList){
					// ProductBean bean = new ProductBean();
					// bean.setProductId(product.getProductId());
					// bean.setParentProductId(product.getParentProductId());
					// bean.setProductName(product.getProductName());
					// bean.setProductDescription(product.getProductDescription());
					// bean.setProductPrice(product.getProductPrice());
					// bean.setDiscountRate(product.getDiscountRate());
					// float discountedPrice = (float) ((Float)
					// product.getProductPrice()
					// - (product.getProductPrice() * (product.getDiscountRate()
					// / 100)));
					// bean.setDiscountedPrice(discountedPrice);
					// bean.setProduct_image(product.getProduct_image());
					// bean.setProductInStock(product.getProductInStock());
					// bean.setCategoryId(product.getCategoryId());
					// bean.setGroupId(product.getGroupId());
					// bean.setIsDefault(product.getIsDefault());
					// bean.setProductColor(product.getProductColor());
					// bean.setProductSizeText(product.getProductSizeText());
					// bean.setProductSizeNumber(product.getProductSizeNumber());
					// bean.setImage1(product.getImage1());
					// bean.setImage2(product.getImage2());
					// bean.setImage3(product.getImage3());
					// bean.setImage4(product.getImage4());
					// bean.setImage5(product.getImage5());
					// bean.setImage6(product.getImage6());
					// bean.setImage7(product.getImage7());
					// bean.setImage8(product.getImage8());
					// bean.setImage9(product.getImage9());
					// bean.setImage10(product.getImage10());
					// beanList.add(bean);
					// bean = null;
					// }
					// listBean.setProductsList(beanList);
					// }
				}
				listBean.setMessage("Success");
			} else {
				listBean.setMessage("Failed");
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("getting products failed : " + re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return listBean;
	}

	public ProductResponseBean removeProductFromStore(int productId) {
		log.debug("removing product");
		ProductResponseBean responseBean = new ProductResponseBean();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			Product product = (Product) session.get("com.limitless.services.engage.sellers.product.dao.Product",
					productId);
			if (product != null && product.getIsRemoved() != 1) {
				product.setIsRemoved(1);
				session.update(product);

				responseBean.setProductId(productId);
				responseBean.setMessage("Success");
			}
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("removing product failed : " + re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}

	public ProductsListResponeBean getProductsList(ProductsListRequestBean requestBean) {
		log.debug("gettng products");
		ProductsListResponeBean responeBean = new ProductsListResponeBean();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			if (requestBean.getSubcatId() > 0) {
				ProductCategory category = (ProductCategory) session.get(
						"com.limitless.services.engage.sellers.product.dao.ProductCategory", requestBean.getCatId());
				if (category != null) {
					responeBean.setCategoryId(requestBean.getCatId());
					responeBean.setCategoryName(category.getProductCategoryName());
				}
				ProductSubcategory subcat = (ProductSubcategory) session.get(
						"com.limitless.services.engage.sellers.product.dao.ProductSubcategory",
						requestBean.getSubcatId());
				if (subcat != null) {
					responeBean.setSubCategoryId(requestBean.getSubcatId());
					responeBean.setSubCategoryName(subcat.getProductScName());
				}

				Criteria criteria = session.createCriteria(Product.class);
				Criterion catIdCriterion = Restrictions.eq("categoryId", requestBean.getCatId());
				Criterion subcatIdCRiterion = Restrictions.eq("subcategoryId", requestBean.getSubcatId());
				LogicalExpression logExp = Restrictions.and(catIdCriterion, subcatIdCRiterion);
				criteria.add(logExp);
				List<Product> products = criteria.list();
				log.debug("products size : " + products.size());
				if (products.size() > 0) {
					List<ProductBean> beanList = new ArrayList<ProductBean>();
					for (Product product : products) {
						ProductBean bean = new ProductBean();
						bean.setProductId(product.getProductId());
						bean.setProductName(product.getProductName());
						bean.setProductDescription(product.getProductDescription());
						bean.setProductPrice(product.getProductPrice());
						bean.setDiscountRate(product.getDiscountRate());
						float discountedPrice = (float) ((Float) product.getProductPrice()
								- (product.getProductPrice() * (product.getDiscountRate() / 100)));
						bean.setDiscountedPrice(discountedPrice);
						bean.setProduct_image(product.getProduct_image());
						bean.setProductInStock(product.getProductInStock());
						bean.setCategoryId(product.getCategoryId());
						bean.setIsDefault(product.getIsDefault());
						bean.setProductColor(product.getProductColor());
						bean.setProductSizeText(product.getProductSizeText());
						bean.setProductSizeNumber(product.getProductSizeNumber());
						bean.setImage1(product.getImage1());
						bean.setImage2(product.getImage2());
						bean.setImage3(product.getImage3());
						bean.setImage4(product.getImage4());
						bean.setImage5(product.getImage5());
						bean.setImage6(product.getImage6());
						bean.setImage7(product.getImage7());
						bean.setImage8(product.getImage8());
						bean.setImage9(product.getImage9());
						bean.setImage10(product.getImage10());
						bean.setPod(product.getPod());
						bean.setAddToCart(product.isAddToCart());
						bean.setGroupId(product.getGroupId());
						beanList.add(bean);
						bean = null;
					}
					responeBean.setProductsList(beanList);
					responeBean.setMessage("Success");
				}
			} else if (requestBean.getSubcatId() == 0 || requestBean.getSubcatId() < 0) {
				ProductCategory category = (ProductCategory) session.get(
						"com.limitless.services.engage.sellers.product.dao.ProductCategory", requestBean.getCatId());
				if (category != null) {
					responeBean.setCategoryId(requestBean.getCatId());
					responeBean.setCategoryName(category.getProductCategoryName());
				}
				Criteria criteria = session.createCriteria(Product.class);
				criteria.add(Restrictions.eq("categoryId", requestBean.getCatId()));
				List<Product> products = criteria.list();
				log.debug("products size : " + products.size());
				if (products.size() > 0) {
					List<ProductBean> beanList = new ArrayList<ProductBean>();
					for (Product product : products) {
						ProductBean bean = new ProductBean();
						bean.setProductId(product.getProductId());
						bean.setProductName(product.getProductName());
						bean.setProductDescription(product.getProductDescription());
						bean.setProductPrice(product.getProductPrice());
						bean.setDiscountRate(product.getDiscountRate());
						float discountedPrice = (float) ((Float) product.getProductPrice()
								- (product.getProductPrice() * (product.getDiscountRate() / 100)));
						bean.setDiscountedPrice(discountedPrice);
						bean.setProduct_image(product.getProduct_image());
						bean.setProductInStock(product.getProductInStock());
						bean.setCategoryId(product.getCategoryId());
						bean.setIsDefault(product.getIsDefault());
						bean.setProductColor(product.getProductColor());
						bean.setProductSizeText(product.getProductSizeText());
						bean.setProductSizeNumber(product.getProductSizeNumber());
						bean.setImage1(product.getImage1());
						bean.setImage2(product.getImage2());
						bean.setImage3(product.getImage3());
						bean.setImage4(product.getImage4());
						bean.setImage5(product.getImage5());
						bean.setImage6(product.getImage6());
						bean.setImage7(product.getImage7());
						bean.setImage8(product.getImage8());
						bean.setImage9(product.getImage9());
						bean.setImage10(product.getImage10());
						bean.setPod(product.getPod());
						bean.setAddToCart(product.isAddToCart());
						bean.setGroupId(product.getGroupId());
						beanList.add(bean);
						bean = null;
					}
					responeBean.setProductsList(beanList);
					responeBean.setMessage("Success");
				}
			} else {
				responeBean.setMessage("Not Found");
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("removing product failed : " + re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responeBean;
	}
	
	public ProductCategoryResponseBean addNewCategory(ProductCategoryRequestBean requestBean){
		log.debug("adding new category");
		ProductCategoryResponseBean responseBean = new ProductCategoryResponseBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			EngageSeller seller = (EngageSeller) session
					.get("com.limitless.services.engage.dao.EngageSeller", requestBean.getSellerId());
			if(seller!=null){
				ProductCategory category = new ProductCategory();
				category.setSellerId(requestBean.getSellerId());
				category.setProductCategoryName(requestBean.getCategoryName());
				category.setProdcuctCategoryImage(requestBean.getCategoryImageUrl());
				session.persist(category);
				
				int categoryId = category.getProductCategoryId();
				if(requestBean.isHasSubcategory()){
					List<ProductSubcategoryRequestBean> subcategoryList = requestBean.getSubcategoryList();
					for(ProductSubcategoryRequestBean scReqBean : subcategoryList){
						ProductSubcategory subcategory = new ProductSubcategory();
						subcategory.setProductCategoryId(categoryId);
						subcategory.setProductScName(scReqBean.getSubcategoryName());
						subcategory.setProductSubcategoryImage(scReqBean.getSubcategoryImageUrl());
						session.persist(subcategory);
					}
				}
			}
			transaction.commit();
		}
		catch(RuntimeException re){
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("adding new category failed : " + re);
			throw re;
		}
		finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}

	public static void main(String[] args) {
		ProductManager manager = new ProductManager();
		// List<Product> products = manager.getAllProducts(5000000);
		// for (Iterator iterator = products.iterator(); iterator.hasNext();) {
		// Product product = (Product) iterator.next();
		// System.out.println(product.getProductId() + " | " +
		// product.getProductName() + " | " + product.getProductPrice() );
		// }
	}

}
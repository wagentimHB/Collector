package de.wagentim.collector.persistance.objectdb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import de.wagentim.collector.entity.Price;
import de.wagentim.collector.entity.Product;

public class ObjectDBProductHandler extends AbstractObjectDBHandler
{
	
	protected Map<Integer, Product> prods = new HashMap<Integer, Product>();

	// DB Query
	private static final String QUERY_GET_ALL_PRODUCT = "SELECT P FROM Product p";

	public ObjectDBProductHandler(String dbLocation)
	{
		super(dbLocation);
	}

	public Collection<Product> getAllProduct()
	{
		return prods.values();
	}

	public void getAllProductFromDB(String uuid)
	{
		EntityManager em = getEntityManager(uuid);

		if (em == null)
		{
			return;
		}

		TypedQuery<Product> query = em.createQuery(QUERY_GET_ALL_PRODUCT, Product.class);
		List<Product> products = query.getResultList();
		convertProductListToMap(products);
	}

	private void convertProductListToMap(List<Product> products)
	{
		Iterator<Product> it = products.iterator();

		while (it.hasNext())
		{
			Product p = it.next();
//			int size = p.getPriceList().size();
//			if(size > 1)
//			{
//				System.out.println(p.getProductName() + " -> " + size);
//				List<Price>ps = p.findLast2Prices();
//				System.out.println("  == " + ps.get(0).getPrice() + " | " + ps.get(1).getPrice());
//			}
			prods.put(p.getProductID(), p);
		}
	}

	public void addOrUpdateProduct(Product product, Price price)
	{
		int productID = product.getProductID();

		Product p = prods.get(productID);

		if (p == null)
		{
			// New Product -> add to DB
			logger.info("Add New Product -> [ " + product.getProductID() + " ]: " + product.getProductName() + " [ "
					+ price.getPrice() + " ]");
			addNewProduct(product, price);
		} else
		{
			updateProduct(p, product, price);
		}
	}

	private void updateProduct(Product old, Product product, Price price)
	{
		old.update(product, price);
	}

	private void addNewProduct(Product product, Price price)
	{
		product.addPrice(price);
		prods.put(product.getProductID(), product);
	}

	public void saveBackToDB(String uuid)
	{
		logger.info("=== Save to DB ===");
		EntityManager em = getEntityManager(uuid);
		em.getTransaction().begin();
		Collection<Product> ps = prods.values();
		Iterator<Product> it = ps.iterator();
		while (it.hasNext())
		{
			Product p = it.next();
			em.persist(p);
		}
		em.getTransaction().commit();

		logger.info("=== Save to DB Finished ===");
	}

	public List<Product> findProductWithDecreatePrice()
	{
		List<Product> rabatts = new ArrayList<Product>();

		Iterator<Product> it = prods.values().iterator();

		while (it.hasNext())
		{
			Product p = it.next();
			if (isLastLowPriceThanBefore(p))
			{
				rabatts.add(p);
			}
		}

		return rabatts;
	}

	private boolean isLastLowPriceThanBefore(Product p)
	{
		List<Price> ps = p.findLast2Prices();

		if (ps.size() > 1)
		{
			Price last = ps.get(0);
			Price before = ps.get(1);

			return last.getPrice() < before.getPrice();
		}
		
		return false;
	}

}

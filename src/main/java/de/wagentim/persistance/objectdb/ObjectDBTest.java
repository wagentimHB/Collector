package de.wagentim.collector.persistance.objectdb;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.wagentim.collector.entity.Price;
import de.wagentim.collector.entity.Product;


public class ObjectDBTest
{
	private static final String TEST_DB = "D:\\test\\test.odb";
	
	private String link = "https://www.dajiangyou.eu/jianguochaohuo/2114-%E9%BB%84%E9%A3%9E%E7%BA%A2";
	
	public void run()
	{
		ObjectDBProductHandler handler = new ObjectDBProductHandler(TEST_DB);
//		List<Product> allProds = handler.getAllProduct();
//		System.out.println(allProds.size());
		String uuid = handler.createEntityManager();
		
		Product product = new Product();
		product.setProductID(1234);
		Price price = new Price();
		price.setPrice(2.0);

//		handler.addOrUpdateProduct(product, price);
		
		price = new Price();
		price.setPrice(1.0);
		
//		handler.addOrUpdateProduct(product, price);
		
		
		handler.saveBackToDB(uuid);
		handler.exit();
		
	}
	
	public void getProductID()
	{
		Pattern pattern = Pattern.compile("^https://[-a-zA-Z0-9+&@#%?=~_|!:,.;]*/[-a-zA-Z0-9+&@#%?=~_|!:,.;]*/([0-9]*)-");
		Matcher m = pattern.matcher(link);
		if(m.find())
		{
			System.out.println(m.group(0));
			System.out.println(m.group(1));
		}
	}
	
	public static void main(String[] args)
	{
		ObjectDBTest test = new ObjectDBTest();
		test.run();
	}
}

package customer.main;

import org.springframework.context.support.GenericXmlApplicationContext;

import customer.dao.CustomerDAO;

public class MainClass {

	public static void main(String[] args) {

		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationContext.xml");
		CustomerDAO customerInfo = ctx.getBean("customerDao", CustomerDAO.class);
		
		ctx.close();
	}

}

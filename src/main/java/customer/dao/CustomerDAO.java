package customer.dao;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import customer.dto.CustomerDTO;
import com.opencsv.CSVReader;

public class CustomerDAO {
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String id = "eddy";
	String pw = "jo";
	
	public CustomerDAO() {
		try {
			Class.forName(driver);
			this.insertToSQL();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void insertToSQL() {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		System.out.println("* * * * * * * * * * * * * *");
		System.out.println("* SQL Database connected! *");
		System.out.println("* * * * * * * * * * * * * *\n");
		System.out.println("=========================================");
		
		try {
			
			con = DriverManager.getConnection(url, id, pw);
			String query = "INSERT INTO CUSTOMER VALUES(CUSTOMER_SEQ.NEXTVAL, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(query);
			
			ArrayList<CustomerDTO> customerListToSQL = getCustomerListFromCSV("customer.csv");
			
			for(int i = 0; i < customerListToSQL.size(); i++) {
//				pstmt.setInt(1, customerListToSQL.get(i).getOrderId());
				pstmt.setString(1, customerListToSQL.get(i).getCustomerName());
				pstmt.setString(2, customerListToSQL.get(i).getDeliveryNumber());
				pstmt.setString(3, customerListToSQL.get(i).getProduct());
				pstmt.setString(4, customerListToSQL.get(i).getPrice());
				
				pstmt.executeUpdate();
				System.out.println("Insert data success: " + (i+1));
			}
				
		} catch (Exception e) {
			e.printStackTrace();		
		
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}		
	}
	
	
	public ArrayList<CustomerDTO> getCustomerListFromCSV(String csvFile) {
		
		CSVReader reader = null;
		ArrayList<CustomerDTO> customerList = new ArrayList<CustomerDTO>();
		
		try {
			reader = new CSVReader(new FileReader(csvFile), ',', '"', 1);
			String[] customerDetail;
			
			
			while ((customerDetail = reader.readNext()) != null) {
				CustomerDTO customers = new CustomerDTO(Integer.parseInt(customerDetail[0]), customerDetail[1], customerDetail[2], customerDetail[3], customerDetail[4]); 
				customerList.add(customers);
			}
			
			for(CustomerDTO c : customerList) {
				System.out.println("Order ID: " + c.getOrderId());
				System.out.println("Customer Name: " + c.getCustomerName());
				System.out.println("Delivery Number: " + c.getDeliveryNumber());
				System.out.println("Product: " + c.getProduct());
				System.out.println("Price: $" + c.getPrice());
				System.out.println("=========================================");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customerList;
	}
	
}


package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.mysql.jdbc.Statement;

import db.DB;

public class Program {

	public static void main(String[] args) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = DB.getConnection();
			
			//insert new seller
			st = conn.prepareStatement( 
					"INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId)"
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)", //place holder
					Statement.RETURN_GENERATED_KEYS); //allow retrive id of newly inserted object
			
					
			st.setString(1, "Carl Purple");
			st.setString(2, "Carl@gmail.com");
			st.setDate(3, new java.sql.Date(sdf.parse("22/04/1985").getTime()));
			st.setDouble(4, 3000.00);
			st.setInt(5, 4);
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				while (rs.next()) {
					int id = rs.getInt(1);
					System.out.println("Done! Id = " + id);
				}
			} else {
				System.out.println("No rown affected!");
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeStatement((Statement) st);
			DB.closeConnection();
		}

	}

}

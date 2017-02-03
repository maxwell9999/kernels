package org.kernels.schedulr.accounts;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class Test {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		
		UserAuthenticator user = new UserAuthenticator();
		Statement stmt = user.queryDatabaseConnection();
		String sql = "INSERT INTO test(name, age) VALUES ('Thomas', 13);";
		stmt.executeUpdate(sql);
		stmt.close();

	}

}

package sodar.server.web.model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DeveloperModel {
	protected final String driver = "com.mysql.jdbc.Driver";
	protected final String databaseURL = "jdbc:mysql://140.121.197.101:3306/sodar";
	protected final String user = "sodar";
	protected final String password = "sunshyboyz";
	protected Connection connection = null;
	protected ResultSet resultSet = null;

	public DeveloperModel() {
		// ��l��Database
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(databaseURL, user, password);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String registerDeveloper(String devpName, String email, String appName, String company,
	        String webPage, String description) {
		String APIKey = "";
		
		// �ˬd�������
		if (devpName.equals("") || email.equals("") || appName.equals("")) {
			return "Ensure you have fill all of the *mark column";
		}
		
		// ����API Key
		try {
			byte[] input = (devpName + email + appName).getBytes();
			// �[�K�t��k
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        md.update(input);
	        // �[�K�᪺�r�� API Key
	        APIKey = new BigInteger(1, md.digest()).toString(16);
        }
        catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	        return "Generate API Key has encounter an error, please try again!";
        }
		
		
		// ���U�ϥΪ̸�T���Ʈw
		try {
			Statement stmt = connection.createStatement();
			String SQL = "SELECT api_key FROM developer WHERE api_key ='" + APIKey + "'";
			ResultSet rs = stmt.executeQuery(SQL);
			
			if (rs.next()) {
				return rs.getString("api_key");
			}
			
			SQL = "INSERT INTO developer(devp_name, email, app_name, company, web_page, description, api_key, create_time) VALUES('"
			        + devpName
			        + "','"
			        + email
			        + "', '"
			        + appName
			        + "', '"
			        + company
			        + "', '"
			        + webPage
			        + "', '" + description + "', '" + APIKey + "', NOW())";
			stmt.execute(SQL);
		}
		catch (Exception e) {
			e.printStackTrace();
			return "Database has encounter an error, please try again!";
		}

		return APIKey;
	}
}

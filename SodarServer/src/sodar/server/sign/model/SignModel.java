package sodar.server.sign.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.sf.json.JSONObject;

public class SignModel {
	protected final String driver = "com.mysql.jdbc.Driver";
	protected final String databaseURL = "jdbc:mysql://140.121.197.101:3306/sodar";
	protected final String user = "sodar";
	protected final String password = "sunshyboyz";
	protected Connection connection = null;
	protected ResultSet resultSet = null;

	public SignModel() {
		// ��l��Database
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(databaseURL, user, password);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JSONObject SignIn(String uid, String name, String sex, String aboutMe, String educationHistory,
	        String workHistory, String APIKey) {
		// ����API Key�O�_�Q���U
		try {
			Statement stmt = connection.createStatement();
			String SQL = "SELECT api_key FROM developer WHERE api_key ='" + APIKey + "'";
			ResultSet rs = stmt.executeQuery(SQL);

			if (!rs.next()) {
				JSONObject errorResult = new JSONObject();
				errorResult.put("status", "error: Invalid API Key");
				return errorResult;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			JSONObject errorResult = new JSONObject();
			errorResult.put("status", "error: " + e.getMessage());
			return errorResult;
		}

		JSONObject jsonResult = new JSONObject();
		String querySQL = "SELECT uid FROM member WHERE uid ='" + uid + "'";

		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(querySQL);

			// �ϥΪ̤w�b��Ʈw�عL��ơA�n�J�ɳ��ݭn���supdate�n�J�ɶ�
			if (rs.next()) {
				querySQL = "UPDATE member SET name = '" + name + "', sex = '" + sex + "', aboutMe = '"
				        + aboutMe + "', educationHistory = '" + educationHistory + "', workHistory = '"
				        + workHistory + "', last_login_time = NOW() WHERE uid='" + uid + "'";
				stmt.execute(querySQL);
			}
			// �Ĥ@���n�J���U
			else {
				// �ǰeuser��ƨ��Ʈw���A�æ^�Ǧ��\�P�_
				querySQL = "INSERT INTO member VALUES('" + uid + "','" + name + "','" + sex + "','" + aboutMe
				        + "','" + educationHistory + "','" + workHistory + "', NOW())";
				stmt.execute(querySQL);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			JSONObject errorResult = new JSONObject();
			errorResult.put("status", "error: " + e.getMessage());
			return errorResult;
		}

		jsonResult.put("status", "success");
		return jsonResult;
	}
}

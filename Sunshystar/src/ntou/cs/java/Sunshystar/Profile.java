package ntou.cs.java.Sunshystar;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;

/*
 * Profile �w�q�ϥΪ̭ӤH�ɮת��{���޿�
 * �i�H�s����Ʈw���ϥΪ̰򥻸�ƨï���i��ק�
 * 
 * @author Ricky, Howard, Talka
 */
public class Profile extends Sunshystar {
	public ValidationState setProfile(int uid, String oldPwd, String newPwd, String comPwd, String name, int dept, int grade, int gender, String age) {
		String passwordFormat = "\\w{6,20}";
		Pattern passwordPattern = Pattern.compile(passwordFormat);
		Matcher passwordMatcher = passwordPattern.matcher(password);
		String nameFormat = ".{1,20}";
		Pattern namePattern = Pattern.compile(nameFormat);
		Matcher nameMatcher = namePattern.matcher(name);
		int ageNum = 0;
		
		try {
			ageNum = Integer.parseInt(age);
		}
		catch(NumberFormatException e) {
			return ValidationState.AGE_FORMAT_ERROR;
		}
		
		if(!passwordMatcher.matches()) {
			return ValidationState.PWD_FORMAT_ERROR;
		}
		else if(!nameMatcher.matches()) {
			return ValidationState.NAME_FORMAT_ERROR;
		}
		else if(ageNum < 18 || ageNum > 30) {
			return ValidationState.AGE_FORMAT_ERROR;
		}
		
		// �T�ӱK�X��쳣���šA��ܨϥΪ̨S���󥿱K�X���ݨD
		if (oldPwd.equals("") && newPwd.equals("") && comPwd.equals("")) {
			try {
				preparedStatement = connection.prepareStatement("UPDATE user_information SET name = ?, department = ?, grade = ?, gender = ?, age = ? WHERE uid = ?");
				preparedStatement.setString(1, name);
				preparedStatement.setInt(2, dept);
				preparedStatement.setInt(3, grade);
				preparedStatement.setInt(4, gender);
				preparedStatement.setInt(5, ageNum);
				preparedStatement.setInt(6, uid);
				preparedStatement.executeUpdate();
			}
			catch (SQLException e) {
				return ValidationState.DATABASE_ERROR;
			}
			finally {
				Close();
			}
			
			return ValidationState.SUCCESS;
		}
		// �T�ӱK�X��쳣����J�K�X�A��ܨϥΪ̦��󥿱K�X���ݨD
		else if ((!oldPwd.equals("")) && (!newPwd.equals("")) && (!comPwd.equals(""))) {	
			// �ϥΪ̿�J���s�K�X�P�T�{�K�X�ˬd
			if (!newPwd.equals(comPwd)) {
				return ValidationState.COMFIRM_PWD_UNEQUAL_ERROR;
			}
			
			try {
				preparedStatement = connection.prepareStatement("SELECT password FROM user_information WHERE uid = ?");
				preparedStatement.setInt(1, uid);
				ResultSet rs = preparedStatement.executeQuery();
			
				if (rs.next()) {
					//�ˬd�ϥΪ̿�J�±K�X�P��K�X�O�_�ۦP
					if((!rs.getString("password").equals(oldPwd))) {
						return ValidationState.PWD_UNEQUAL_ERROR;
					}
				}
				
				preparedStatement = connection.prepareStatement("UPDATE user_information SET password = ?, name = ?, department = ?, grade = ?, gender = ?, age = ? WHERE uid = ?");
				preparedStatement.setString(1, newPwd);
				preparedStatement.setString(2, name);
				preparedStatement.setInt(3, dept);
				preparedStatement.setInt(4, grade);
				preparedStatement.setInt(5, gender);
				preparedStatement.setInt(6, ageNum);
				preparedStatement.setInt(7, uid);
				preparedStatement.executeUpdate();

			}
			catch (SQLException e) {
				return ValidationState.DATABASE_ERROR;
			}
			finally {
				Close();
			}
		}
		
		// ��l���p���T�ӱK�X��즳�@�Ӧ����ơA��ܨϥΪ̿�J�ä����T
		else {
			return ValidationState.PWD_FIELD_INPUT_ERROR;
		}
		
		return ValidationState.SUCCESS;
	}
	
	public String getUserMail(int uid) {
		String mail = "";
		
		try {
			preparedStatement = connection.prepareStatement("SELECT mail FROM user_information WHERE uid = ?");
			preparedStatement.setInt(1, uid);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				mail = resultSet.getString("mail");
			}
		}
		catch (SQLException e) {
			System.out.println(e.toString());
		}
		finally {
			Close();
		}

		return mail;
	}
	
	public boolean setUserImage(int uid, File imagePath) {
		try {
			FileInputStream fis = new FileInputStream(imagePath);
			preparedStatement = connection.prepareStatement("UPDATE user_information SET image = ? WHERE uid = ?");
			preparedStatement.setInt(2, uid);
			preparedStatement.setBinaryStream(1, (InputStream) fis, (int)(imagePath.length()));
			preparedStatement.executeUpdate();
		}
		catch (FileNotFoundException e) {
			System.out.println("Found some error : " + e.toString());
			return false;
		}
		catch (SQLException e) {
			System.out.println("DropDB Exception :" + e.toString());
			return false;
		}
		finally {
			Close();
		}
		
		return true;
	}

	public ImageIcon getUserImage(int uid) {
		ImageIcon image = null;
		
		try {
			preparedStatement = connection.prepareStatement("SELECT image FROM user_information WHERE uid = ?");
			preparedStatement.setInt(1, uid);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				Blob blob = resultSet.getBlob("image");
				byte[] imageByte = blob.getBytes(1, (int) blob.length());
				image = new ImageIcon(imageByte);
			}
		}
		catch (SQLException e) {
			System.out.println(e.toString());
		}
		finally {
			Close();
		}

		return image;
	}
	
	public String getUserName(int uid) {
		String name = "";
		
		try {
			preparedStatement = connection.prepareStatement("SELECT name FROM user_information WHERE uid = ?");
			preparedStatement.setInt(1, uid);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				name = resultSet.getString("name");
			}
		}
		catch (SQLException e) {
			System.out.println(e.toString());
		}
		finally {
			Close();
		}

		return name;
	}

	public int getUserDeptartment(int uid) {
		int dept = 0;
		
		try {
			preparedStatement = connection.prepareStatement("SELECT department FROM user_information WHERE uid = ?");
			preparedStatement.setInt(1, uid);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				dept = resultSet.getInt("department");
			}
		}
		catch (SQLException e) {
			System.out.println(e.toString());
		}
		finally {
			Close();
		}

		return dept;
	}

	public int getUserGrade(int uid) {
		int grade = 0;
		
		try {
			preparedStatement = connection.prepareStatement("SELECT grade FROM user_information WHERE uid = ?");
			preparedStatement.setInt(1, uid);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				grade = resultSet.getInt("grade");
			}
		}
		catch (SQLException e) {
			System.out.println(e.toString());
		}
		finally {
			Close();
		}

		return grade;
	}

	public int getUserGender(int uid) {
		int gender = 0;
		
		try {
			preparedStatement = connection.prepareStatement("SELECT gender FROM user_information WHERE uid = ?");
			preparedStatement.setInt(1, uid);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				gender = resultSet.getInt("gender");
			}
		}
		catch (SQLException e) {
			System.out.println(e.toString());
		}
		finally {
			Close();
		}

		return gender;
	}

	public int getUserAge(int uid) {
		int age = 0;
		
		try {
			preparedStatement = connection.prepareStatement("SELECT age FROM user_information WHERE uid = ?");
			preparedStatement.setInt(1, uid);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				age = resultSet.getInt("age");
			}
		}
		catch (SQLException e) {
			System.out.println(e.toString());
		}
		finally {
			Close();
		}

		return age;
	}
}

package ntou.cs.java.Sunshystar;

import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

/*
 * Sunshystar �D�{��
 * �t�d�D�{�����y�{�P�޲z�A�I�s�U�Ӥl�t�ΰ���{��
 * 
 * @author Ricky
 */
public class Sunshystar {
	
	private final String pathOfImages[] = { "images/background.jpg",
			  								"images/balloon.png",
			  								"images/cloud.png",
			  								"images/sunny.png",
			  								"images/depression.png",
			  								"images/freekick.png",
			  								"images/golf.png",
			  								"images/kemal.png",
			  								"images/squiggles.png",
			  								"images/sunflower.png",
			  								"images/thesea.png" };
	private final int guiWidth = 750;
	private final int guiHeight = 550;
	private static int meUID = -1;
	private static int pairUID = 0;
	private static JFrame frame;
	private static Container main;
	
	protected final static String serverIPAddress = "140.121.197.101";
	protected final static int port = 8000;
	protected static Socket socket;
	protected final String driver = "com.mysql.jdbc.Driver";
	protected final String databaseURL = "jdbc:mysql://140.121.197.101:3306/mysql";
	protected final String user = "sunshystar";
	protected final String password = "sunshyboyz";
	protected Connection connection = null;
	protected ResultSet resultSet = null;
	protected PreparedStatement preparedStatement = null;
	
	static BufferedImage bufferedImage;
	static LoginRegisterGUI loginRegisterGUI;
	static ChatRoomGUI chatRoomGUI;
	static ProfileGUI profileGUI;
	static LoginRegister loginRegister;
	static ChatRoom chatRoom;
	static Profile myProfile;
	static Profile pairProfile;
	static Sunshystar sunshystar = new Sunshystar();
	
	enum ValidationState {
		SUCCESS("���\�I"),
		DATABASE_ERROR("��Ʈw�o�Ϳ��~�I"),
		PWD_UNEQUAL_ERROR("�K�X���ҿ��~�I"),
		COMFIRM_PWD_UNEQUAL_ERROR("�s�K�X�P�T�{�K�X���ۦP�I"),
		PWD_FIELD_INPUT_ERROR("�K�X����J���~�A�Y����s�K�X�Яd�šI"),
		MAIL_NOT_FOUND_ERROR("���H�c�����U�I"),
		MAIL_HAS_BE_USEED_ERROR("���H�c�w�Q���U�I"),
		MAIL_FORMAT_ERROR("�H�c�榡���~�A�榡�аѦҦܩ��T���I"),
		PWD_FORMAT_ERROR("�K�X�ܤ�6�Ӧr���A�̦h���W�L20�Ӧr���C"),
		NAME_FORMAT_ERROR("�W�٦ܤ�1�Ӧr���A�̦h���W�L20�Ӧr���C"),
		AGE_FORMAT_ERROR("�j�ǥͦ~��������18-30�����A�Ф������~�֡I");
		
		private String description;
		
		ValidationState(String description) {
			this.description = description;
		}
		
		public String toString() {
			return description;
		}
		
	};
	
	Sunshystar() {
		Random randomOfImages = new Random();
		String path = pathOfImages[randomOfImages.nextInt(pathOfImages.length)];
		
		//��l��Frame�I���Ϥ�
		try {
			bufferedImage = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}

		//��l��Database
		try {
            Class.forName(driver); 
            connection = DriverManager.getConnection(databaseURL, user, password);
        } 
        catch(ClassNotFoundException e) { 
            e.printStackTrace(); 
        } 
        catch(SQLException e) { 
            e.printStackTrace();
        }
		
		//�إߵ{���l�t��
		loginRegister = new LoginRegister();
		loginRegisterGUI = new LoginRegisterGUI(loginRegister, bufferedImage);
		
		//ø�s�n�J�P���U�ϧΤ���
		frame = new JFrame("SunshyStar");
		
		//Paint LoginRegisterGUI
		main = frame.getContentPane();
		main.add(loginRegisterGUI);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(guiWidth, guiHeight);
		frame.setVisible(true);
		
		//��ø�sFrame�b�ù�����
		Point p = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		frame.setLocation(p.x - guiWidth / 2, p.y - guiHeight / 2);
		
		//�������������A�N�VServer�e�X�T��
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				//�p�G��襼�n�J�N���}�{�����ݦVServer�o�X�T��
				if(ChatRoom.sendMsgWriter != null) {
					ChatRoom.sendMsgWriter.println("out");
					ChatRoom.sendMsgWriter.flush();
				}
			} 
		});
	}
	
	protected void Close() { 
	    try { 
	    	if(resultSet!=null) { 
	    		resultSet.close(); 
	    		resultSet = null; 
	    	}
	    	if(preparedStatement!=null) { 
	    		preparedStatement.close(); 
	    		preparedStatement = null; 
	    	} 
	    } 
	    catch(SQLException e) { 
	      System.out.println("Close Exception :" + e.toString()); 
	    } 
	}
	
	public static void setMeUID(int meUID) {
		Sunshystar.meUID = meUID;
	}

	public static int getMeUID() {
		return meUID;
	}
	
	public static void setYouUID(int youUID) {
		Sunshystar.pairUID = youUID;
	}

	public static int getYouUID() {
		return pairUID;
	}
	
	public static void paintChatRoomGUI() {
		main.removeAll();
		main.add(chatRoomGUI);
		main.repaint();
		main.validate();
	}
	
	public static void paintProfileGUI() {
		main.removeAll();
		main.add(profileGUI);
		main.repaint();
		main.validate();
	}
	
	public static void main(String[] args) {
	}
}

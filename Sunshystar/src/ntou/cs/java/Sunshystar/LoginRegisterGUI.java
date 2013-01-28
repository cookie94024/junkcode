package ntou.cs.java.Sunshystar;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/*
 * LoginRegisterGUI �w�q�n�J�P���U���ϧΤ���
 * �{�����i�J�}�l�e���Aø�s�n�J�P���U�򥻸�T���ϧΤ���
 * 
 * @author Jack, Ricky
 */
public class LoginRegisterGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel spacelabel;
	private JLabel title;
	private JLabel description;
	private JLabel notice;
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JLabel label4;
	private JLabel label5;
	private JLabel label6;
	private JLabel label7;
	private JLabel label8;
	private JLabel label9;
	private JLabel label10;
	private JLabel label11;
	private JLabel label12;
	private JTextField textfield1;
	private JTextField textfield2;
	private JTextField textfield3;
	private JPasswordField passwordfield1;
	private JPasswordField passwordfield2;
	private JPasswordField passwordfield3;
	private JButton button1;
	private JButton button2;
	private JComboBox dept;
	private JComboBox grad;
	private JComboBox gend;
	private JTextField age;
	private String department[] = { "�Ӳ�Ǩt",
									"��B�޲z�Ǩt",
									"�B���Ǩt",
									"�����u�{�Ǩt",
									"���~��Ǩt",
									"�����i�޾Ǩt",
									"�ͩR��Ǩt",
									"���v�ͪ���s��",
									"�ͪ���ެ�s��",
									"���ҥͪ��P���~��Ǩt ",
									"���v���Ҹ�T�t",
									"���v�ưȻP�귽�޲z��s��",
									"���v���ҤƾǻP�ͺA��s��",
									"����P���q�u�{�Ǩt",
									"�t�Τu�{�[�y��Ǩt",
									"�e���u�{�Ǩt",
									"���Ƥu�{��s��",
									"�q���u�{�Ǩt",
									"��T�u�{�Ǩt",
									"�q�T�P�ɯ�u�{�Ǩt",
									"���q��Ǭ�s��",
									"���v�k�߬�s��",
									"���θg�٬�s��",
									"�Ш|��s��",
									"���v��Ƭ�s��",
									"���έ^�y��s��",
									"��B�޲z�Ǩt(�i�׳�)",
									"���~��ǾǨt(�i�׳�)",
									"�q���u�{�Ǩt(�i�׳�)",
									"��T�޲z�Ǩt(�i�׳�)",
									"���v�귽�޲z�Ǩt(�i�׳�)",
									"�e���u�{�Ǩt(�i�׳�)",
									"�Ӳ�Ǩt(�i�׳�)",
									"���ҥͪ��P���~��Ǩt(�i�׳�)",
									"���v�k�߬�s��(�i�׳�)",
									"���v���Ҹ�T�t(�i�׳�)" };
	private String grade[] = {"1", "2", "3", "4"};
	private String gender[] = {"�k", "�k"};
	private int gradIndex;
	private int gendIndex;
	private int deptIndex;
	private BufferedImage image;
	
	public LoginRegisterGUI(final LoginRegister loginRegister, final BufferedImage image) {
		this.image = image;
		GridBagLayout gridbag = new GridBagLayout();
	    GridBagConstraints cons = new GridBagConstraints();
		setLayout( gridbag );
		
		cons.gridx = 1;
		cons.gridy = 0;
		cons.gridwidth = 10;
		title = new JLabel("SunshyStar");
		title.setFont( new Font("Serif", Font.BOLD, 80) );
		gridbag.setConstraints(title, cons);
		add(title);
		
		cons.gridx = 1;
		cons.gridy = 1;
		cons.gridwidth = 10;
		cons.insets = new Insets(5, 0, 5, 0);
		description = new JLabel("���W�[�J�A���n�`�ۡI");
		description.setFont( new Font("Serif", Font.ITALIC, 20) );
		gridbag.setConstraints(description, cons);
		add(description);
		
		cons.insets = new Insets(2, 0, 2, 0);
		
		cons.gridx = 1;
		cons.gridy = 2;
		cons.gridwidth = 2;
		label1 = new JLabel("Login");
		label1.setFont( new Font("Serif", Font.BOLD, 40) );
		gridbag.setConstraints(label1, cons);
		add(label1);
		
		cons.gridx = 1;
		cons.gridy = 3;
		cons.gridwidth = 1;
		label2 = new JLabel("�ǮիH�c  ");
		gridbag.setConstraints(label2, cons);
		add(label2);
		
		cons.gridx = 2;
		cons.gridy = 3;
		cons.gridwidth = 1;
		textfield1 = new JTextField(15);
		gridbag.setConstraints(textfield1, cons);
		add(textfield1);
		
		cons.gridx = 1;
		cons.gridy = 4;
		cons.gridwidth = 1;
		label3 = new JLabel("�n�J�K�X  ");
		gridbag.setConstraints(label3, cons);
		add(label3);
		
		cons.gridx = 2;
		cons.gridy = 4;
		cons.gridwidth = 1;
		passwordfield1 = new JPasswordField(15);
		gridbag.setConstraints(passwordfield1, cons);
		add(passwordfield1);
		
		cons.gridx = 1;
		cons.gridy = 6;
		cons.gridwidth = 2;
		button1 = new JButton("�n�J");
		gridbag.setConstraints(button1, cons);
		add(button1);
		
		button1.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent event) {
						textfield1.setText("B97570136@ntou.edu.tw");
						passwordfield1.setText("123456");
						
						if(textfield1.getText().equals("")) {
							JOptionPane.showMessageDialog(LoginRegisterGUI.this, "�H�c��쥼��J");
						}
						else if(String.valueOf(passwordfield1.getPassword()).equals("")) {
							JOptionPane.showMessageDialog(LoginRegisterGUI.this, "�K�X��쥼��J");
						}
						else {
							Sunshystar.ValidationState state = loginRegister.login(textfield1.getText(), String.valueOf(passwordfield1.getPassword()));
							
							//�n�J���Ѹ��X���~�T������
							if(state != Sunshystar.ValidationState.SUCCESS) {
								JOptionPane.showMessageDialog(LoginRegisterGUI.this, state) ;
							}
							//���\�n�J��|�]�w�P���o����uid�A�ëإ�ChatRoom�t�λPProfile�t��
							else {
								loginPreparation();
							}
						}
					}
				}
		);
		
		cons.gridx = 4;
		cons.gridy = 1;
		cons.gridwidth = 2;
		spacelabel = new JLabel("                ");
		gridbag.setConstraints(spacelabel, cons);
		add(spacelabel);
		
		cons.gridx = 6;
		cons.gridy = 2;
		cons.gridwidth = 4;
		label4 = new JLabel("Register");
		label4.setFont( new Font("Serif", Font.BOLD, 40) );
		gridbag.setConstraints(label4, cons);
		add(label4);
		
		cons.anchor = GridBagConstraints.WEST;
		
		cons.gridx = 6;
		cons.gridy = 3;
		cons.gridwidth = 1;
		label5 = new JLabel("�ǮիH�c  ");
		gridbag.setConstraints(label5, cons);
		add(label5);
		
		cons.gridx = 7;
		cons.gridy = 3;
		cons.gridwidth = 4;
		textfield2 = new JTextField(20);
		gridbag.setConstraints(textfield2, cons);
		add(textfield2);
		
		cons.gridx = 6;
		cons.gridy = 4;
		cons.gridwidth = 1;
		label6 = new JLabel("�n�J�K�X  ");
		gridbag.setConstraints(label6, cons);
		add(label6);
		
		cons.gridx = 7;
		cons.gridy = 4;
		cons.gridwidth = 4;
		passwordfield2 = new JPasswordField(20);
		gridbag.setConstraints(passwordfield2, cons);
		add(passwordfield2);
		
		cons.gridx = 6;
		cons.gridy = 5;
		cons.gridwidth = 1;
		label7 = new JLabel("�T�{�K�X  ");
		gridbag.setConstraints(label7, cons);
		add(label7);
		
		cons.gridx = 7;
		cons.gridy = 5;
		cons.gridwidth = 4;
		passwordfield3 = new JPasswordField(20);
		gridbag.setConstraints(passwordfield3, cons);
		add(passwordfield3);
		
		cons.gridx = 6;
		cons.gridy = 6;
		cons.gridwidth = 1;
		label8 = new JLabel("�W��  ");
		gridbag.setConstraints(label8, cons);
		add(label8);
		
		cons.fill = GridBagConstraints.HORIZONTAL;
		
		cons.gridx = 7;
		cons.gridy = 6;
		cons.gridwidth = 4;
		textfield3 = new JTextField(20);
		gridbag.setConstraints(textfield3, cons);
		add(textfield3);
		
		cons.gridx = 6;
		cons.gridy = 7;
		cons.gridwidth = 1;
		label9 = new JLabel("�t��  ");
		gridbag.setConstraints(label9, cons);
		add(label9);
		
		cons.gridx = 7;
		cons.gridy = 7;
		cons.gridwidth = 4;
		dept = new JComboBox(department);
		dept.setMaximumRowCount(5);
		gridbag.setConstraints(dept, cons);
		add(dept);
		
		dept.addItemListener(
				new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent event) {
						if ( event.getStateChange() == ItemEvent.SELECTED )
							deptIndex = dept.getSelectedIndex();
					}
				}
		);
		
		cons.gridx = 6;
		cons.gridy = 8;
		cons.gridwidth = 1;
		label10 = new JLabel("�~��  ");
		gridbag.setConstraints(label10, cons);
		add(label10);
		
		cons.gridx = 7;
		cons.gridy = 8;
		cons.gridwidth = 4;
		grad = new JComboBox(grade);
		grad.setMaximumRowCount(5);
		gridbag.setConstraints(grad, cons);
		add(grad);
		
		grad.addItemListener(
				new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent event) {
						if ( event.getStateChange() == ItemEvent.SELECTED )
							gradIndex = grad.getSelectedIndex();
					}
				}
		);
		
		cons.gridx = 6;
		cons.gridy = 9;
		cons.gridwidth = 1;
		cons.fill = GridBagConstraints.NONE;
		label12 = new JLabel("�ʧO  ");
		gridbag.setConstraints(label12, cons);
		add(label12);
		
		cons.gridx = 7;
		cons.gridy = 9;
		cons.gridwidth = 1;
		gend = new JComboBox(gender);
		gend.setMaximumRowCount(5);
		gridbag.setConstraints(gend, cons);
		add(gend);
		
		gend.addItemListener(
				new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent event) {
						if ( event.getStateChange() == ItemEvent.SELECTED )
							gendIndex = gend.getSelectedIndex();
					}
				}
		);
		
		cons.gridx = 8;
		cons.gridy = 9;
		cons.gridwidth = 1;
		JLabel space = new JLabel("                  ");
		gridbag.setConstraints(space, cons);
		add(space);
		
		cons.gridx = 9;
		cons.gridy = 9;
		cons.gridwidth = 1;
		label11= new JLabel("�~��  ");
		gridbag.setConstraints(label11, cons);
		add(label11);
		
		cons.gridx = 10;
		cons.gridy = 9;
		cons.gridwidth = 1;
		age = new JTextField(5);
		gridbag.setConstraints(age, cons);
		add(age);
		
		cons.gridx = 6;
		cons.gridy = 11;
		cons.gridwidth = 5;
		spacelabel = new JLabel("                          ");
		gridbag.setConstraints(spacelabel, cons);
		add(spacelabel);
		
		cons.anchor = GridBagConstraints.CENTER;
		
		cons.gridx = 7;
		cons.gridy = 12;
		cons.gridwidth = 3;
		cons.fill = 0;
		button2 = new JButton("���U");
		gridbag.setConstraints(button2, cons);
		add(button2);
		
		button2.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent event) {
						int ageNum = 0;
						
						try {
							ageNum = Integer.parseInt(age.getText());
						}
						catch(NumberFormatException e) {
							ageNum = 0;
							JOptionPane.showMessageDialog( LoginRegisterGUI.this, "�ж�J���T���~��" );
						}
						
						if(textfield2.getText().equals("")) {
							JOptionPane.showMessageDialog( LoginRegisterGUI.this, "�H�c��쥼��J" );
						}
						else if(String.valueOf(passwordfield2.getPassword()).equals("")) {
							JOptionPane.showMessageDialog( LoginRegisterGUI.this, "�K�X��쥼��J" );
						}
						else if(String.valueOf(passwordfield3.getPassword()).equals("")) {
							JOptionPane.showMessageDialog( LoginRegisterGUI.this, "�T�{�K�X��쥼��J" );
						}
						else if(textfield3.getText().equals("")) {
							JOptionPane.showMessageDialog( LoginRegisterGUI.this, "�ʺ���쥼��J" );
						}
						else if(ageNum < 18 || ageNum > 30) {
							JOptionPane.showMessageDialog( LoginRegisterGUI.this, "�j�ǥͦ~��������18-30�����A�Ф������~�֡I" );
						}
						else if(!String.valueOf(passwordfield2.getPassword()).equals(String.valueOf(passwordfield3.getPassword()))) {
							JOptionPane.showMessageDialog( LoginRegisterGUI.this, "�⦸�K�X��J�۲�" );
						}
						else {
							Sunshystar.ValidationState state = loginRegister.register(textfield2.getText(), String.valueOf(passwordfield2.getPassword()), textfield3.getText(), gendIndex, deptIndex, gradIndex, Integer.parseInt(age.getText()));
							
							// ���U���Ѹ��X���~�T�������A���U���\�i�J��ѫǡA
							if(state != Sunshystar.ValidationState.SUCCESS) {
								JOptionPane.showMessageDialog( LoginRegisterGUI.this, state) ;
							}
							else {
								JOptionPane.showMessageDialog( LoginRegisterGUI.this, "���U���\�I ���W�}�l SunshyStar�I") ;
								loginPreparation();
							}
						}
					}
				}
		);
		
		cons.gridx = 0;
		cons.gridy = 13;
		cons.gridwidth = 10;
		spacelabel = new JLabel("                          ");
		gridbag.setConstraints(spacelabel, cons);
		add(spacelabel);
		
		cons.gridx = 0;
		cons.gridy = 14;
		cons.gridwidth = 10;
		notice = new JLabel("���H�c����ߥx�W���v�j�Ǥ��H�c, �p�GB12345678@ntou.edu.tw");
		gridbag.setConstraints(notice, cons);
		add(notice);
	}
	
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = (getWidth() - image.getWidth()) / 2;
        int y = (getHeight() - image.getHeight()) / 2;
        g.drawImage(image, x, y, this);
    }
	
	public void loginPreparation() {
		Sunshystar.pairProfile = new Profile();
		Sunshystar.chatRoom = new ChatRoom();
		Sunshystar.chatRoomGUI = new ChatRoomGUI(Sunshystar.chatRoom, image);
		Sunshystar.myProfile = new Profile();
		Sunshystar.profileGUI = new ProfileGUI(Sunshystar.myProfile, image);

		// �إ�Thread �����T��
		Thread receiveMsgThread = new Thread(new ReceiveMsgThread());
		receiveMsgThread.start();
		
		Sunshystar.paintChatRoomGUI();
	}

}

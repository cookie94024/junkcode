package ntou.cs.java.Sunshystar;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/*
 * ProfileGUI �w�q�ϥΪ̭ӤH�ɮת��ϧΤ���
 * �z�L�ϧΤ������ϥΪ̧�e�����s���έק��Ʈw���e
 * 
 * @author Ricky
 */
public class ProfileGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private JPasswordField oldPwd;
	private JPasswordField newPwd;
	private JPasswordField comPwd;
	private JTextField name;
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
	private String grade[] = { "1", "2", "3", "4" };
	private String gender[] = { "�k", "�k" };
	private JLabel imageLabel;
	private BufferedImage image;
	private String nameField = "";
	private String oldPwdField = "";
	private String newPwdField = "";
	private String comPwdField = "";
	private int deptField = 0;
	private int gradeField = 0;
	private int genderField = 0;
	private String ageField = null;
	private int uid = Sunshystar.getMeUID();
	
	ProfileGUI(final Profile profile, BufferedImage image) {
		this.image = image;
		setLayout(new GridBagLayout());
		String mailTitle = "  ���o�I " + profile.getUserMail(uid) + "  ";
		this.setBorder(BorderFactory.createTitledBorder(mailTitle));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(3, 3, 3, 3);
		
		ImageIcon imageIcon = profile.getUserImage(uid);
		imageLabel = new JLabel(imageIcon);
		imageLabel.setPreferredSize(new Dimension(180, 180));
		imageLabel.setBorder(BorderFactory.createEtchedBorder());
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 7;
		add(imageLabel, gbc);

		JLabel space = new JLabel("            ");
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		add(space, gbc);

		gbc.anchor = GridBagConstraints.WEST;
		
		JLabel label2 = new JLabel("��J�±K�X  ");
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		add(label2, gbc);

		oldPwd = new JPasswordField(15);
		gbc.gridx = 3;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		add(oldPwd, gbc);

		JLabel label3 = new JLabel("��J�s�K�X  ");
		gbc.gridx = 2;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		add(label3, gbc);

		newPwd = new JPasswordField(15);
		gbc.gridx = 3;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		add(newPwd, gbc);

		JLabel label4 = new JLabel("�T�{�s�K�X  ");
		gbc.gridx = 2;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		add(label4, gbc);

		comPwd = new JPasswordField(15);
		gbc.gridx = 3;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		add(comPwd, gbc);
		
		JLabel nameLabel = new JLabel("�W�� ");
		gbc.gridx = 2;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		add(nameLabel, gbc);
		
		name = new JTextField(15);
		name.setText(profile.getUserName(uid));
		nameField = name.getText();
		gbc.gridx = 3;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		add(name, gbc);

		JLabel label5 = new JLabel("��t  ");
		gbc.gridx = 2;
		gbc.gridy = 6;
		gbc.gridwidth = 1;
		add(label5, gbc);

		dept = new JComboBox(department);
		dept.setMaximumRowCount(5);
		dept.setSelectedIndex(profile.getUserDeptartment(uid));
		deptField = dept.getSelectedIndex();
		gbc.gridx = 3;
		gbc.gridy = 6;
		gbc.gridwidth = 1;
		add(dept, gbc);
		
		JLabel label6 = new JLabel("�~��  ");
		gbc.gridx = 2;
		gbc.gridy = 7;
		gbc.gridwidth = 1;
		add(label6, gbc);

		grad = new JComboBox(grade);
		grad.setMaximumRowCount(5);
		grad.setSelectedIndex(profile.getUserGrade(uid));
		gradeField = grad.getSelectedIndex();
		gbc.gridx = 3;
		gbc.gridy = 7;
		gbc.gridwidth = 1;
		add(grad, gbc);

		JLabel label7 = new JLabel("�ʧO  ");
		gbc.gridx = 2;
		gbc.gridy = 8;
		gbc.gridwidth = 1;
		add(label7, gbc);

		gend = new JComboBox(gender);
		gend.setMaximumRowCount(5);
		gend.setSelectedIndex(profile.getUserGender(uid));
		genderField = gend.getSelectedIndex();
		gbc.gridx = 3;
		gbc.gridy = 8;
		gbc.gridwidth = 1;
		add(gend, gbc);

		JLabel label8 = new JLabel("�~��  ");
		gbc.gridx = 2;
		gbc.gridy = 9;
		gbc.gridwidth = 1;
		add(label8, gbc);

		age = new JTextField(3);
		age.setText(String.valueOf(profile.getUserAge(uid)).toString());
		ageField = age.getText();
		gbc.gridx = 3;
		gbc.gridy = 9;
		gbc.gridwidth = 1;
		add(age, gbc);

		gbc.anchor = GridBagConstraints.CENTER;
		
		JButton uploadButton = new JButton("�W�Ƿs�Ӥ�");
		gbc.gridx = 0;
		gbc.gridy = 9;
		gbc.gridwidth = 1;
		add(uploadButton, gbc);

		final JFileChooser fc = new JFileChooser();

		uploadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int returnVal = fc.showOpenDialog(ProfileGUI.this);
				Profile user = new Profile();

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					if(user.setUserImage(uid, file)) {
						JOptionPane.showMessageDialog(ProfileGUI.this, "�W�ǷӤ����\�I");
						ImageIcon imageIcon = user.getUserImage(uid);
						imageLabel.setIcon(imageIcon);
						ChatRoom.sendMsgWriter.println("uid=" + Sunshystar.getMeUID() + "�ӤH�Ӥ���s�F�I");
					} else {
						JOptionPane.showMessageDialog(ProfileGUI.this, "�W�ǷӤ����ѡA�Э��s�W�ǡC");
					}
				}
			}
		});
		
		JLabel tipLabel = new JLabel("���Ϥ������j�p��180x180");
		gbc.gridx = 0;
		gbc.gridy = 11;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(25, 0, 0, 0);
		add(tipLabel, gbc);
		
		JLabel tipLabel2 = new JLabel("�Y��ܤ����T�A�е���ܾA��j�p");
		gbc.gridx = 0;
		gbc.gridy = 12;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(0, 0, 0, 0);
		add(tipLabel2, gbc);

		JButton okButton = new JButton("�T�w");
		gbc.gridx = 1;
		gbc.gridy = 13;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(50, 5, 5, 5);
		add(okButton, gbc);

		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				oldPwdField = String.valueOf(oldPwd.getPassword());
				newPwdField = String.valueOf(newPwd.getPassword());
				comPwdField = String.valueOf(comPwd.getPassword());
				nameField = name.getText();
				deptField = dept.getSelectedIndex();
				gradeField = grad.getSelectedIndex();
				genderField = gend.getSelectedIndex();
				ageField = age.getText();
				
				Sunshystar.ValidationState state = profile.setProfile(uid, oldPwdField, newPwdField, comPwdField, nameField, deptField, gradeField, genderField, ageField);
				
				// ��s���ҥ��Ѹ��X���ѰT�������A�Y���\�N��ܧ�s���\��^��ѫǹϧΤ���
				if(state != Sunshystar.ValidationState.SUCCESS) {
					JOptionPane.showMessageDialog(ProfileGUI.this, state);
				} else {
					JOptionPane.showMessageDialog(ProfileGUI.this, "�ӤH��Ƨ�s���\�I");
					ChatRoom.sendMsgWriter.println("uid=" + Sunshystar.getMeUID() + "�ӤH��Ƨ�s�F�I");
					Sunshystar.paintChatRoomGUI();
				}
			}
		});

		JButton backButton = new JButton("��^");
		gbc.gridx = 2;
		gbc.gridy = 13;
		gbc.gridwidth = 1;
		add(backButton, gbc);

		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Sunshystar.paintChatRoomGUI();
			}
		});
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int x = (getWidth() - image.getWidth());
		int y = (getHeight() - image.getHeight());
		g.drawImage(image, x, y, this);
	}
}

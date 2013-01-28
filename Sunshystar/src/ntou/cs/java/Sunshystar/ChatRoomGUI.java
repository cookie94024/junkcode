package ntou.cs.java.Sunshystar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/*
 * ChatRoomGUI �w�q��ѫǪ��ϧΤ���
 * ���H�H����ѫǪ��ϧΤ����A��ܰt�諸���Ӥ��θ�T�ä��۲��
 * �i�z�L����Ѥ����i�J�ӤH�ɮפ����Τ����U�@���H������ѹ�H
 * 
 * @author Jack, Howard
 */
public class ChatRoomGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel imageTitle = new JLabel("�z�{�b����ѹ�H�O...");
	private ImageIcon imageIcon = new ImageIcon();
	private JLabel imageLabel = new JLabel();
	private JTextArea profile = new JTextArea(10, 5);
	static JTextArea messageBox = new JTextArea();
	private JTextField inputMessage = new JTextField(49);
	private JScrollPane messageBoxScrollPane = new JScrollPane(messageBox);
	private JButton nextPerson;
	private JButton setProfile;
	private JButton changeColorButton;
	private JButton changeTextColor;
	private BufferedImage image;
	private Color messageBoxColor = new Color(51, 170, 191);
	private Container container1;
	private Container container2;
	private Container container3;
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

	public ChatRoomGUI(final ChatRoom chatRoom, BufferedImage image) {
		int youUID = Sunshystar.getYouUID();
		String profileSet = "\n�ʺ١G"
				+ Sunshystar.pairProfile.getUserName(youUID) + "\n�ʧO�G"
				+ gender[Sunshystar.pairProfile.getUserGender(youUID)]
				+ "\n�~�֡G" + Sunshystar.pairProfile.getUserAge(youUID) + "\n�t�ҡG"
				+ department[Sunshystar.pairProfile.getUserDeptartment(youUID)]
				+ "\n�~�šG" + grade[Sunshystar.pairProfile.getUserGrade(youUID)];
		String nameTitle = "  ���o�I " + Sunshystar.pairProfile.getUserName(Sunshystar.getMeUID()) + "  ";
		
		this.image = image;
		this.setBorder(BorderFactory.createTitledBorder(nameTitle));

		setLayout(new BorderLayout(10, 10));

		container1 = new Container();
		container1.setLayout(new FlowLayout());

		container2 = new Container();
		container2.setLayout(new BorderLayout());

		container3 = new Container();
		container3.setLayout(new BorderLayout());

		imageTitle.setOpaque(false);
		imageTitle.setFont(new Font("Serif", Font.BOLD, 14));
		imageTitle.setForeground(Color.BLACK);
		container2.add(imageTitle, BorderLayout.NORTH);

		imageIcon = Sunshystar.pairProfile.getUserImage(youUID);
		imageLabel.setIcon(imageIcon);
		imageLabel.setPreferredSize(new Dimension(180, 180));
		imageLabel.setBorder(BorderFactory.createEtchedBorder());
		container2.add(imageLabel, BorderLayout.CENTER);

		profile.setLineWrap(true);
		profile.setWrapStyleWord(true);
		profile.setEditable(false);
		profile.setOpaque(false);
		profile.setText(profileSet);
		profile.setFont(new Font("Serif", Font.BOLD, 13));
		profile.setForeground(Color.BLACK);
		container2.add(profile, BorderLayout.SOUTH);

		messageBox.setBackground(messageBoxColor);
		messageBox.setLineWrap(true);
		messageBox.setWrapStyleWord(true);
		messageBox.setEditable(false);
		add(messageBoxScrollPane, BorderLayout.CENTER);

		inputMessage.setEditable(true);
		container3.add(new JScrollPane(inputMessage), BorderLayout.EAST);
		inputMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg = inputMessage.getText();

				if (msg.trim().equals("")) {
					JOptionPane.showMessageDialog(ChatRoomGUI.this, "�п�J��Ѥ��e");
					inputMessage.requestFocus();
					return;
				}

				// �o�e�T����Server
				String name = Sunshystar.myProfile.getUserName(Sunshystar.getMeUID());
				ChatRoom.sendMsgWriter.println(name + "�G" + msg);

				// �ϥΪ̦b���r�Ϥ��e�X�T����M�ť��r��
				inputMessage.setText("");
			}
		});

		changeColorButton = new JButton("�󴫭��O�C��");
		changeColorButton.setPreferredSize(new Dimension(130, 40));
		container1.add(changeColorButton);
		changeColorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				messageBoxColor = JColorChooser.showDialog(ChatRoomGUI.this,
						"�п�ܤ@�ت����C��", messageBoxColor);
				if (messageBoxColor == null)
					messageBoxColor = new Color(51, 170, 191);
				messageBox.setBackground(messageBoxColor);
			}
		});
		
		changeTextColor = new JButton("�󴫤�r�C��");
		changeTextColor.setPreferredSize(new Dimension(130, 40));
		container1.add(changeTextColor);
		changeTextColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				messageBoxColor = JColorChooser.showDialog(ChatRoomGUI.this,
						"�п�ܤ@�ؤ�r�C��", messageBoxColor);
				if (messageBoxColor == null)
					messageBoxColor = new Color(0, 0, 0);
				messageBox.setForeground(messageBoxColor);
			}
		});

		setProfile = new JButton("�s��ӤH���");
		setProfile.setPreferredSize(new Dimension(130, 40));
		container1.add(setProfile);
		setProfile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Sunshystar.paintProfileGUI();
			}
		});

		nextPerson = new JButton("�U�@��");
		nextPerson.setPreferredSize(new Dimension(130, 40));
		container1.add(nextPerson);
		nextPerson.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				ChatRoom.sendMsgWriter.println("change");
			}
		});

		add(container1, BorderLayout.NORTH);
		add(container2, BorderLayout.WEST);
		add(container3, BorderLayout.SOUTH);
	}
	
	public void setChatRoomGUIInfo() {
		int youUID = Sunshystar.getYouUID();
		String profileSet = "\n�ʺ١G"
			+ Sunshystar.pairProfile.getUserName(youUID) + "\n�ʧO�G"
			+ gender[Sunshystar.pairProfile.getUserGender(youUID)]
			+ "\n�~�֡G" + Sunshystar.pairProfile.getUserAge(youUID) + "\n�t�ҡG"
			+ department[Sunshystar.pairProfile.getUserDeptartment(youUID)]
			+ "\n�~�šG" + grade[Sunshystar.pairProfile.getUserGrade(youUID)];
		String nameTitle = "  ���o�I " + Sunshystar.pairProfile.getUserName(Sunshystar.getMeUID()) + "  ";
		
		this.setBorder(BorderFactory.createTitledBorder(nameTitle));
		imageIcon = Sunshystar.pairProfile.getUserImage(youUID);
		imageLabel.setIcon(imageIcon);
		profile.setText(profileSet);
		
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int x = (getWidth() - image.getWidth()) / 2;
		int y = (getHeight() - image.getHeight()) / 2;
		g.drawImage(image, x, y, this);
	}
}

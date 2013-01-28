package ntou.cs.java.Sunshystar;

import java.io.IOException;

/*
 * ReceiveMsgThread �t�d���_������Server�ǨӪ��T��
 * �|�P�_Server�ǹL�Ӫ��O�@��T���٬O�S��T���A���O�����P���B�z
 * 
 * @author Ricky
 */
public class ReceiveMsgThread implements Runnable {
	String msg = null;
	String uid = null;
	int pairUID = 0;
	int firstMessage = 0;

	public void run() {
		//�}�ұ����T��
		try {
			while ((msg = ChatRoom.receiveMsgReader.readLine()) != null) {
				//�p�G�t�令�\�A�����ۥ洫UID
				if(msg.matches("uid=+\\d")) {
					pairUID = Integer.parseInt(msg.substring(4));
					
					//���o���UID�A���L�|�P�ɦ���ۤvUID�A�ҥH�ݭn���L�o
					if (pairUID != Sunshystar.getMeUID()) {
						Sunshystar.setYouUID(pairUID);
					}
					
					//��s���ӤH�ꥻ��ƻP�j�Y��
					Sunshystar.chatRoomGUI.setChatRoomGUIInfo();
					
					//���ݭn��uid msg�L�X��
					continue;
				}
				else if(msg.equals("���H�n��A���o�I�I")) {
					//�p�G�t�令�\�N���ۥ洫UID
					ChatRoom.sendMsgWriter.println("uid=" + Sunshystar.getMeUID());
				}
				else if(msg.equals("���ݤ�")) {
					//�]�w�w�]Sunshystar�򥻸�T
					Sunshystar.setYouUID(0);
					
					//��s���ӤH�ꥻ��ƻP�j�Y��
					Sunshystar.chatRoomGUI.setChatRoomGUIInfo();
				}
				else if(msg.equals("uid=" + Sunshystar.getMeUID() + "�ӤH��Ƨ�s�F�I")) {
					msg = "�ӤH��Ƨ�s���\�I";
					//��s�ӤH�ꥻ��ƻP�j�Y��
					Sunshystar.chatRoomGUI.setChatRoomGUIInfo();
				}
				else if(msg.equals("uid=" + Sunshystar.getYouUID() + "�ӤH��Ƨ�s�F�I")) {
					msg = "����s�F�ӤH��ơI";
					//��s���ӤH�ꥻ��ƻP�j�Y��
					Sunshystar.chatRoomGUI.setChatRoomGUIInfo();
				}
				else if(msg.equals("uid=" + Sunshystar.getMeUID() + "�ӤH�Ӥ���s�F�I")) {
					msg = "�z���ӤH�Ӥ��󴫤F�I";
					//��s�ӤH�ꥻ��ƻP�j�Y��
					Sunshystar.chatRoomGUI.setChatRoomGUIInfo();
				}
				else if(msg.equals("uid=" + Sunshystar.getYouUID() + "�ӤH�Ӥ���s�F�I")) {
					msg = "���󴫤F�ӤH�Ӥ��o�I";
					//��s���ӤH�ꥻ��ƻP�j�Y��
					Sunshystar.chatRoomGUI.setChatRoomGUIInfo();
				}
				
				//�L�X���쪺�T����ChatRoomGUI�W
				ChatRoom.listData.add(msg);
				if( firstMessage == 0 ){
					ChatRoomGUI.messageBox.append(msg);
					firstMessage = 1;
				}
				else {
					ChatRoomGUI.messageBox.append("\n" + msg);
				}
				
				ChatRoomGUI.messageBox.setCaretPosition(ChatRoomGUI.messageBox.getText().length());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

package ntou.cs.java.Sunshystar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

/*
 * ChatRoom �w�q�i�J��ѫǫ�PServer�إ߳s�u
 * 
 * @author Ricky
 */
public class ChatRoom extends Sunshystar {
	static BufferedReader receiveMsgReader;
	static PrintWriter sendMsgWriter;
	static Vector<String> listData = new Vector<String>();
	
	ChatRoom() {
		try {
        	// �إ�Socket
			socket = new Socket(serverIPAddress, port);
			
			// ����Server�ݪ��T��
			InputStream in = socket.getInputStream();
			receiveMsgReader = new BufferedReader(new InputStreamReader(in, "Unicode"));
			
			// �e��Server�ݪ��T��
			OutputStream out = socket.getOutputStream();
			sendMsgWriter = new PrintWriter(new OutputStreamWriter(out, "Unicode"), true);
		}
		catch (UnknownHostException e) {
			System.out.println(e.toString());
		}
		catch (IOException e) {			
			System.out.println(e.toString());
		}
		
	}
}

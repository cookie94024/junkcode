



import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import java.io.OutputStream;

import java.io.OutputStreamWriter;

import java.io.PrintWriter;

import java.net.Socket;

/*

 * �t�d�B�z�s��server�ݪ�socket�A�C��client�����U�۪�ChangeThread�b��ť

 * �ΨӺ�ťclient�ǰe�L�Ӫ��T���C

 * 

 * 

 * @author Arvin

 */

public class ChatThread extends Thread {

private Socket socket = null;

   private ChatServer server;

   private BufferedReader br;

   private PrintWriter pw;

   private int ID;

   public ChatThread(Socket socket1,ChatServer server) {

	   this.socket = socket1;

	   this.server = server;	

	   this.setName(socket.getInetAddress().getHostAddress());

	   try {

	    InputStream in = socket.getInputStream();      //�����Ȥ�ݪ��T��

		br = new BufferedReader(new InputStreamReader(in,"Unicode"));  

		   OutputStream out = socket.getOutputStream(); //�e�X�T�����Ȥ��

		   pw = new PrintWriter(new OutputStreamWriter(out, "Unicode"),true);

	} catch (IOException e) {		

		e.printStackTrace();

	}

	   

   }

	

   //Ū���Ȥ�ݨӪ��T��

	public void run() {

		int out = 0;

		try {

			String msg = br.readLine();

			if(msg==null){

				server.removeClient(this);

			}

			while(msg != null) {

				if(msg.equalsIgnoreCase("out")){

					server.removeClient(this);

					out=1;

				}

				else if(msg.equalsIgnoreCase("change")){

					//�Nclient���waitting

						if( ChatServer.waitting.contains(this)){

							//do nothing

						}

						else{

					server.changeClient(this);

						}

				}

				else{

				server.sendMessage(msg,getNumber()); //�N�T���൹server�h�o�e

				}

				if(out==0){

				msg = br.readLine();

				}

				else break;

			}

		} catch (IOException e) {			

			e.printStackTrace();

			

		}

		

	}

	

	

	public void sendMessage(String msg) {	

		pw.println(msg);

	}

	public void setNumber(int x){

		ID = x ;

	}

	public int getNumber(){

		return ID;

	}

	public boolean isClose(){

		return socket.isClosed();

	}

	public void release() {

		if(br != null ) {

			try {

				br.close();

			} catch (IOException e) {		

				e.printStackTrace();

			}

		}

		

		if(pw != null) {

			pw.close();

		}

		

		if(socket != null && ! socket.isClosed()) {

			try {

				socket.close();

			} catch (IOException e) {				

				e.printStackTrace();

			}

		}

		

	}

}


import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Random;

/*
 * �t�d��ťclients(�s��ثe�u�Wclient����C)�Pwaitting(client�����ݦ�C)
 * �i��۹�����client�ݪ��A�ഫ (��Ѥ��B���ݤ�)
 * 
 * @author Arvin
 */
public class ChangeThread extends Thread{
    private ArrayList<ChatThread> waitting;
    private int id;
    private ServerSocket server;
    private Random randomclient = new Random();
	public ChangeThread(ServerSocket server){
	  this.server = server;
	  this.waitting = ChatServer.waitting;
	  this.id = ChatServer.id;
  }
  public void run(){
	  
	 while(!server.isClosed()){
		 
	  if(waitting.size()==1 && ChatServer.check == 0){  //�Y���ݧǦC���u���Ȥ�ݤ@�ӤH
	    	waitting.get(0).sendMessage("���ݤ�");
	    	waitting.get(0).setNumber(id++); //�]�w���Ȥ�ݪ��Ǹ�
	    	ChatServer.check = 1;
	    }
	    else if(waitting.size()>1){   //�Y���Ȥ�i�Jserver�ɡA���ݧǦC���w���H
	    	ChatServer.check = 0;
	    	int rand = randomclient.nextInt(waitting.size()); //�q���ݧǦC���ⵥ�ݪ��H��X
	    	int rand2 = randomclient.nextInt(waitting.size());
	    	while(rand==rand2){
	    		rand = randomclient.nextInt(waitting.size()); 
		    	rand2 = randomclient.nextInt(waitting.size());
	    	}
          waitting.get(rand).setNumber(id);  //�N�����ݪ��H�P��n�J���H�i��t�X
          waitting.get(rand).sendMessage("���H�n��A���o�I�I");
          waitting.get(rand2).setNumber(id);  //�N�����ݪ��H�P��n�J���H�i��t�X
          waitting.get(rand2).sendMessage("���H�n��A���o�I�I");
          if (rand > rand2){
          waitting.remove(rand);  //�N�쵥�ݤH����
          waitting.remove(rand2);//�N�Ȥ�ݱq���ݦC������
          }
          else {
          waitting.remove(rand2);  //�N�쵥�ݤH����
          waitting.remove(rand);//�N�Ȥ�ݱq���ݦC������	  
          }
          id++;  //�Ȥ�Ǹ��~��W�[
	    }
	 }
  }
}

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

public class ServerController {
    public TextArea msgBox;
    public Label lblsStatus;


    static ServerSocket serverSocket;
    static HashMap clientColl=new HashMap();


    public void initialize(){
        new Thread(() -> {
        try{

            serverSocket=new ServerSocket(5000);
            lblsStatus.setText("Server Started!");

             new ClientAccept().start();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }).start();
    }






    class ClientAccept extends Thread{

        @Override
        public void run() {
            while (true){

                try{

                    Socket s=serverSocket.accept();
                    String i=new DataInputStream(s.getInputStream()).readUTF();

                    if (clientColl.containsKey(i)){
                        DataOutputStream dataOutputStream=new DataOutputStream(s.getOutputStream());
                        dataOutputStream.writeUTF("You are already Registered");
                    }else {
                        clientColl.put(i,s);
                        msgBox.appendText(i+"Joined !\n");
                        DataOutputStream dout=new DataOutputStream(s.getOutputStream());
                        dout.writeUTF("");

                        new MsgRead(s,i).start();
                        new PrepareClientList().start();
                    }





                }catch (Exception ex){
                    ex.printStackTrace();
                }



            }
        }
    }

    class MsgRead extends Thread{
         Socket s;
         String ID;
         MsgRead(Socket s, String ID) {
          this.s=s;
          this.ID=ID;
        }

        @Override
        public void run() {
         while (!clientColl.isEmpty()){

             try {
                 String i=new DataInputStream(s.getInputStream()).readUTF();

                 if(i.equals("morkingdhsjdsgd1234")){
                     clientColl.remove(ID);
                     msgBox.appendText(ID+": removed! \n");
                     new PrepareClientList().start();

                     Set k=clientColl.keySet();
                     Iterator itr=k.iterator();

                     while (itr.hasNext()){
                         String key= (String) itr.next();
                         if(!key.equalsIgnoreCase(ID)){
                             try{
                               new DataOutputStream(((Socket) clientColl.get(key)).getOutputStream()).writeUTF(/*"<"+ID+" to "+key+" > "+*/i);
                             }catch (Exception ex){
                                 clientColl.remove(key);
                                 msgBox.appendText(key+": removed!");
                                 new PrepareClientList().start();
                             }
                         }
                     }

                 }
                 else if(i.contains("#4344554@@@@@67667@@")){
                     i=i.substring(20);
                     StringTokenizer st=new StringTokenizer(i+":");
                     String id=st.nextToken();
                     i=st.nextToken();

                     try{
                         new DataOutputStream(((Socket) clientColl.get(id)).getOutputStream()).writeUTF("<"+ID+" to "+id+" > "+i);
                     }catch (Exception ex){
                         clientColl.remove(id);
                         msgBox.appendText(id+": removed!");
                         new PrepareClientList().start();
                     }

                 }else {

                     Set k=clientColl.keySet();
                     Iterator itr=k.iterator();

                     while (itr.hasNext()){
                         String key= (String) itr.next();
                         if(!key.equalsIgnoreCase(ID)){

                             try{
                                 new DataOutputStream(((Socket) clientColl.get(key)).getOutputStream()).writeUTF(/*"< "+" to All "+*/i);
                             }catch (Exception ex){
                                 clientColl.remove(key);
                                 msgBox.appendText(key+": removed!");
                                 new PrepareClientList().start();
                             }

                         }
                     }


                 }

             }catch (Exception ex){
                 ex.printStackTrace();
             }

          }

         }


    }

    class PrepareClientList extends Thread{

        @Override
        public void run() {

            try{

                String ids="";
                Set k=clientColl.keySet();
                Iterator itr=k.iterator();

                while (itr.hasNext()){
                    String key= (String) itr.next();
                    ids+=key+",";
                }
                if (ids.length()!=0){
                    ids=ids.substring(0,ids.length()-1);
                    itr=k.iterator();
                    while (itr.hasNext()){
                        String key= (String) itr.next();

                        try{
                            new DataOutputStream(((Socket) clientColl.get(key)).getOutputStream()).writeUTF(":;.,/="+ids);
                        }catch (Exception ex){
                            clientColl.remove(key);
                            msgBox.appendText(key+": removed!");
                        }
                    }
                }


            }catch (Exception ex){
                ex.printStackTrace();
            }


        }
    }

}

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class MyClientController {

    public TextArea msgBox;

    public TextField txtSendText;
    public TextArea UL;
    public Label lblid;

    static String Id,ClientId="";
    static DataInputStream dataInputStream;
    static DataOutputStream dataOutputStream;
    static ServerSocket serverSocket;
    static DefaultListModel dlm;

    public MyClientController() {

    }


    public void initialize(){

        Id="Vihaga";



        try{


            Socket socket=new Socket("localhost",5000);
            dataInputStream=new DataInputStream(socket.getInputStream());
            dataOutputStream=new DataOutputStream(socket.getOutputStream());

            dataOutputStream.writeUTF(Id);

            String i=new DataInputStream(socket.getInputStream()).readUTF();

            if (i.equals("You are already Registered")){
                new Alert(Alert.AlertType.INFORMATION,"You are already Registered!", ButtonType.OK).show();
            }else {


                try {



                    dlm=new DefaultListModel();

            lblid.setText(Id);
                    dataInputStream=new DataInputStream(socket.getInputStream());
                    dataOutputStream=new DataOutputStream(socket.getOutputStream());
                    new Read().start();

                }catch (Exception ex){
                    ex.printStackTrace();
                }

            }


        }catch (Exception ex){
            ex.getStackTrace();
        }


    }





    public void btnSendOnAction(ActionEvent actionEvent) {

        try{

            String m=txtSendText.getText(),mm=m;
            String CI=ClientId;
            if (!ClientId.isEmpty()){
                m="#4344554@@@@@67667@@"+CI+" : "+mm;
                dataOutputStream.writeUTF(m);
                txtSendText.setText("");
                msgBox.appendText(/*"< You send to "+CI+" > "+*/mm+"\n");
            }
            else {
                dataOutputStream.writeUTF(m);
                txtSendText.setText("");
                msgBox.appendText(/*"< You to All > "+*/mm+"\n");
            }



        }catch (Exception ex){
           new Alert(Alert.AlertType.INFORMATION,"User does not exit anymore",ButtonType.OK).show();

        }






    }


    class Read extends Thread{

        @Override
        public void run() {

            while (true){
                try {

                    String m=dataInputStream.readUTF();
                    if (m.contains(":,.,/=")){
                        m=m.substring(6);
                        dlm.clear();
                        StringTokenizer st=new StringTokenizer(m,",");

                        while (st.hasMoreTokens()){
                            String u=st.nextToken();
                            if (!Id.equals(u)){
                                dlm.addElement(u);
                            }
                        }

                    }else {
                        msgBox.appendText(""+m+"\n");
                    }








                }catch (Exception ex){
                    break;
                }




            }



        }
    }






}


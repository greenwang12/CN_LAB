import java.net.*;

public class DatagramClient{
        public static void main(String[] args){
                try{
                        DatagramSocket clientSocket=new DatagramSocket(9876);
                        byte[] receiveData =new byte[1024];
                        System.out.println("Client is running...Waiting for messages from the server.\n");
                        while(true){
                                DatagramPacket receivePacket= new DatagramPacket(receiveData,receiveData.length);
                                clientSocket.receive(receivePacket);
                                String message=new String(receivePacket.getData(),0,receivePacket.getLength());
                                System.out.println("Message from Server:"+message);
                                if(message.equalsIgnoreCase("exit")){
                                        System.out.println("Client Shutting down...");
                                        break;
                                }
                        }
                        clientSocket.close();
                }catch(Exception e){
                        e.printStackTrace();
                }
        }
}

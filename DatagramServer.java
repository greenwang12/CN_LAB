import java.net.*;
import java.util.Scanner;

public class DatagramServer{
        public static void main(String[] args){
                Scanner sc=new Scanner(System.in);
                try{
                        DatagramSocket serverSocket=new DatagramSocket();
                        InetAddress clientAddress=InetAddress.getByName("localhost");
                        int clientPost=9876;
                        System.out.println("Server started.Type messages to send to the client.");
                        System.out.println("Type 'exit' to quit.\n");
                        while(true){
                                System.out.print("Server:");
                                String message=sc.nextLine();
                                byte[]sendData=message.getBytes();
                                DatagramPacket sendPacket=new DatagramPacket(sendData,sendData.length,clientAddress,clientPost);
                                serverSocket.send(sendPacket);
                                if(message.equalsIgnoreCase("exit")){
                                        System.out.println("Server shutting down...");
                                        break;
                                }
                        }
                        serverSocket.close();
                        sc.close();
                }catch(Exception e){
                        e.printStackTrace();
                }
        }
}

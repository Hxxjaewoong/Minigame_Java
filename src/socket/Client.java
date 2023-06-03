package socket;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    public int userNumber;
    private DataInputStream in;
    private DataOutputStream out;

    private String receivedMessage;


    public Client() {
        Socket socket;
        try {
            socket = new Socket("localhost", 5000);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            // 유저 번호 저장
            userNumber = in.readInt();
            System.out.println("user number: " + userNumber);

            Thread receiveThread = new Thread(new ReceiveThread());
            receiveThread.start();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    // 서버로부터 메시지 수신하는 쓰레드
    private class ReceiveThread extends Thread {
        @Override
        public void run() {
            String message;
            try {
                while (true) {
                    message = in.readUTF(); // 서버로부터 메시지 수신

                    if (message != null) {
                        System.out.println("server >>> client : " + message);
                        receivedMessage = message;
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    // 서버로 메시지를 전송하는 메소드
    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    
    // 서버로부터 받은 메시지를 각 게임에게 전달하고 받은 메시지는 null로 set
    // receivedMessage 변수의 잠금 필요
    public synchronized String getReceivedMessage() {
        String temp = receivedMessage;
        if (temp != null) {
            receivedMessage = null;
            return temp;
        }

        return null;
    }
}
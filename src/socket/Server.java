/*
 * 
 * 서버
 * 
 * TODO: 게임이 진행 중일 때는 다른 게임 선택 못하도록?
 * 
 * 
 */

package socket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;





public class Server implements Runnable {
    private final int MAX_USER = 2;

    public DataOutputStream[] out = new DataOutputStream[2];

    @Override
    public void run() {

        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("서버 시작. 클라이언트 연결 대기 중...");

            int userNumber = 0;
            while (userNumber < MAX_USER) {
                Socket socket = serverSocket.accept(); // 클라이언트 연결 수락
                System.out.println("클라이언트 연결: " + userNumber);

                // 클라이언트로부터 메시지 받아 게임에 대한 처리하는 쓰레드 생성 및 시작
                GameManager gameManager = new GameManager(socket, userNumber, out);
                gameManager.start();
                
                userNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

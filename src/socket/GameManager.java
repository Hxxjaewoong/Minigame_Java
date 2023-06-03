package socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

import games.Game1;
import games.Game2;
import games.Game3;



public class GameManager extends Thread {
    Random rd = new Random();

    // static으로 선언해야 멀티쓰레드에서도 같은 게임을 다루게 됨
    // 두 유저가 같은 조건일 필요 없으면 굳이 아니어도 됨
    public static Game1 game1;
    public static Game2 game2;
    public static Game3 game3;

    private int userNumber;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream[] out;

    public static int playing; // 어떤 게임을 플레이하고 있는지
    public static int choice0;
    public static int choice1;
    
    public GameManager(Socket socket, int userNumber, DataOutputStream[] out) {

        game1 = new Game1();
        game2 = new Game2();
        game3 = new Game3();

        this.userNumber = userNumber;
        this.socket = socket;
        this.out = out;
        
        playing = 0;
        choice0 = 0;
        choice1 = 0;

        try {
            in = new DataInputStream(socket.getInputStream());
            out[userNumber] = new DataOutputStream(socket.getOutputStream());
            
            // 유저 번호 알려줌
            out[userNumber].writeInt(userNumber);
            out[userNumber].flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = in.readUTF(); // 클라이언트로부터 메시지 수신

                if (message != null) {
                    System.out.println("클라이언트 " + userNumber + ": " + message);

                    String response = handleReceivedMessage(message);
                    System.out.println(">>> " + response);

                    if (response != null) {
                        out[0].writeUTF(response);
                        out[0].flush();
                        out[1].writeUTF(response);
                        out[1].flush();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out[userNumber].close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    // 클라이언트로부터 받은 메시지 처리
    public String handleReceivedMessage(String message) {

        String[] parsed = message.split(" ");

        // "start gameNumber"
        // 게임 시작버튼 누름
        if (parsed[0].equals("start")) {
            int gameNumber = Integer.parseInt(parsed[1]);
            if (userNumber == 0) choice0 = gameNumber;
            else                 choice1 = gameNumber;
            
            // 시작버튼 누른 게임이 같으면 게임 시작
            if (choice0 == choice1) {
                playing = choice0;
                switch (playing) {
                    case 1:
                        game1.initTarget();
                        break;
                    case 2:
                        break;
                        //
                        //
                        //
                }
                return "start " + gameNumber + " " + rd.nextInt(2); // "start gameNumber"
            }
            return null;
        }

        if (message.startsWith("exit")) {
            // 유저 나감
        }

        // 게임 선택이 아니면
        // 게임별로 각각의 method가 처리
        switch (playing) {
            case 1:
                return handleGame1(message);
            default:
                return null;
        }
    }






    // 선택한 게임에 따라 게임 상태 초기화 (필요시 작성)
    public void gameInit() {
        System.out.println("game " + playing + " 시작");
        switch (playing) {
            case 0:
                // main 화면
                break;
            case 1:
                game1.initTarget();
            // case 2:
            //     game2 = new Game2();
            //     // + send the initial info of game2
        }
    }

    
    // game1의 행동 처리
    public String handleGame1(String message) {
        String[] parsed = message.split(" ");

        // "click user r c"
        // user가 (r,c)를 클릭함
        if (parsed[0].equals("click")) {
            int user = Integer.parseInt(parsed[1]);
            int r = Integer.parseInt(parsed[2]);
            int c = Integer.parseInt(parsed[3]);
            int result = game1.checkPosition(r, c);

            if (result == -1) {
                return "alreadyOpen";
            }
            if (result == 0) {
                game1.incScore(user);

                if (game1.isFinished()) {
                    return "winner " + game1.score0 + " " + game1.score1;
                }
                return "target " + r + " " + c + " " + game1.foundCount;
            }
            return "empty " + r + " " + c + " " + result;
        }


        return null;
    }

    public String handleGame2(String message) {
        String[] parsed = message.split(" ");


        return null;
    }



    /*
     * 나머지 게임에 대해서도 처리하는 함수 만들어야 함
     */


}

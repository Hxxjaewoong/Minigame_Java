package socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

import games.Game1;
import games.Game2;
import games.Game3;
import games.Game4;
import games.Game5;



public class GameManager extends Thread {
    Random rd = new Random();

    // static으로 선언해야 멀티쓰레드에서도 같은 게임을 다루게 됨
    // 두 유저가 같은 조건일 필요 없으면 굳이 아니어도 됨
    // 서버가 주체가 되지 않아도 되는 게임은 여기서 필요 없음 (e.g. 먼저 끝나면 이기는 게임)
    public static Game1 game1;
    public static Game2 game2;
    public static Game3 game3;
    public static Game4 game4;
    public static Game5 game5;

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
        game4 = new Game4();
        game5 = new Game5();

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
        System.out.println("<<< " + message);

        // "start [gameNumber]"
        // 게임 시작버튼 누름
        if (parsed[0].equals("start")) {
            int gameNumber = Integer.parseInt(parsed[1]);
            if (userNumber == 0) choice0 = gameNumber;
            else                 choice1 = gameNumber;
            
            // 시작버튼 누른 게임이 같으면 게임 시작
            if (choice0 == choice1) {
                
                playing = choice0; // 시작할 game number 기록
                String initString = "";

                switch (playing) {
                    case 1:
                        game1.initGame();
                        // first player
                        initString = String.valueOf(rd.nextInt(2));
                        break;

                    case 2:
                        game2.initGame();
                        // game 2의 배치 전달
                        for (int r = 0; r < Game2.SIZE; r++) {
                            for (int c = 0; c < Game2.SIZE; c++) {
                                initString = initString + String.valueOf(Game2.layer1[r][c]) + " ";
                            }
                        }
                        break;

                    case 3:
                        game3.initGame();
                        // first player
                        initString = String.valueOf(rd.nextInt(2));
                        break;

                    case 4:
                        break;

                    case 5:
                        break;
                }
                return "start " + gameNumber + " " + initString; // "start gameNumber"
            }
            return null;
        }


        // "exit [userNumber]"
        // 유저 나감
        if (message.startsWith("exit")) {
            // 유저 나감
        }

        // 게임 선택이 아니면
        // 게임별로 각각의 method가 처리
        switch (playing) {
            case 1:
                return handleGame1(message);
            case 2:
                return handleGame2(message);
            case 3:
                return handleGame3(message);
            // case 4:
            //     return handleGame4(message);
            // case 5:
            //     return handleGame5(message);
            default:
                return null;
        }
    }



    
    // game1의 행동 처리
    public String handleGame1(String message) {
        String[] parsed = message.split(" ");

        // "click [user] [r] [c]"
        // user가 (r,c)를 클릭함
        if (parsed[0].equals("click")) {
            int user = Integer.parseInt(parsed[1]);
            int r = Integer.parseInt(parsed[2]);
            int c = Integer.parseInt(parsed[3]);
            int result = game1.isTarget(r, c);

            if (result == -1) {
                return "alreadyOpen";
            }
            if (result == 0) {
                game1.incScore(user);

                if (game1.isFinished()) {
                    return "finish " + r + " " + c + " " + game1.score0 + " " + game1.score1;
                }
                return "target " + r + " " + c + " " + game1.foundCount;
            }
            return "empty " + r + " " + c + " " + result;
        }


        return null;
    }

    // game2의 행동 처리
    public String handleGame2(String message) {
        String[] parsed = message.split(" ");
        // "click [user] [r] [c]"
        // user가 (r,c)를 클릭함
        if (parsed[0].equals("click")) {
            int user = Integer.parseInt(parsed[1]);
            int r = Integer.parseInt(parsed[2]);
            int c = Integer.parseInt(parsed[3]);

			if (game2.isTargetNumber(r, c, user)) {
                int nextTarget = Game2.targetNumber[user];
                int nextLayer = Game2.layer2[r][c];
                System.out.println(user + " next target is " + nextTarget);

                if (game2.isFinished(user)) {
                    return "finish " + user + " " + r + " " + c;
                }
                return "target " + user + " " + r + " " + c + " " + nextTarget + " " + nextLayer;

            } else {
                return "notTarget " + user + " " + r + " " + c;
            }
        }

        return null;
    }


    // game3의 행동 처리
    public String handleGame3(String message) {
        String[] parsedMessage = message.split(" ");

        // "open [user]"
        // user가 카드 open
        if (parsedMessage[0].equals("open")) {
            int user = Integer.parseInt(parsedMessage[1]);

            game3.openCard(user);
            int loser = game3.isFinished();

            // 게임 끝 누군가 카드 0장 됨
            if (loser != -1) {
                return "finish open " + loser;
            }

            int topFruit = Game3.topCard[user][0];
            int topNumber = Game3.topCard[user][1];

            return "open " + user + " " + topFruit + " " + topNumber;
        }


        // "bell [user]"
        // user가 종 누름
        if (parsedMessage[0].equals("bell")) {
            int user = Integer.parseInt(parsedMessage[1]);

            boolean isSuccess = game3.isSum5(user);

            int loser = game3.isFinished();

            // 게임 끝 누군가 카드 0장 됨
            if (loser != -1) {
                return "finish bell " + loser;
            }

            // 성공: 5개인 과일 있음
            if (isSuccess) {
                return "success " + user + " " + Game3.stackCardCount[user];
            }

            // 실패
            return "fail " + user;
        }

        return null;
    }



    /*
     * 나머지 게임에 대해서도 처리하는 함수 만들어야 함
     */


}

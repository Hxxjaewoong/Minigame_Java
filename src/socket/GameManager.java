package socket;

import java.util.Random;

import games.Game1;
import games.Game2;
import games.Game3;
import games.Game4;



public class GameManager {
    Random rd = new Random();

    public static Game1 game1;
    public static Game2 game2;
    public static Game3 game3;
    public static Game4 game4;

    public static int playingNumber; // 어떤 게임을 플레이하고 있는지
    public static int[] choice = new int[2];
    public static int[] scores = new int[2]; // 두 유저의 점수 기록. 필요하지 않을 수 있음

    private static final int MIN = -9999; // 임의의 최소 점수
    
    public GameManager() {

        game1 = new Game1();
        game2 = new Game2();
        game3 = new Game3();
        game4 = new Game4();
        
        playingNumber = 0;
        choice[0] = 0;
        choice[1] = 0;
    }



    // 클라이언트로부터 받은 메시지 처리
    public String handleReceivedMessage(String message) {

        String[] parsed = message.split(" ");

        // "start [user] [gameNumber]"
        // 게임 시작 버튼 누름
        if (parsed[0].equals("start")) {
            int user = Integer.parseInt(parsed[1]);
            int gameNumber = Integer.parseInt(parsed[2]);
            choice[user] = gameNumber;
            
            // 시작 버튼 누른 게임이 같으면 게임 시작
            if (choice[0] == choice[1]) {
                playingNumber = choice[0]; // 시작할 game number 기록
                String initString = "";

                switch (playingNumber) {
                    case 1:
                        game1.initGame();
                        // first player
                        initString = String.valueOf(rd.nextInt(2));
                        break;
                    case 2:
                        game2.initGame();
                        // 타일 배치 전달
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
                        // 점수 초기화
                        scores[0] = MIN;
                        scores[1] = MIN;
                        break;
                    case 5:
                        break;
                }

                return "start " + playingNumber + " " + initString; // "start [gameNumber] [initString]"
            }

            return null;
        }


        // 게임 시작이나 종료가 아니면
        // 게임별로 각각의 method가 처리
        switch (playingNumber) {
            case 1:
                return handleGame1(message);
            case 2:
                return handleGame2(message);
            case 3:
                return handleGame3(message);
            case 4:
                return handleGame4(message);
            // case 5:
            //     return handleGame5(message);
            default:
                return null;
        }
    }



    // game1의 행동 처리
    private String handleGame1(String message) {
        String[] parsedMessage = message.split(" ");

        // "click [user] [r] [c]"
        // user가 (r,c)를 클릭함
        if (parsedMessage[0].equals("click")) {
            int user = Integer.parseInt(parsedMessage[1]);
            int r = Integer.parseInt(parsedMessage[2]);
            int c = Integer.parseInt(parsedMessage[3]);
            int result = game1.isTarget(r, c);

            if (result == -1) {
                return "alreadyOpen " + user;
            }
            if (result == 0) {
                game1.incScore(user);

                if (game1.isFinished()) {
                    return "finish " + r + " " + c + " " + game1.score[0] + " " + game1.score[1];
                }
                return "target " + r + " " + c + " " + game1.foundCount;
            }
            return "empty " + r + " " + c + " " + result;
        }


        return null;
    }

    // game2의 행동 처리
    private String handleGame2(String message) {
        String[] parsedMessage = message.split(" ");
        // "click [user] [r] [c]"
        // user가 (r,c)를 클릭함
        if (parsedMessage[0].equals("click")) {
            int user = Integer.parseInt(parsedMessage[1]);
            int r = Integer.parseInt(parsedMessage[2]);
            int c = Integer.parseInt(parsedMessage[3]);

			if (game2.isTargetNumber(r, c, user)) {
                int nextTarget = Game2.targetNumber[user];
                int nextLayer = Game2.layer2[r][c];

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
    private String handleGame3(String message) {
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

        
    private String handleGame4(String message) {
        String[] parsedMessage = message.split(" ");

        // "finish [user] [score]"
        // 게임 종료 유저의 스코어 기록
        if (parsedMessage[0].equals("finish")) {
            int user = Integer.parseInt(parsedMessage[1]);
            int score = Integer.parseInt(parsedMessage[2]);

            scores[user] = score;

            if (scores[0] == MIN || scores[1] == MIN) {
                return null;
            }

            return "finish " + scores[0] + " " + scores[1];
        }
        
        return null;
    }

}

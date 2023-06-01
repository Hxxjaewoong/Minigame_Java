package games;


import java.util.Random;
import java.util.Scanner;


public class Game4 {
//	private static final int GAME_DURATION = 30; // 게임 지속 시간 (초)
//    private static final int RANGE_MIN = 1;      // 랜덤 수 범위 최솟값
//    private static final int RANGE_MAX = 100;    // 랜덤 수 범위 최댓값
//
//    private int player1Score;                    // 플레이어 1의 점수
//    private int player2Score;                    // 플레이어 2의 점수
//
//    public Game4() {
//    	player1Score = 0;
//        //player2Score = 0; 소켓을 이용해 대결시 구현 예정
//        
//        Random random = new Random();
//        
//        while(true) {
//        	int num1 = random.nextInt(RANGE_MAX - RANGE_MIN + 1) + RANGE_MIN;
//            int num2 = random.nextInt(RANGE_MAX - RANGE_MIN + 1) + RANGE_MIN;
//            int result;
//            
//            switch (random.nextInt(3)) {
//            case 0:
//                result = num1 + num2;
//                System.out.print(num1 + " + " + num2 + " = ");
//                break;
//            case 1:
//                result = num1 - num2;
//                System.out.print(num1 + " - " + num2 + " = ");
//                break;
//            case 2:
//                result = num1 * num2;
//                System.out.print(num1 + " * " + num2 + " = ");
//                break;
//                /*
//            case 3:
//                result = num1 / num2;
//                System.out.print(num1 + " / " + num2 + " = ");
//                break;
//                */
//            default:
//                result = 0;
//                break;
//            }
//            
//            boolean isEven = result % 2 == 0;
//            //int answer = getPlayerAnswer();
//            /*
//            if ((isEven && answer == 2) || (!isEven && answer == 1)) {
//            	player1Score++;
//            
//            } */    
//        }
//        //scanner.close();
//        //String winner = getWinner();
//        
//        
//        		
//        
//    }
//    /*
//    private int getPlayerAnswer() {
//        int answer;
//        do {
//            System.out.println("	1. 홀수\t2. 짝수");
//            System.out.print("정답을 입력하세요: ");
//            
//        } while (answer != 1 && answer != 2);
//
//        return answer;
//    }
//	*/
//    private String getWinner() {
//        if (player1Score > player2Score)
//            return "플레이어 1";
//        else if (player2Score > player1Score)
//            return "플레이어 2";
//        else
//            return "무승부";
//    }
//    
//    
}

package games;
/*
 * 
 * 할리갈리
 * 
 * 게임 진행은 서버가?
 * 서버가 각 클라이언트에게 누구 차례인지 상대가 카드를 뽑았는지 알려줘야 함
 * 
 * TODO: 각 사람에게 남은 카드 장수 표시
 * 
 */


import java.util.Random;


public class Game3 {
	private static final int TOTAL_CARDS = 56;
	public static final int FRUITS = 4;
	public static final String[] FRUITS_NAME = { "banana", "lime", "plum", "strawberry" };
	public static final int[] NUMBERS = { 1,1,1,2,2,2,3,3,3,4,4,4,5,5 }; // 과일 개수 별 확률 조절


	public boolean myTurn;
	public int myCardsCount;
	
	public int myStackCount;
	public int opponentStackCount;
	public int[] myTopCard; 	  // (과일, 개수)
	public int[] opponentTopCard; // (과일, 개수)

	
	Random rd = new Random();



	
	public Game3() {
		myTurn = true;
		myCardsCount = TOTAL_CARDS / 2;
		myStackCount = 0;
		opponentStackCount = 0;

		clearBoard();
	}
	
	public void clearBoard() {
		myTopCard = new int[] {-1, -1};
		opponentTopCard = new int[] {-1, -1};
	}
	
	// 개수의 합이 5개인 과일이 있는지 확인
	public boolean isSum5() {
		int[] sums = {0, 0, 0, 0};

		if (myTopCard[0] != -1) sums[myTopCard[0]] += myTopCard[1];
		if (opponentTopCard[0] != -1) sums[opponentTopCard[0]] += opponentTopCard[1];

		for (int sum: sums) {
			if (sum == 5) return true;
		}

		return false;
	}
	

	public boolean openCard() {
		if (myTurn == false) {
			return false;
		}

		myTopCard[0] = rd.nextInt(FRUITS);
		myTopCard[1] = NUMBERS[rd.nextInt(NUMBERS.length)];


		// game의 테스트를 위해 임시로 opponent도 랜덤 생성
		// socket을 통해 상대가 누를 때 변경 되도록 구현해야 함
		opponentTopCard[0] = rd.nextInt(FRUITS);
		opponentTopCard[1] = NUMBERS[rd.nextInt(NUMBERS.length)];

		myCardsCount--;
		return true;
	}

	// targetNumber가 전체 숫자 크기보다 크면 종료
	public boolean isFinished() {
		return myCardsCount == 0 || myCardsCount == TOTAL_CARDS;
	}
}



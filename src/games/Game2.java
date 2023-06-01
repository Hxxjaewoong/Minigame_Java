package games;
/*
 * 
 * 50개의 숫자를 빨리 클릭하는 게임
 * 
 * TODO: 화면에 결과 출력
 * TODO: 승리조건 만족되면 게임 종료
 * 
 * 
 */


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;


public class Game2 {
	public static final int SIZE = 5;
	
	List<Integer> baseNumbers = new ArrayList<>(); // for initailizating game

	public static int[][] layer1 = new int[SIZE][SIZE];;
	public static int[][] layer2 = new int[SIZE][SIZE];;
	public static boolean[][] opened1 = new boolean[SIZE][SIZE];;
	public static boolean[][] opened2 = new boolean[SIZE][SIZE];;
	public static int targetNumber;
	
	public Game2() {
		// add base numbers to list
        for (int i = 1; i <= SIZE*SIZE; i++) baseNumbers.add(i);

		// init layer 1 (1 ~ 25)
		Collections.shuffle(baseNumbers);
		for (int r = 0; r < SIZE; r++) {
			for (int c = 0; c < SIZE; c++) {
				layer1[r][c] = baseNumbers.get(r*SIZE+c);
			}
		}

		// init layer 2 (26 ~ 50)
		Collections.shuffle(baseNumbers);
		for (int r = 0; r < SIZE; r++) {
			for (int c = 0; c < SIZE; c++) {
				layer2[r][c] = baseNumbers.get(r*SIZE+c) + SIZE*SIZE;
			}
		}
		
		// init opened
		for (boolean[] row : opened1) Arrays.fill(row, false);
		for (boolean[] row : opened2) Arrays.fill(row, false);
		
		targetNumber = 1;
	}
	
	
	// 현재 클릭해야할 숫자인지 확인
	// true면 targetNumber 증가
	// false면 do nothing (or 벌칙?)
	public boolean isTargetNumber(int row, int col) {
		if (opened1[row][col] == false && layer1[row][col] == targetNumber) {
			opened1[row][col] = true;
			targetNumber++;
			return true;
		}
		if (opened2[row][col] == false && layer2[row][col] == targetNumber) {
			opened2[row][col] = true;
			targetNumber++;
			return true;
		}

		return false;
	}
	

	// targetNumber가 전체 숫자 크기보다 크면 종료
	public boolean isFinished() {
		return targetNumber > SIZE*SIZE*2;
	}
}



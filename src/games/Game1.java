package games;
/*
 * 
 * 10*10의 격자에 숨겨진 5개의 보물을 찾는 게임
 * 특정 위치를 고르면 보물을 찾거나 가장 가까운 보물과의 거리가 출력됨
 * 
 * TODO: 화면에 결과 출력
 * TODO: 승리조건 만족되면 게임 종료
 * TODO: 턴제 구현 
 * 
 */


import java.util.Arrays;
import java.util.Random;


public class Game1 {
	public static final int MAP_SIZE = 10;
	public static final int TARGET_COUNT = 5;

	public boolean playing;
	
	public static boolean[][] map = new boolean[MAP_SIZE][MAP_SIZE];    // target이 있는지
	public static boolean[][] opened = new boolean[MAP_SIZE][MAP_SIZE]; // 확인했는지
	public static int[][] targetPositions = new int[TARGET_COUNT][2];   // target의 위치 저장
	public static boolean[] targetFounded = new boolean[TARGET_COUNT];  // target을 찾았는지
	public static int foundCount;
	
	public Game1() {
		// fill map and opened with false
		for (boolean[] row :    map) Arrays.fill(row, false);
		for (boolean[] row : opened) Arrays.fill(row, false);
		
		initTarget();
		
		foundCount = 0;
	}
	
	// map에 무작위로 5개의 target을 표시하고 그 위치를 기록함. targetFounded는 false로 초기화
	public void initTarget() {
		Random rd = new Random();
		int r, c;
		
		for (int target = 0; target < TARGET_COUNT; target++) {
			r = rd.nextInt(MAP_SIZE);
			c = rd.nextInt(MAP_SIZE);
			while (map[r][c] == true) { // 이미 target 지정된 위치면 다시 탐색
				r = rd.nextInt(MAP_SIZE);
				c = rd.nextInt(MAP_SIZE);
			}
			
			map[r][c] = true;
			targetPositions[target][0] = r;
			targetPositions[target][1] = c;
			targetFounded[target] = false;
		}
	}
	
	
	// 확인할 위치로부터 찾지 못한 target 중 가장 짧은 거리 반환
	public int getDistanceOfNearestTarget(int row, int col) {
		int minDis = MAP_SIZE * 2; // 아무리 멀어도 거리는 19 이하
		int r, c;
		
		for (int target = 0; target < TARGET_COUNT; target++) {
			if (targetFounded[target]) continue;
			
			r = targetPositions[target][0];
			c = targetPositions[target][1];
			minDis = Math.min(minDis, Math.abs(row-r) + Math.abs(col-c));
		}
		
		return minDis;
	}
	
	
	
	// 선택한 위치에 target이 있는지 확인
	// 이미 열림 -> -1
	// 나머지 -> 가장 가까운 target과의 거리 
	public int checkPosition(int row, int col) {
		if (opened[row][col] == true) {
			return -1;
		}
		
		Game1.opened[row][col] = true;
		if (map[row][col] == false) {
			return getDistanceOfNearestTarget(row, col);
		}
		else {
			// target 찾음. founded에 기록하고 count 증가
			for (int target = 0; target < TARGET_COUNT; target++) {
				if (targetPositions[target][0] == row && targetPositions[target][1] == col) {
					targetFounded[target] = true;
					break;
				}
			}
			foundCount++;
			return 0;
		}
	}
	
	
	public boolean isFinished() {
		return foundCount == TARGET_COUNT;
	}
}



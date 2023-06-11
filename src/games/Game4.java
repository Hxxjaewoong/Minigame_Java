package games;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Game4 {

	//왼쪽 오른쪽 게임
	public static final int COUNT = 20;
	public boolean playing;
	
	public int score; //맞으면 +3 틀리면 -4 
	public String[] ment = {"오른쪽!", "왼쪽!", "오른쪽..이 아니라 왼쪽!", "왼쪽..이 아니라 오른쪽!", 
			"오른쪽같은 왼쪽", "왼쪽같은 오른쪽", "아래쪽!", "위쪽!", "위쪽..이 아니라 아래쪽", "아래쪽..이 아니라 위쪽",
			"not up, Left!", "not Down, Right!"};
	
	//오른쪽:0 / 왼쪽: 1 / 아래쪽: 2 / 위쪽: 3
	public int[] answer = {0, 1, 1, 0, 1, 0, 2, 3, 2, 3, 1, 0};
	//맞았을때 / 틀렸을 때 
	public int[] InfoScore = {3, -4};
	
	
	public Game4()
	{
		score = 0;
	}
	
	//맞았는지 틀렸는지 확인 -> 맞았으면 true/ 틀렸으면 false 반환
	public boolean checkCorrect(int key, int press)
	{
		//key: 문제 / press: 누른 키
		if(answer[key] != press)
		{
			return false;
		}
		return true;
	}
	
	//점수 올려주는 함수
	public void scoring(boolean result)
	{
		if(result == true)
		{	
			this.score += InfoScore[0];
		}
		else
		{
			this.score += InfoScore[1];
		}
	}
	
	//30회가 모두 끝났을 때 게임 종료
	public boolean isFinished(int countPlay)
	{
		return countPlay == COUNT;
	}
	

	
}

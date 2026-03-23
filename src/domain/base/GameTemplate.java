package domain.base;

import util.InputUtil;

/**
 * 게임이 공통적으로 갖는 흐름을 추상화한 템플릿 메소드
 * 모든 게임이 상속받아야 하는 건 아니다.
 * 또한 상속을 받았더라도 내부적으로 오버라이딩해서 새롭게 구현할 수 있다.
 */
public abstract class GameTemplate implements GameApp 
{
	@Override
	public void run() 
	{
		do 
		{
			initialize();
			startGame();
			
			while(isRunning)
			{
				render();
				handleInput();
				update();
			}
			
		} while (restart());
	}
	
	// 실행 흐름
	private boolean isRunning = false;
	
	// 다시 시작 메소드
	// 필요하다면 하위 클래스에서 수정 가능
	protected boolean restart()
	{
		return InputUtil.readBool("다시 시작하시겠습니까?", "y", "n");
	}
	
	protected void startGame()
	{
		isRunning = true;
	}
	
	// 게임이 종료되었을때 하위 클래스에서 실행하는 메소드
	// update 에서 실행함
	protected void endGame()
	{
		isRunning = false;
	}
	
	protected boolean isRunning()
	{
		return isRunning;
	}
	
	/*
	 * 게임 초기화 
	 * 게임을 새로 시작할때 한번 실행되는 초기화
	 */
	protected abstract void initialize();
	
	/*
	 * 화면을 콘솔단에 출력하는 메소드
	 */
	protected abstract void render();
	
	/*
	 * 사용자의 입력을 받는 메소드
	 */
	protected abstract void handleInput();
	
	/*
	 * 사용자의 입력이나 기타 요소등을 게임에 반영하는 메소드
	 */
	protected abstract void update();
}

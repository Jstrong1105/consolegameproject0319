package util;

/**
 * 
 * @author SIST111
 * Thread 를 컨트롤 하는 유틸리티
 */
public final class ThreadUtil
{
	// 유틸리티 특성상 클래스 final 생성자 ThreadUtil
	// 메소드 static
	private ThreadUtil() {}
	
	
	// 시간을 밀리초 (0.001) 초 단위로 받아서 그 시간 만큼 프로그램을 정지 시키는 메소드
	public static void sleep(long milliSecond)
	{
		try
		{
			Thread.sleep(milliSecond);
		}
		catch (InterruptedException e)
		{
			Thread.currentThread().interrupt();
		}
	}
	
	// 시간을 초 단위로 받아서 그 시간 만큼 프로그램을 정지시키고
	// 남은 시간을 1초 단위로 출력해주는 메소드
	public static void sleepCountDown(int second)
	{
		try
		{
			while(second > 0)
			{
				System.out.printf("\r%d초 남았습니다.",second);
				Thread.sleep(1000);
				second--;
			}
		} 
		catch (InterruptedException e)
		{
			Thread.currentThread().interrupt();
		}
	}
}

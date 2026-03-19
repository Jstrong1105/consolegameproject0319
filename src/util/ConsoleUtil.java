package util;

/**
 * 
 * @author SIST111
 * 콘솔창을 컨트롤 하는 유틸리티
 */
public final class ConsoleUtil
{
	// 유틸리티 특성 상 클래스 final 생성자 private
	// 모든 메소드 static
	private ConsoleUtil() {}
	
	
	// 콘솔 창을 지우는 명령어
	public static void clear()
	{
		System.out.print("\033[H\033[2J\033[3J");
		System.out.flush();
		
	}
}

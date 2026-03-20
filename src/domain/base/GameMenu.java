package domain.base;

/**
 * 화면에 출력할 메뉴들이 구현해야하는 메소드 
 */
public interface GameMenu
{
	String getName();		// 이름
	String getExplain();	// 설명
	Runnable run();			// 선택하면 실행되는 메소드
}

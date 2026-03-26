package domain.base;

/**
 * 게임 목록과 옵션 수정 목록을 출력할때 사용하는 인터페이스
 */
public interface TitleMenu
{
	String getName();		// 이름
	String getExplain();	// 설명
	void run();				// 선택하면 실행되는 메소드
}

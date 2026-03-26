package domain.base;

/**
 * 옵션 메뉴들이 구현해야하는 인터페이스
 * util/menuUtil/optionMenu.java 에서 활용함
 */
public interface OptionMenu<T>
{
	String getName();		// 이름
	String getExplain();	// 설명
	void run(T option);		// 선택하면 실행되는 메소드
}

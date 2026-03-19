package domain.base;

/**
 * 
 * @author SIST111
 * 모든 게임이 상속받아야 하는 인터페이스
 */
public interface GameApp
{
	// 모든 게임은 해당 인터페이스를 상속받아서
	// run 이라는 메소드를 보유해야하며
	// run 메소드를 실행했을때 게임 로직이 실행되어야 한다.
	void run();
	
}

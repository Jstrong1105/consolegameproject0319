package domain.minesweeper;

import util.MenuUtil;

/**
 * 패키지에서 유일하게 외부와 연결된 클래스
 */
public class MinesweeperGetter
{
	private static MinesweeperOption option = null;

	// singleton
	// Lazy initialize
	// 아직까지는 게임 목록이 그렇게 많지 않지만
	// 만약 게임이 1억개쯤 존재한다고 하면
	// 그 중에서 나는 하나의 게임만 실행할건데
	// 나머지 99999999 개의 게임의 옵션 객체가 사용하지도 않을 건데 생성된다면
	// 메모리 낭비가 발생한다
	// 그렇지만... 이 또한 ... 현대 컴퓨팅 환경에서 유의미한 수준이라고 하긴 어렵다...
	// 진짜 게임이 1억개가 있으면 유의미할 듯?
	private static MinesweeperOption getOption()
	{
		if(option == null)
		{
			option = new MinesweeperOption();
		}
		
		return option;
	}
	
	// 게임 시작
	public static void startGame()
	{
		new MinesweeperApp(getOption()).run();
	}
	
	// 옵션 수정
	public static void setOption()
	{
		MenuUtil.optionMenu(MinesweeperOptionSetter.values(), getOption(), "지뢰찾기옵션");
	}
}

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

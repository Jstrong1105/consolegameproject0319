package domain.memorygame;

import util.MenuUtil;

/**
 * 패키지에서 외부에 연결된 유일한 클래스
 */
public class MemoryGameGetter
{
	private static MemoryGameOption option;
	
	private static MemoryGameOption getOption()
	{
		if(option == null)
		{
			option = new MemoryGameOption();
		}
		
		return option;
	}
	
	public static void startGame()
	{
		new MemoryGameApp(getOption()).run();
	}
	
	public static void setOption()
	{
		MenuUtil.optionMenu(MemoryGameOptionSetter.values(), getOption(), "메모리게임옵션");
	}
}

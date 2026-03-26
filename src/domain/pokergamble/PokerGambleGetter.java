package domain.pokergamble;

import util.MenuUtil;

/**
 * 패키지 내에서 외부와 연결된 유일한 클래스
 */
public class PokerGambleGetter
{
	private static PokerGambleOption option;
	
	private static PokerGambleOption getOption()
	{
		if(option == null)
		{
			option = new PokerGambleOption();
		}
		
		return option;
	}
	
	public static void startGame()
	{
		new PokerGambleApp(getOption()).run();
	}
	
	public static void setOption()
	{
		MenuUtil.optionMenu(PokerGambleOptionSetter.values(), getOption(), "포커겜블옵션");
	}
}

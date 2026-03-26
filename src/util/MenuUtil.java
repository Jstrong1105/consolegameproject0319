package util;

import domain.base.OptionMenu;

/**
 * 메뉴 유틸리티
 */
public final class MenuUtil
{
	private MenuUtil() {}
	
	// 각각의 게임에서 옵션을 수정할때 사용하는 메소드
	public static <E extends Enum<E> & OptionMenu<T>, T> void optionMenu(E[] value,T option, String title)
	{
		int exit = value.length + 1;
		
		while(true)
		{
			ConsoleUtil.clear();
			System.out.println("================" +title + "================");
			System.out.println("================" + "=".repeat(title.length()*2) + "================");
			
			for(E menu : value)
			{
				System.out.println((menu.ordinal()+1) + ". " + menu.getName() + " : " + menu.getExplain());
			}
			
			System.out.println(exit + ". 뒤로가기");
			
			int answer = InputUtil.readInt("번호",1,exit);
			
			if(answer == exit)
			{
				break;
			}
			else
			{
				value[answer-1].run(option);
			}
		}
	}
}

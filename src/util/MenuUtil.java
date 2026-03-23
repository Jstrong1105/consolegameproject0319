package util;

import domain.base.GameMenu;
import main.GameOptionList;

/**
 * 메뉴 유틸리티
 */
public final class MenuUtil
{
	private MenuUtil() {}
	
	public static <E extends Enum<E> & GameMenu<T>, T> void optionMenu(E[] value,T option, String title)
	{
		int exit = value.length + 1;
		
		while(true)
		{
			ConsoleUtil.clear();
			System.out.println("====" +title + "====");
			System.out.println("====" + "=".repeat(title.length()*2) + "====");
			
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
	
	public static <E extends Enum<E> & GameMenu<Integer>> void titleMenu(E[] value,Integer i, String title)
	{
		int option = value.length + 1;
		int exit = option + 1;
		
		while(true)
		{
			ConsoleUtil.clear();
			System.out.println("====" +title + "====");
			System.out.println("====" + "=".repeat(title.length()*2) + "====");
			
			for(E menu : value)
			{
				System.out.println((menu.ordinal()+1) + ". " + menu.getName() + " : " + menu.getExplain());
			}
			
			System.out.println(option + ". 옵션 수정");
			System.out.println(exit + ". 종료");
			
			int answer = InputUtil.readInt("번호",1,exit);
			
			if(answer == exit)
			{
				System.out.println("프로그램 종료");
				break;
			}
			else if(answer == option)
			{
				optionMenu(GameOptionList.values(),1,"옵션수정");
			}
			else
			{
				value[answer-1].run(i);
			}
		}
	}
}

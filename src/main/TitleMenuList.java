package main;

import util.ConsoleUtil;
import util.InputUtil;

public class TitleMenuList
{
	private TitleMenuList() {}
	
	// 게임 리스트 출력 메소드
	public static void titleMenu(GameList[] gameList,GameOptionList[] optionList, String title)
	{
		int option = gameList.length + 1;
		int exit = option + 1;
		
		while(true)
		{
			ConsoleUtil.clear();
			System.out.println("================" +title + "================");
			System.out.println("================" + "=".repeat(title.length()*2) + "================");
			
			for(GameList menu : gameList)
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
				titleOptionMenu(optionList,"옵션수정");
			}
			else
			{
				gameList[answer-1].run();
			}
		}
	}
	
	// 게임 옵션 리스트 출력 메소드
	public static void titleOptionMenu(GameOptionList[] optionList, String title)
	{
		int exit = optionList.length + 1;
		
		while(true)
		{
			ConsoleUtil.clear();
			System.out.println("================" +title + "================");
			System.out.println("================" + "=".repeat(title.length()*2) + "================");
			
			for(GameOptionList menu : optionList)
			{
				System.out.println((menu.ordinal()+1) + ". " + menu.getName());
			}
			
			System.out.println(exit + ". 뒤로가기");
			
			int answer = InputUtil.readInt("번호",1,exit);
			
			if(answer == exit)
			{
				break;
			}
			else
			{
				optionList[answer-1].run();
			}
		}
	}
}

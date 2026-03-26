package main;

/**
 * 
 * @author SIST111
 * 프로그램 진입점
 * (entry point)
 */
public class Launcher
{
	public static void main(String[] args)
	{
		TitleMenuList.titleMenu(GameList.values(),GameOptionList.values(),"게임목록");
	}
}

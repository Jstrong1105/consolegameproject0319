package main;

import util.MenuUtil;

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
		MenuUtil.titleMenu(GameList.values(), 1, "게임 목록");
	}
}

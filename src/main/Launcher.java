package main;

import domain.base.GameApp;
import domain.minesweeper.MinesweeperApp;
import domain.minesweeper.MinesweeperOption;

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
		GameApp game = new MinesweeperApp(new MinesweeperOption());
		
		game.run();
	}
}

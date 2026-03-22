package main;

import domain.base.GameApp;
import domain.baseball.Baseball;
import util.InputUtil;

/**
 * 
 * @author SIST111
 * 프로그램 진입점
 * (entry point)
 */
public class Launcher
{
	private static GameApp app;
	public static void main(String[] args)
	{
		int answer = InputUtil.readInt("번호를 선택 : ",1,5);
			
			switch(answer)
			{
				//case 1 : app = new Minesweeper(); break;
				case 2 : 
				case 3 : 
				case 4 : app = new Baseball(); break;
				case 5 : return;
			}

		app.run();	
	}
}

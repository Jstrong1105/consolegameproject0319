package main;

import domain.base.GameMenu;
import domain.memorygame.MemoryGameGetter;
import domain.minesweeper.MinesweeperGetter;

/**
 * 게임 목록
 */
public enum GameList implements GameMenu<Integer>
{
	MINESWEEPER("지뢰찾기","폭탄이 아닌 칸을 전부 여세요!",MinesweeperGetter::startGame),
	MEMORYGAME("메모리 게임","같은 카드를 맞추세요!",MemoryGameGetter::startGame)
	;

	private final String name;
	private final String exlpain;
	private final Runnable run;
	
	private GameList(String name, String exlpain, Runnable run)
	{
		this.name = name;
		this.exlpain = exlpain;
		this.run = run;
	}
	
	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public String getExplain()
	{
		return exlpain;
	}

	@Override
	public void run(Integer option)
	{
		run.run();
	}
}

package main;

import domain.base.GameMenu;
import domain.minesweeper.MinesweeperGetter;

/**
 * 게임 옵션 수정
 */
public enum GameOptionList implements GameMenu<Integer>
{
	MINESWEEPER("지뢰찾기","폭탄이 아닌 칸을 전부 여세요!",MinesweeperGetter::setOption)
	;

	private final String name;
	private final String exlpain;
	private final Runnable run;
	
	private GameOptionList(String name, String exlpain, Runnable run)
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

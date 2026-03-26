package main;

import domain.base.TitleMenu;
import domain.memorygame.MemoryGameGetter;
import domain.minesweeper.MinesweeperGetter;
import domain.pokergamble.PokerGambleGetter;

/**
 * 게임 옵션 수정
 */
public enum GameOptionList implements TitleMenu
{
	MINESWEEPER("지뢰찾기","폭탄이 아닌 칸을 전부 여세요!",MinesweeperGetter::setOption),
	MEMORYGAME("메모리게임","같은 카드를 맞추세요!",MemoryGameGetter::setOption),
	POKERGAMBLE("포커겜블","목표 코인을 달성하세요!",PokerGambleGetter::setOption)
	;

	private final String name;
	private final String explain;
	private final Runnable run;
	
	private GameOptionList(String name, String explain, Runnable run)
	{
		this.name = name;
		this.explain = explain;
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
		return explain;
	}

	@Override
	public void run()
	{
		run.run();
	}
}

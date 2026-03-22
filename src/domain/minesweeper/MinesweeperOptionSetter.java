package domain.minesweeper;

import java.util.function.Consumer;

import domain.base.GameMenu;
import util.InputUtil;

/**
 * 지뢰찾기 옵션 수정 리스트
 */
enum MinesweeperOptionSetter implements GameMenu<MinesweeperOption>
{
	SIZE("사이즈","보드판의 가로,세로 사이즈",(option)->
	{
		int size = InputUtil.readInt("변경할 사이즈를 입력",option.getMinSize(),option.getMaxSize());
		option.setSize(size);
	}),
	WEIGHT("지뢰 배율","지뢰의 수량을 조절합니다.",(option)->
	{
		int weight = InputUtil.readInt("변경할 가중치를 입력",option.getMinWeight(),option.getMaxWeight());
		option.setWeight(weight);
	}),
	PRINTER("프린터","출력 타입을 결정합니다.",(option)->
	{
		int type = InputUtil.readInt("1. 숫자 / 2. 모양",1,2);
		if(type == 1)
		{
			option.setPrinter(new CellPrinter1());
		}
		else
		{
			option.setPrinter(new CellPrinter1());
		}
	})
	;
	
	MinesweeperOptionSetter(String name, String explain, Consumer<MinesweeperOption> setter)
	{
		this.name = name;
		this.explain = explain;
		this.setter = setter;
	}

	private final String name;
	private final String explain;
	private final Consumer<MinesweeperOption> setter;
	
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
	public void run(MinesweeperOption option)
	{
		setter.accept(option);
		InputUtil.pause("옵션이 변경되었습니다.");
	}
}

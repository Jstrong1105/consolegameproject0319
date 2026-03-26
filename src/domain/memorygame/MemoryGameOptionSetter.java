package domain.memorygame;

import java.util.function.Consumer;

import domain.base.OptionMenu;
import util.InputUtil;

/**
 * 메모리 게임 옵션 수정
 */
enum MemoryGameOptionSetter implements OptionMenu<MemoryGameOption>
{
	SIZE("사이즈","다른 카드의 개수를 결정합니다",(option)->
	{
		int size = InputUtil.readInt("변경할 사이즈를 입력",option.getMinSize(),option.getMaxSize());
		option.setSize(size);
	}),
	PAIR("페어 수","같은 카드의 개수를 결정합니다",(option)->
	{
		int pair = InputUtil.readInt("변경할 페어를 입력",option.getMinPair(),option.getMaxPair());
		option.setPair(pair);
	}),
	WEIGHT("시간 가중치","보여지는 시간을 결정합니다",(option)->
	{
		int weight = InputUtil.readInt("변경할 가중치를 입력",option.getMinWeight(),option.getMaxWeight());
		option.setWeight(weight);
	});
	
	MemoryGameOptionSetter(String name, String explain, Consumer<MemoryGameOption> setter)
	{
		this.name = name;
		this.explain = explain;
		this.setter = setter;
	}

	private final String name;
	private final String explain;
	private final Consumer<MemoryGameOption> setter;
	
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
	public void run(MemoryGameOption option)
	{
		setter.accept(option);
		InputUtil.pause("옵션이 변경되었습니다.");
	}
}

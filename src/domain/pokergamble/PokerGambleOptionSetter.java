package domain.pokergamble;

import java.util.function.Consumer;

import domain.base.OptionMenu;
import domain.pokergamble.PokerGambleOption.PokerMode;
import util.InputUtil;

enum PokerGambleOptionSetter implements OptionMenu<PokerGambleOption>
{
	LEVEL("난이도","시작 코인의 비율을 결정합니다",(option)->
	{
		int level = InputUtil.readInt("변경할 난이도를 입력",option.getMinLevel(),option.getMaxLevel());
		option.setLevel(level);
	}),
	TARGET("목표","목표 코인을 결정합니다",(option)->
	{
		int target = InputUtil.readInt("변경할 목표 코인을 입력",option.getMinTarget(),option.getMaxTarget());
		option.setTarget(target);
	}),
	WEIGHT("배율","승리 시 획득하는 코인 비율을 결정합니다",(option)->
	{
		int weight = InputUtil.readInt("변경할 배율을 입력",option.getMinWeight(),option.getMaxWeight());
		option.setWeight(weight);
	}),
	MODE("모드","카드의 장 수를 결정합니다",(option)->
	{
		int mode = InputUtil.readInt("1. 5포커 / 2. 7포커",1,2);
		if(mode == 1)
		{
			option.setMode(PokerMode.FIVE);
		}
		else
		{
			option.setMode(PokerMode.SEVEN);
		}
	})
	;
	
	private PokerGambleOptionSetter(String name,String explain, Consumer<PokerGambleOption> setter)
	{
		this.name = name;
		this.explain = explain;
		this.setter = setter;
	}
	
	private final String name;
	private final String explain;
	private final Consumer<PokerGambleOption> setter;
	
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
	public void run(PokerGambleOption option)
	{
		setter.accept(option);
		InputUtil.pause("옵션이 변경되었습니다");
	}
}

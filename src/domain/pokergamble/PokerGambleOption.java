package domain.pokergamble;

/**
 * 포커 겜블 옵션 클래스
 */
class PokerGambleOption
{
	// 5 / 7 모드
	enum PokerMode
	{
		FIVE(5),
		SEVEN(7);
		
		PokerMode(int number)
		{
			this.number = number;
		}
		
		private final int number;
		
		int getNumber()
		{
			return number;
		}
	}
	
	private PokerMode mode = PokerMode.FIVE;
	
	PokerMode getMode() {return mode;}
	
	// 난이도
	private static final int MIN_LEVEL = 1;
	private static final int MAX_LEVEL = 3;
	private int level = MIN_LEVEL;
	
	int getMinLevel() {return MIN_LEVEL;}
	int getMaxLevel() {return MAX_LEVEL;}
	int getLevel() {return level;}
	
	// 목표 코인
	private static final int MIN_TARGET = 1000;
	private static final int MAX_TARGET = 10000;
	private int target = MIN_TARGET;
	
	int getMinTarget() {return MIN_TARGET;}
	int getMaxTarget() {return MAX_TARGET;}
	int getTarget() {return target;}
	
	// 획득 배율
	private static final int MIN_WEIGHT = 1;
	private static final int MAX_WEIGHT = 3;
	private int weight = MIN_WEIGHT;
	
	int getMinWeight() {return MIN_WEIGHT;}
	int getMaxWeight() {return MAX_WEIGHT;}
	int getWeight() {return weight;}
	
	// setter
	void setMode(PokerMode mode)
	{
		this.mode = mode;
	}
	
	void setLevel(int level)
	{
		if(level < MIN_LEVEL || level > MAX_LEVEL)
		{
			throw new IllegalArgumentException("허용하지 않는 난이도 입니다.");
		}
		
		this.level = level;
	}
	
	void setTarget(int target)
	{
		if(target < MIN_TARGET || target > MAX_TARGET)
		{
			throw new IllegalArgumentException("허용하지 않는 목표치 입니다.");
		}
		
		this.target = target;
	}
	
	void setWeight(int weight)
	{
		if(weight < MIN_WEIGHT || weight > MAX_WEIGHT)
		{
			throw new IllegalArgumentException("허용하지 않는 배율입니다.");
		}
		
		this.weight = weight;
	}
}

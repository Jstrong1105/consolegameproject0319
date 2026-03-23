package domain.memorygame;

/**
 * 메모리 게임 옵션 객체
 */
class MemoryGameOption
{
	// 사이즈
	private static final int MIN_SIZE = 4;
	private static final int MAX_SIZE = 8;
	private int size = MIN_SIZE;
	
	int getMinSize() {return MIN_SIZE;}
	int getMaxSize() {return MAX_SIZE;}
	int getSize() {return size;}
	
	// 페어
	private static final int MIN_PAIR = 2;
	private static final int MAX_PAIR = 4;
	private int pair = MIN_PAIR;
	
	int getMinPair() {return MIN_PAIR;}
	int getMaxPair() {return MAX_PAIR;}
	int getPair() {return pair;}
	
	// 시간 가중치
	private static final int MIN_WEIGHT = 1;
	private static final int MAX_WEIGHT = 3;
	private int weight = MIN_WEIGHT;
	
	int getMinWeight() {return MIN_WEIGHT;}
	int getMaxWeight() {return MAX_WEIGHT;}
	int getWeight() {return weight;}
	
	// setter
	void setSize(int size)
	{
		if(size < MIN_SIZE || size > MAX_SIZE)
		{
			throw new IllegalArgumentException("허용하지 않는 사이즈입니다.");
		}
		
		this.size = size;
	}
	
	void setPair(int pair)
	{
		if(pair < MIN_PAIR || pair > MAX_PAIR)
		{
			throw new IllegalArgumentException("허용하지 않는 페어입니다.");
		}
		
		this.pair = pair;
	}
	
	void setWeight(int weight)
	{
		if(weight < MIN_WEIGHT || weight > MAX_WEIGHT)
		{
			throw new IllegalArgumentException("허용하지 않는 가중치입니다.");
		}
		
		this.weight = weight;
	}
}

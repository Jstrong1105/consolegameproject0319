package domain.minesweeper;

/**
 * 지뢰찾기 옵션 객체
 */
class MinesweeperOption
{
	// 사이즈
	private static final int MIN_SIZE = 10;
	private static final int MAX_SIZE = 20;
	private int size = MIN_SIZE;
	
	int getMinSize() { return MIN_SIZE;}
	int getMaxSize() { return MAX_SIZE;}
	int getSize() { return size; }
	
	void setSize(int size)
	{
		if(size < MIN_SIZE || size > MAX_SIZE)
		{
			throw new IllegalArgumentException("허용하지 않는 사이즈입니다.");
		}
		
		this.size = size;
	}
	
	// 난이도 배율(지뢰 배율)
	private static final int MIN_WEIGHT = 1;
	private static final int MAX_WEIGHT = 3;
	private int weight = MIN_WEIGHT;
	
	int getMinWeight() { return MIN_WEIGHT; }
	int getMaxWeight() { return MAX_WEIGHT; }
	int getWeight() { return weight; }
	
	void setWeight(int weight)
	{
		if(weight < MIN_WEIGHT || weight > MAX_WEIGHT)
		{
			throw new IllegalArgumentException("허용하지 않는 가중치입니다.");
		}
		
		this.weight = weight;
	}
	
	// 프린터(모양/숫자)
	private CellPrinter printer = new CellPrinter1();
	
	CellPrinter getPrinter() { return printer; }
	
	void setPrinter(CellPrinter printer)
	{
		this.printer = printer;
	}
}

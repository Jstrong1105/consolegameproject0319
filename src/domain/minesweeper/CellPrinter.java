package domain.minesweeper;

/**
 * 지뢰찾기에서 셀을 화면에 출력하는 녀석
 * 인터페이스로 만들어서 
 * 여러가지 타입으로 출력할 수 있게 만들어둠
 */
interface CellPrinter
{
	int getPlayerNumber();
	
	String endMsg(int playerNumber);
	
	void print(Cell[][] board);
	
	public static final String MINE_SHAPE = "※";
	public static final String FLAG_SHAPE = "P";
	public static final String CLOSE_SHAPE = "■";
	
	public static final String COLOR_GREEN = "\u001B[92m"; 
	public static final String COLOR_YELLOW = "\u001B[93m";
	public static final String COLOR_RED = "\u001B[91m";
	
	public static final String BG_YELLOW = "\u001B[103m";
	
	public static final String RESET = "\u001B[0m";
	
	public static final String[] OPEN_SHAPE = {"□","①","②","③","④","⑤","⑥","⑦","⑧"};
}

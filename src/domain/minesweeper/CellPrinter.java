package domain.minesweeper;

/**
 * 지뢰찾기에서 셀을 화면에 출력하는 녀석
 * 인터페이스로 만들어서 
 * 여러가지 타입으로 출력할 수 있게 만들어둠
 */
interface CellPrinter
{
	void print();
	
	public static String MINE_SHAPE = "※";
	public static String FLAG_SHAPE = "P";
	public static String CLOSE_SHAPE = "■";
	public static String[] OPEN_SHAPE = {"□","①","②","③","④","⑤","⑥","⑦","⑧"};
}

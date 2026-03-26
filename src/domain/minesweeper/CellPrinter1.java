package domain.minesweeper;

import util.InputUtil;

/**
 * 지뢰찾기 프린터 첫번째 타입
 * 전통적인 그리드 방식으로 
 * 화면을 출력하고 
 * row / col 을 선택 받는다.
 */
class CellPrinter1 implements CellPrinter
{
	private int size;

	@Override
	public int getPlayerNumber()
	{
		int row = InputUtil.readInt("열 번호 선택",1,size);
		int col = InputUtil.readInt("행 번호 선택",1,size);
		
		return (row-1) * size + col;
	}

	@Override
	public String endMsg(int playerNumber)
	{
		int row = (playerNumber) / size;
		int col = (playerNumber) % size;
		
		return String.format("%d열 %d행은 지뢰입니다.",row,col);
	}

	@Override
	public void print(Cell[][] board)
	{
		// 상당히 노가다 스러운 작업인데
		// 콘솔창에 출력하는 상황이라 다소 어쩔수 없기도 한데
		// 중복을 줄이려면 줄일수는 있겠지만....
		// web 환경으로 넘어가기 전에 콘솔창에서 구현해보는 단계니까
		// 이 부분을 너무 신경쓰지는 않겠습니다
		size = board.length;
		
		System.out.print("==".repeat(size));
		System.out.print("지뢰찾기");
		System.out.print("==".repeat(size));
		System.out.println();
		System.out.println("====".repeat(size) + "========");	
		
		System.out.print("    ");
		
		for(int i = 1; i <= size; i++)
		{
			System.out.printf(" %2d",i);
		}
		
		System.out.println();
		
		System.out.print("    ");
		
		System.out.print("┌");
		
		System.out.print("───".repeat(size));
		
		System.out.println("┐");
		
		for(int i = 0; i < size; i++)
		{
			System.out.printf(" %2d │",(i+1));
			
			for(int j = 0; j < size; j++)
			{
				System.out.printf("%3s",getShape(board[i][j]));
			}
			
			System.out.print("│");
			
			System.out.println();
		}
		
		System.out.print("    ");
		
		System.out.print("└");
		
		System.out.print("───".repeat(size));
		
		System.out.println("┘");
	}
	
	private String getShape(Cell cell)
	{
		String result = "";
		
		if(cell.isClosed())
		{
			result = CLOSE_SHAPE;
		}
			
		else if(cell.isFlagged())
		{
			result = FLAG_SHAPE;
		}
		
		else
		{
			if(cell.isMine())
			{
				result = MINE_SHAPE;
			}
			else
			{
				result = OPEN_SHAPE[cell.getAdjacentMines()];
				
				if(cell.getAdjacentMines() == 1)
				{
					result = COLOR_GREEN + result + RESET;
				}
				else if(cell.getAdjacentMines() == 2)
				{
					result = COLOR_YELLOW + result + RESET;
				}
				else if(cell.getAdjacentMines() >= 3)
				{
					result = COLOR_RED + result + RESET;
				}
			}
		}
		
		result = " " + result + " ";
		
		if(cell.isChoice())
		{
			result = BG_YELLOW + result + RESET;
		}
		
		return result;
	}
	
	/* 테스트 용
	 * public static void main(String[] args) { CellBoard board = new CellBoard(10,
	 * 10, new CellPrinter1());
	 * 
	 * board.openFirst(0, 0);
	 * 
	 * board.openCell(3, 3);
	 * 
	 * board.flagToggle(9, 9);
	 * 
	 * board.cellChoice(5, 5);
	 * 
	 * board.print(); }
	 */
}

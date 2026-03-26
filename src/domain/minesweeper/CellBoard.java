package domain.minesweeper;

import java.util.Random;
import java.util.Stack;

/**
 * 지뢰찾기에서 보드판을 담당하는 클래스
 * 10 * 10 사이즈 라면 100개의 셀 객체를 관리한다.
 * 패키지 프라이빗
 */
class CellBoard
{
	// 상수
	// 주변 8방향을 탐색할때 사용할 방향 상수
	private static final int[] ROW_DIRECTIONS = {-1,-1,-1,0,0,1,1,1};
	private static final int[] COL_DIRECTIONS = {-1,0,1,-1,1,-1,0,1};
	
	// 속성 구성
	private final int size;			// 가로/세로 길이
	private final int totalCell;	// 토탈 셀 개수 (size*size)
	private final int mineCount;	// 지뢰의 개수
	private final Cell[][] board;	// 보드판 객체

	private int openCellCount;		// 오픈한 셀의 개수 (클리어 여부 확인때 사용)
	
	private final CellPrinter printer;	// 콘솔창에 출력하는 녀석
	
	// 생성자
	// 패키지 프라이빗
	CellBoard(int size, int mineCount,CellPrinter printer)
	{
		this.size = size;
		totalCell = size * size;
		this.mineCount = mineCount;
		this.printer = printer;
		board = new Cell[size][size];
		
		init();
	}
	
	// 초기화
	private void init()
	{
		openCellCount = 0;
		
		for(int row = 0; row < size; row++)
		{
			for(int col = 0; col < size; col++)
			{
				// 이후 지뢰 배치 로직에 의해서
				// 지뢰의 개수가 보드판의 전체 개수의 절반 이하이라면
				// 전체를 일반셀로 배치한 다음 지뢰의 개수만큼 지뢰 셀로 바꾸고
				// 절반 이상이라면 전체를 지뢰 셀로 배치한 다음
				// 전체셀 - 지뢰개수 만큼 일반 셀로 바꾼다.
				if( (totalCell/2) > mineCount)
				{
					board[row][col] = new Cell(false);
				}
				else
				{
					board[row][col] = new Cell(true);
				}
			}
		}
		
		Random rd = new Random();
		
		// 지뢰가 절반 이상인지 여부에 따라 배치 로직을 역전시킴
		// 일반 -> 지뢰 or 지뢰 -> 일반
		if( (totalCell/2) > mineCount) 
		{
			rd.ints(0,totalCell)
			.distinct()
			.limit(mineCount)
			.forEach(num -> board[num/size][num%size].setMine(true));
		}
		else
		{
			rd.ints(0,totalCell)
			.distinct()
			.limit(totalCell-mineCount)
			.forEach(num -> board[num/size][num%size].setMine(false));
		}
	}
	
	// 화면 출력
	void print()
	{
		printer.print(board);
	}
	
	// 파라미터 값이 보드판의 범위 안에 존재하는지 확인하는 메소드
	private boolean isValid(int row, int col)
	{
		return !(row < 0 || col < 0 || row >= size || col >= size);
	}
	
	// 파라미터 값이 보드판의 범위를 벗어나서 예외를 던지는 래퍼 메소드
	private void checkOutOfArray(int row, int col)
	{
		if(!isValid(row,col))
		{
			throw new IllegalArgumentException("보드판의 범위를 벗어났습니다.");
		}
	}
	
	// 지뢰 여부 반환
	boolean isMine(int row, int col)
	{
		checkOutOfArray(row,col);
		
		return board[row][col].isMine();
	}
	
	// 깃발 여부 반환
	boolean isFlag(int row, int col)
	{
		checkOutOfArray(row,col);
		
		return board[row][col].isFlagged();
	}
	
	// 오픈 여부 반환
	boolean isOpen(int row, int col)
	{
		checkOutOfArray(row, col);
		
		return board[row][col].isOpen();
	}
	
	// 닫힘 여부 반환
	boolean isClosed(int row, int col)
	{
		checkOutOfArray(row, col);
		
		return board[row][col].isClosed();
	}
	
	// 선택하기
	void choiceCell(int row, int col)
	{
		checkOutOfArray(row, col);
		
		board[row][col].setChoice(true);
	}
	
	// 선택 취소하기
	void cancleCell(int row, int col)
	{
		checkOutOfArray(row, col);
		
		board[row][col].setChoice(false);
	}
	
	// 첫 입력 처리
	void openFirst(int row, int col)
	{
		checkOutOfArray(row, col);
		
		// 만약 처음 고른 칸이 지뢰라면
		// 지뢰가 아닌 칸을 랜덤하게 뽑아서
		// 그 칸을 지뢰로 만들고
		// 플레이어가 선택한 칸은 일반 셀로 바꾼다
		// UX 중시
		if(isMine(row, col))
		{
			Random rd = new Random();
			
			while(true)
			{
				int nRow = rd.nextInt(0,size);
				int nCol = rd.nextInt(0,size);
				
				if(!isMine(nRow, nCol))
				{
					board[nRow][nCol].setMine(true);
					board[row][col].setMine(false);
					break;
				}
			}
		}
		
		// 인접 지뢰의 수는 플레이어가 처음 한칸을 오픈 한 이후에 계산하기 때문에 (그때 확정되기 때문에)
		// 첫 오픈을 하기전에 찬스나 깃발 등은 사용하지 못하게 해야한다.
		
		// 모든 칸을 순회하면서
		// 지뢰인 셀을 발견하면
		// 해당 셀 주변 8칸을
		// 인접 지뢰 수를 1씩 증가시킨다.
		for(int r = 0; r < size; r++)
		{
			for(int c = 0; c < size; c++)
			{
				if(isMine(r, c))
				{
					addAdjacentMines(r, c);
				}
			}
		}
	}
	
	// 주변 8칸 인접 지뢰 증가 시키기
	private void addAdjacentMines(int row, int col)
	{
		for(int i = 0; i < 8; i++)
		{
			int nRow = row + ROW_DIRECTIONS[i];
			int nCol = col + COL_DIRECTIONS[i];
			
			if(isValid(nRow, nCol))
			{
				board[nRow][nCol].addAdjacentMines();
			}
		}
	}
	
	// 오픈하기
	void openCell(int row, int col)
	{
		checkOutOfArray(row, col);
		
		if(!isClosed(row, col))
		{
			return;
		}
		
		board[row][col].openCell();
		openCellCount++;
		
		if(board[row][col].getAdjacentMines() == 0)
		{
			openAdjacentCell(row,col);
		}
	}
	
	// 주변 연쇄 오픈
	private void openAdjacentCell(int row, int col)
	{
		Stack<int[]> arr = new Stack<int[]>();
		
		arr.push(new int[] {row,col});
		
		while(!arr.isEmpty())
		{
			int[] now = arr.pop();
			
			for(int i = 0; i < 8; i++)
			{
				int nRow = now[0] + ROW_DIRECTIONS[i];
				int nCol = now[1] + COL_DIRECTIONS[i];
				
				if(isValid(nRow, nCol) && isClosed(nRow, nCol))
				{
					board[nRow][nCol].openCell();
					openCellCount++;
					
					if(board[nRow][nCol].getAdjacentMines() == 0)
					{
						arr.push(new int[] {nRow,nCol});
					}
				}
			}
		}
	}
	
	// 깃발 설치
	void toggleFlag(int row, int col)
	{
		checkOutOfArray(row, col);
		
		if(isOpen(row, col))
		{
			return;
		}
		
		board[row][col].toggleFlag();
	}
	
	// 클리어 확인
	boolean isClear()
	{
		/*
		 * return Arrays.stream(board) 
		 * .flatMap(Arrays::stream) 
		 * .allMatch(cell -> cell.isMine() || cell.isOpen());
		 */
		// 위의 방법은 상당히 우아한 방법 이긴 합니다만 ...
		// 매번 모든 보드판을 순회하면서 체크하기 때문에
		// 메모리 관점에서 손해를 봅니다. 물론 현대 컴퓨팅 환경에서 유의미한 수준은 아닙니다만....
		return (totalCell-mineCount) <= openCellCount;
	}
	
	// 게임 종료시 실행되는 메소드
	void openAll()
	{
		for(int r = 0; r < size; r++)
		{
			for(int c = 0; c < size; c++)
			{
				board[r][c].openAll();
			}
		}
	}
}

















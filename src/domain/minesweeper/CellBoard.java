package domain.minesweeper;

import java.util.Arrays;
import java.util.Random;
import java.util.Stack;
import java.util.stream.IntStream;

/**
 * 지뢰찾기에서 보드판을 담당하는 클래스
 * 10 * 10 사이즈 라면 100개의 셀 객체를 관리한다.
 * 패키지 프라이빗
 */
class CellBoard
{
	// 상수
	private static final int[] ROW_DIRECTIONS = {-1,-1,-1,0,0,1,1,1};
	private static final int[] COL_DIRECTIONS = {-1,0,1,-1,1,-1,0,1};
	
	// 속성 구성
	private final int size;			// 가로/세로 길이
	private final int totalCell;	// 토탈 셀 개수 (size*size)
	private final int mineCount;	// 지뢰의 개수
	private final Cell[][] board;	// 보드판 객체

	private int openCellCount;
	
	// 생성자
	// 패키지 프라이빗
	CellBoard(int size, int mineCount)
	{
		this.size = size;
		totalCell = size * size;
		this.mineCount = mineCount;
		board = new Cell[size][size];
		openCellCount = 0;
		
		init();
	}
	
	// 초기화
	private void init()
	{
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
	
	// 파라미터 값이 보드판의 범위 안에 존재하는지 확인하는 메소드
	private boolean isValid(int row, int col)
	{
		return !(row < 0 || col < 0 || row >= size || col >= size);
	}
	
	// 파라미터 값이 보드판의 범위를 벗어나서 예외를 던지는 래퍼 메소드
	private void isOutOfArray(int row, int col)
	{
		if(!isValid(row,col))
		{
			throw new IllegalArgumentException("보드판의 범위를 벗어났습니다.");
		}
	}
	
	// 지뢰 여부 반환
	boolean isMine(int row, int col)
	{
		isOutOfArray(row,col);
		
		return board[row][col].isMine();
	}
	
	// 깃발 여부 반환
	boolean isFlag(int row, int col)
	{
		isOutOfArray(row,col);
		
		return board[row][col].isFlagged();
	}
	
	// 오픈 여부 반환
	boolean isOpen(int row, int col)
	{
		isOutOfArray(row, col);
		
		return board[row][col].isOpen();
	}
	
	// 닫힘 여부 반환
	boolean isClosed(int row, int col)
	{
		isOutOfArray(row, col);
		
		return board[row][col].isClosed();
	}
	
	// 첫 입력 처리
	void openFirst(int row, int col)
	{
		isOutOfArray(row, col);
		
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
		isOutOfArray(row, col);
		
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
	void flagToggle(int row, int col)
	{
		isOutOfArray(row, col);
		
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
		// 메모리 관점에서 손해 같습니다. 물론 현재 컴퓨팅 환경에서 유의미한 수준은 아닙니다만....
		
		return (totalCell-mineCount) <= openCellCount;
	}
}

















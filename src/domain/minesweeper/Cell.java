package domain.minesweeper;

/**
 * 지뢰찾기에서 한칸을 의미하는 객체
 * 패키지 프라이빗
 */
class Cell 
{
	/**
	 * 셀이 가지는 상태 값
	 */
	private enum CellStatus
	{
		CLOSED,OPEN,FLAGGED
	}
	
	// 속성 구성
	private boolean mine;			// 지뢰 여부	
	// 사용자가 처음 입력한 칸이 지뢰라면 해당 지뢰를 다른 곳으로 옮기는 로직 때문에
	// final 속성을 적용하지 않았음
	
	private int adjacentMines; 		// 인접한 지뢰의 개수
	private CellStatus status;		// 셀의 상태
	private boolean choice;			// 선택된 셀 여부 (사용자가 선택한 셀의 색상을 다르게 표현해서 가시성 개선)
	
	// 생성자
	// 패키지 프라이빗
	// 지뢰 여부만 받음
	Cell(boolean mine)
	{
		this.mine = mine;
		adjacentMines = 0;
		status = CellStatus.CLOSED;
		choice = false;
	}
	
	// getter
	boolean isMine() { return mine; }
	boolean isChoice() { return choice; }
	int getAdjacentMines() { return adjacentMines; }
	
	boolean isClosed() { return status == CellStatus.CLOSED; }
	boolean isOpen() { return status == CellStatus.OPEN; }
	boolean isFlagged() { return status == CellStatus.FLAGGED; }
	
	// setter
	void setMine(boolean mine) { this.mine = mine; }
	void setChoice(boolean choice) { this.choice = choice; }
	
	// 인접 지뢰 수는 배치 로직 상 한번에 3,4 등으로 넣는게 아니라
	// 각각의 지뢰들이 근처에 있는 8칸을 대상으로 인접 지뢰 수를 증가 시키는 방식으로 구현할 것이기 때문에
	// 일반적인 setter 방식이 아닌 add 방식으로 구현
	void addAdjacentMines() { adjacentMines++;}
	
	// 깃발 혹은 이미 열린 칸은 다시 열 수 없음
	void openCell()
	{
		if(status == CellStatus.CLOSED)
		{
			status = CellStatus.OPEN;
		}
	}
	
	// 닫힌 칸은 깃발로
	// 깃발 칸은 닫힌 칸으로
	// 열린 칸은 액션 없음
	void toggleFlag()
	{
		if(status == CellStatus.CLOSED)
		{
			status = CellStatus.FLAGGED;
		}
		else if(status == CellStatus.CLOSED)
		{
			status = CellStatus.FLAGGED;
		}
	}
	
	// 게임이 종료되어서 모든 칸을 개방해 주는 메소드
	// 별다른 조건 없이 모든 칸을 OPEN 으로 바꾼다.
	// 해당 메소드는 게임이 종료되기 전에는 실행되면 안된다.
	void openAll()
	{
		if(status != CellStatus.OPEN)
		{
			status = CellStatus.OPEN;
		}
	}
}

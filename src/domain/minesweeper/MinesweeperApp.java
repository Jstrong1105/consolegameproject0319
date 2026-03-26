package domain.minesweeper;

import domain.base.GameTemplate;
import util.ConsoleUtil;
import util.InputUtil;

/**
 * 지뢰찾기 구체화 클래스
 */
public class MinesweeperApp extends GameTemplate
{
	// run 메소드 재구성
	@Override
	public void run()
	{
		do
		{
			startGame();
			initialize();
			
			while(isRunning())
			{
				render();
				handleInput();
				render();
				update();
			}
			
		} while (restart());
	}
	
	// 속성 구성
	private final int size;
	private final int totalCell;
	private final int weight;
	private final CellPrinter printer;
	
	private int chanceCount;
	
	private int playerNumber;
	private int playerRow;
	private int playerCol;
	
	private boolean first;
	
	private CellBoard board;
	
	// 생성자
	public MinesweeperApp(MinesweeperOption option)
	{
		this.size = option.getSize();
		totalCell = size * size;
		this.weight = option.getWeight();
		this.printer = option.getPrinter();
	}
	
	@Override
	protected void initialize()
	{
		InputUtil.pause("지뢰찾기 게임입니다. 엔터를 누르면 시작합니다");
		playerNumber = -1;
		playerRow = -1;
		playerCol = -1;
		first = true;
		chanceCount = 5;
		
		// 지뢰의 개수는 전체 개수의 10% * 가중치
		board = new CellBoard(size, (totalCell/10)*weight, printer);
	}

	@Override
	protected void render()
	{
		ConsoleUtil.clear();
		board.print();
	}

	@Override
	protected void handleInput()
	{
		do
		{
			playerNumber = printer.getPlayerNumber();
			
			playerRow = (playerNumber-1) / size;
			playerCol = (playerNumber-1) % size;
			
			if(board.isOpen(playerRow, playerCol))
			{
				InputUtil.pause("이미 선택한 칸입니다.");
			}
			
		} while (board.isOpen(playerRow, playerCol));
		
		choice();
	}

	@Override
	protected void update()
	{
		System.out.println("1. 오픈");
		System.out.println("2. 깃발");
		System.out.println("3. 찬스");
		System.out.println("4. 취소");
		
		int answer = InputUtil.readInt("번호를 선택",1,4);
		
		switch (answer)
		{
			case 1: open(); break;
			case 2: flag(); break;
			case 3: chance();
		}
		
		cancle();
		
		if(board.isClear())
		{
			finish(true);
		}
	}
	
	// 선택 시 실행되는 메소드
	private void choice()
	{
		board.cellChoice(playerRow, playerCol);
	}
	
	// 오픈 시 실행되는 메소드
	private void open()
	{
		if(board.isFlag(playerRow, playerCol))
		{
			InputUtil.pause("깃발은 열 수 없습니다.");
			return;
		}
		
		if(first)
		{
			first = false;
			board.openFirst(playerRow, playerCol);
		}
		
		if(board.isMine(playerRow, playerCol))
		{
			finish(false);
			
			return;
		}
		
		board.openCell(playerRow, playerCol);
	}
	
	// 깃발 시 실행되는 메소드
	private void flag()
	{
		if(first)
		{
			InputUtil.pause("첫 오픈 이후 사용 가능합니다.");
		}
		
		board.flagToggle(playerRow, playerCol);
	}
	
	// 찬스 시 실행되는 메소드
	private void chance()
	{
		if(first)
		{
			InputUtil.pause("첫 오픈 이후 사용 가능합니다.");
		}
		
		if(chanceCount < 0)
		{
			InputUtil.pause("찬스를 모두 소진했습니다.");
			return;
		}
		
		chanceCount--;
		
		if(board.isMine(playerRow, playerCol))
		{
			InputUtil.pause("해당 칸은 지뢰입니다");
			
			if(!board.isFlag(playerRow, playerCol))
			{
				flag();
			}
		}
		else
		{
			InputUtil.pause("해당 칸은 지뢰가 아닙니다.");
			
			if(board.isFlag(playerRow, playerCol))
			{
				flag();
			}
			
			open();
		}
	}
	
	// 취소 시 실행되는 메소드
	private void cancle()
	{
		board.cellCancle(playerRow, playerCol);
	}
	
	private void finish(boolean win)
	{
		endGame();
		
		board.openAll();
		
		render();
		
		if(win)
		{
			System.out.println("축하합니다. 모든 지뢰를 찾았습니다.");
		}
		else
		{
			System.out.println(printer.endMsg(playerNumber));
		}
	}
}

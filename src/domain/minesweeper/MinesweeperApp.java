package domain.minesweeper;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Consumer;

import domain.base.GameTemplate;
import domain.base.OptionMenu;
import util.ConsoleUtil;
import util.InputUtil;

/**
 * 지뢰찾기 구체화 클래스
 */
class MinesweeperApp extends GameTemplate
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
	private final int size;				// 가로/세로 길이
	private final int totalCell;		// 토탈 셀 개수 (size * size)
	private final int weight;			// 지뢰 가중치 (1~3) : (10%~30%)
	private final CellPrinter printer;	// 화면에 출력하는 프린터 종류
	
	private int chanceCount;			// 찬스 횟수 
	
	private int playerNumber;			// 플레이어가 선택한 셀의 번호 
	private int playerRow;				// 플레이어가 선택한 번호의 row 
	private int playerCol;				// 플레이어가 선택한 번호의 col
	
	private boolean first;				// 첫 입력 여부
	
	private CellBoard board;			// 보드판 객체
	
	private Instant startTime;			// 시작 시간
	
	private CellAction[] actions = CellAction.values();	// 액션 처리 목록
	// ※ CellAction은 MinesweeperApp 하단에 정의되어 있음
	
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
		// 초기화
		playerNumber = -1;
		playerRow = -1;
		playerCol = -1;
		first = true;
		chanceCount = 5;
		
		// 객체 생성
		// 지뢰의 개수는 전체 개수의 10% * 가중치
		board = new CellBoard(size, (totalCell/10)*weight, printer);
		
		InputUtil.pause("지뢰찾기 게임입니다. 엔터를 누르면 시작합니다");
		
		// 시간 측정 시간
		startTime = Instant.now();
	}

	@Override
	protected void render()
	{
		// 기존 화면 지우고 보드 출력
		ConsoleUtil.clear();
		board.print();
	}

	@Override
	protected void handleInput()
	{
		// 열리지 않은 칸을 입력할때 까지 반복한다.
		do
		{
			playerNumber = printer.getPlayerNumber();
			
			playerRow = (playerNumber-1) / size;
			playerCol = (playerNumber-1) % size;
			
			// 열린 칸을 선택하면 안내 메시지 출력
			if(board.isOpen(playerRow, playerCol))
			{
				InputUtil.pause("이미 선택한 칸입니다.");
			}
			
		} while (board.isOpen(playerRow, playerCol));
		
		choice();
	}

	// 선택 시 실행되는 메소드
	private void choice()
	{
		board.choiceCell(playerRow, playerCol);
	}
	
	@Override
	protected void update()
	{
		for(CellAction action : actions)
		{
			System.out.println((action.ordinal()+1) + ". " + action.getName() + " : " + action.getExplain());
		}
		
		int answer = InputUtil.readInt("번호를 선택",1,actions.length);
		
		actions[answer-1].run(this);
		
		cancle();
		
		if(board.isClear())
		{
			finish(true);
		}
	}
	
	// 게임 종료 처리
	private void finish(boolean win)
	{
		endGame();
		
		board.openAll();
		
		render();
		
		if(win)
		{
			System.out.println("축하합니다. 모든 지뢰를 찾았습니다.");
			System.out.println("클리어 타임 : " + Duration.between(startTime, Instant.now()).getSeconds() + " 초");
		}
		else
		{
			System.out.println(printer.endMsg(playerNumber));
		}
	}
	
	// 사용자가 선택가능한 액션 목록
	private enum CellAction implements OptionMenu<MinesweeperApp>
	{
		OPEN("열기","셀을 오픈합니다.",MinesweeperApp::open),
		FLAG("깃발","깃발을 설치하거나 취소합니다",MinesweeperApp::flag),
		CHANCE("찬스","찬스를 사용해 지뢰여부를 알아냅니다",MinesweeperApp::chance),
		CANCLE("취소","셀 선택을 취소합니다",(app)->{})
		;
		
		CellAction(String name, String explain, Consumer<MinesweeperApp> action)
		{
			this.name = name;
			this.explain = explain;
			this.action = action;
		}
		
		private final String name;
		private final String explain;
		private final Consumer<MinesweeperApp> action;
		
		@Override
		public String getName()
		{
			return name;
		}

		@Override
		public String getExplain()
		{
			return explain;
		}

		@Override
		public void run(MinesweeperApp app)
		{
			action.accept(app);;
		}
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
			return;
		}
		
		board.toggleFlag(playerRow, playerCol);
	}
	
	// 찬스 시 실행되는 메소드
	private void chance()
	{
		if(first)
		{
			InputUtil.pause("첫 오픈 이후 사용 가능합니다.");
			return;
		}
		
		if(chanceCount <= 0)
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
		board.cancleCell(playerRow, playerCol);
	}
}

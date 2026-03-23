package domain.memorygame;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import domain.base.GameTemplate;
import util.ConsoleUtil;
import util.InputUtil;
import util.ThreadUtil;

/**
 * 메모리 게임 구체화 클래스
 */
class MemoryGameApp extends GameTemplate
{
	// 속성 구성
	private final int size;
	private final int pair;
	private final int weight;
	
	private List<Integer> playerChoice;
	
	private MemoryBoard board;
	
	private Instant startTime;
	
	// 생성자
	MemoryGameApp(MemoryGameOption option)
	{
		this.size = option.getSize();
		this.pair = option.getPair();
		this.weight = option.getWeight();
	}
	
	@Override
	protected void initialize()
	{
		playerChoice = new ArrayList<Integer>();
		board = new MemoryBoard(size, pair);
		
		InputUtil.pause("메모리 게임입니다. 엔터를 누르면 카드가 보여집니다.");
		
		for(int i = 0; i < size*pair; i++)
		{
			board.openCard(i);
		}
		
		render();
		
		System.out.println();
		
		ThreadUtil.sleepCountDown(10 * weight);
		
		for(int i = 0; i < size*pair; i++)
		{
			board.hiddenCard(i);
		}
		
		startTime = Instant.now();
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
		int answer;
		
		do
		{
			System.out.println();
			answer = InputUtil.readInt("카드를 선택",1,size*pair)-1;
			
			if(board.isOpen(answer))
			{
				System.out.print("이미 열린 카드입니다.");
			}
			
		} while (board.isOpen(answer));
		
		board.openCard(answer);
		playerChoice.add(answer);
	}

	@Override
	protected void update()
	{
		if(playerChoice.size() >= pair)
		{
			render();
			
			System.out.println();
			
			if(board.isPairCard(playerChoice))
			{
				// 전부 같은 카드 고름
				System.out.println("같은 카드입니다.");
				
				ThreadUtil.sleepCountDown(1);
			}
			else
			{
				// 다른 카드 섞임
				System.out.println("다른 카드입니다.");
				
				ThreadUtil.sleepCountDown(3 * weight);
				
				for(int i : playerChoice)
				{
					board.hiddenCard(i);
				}
			}
			
			playerChoice.clear();
			
			System.out.println();
			
			if(board.isClear())
			{
				finish();
			}
		}
	}
	
	// 게임 종료
	private void finish()
	{
		endGame();
		
		System.out.println("클리어!");
		System.out.println("클리어 타임 : " + Duration.between(startTime, Instant.now()).getSeconds() + "초");
	}
}

package domain.pokergamble;

import domain.base.GameTemplate;
import domain.trumpcard.CardDeck;
import util.ConsoleUtil;
import util.InputUtil;

/**
 * 포커 겜블 구체화 클래스
 */
class PokerGambleApp extends GameTemplate
{
	private final int[] levelPercent = {3,2,1}; 
	
	private final int level;
	private final int target;
	private final int weight;
	private final int mode;
	
	private int playerCoin;
	private int totalBetCoin;
	private int betCoin;
	
	private HandCard playerCard;
	private HandCard cpuCard;
	private CardDeck cardDeck;
	
	private PokerRankingResult playerResult;
	private PokerRankingResult cpuResult;
	
	PokerGambleApp(PokerGambleOption option)
	{
		level = option.getLevel();
		target = option.getTarget();
		weight = option.getWeight();
		mode = option.getMode().getNumber();
	}

	@Override
	protected void initialize()
	{
		InputUtil.pause("포커 겜블입니다.");
		
		playerCoin = target / 10 * levelPercent[level-1];
		
		roundInit();
	}

	// 라운드 초기화
	private void roundInit()
	{
		totalBetCoin = 0;
		playerCard = new HandCard();
		cpuCard = new HandCard();
		cardDeck = new CardDeck();
		
		drawCard();
		drawCard();
		
		cpuCard.openCard(0);
		cpuCard.openCard(1);
	}
	
	@Override
	protected void render()
	{
		cpuCard.print();
		System.out.println(" 컴퓨터 카드");
		playerCard.print();
		System.out.println(" 당신의 카드");
		System.out.println("목표 코인 : " + target);
		System.out.println("현재 베팅 : " + totalBetCoin);
		System.out.println("남은 코인 : " + playerCoin);
	}

	@Override
	protected void handleInput()
	{
		betCoin = InputUtil.readInt("베팅 금액",0,playerCoin);
	}

	@Override
	protected void update()
	{
		totalBetCoin += betCoin;
		playerCoin -= betCoin;
		
		if(betCoin == 0 && playerCoin > 0)
		{
			finish(Result.FOLD);
		}
		else if(playerCard.getCount() >= mode)
		{
			playerResult = playerCard.getResult();
			cpuResult = playerCard.getResult();
			
			int result = playerResult.compareTo(cpuResult);
			
			if(result > 0)
			{
				finish(Result.WIN);
			}
			else if(result < 0)
			{
				finish(Result.LOSE);
			}
			else
			{
				finish(Result.DRAW);
			}
		}
		else
		{
			drawCard();
		}
	}
	
	private enum Result
	{
		WIN,DRAW,FOLD,LOSE;
	}
	
	private void finish(Result result)
	{
		for(int i = 2; i < cpuCard.getCount(); i++)
		{
			cpuCard.openCard(i);
		}
		
		ConsoleUtil.clear();
		cpuCard.print();
		cpuResult.getName();
		
		System.out.println();
		
		playerCard.print();
		playerResult.getName();
		
		if(result == Result.WIN)
		{
			playerCoin += totalBetCoin * 2 * weight;
			InputUtil.pause("WIN!");
		}
		else if(result == Result.DRAW)
		{
			playerCoin += totalBetCoin;
			InputUtil.pause("DRAW");
		}
		else if(result == Result.FOLD)
		{
			InputUtil.pause("FOLD");
		}
		else if(result == Result.LOSE)
		{
			InputUtil.pause("LOSE");
		}
		
		if(playerCoin >= target)
		{
			endGame();
			System.out.println("목표를 달성했습니다.");
		}
		else if(playerCoin <= 0)
		{
			endGame();
			System.out.println("모든 코인을 소진했습니다.");
		}
		else
		{
			roundInit();
		}
	}
	
	// 카드 나눠주기
	private void drawCard()
	{
		playerCard.drawCard(cardDeck.drawCard());
		cpuCard.drawCard(cardDeck.drawCard());
	}
}

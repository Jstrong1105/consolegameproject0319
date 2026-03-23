package domain.pokergamble;

import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

import domain.trumpcard.Card;

/**
 * 족보 판독기
 */
class HandCardEvaluator
{
	private List<Card> handCard;
	
	// 속성 구성
	private HashMap<String, Integer> shapeCount;
	private HashMap<Integer, Integer> numberCount;
	private HashMap<Integer, Integer> groupCount;
	
	private List<Integer> numberList;
	private List<Integer> flushNumberList;
	
	private int straightNumber;
	private int flushStraightNumber;
	
	private List<Supplier<PokerRankingResult>> evaluators = List.of
			(
				this::straightFlush,
				this::fourOfAKind,
				this::fullHouse,
				this::flush,
				this::straight,
				this::threeOfAKind,
				this::twoPair,
				this::onePair,
				this::highCard
			);
			
	
	PokerRankingResult getResult(List<Card> handCard)
	{
		this.handCard = handCard;
		
		return null;
	}
	
	private void prepareData()
	{
		
	}
	
	private int straightNumber(List<Integer> list)
	{
		return 0;
	}
	
	private PokerRankingResult straightFlush()
	{
		return null;
	}
	
	private PokerRankingResult fourOfAKind()
	{
		return null;
	}
	
	private PokerRankingResult fullHouse()
	{
		return null;
	}
	
	private PokerRankingResult flush()
	{
		return null;
	}
	
	private PokerRankingResult straight()
	{
		return null;
	}
	
	private PokerRankingResult threeOfAKind()
	{
		return null;
	}
	
	private PokerRankingResult twoPair()
	{
		return null;
	}
	
	private PokerRankingResult onePair()
	{
		return null;
	}
	
	private PokerRankingResult highCard()
	{
		return null;
	}
}

package domain.pokergamble;

import java.util.ArrayList;
import java.util.List;

/**
 * 포커 족보 판독 결과
 */
class PokerRankingResult implements Comparable<PokerRankingResult>
{
	private PokerRankingList result;
	private List<Integer> kicker;
	
	private PokerRankingResult(PokerRankingList result)
	{
		this.result = result;
		kicker = new ArrayList<Integer>();
	}
	
	static PokerRankingResult royalFlush() { return new PokerRankingResult(PokerRankingList.ROYAL_FLUSH); }
	static PokerRankingResult straightFlush() { return new PokerRankingResult(PokerRankingList.STRAIGHT_FLUSH); }
	static PokerRankingResult fourOfAKind() { return new PokerRankingResult(PokerRankingList.FOUR_OF_A_KIND); }
	static PokerRankingResult fullHouse() { return new PokerRankingResult(PokerRankingList.FULL_HOUSE); }
	static PokerRankingResult flush() { return new PokerRankingResult(PokerRankingList.FLUSH); }
	static PokerRankingResult mountain() { return new PokerRankingResult(PokerRankingList.MOUNTAIN); }
	static PokerRankingResult straight() { return new PokerRankingResult(PokerRankingList.STRAIGHT); }
	static PokerRankingResult backStraight() { return new PokerRankingResult(PokerRankingList.BACK_STRAIGHT); }
	static PokerRankingResult threeOfAKind() { return new PokerRankingResult(PokerRankingList.THREE_OF_A_KIND); }
	static PokerRankingResult twoPair() { return new PokerRankingResult(PokerRankingList.TWO_PAIR); }
	static PokerRankingResult onePair() { return new PokerRankingResult(PokerRankingList.ONE_PAIR); }
	static PokerRankingResult highCard() { return new PokerRankingResult(PokerRankingList.HIGH_CARD); }
	
	String getName()
	{
		return result.getName();
	}
	
	void addKicker(int kicker)
	{
		this.kicker.add(kicker);
	}
	
	@Override
	public int compareTo(PokerRankingResult o)
	{
		int dec = this.result.getPower() - o.result.getPower();
		
		if(dec != 0)
		{
			return dec;
		}
		
		if(this.kicker.size() != o.kicker.size())
		{
			throw new IllegalStateException("동일한 족보의 키커의 길이가 다릅니다.");
		}
		
		for(int i = 0; i < this.kicker.size(); i++)
		{
			dec = this.kicker.get(i) - o.kicker.get(i);
			
			if(dec != 0)
			{
				return dec;
			}
		}
		
		return 0;
	}
}

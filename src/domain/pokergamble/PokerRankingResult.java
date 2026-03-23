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

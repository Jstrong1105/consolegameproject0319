package domain.pokergamble;

import java.util.ArrayList;
import java.util.List;

import domain.trumpcard.Card;
import domain.trumpcard.CardPrinter;

/**
 * 각각의 플레이어의 카드를 관리할 클래스
 */
class HandCard
{
	private final List<Card> handCard;
	
	HandCard()
	{
		handCard = new ArrayList<Card>();
	}
	
	// 추가하기
	void drawCard(Card card)
	{
		handCard.add(card);
	}
	
	// 개수 반환하기
	int getCount()
	{
		return handCard.size();
	}
	
	// 출력하기
	void print()
	{
		CardPrinter.print(handCard);
	}
	
	// 오픈하기
	void openCard(int index)
	{
		if(index < 0 || index >= handCard.size())
		{
			throw new IllegalArgumentException("잘못된 인덱스입니다.");
		}
		
		handCard.get(index).openCard();
	}
	
	// 결과 반환하기
	PokerRankingResult getResult()
	{
		HandCardEvaluator eval = new HandCardEvaluator();
		
		return eval.getResult(handCard);
	}
}

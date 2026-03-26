package domain.trumpcard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import domain.trumpcard.Card.CardShape;

/**
 * 카드 52장을 들고 있는 카드덱
 */
public class CardDeck
{
	// 카드 52장을 들고 있는 카드덱
	List<Card> cardDeck;
	
	// 생성자
	public CardDeck()
	{
		init();
	}
	
	// 초기화
	public void init()
	{
		cardDeck = new ArrayList<Card>();
		
		for(CardShape shape : CardShape.values())
		{
			for(int number = Card.MIN_NUMBER; number <= Card.MAX_NUMBER; number++)
			{
				cardDeck.add(new Card(shape,number));
			}
		}
		
		Collections.shuffle(cardDeck);
	}
	
	// 남은 카드 장수 반환
	public int getCount()
	{
		return cardDeck.size();
	}
	
	// 카드 한장 뽑아주기
	public Card drawCard()
	{
		if(cardDeck.isEmpty())
		{
			throw new IllegalStateException("카드 덱이 비어있습니다.");
		}
		
		// 재정렬 되면서 발생하는 자원 소모를 절약하기 위해서
		// 뒤에서 부터 뽑아줌
		return cardDeck.remove(cardDeck.size()-1);
	}
	
	/*
	 * // 테스트를 위한 임시 메소드 public Card getCard(int shape,int number) { // 임시 메소드임 //
	 * 샤프가 1 ~ 4 사이 // 넘버가 2 ~ 14 사이가 아니라면 에러남 switch (shape) { case 1: return new
	 * Card(CardShape.SPADE,number); case 2: return new
	 * Card(CardShape.HEART,number); case 3: return new Card(CardShape.DIA,number);
	 * case 4: return new Card(CardShape.CLUB,number); }
	 * 
	 * return null; }
	 */
}

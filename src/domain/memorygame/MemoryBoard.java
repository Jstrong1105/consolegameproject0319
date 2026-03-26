package domain.memorygame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import domain.trumpcard.Card;
import domain.trumpcard.CardDeck;
import domain.trumpcard.CardPrinter;

/**
 * 메모리 게임에서 보드판을 담당하는 객체
 */
class MemoryBoard
{
	// 속성
	private List<Card> boardCard;
	
	private final int size;
	private final int pair;
	
	// 생성자
	MemoryBoard(int size, int pair)
	{
		this.size = size;
		this.pair = pair;
		boardCard = new ArrayList<Card>();
		
		CardDeck cardDeck = new CardDeck();
		
		for(int i = 0; i < size; i++)
		{
			Card card = cardDeck.drawCard();
			
			for(int j = 0; j < pair; j++)
			{
				boardCard.add(card.copy());
			}
		}
		
		Collections.shuffle(boardCard);
	}
	
	// 출력하기
	void print()
	{
		for(int i = 0; i < pair - 1; i++)
		{
			CardPrinter.print(boardCard.subList(0 + (i*size), size + (i*size)));
			System.out.println();
		}
		
		// 마지막 개행 안하려고 빼냄
		CardPrinter.print(boardCard.subList((size*pair)-size, (size*pair)));
	}
	
	// 열린 카드 인지 확인
	boolean isOpen(int index)
	{
		if(index < 0 || index >= boardCard.size())
		{
			throw new IllegalArgumentException("잘못된 인덱스 입니다.");
		}
		
		return boardCard.get(index).isOpen();
	}
	
	// 오픈하기
	void openCard(int index)
	{
		if(index < 0 || index >= boardCard.size())
		{
			throw new IllegalArgumentException("잘못된 인덱스 입니다.");
		}
		
		boardCard.get(index).openCard();
	}
	
	// 숨기기
	void hiddenCard(int index)
	{
		if(index < 0 || index >= boardCard.size())
		{
			throw new IllegalArgumentException("잘못된 인덱스 입니다.");
		}
		
		boardCard.get(index).hiddenCard();
	}
	
	// 같은 카드 인지 여부 반환
	boolean isPairCard(List<Integer> playerChoice)
	{
		for(int i = 0; i < playerChoice.size()-1; i++)
		{
			if(!boardCard.get(playerChoice.get(i)).equals(boardCard.get(playerChoice.get(i+1))))
			{
				return false;
			}
		}
		
		return true;
	}
	
	// 클리어 여부
	boolean isClear()
	{
		return boardCard.stream().filter(card -> card.isOpen()).count() >= size * pair;
	}
}

package domain.trumpcard;

import java.util.List;

/**
 * 카드 출력 프린터
 */
public final class CardPrinter
{
	private CardPrinter() {}
	
	private static final String[] numberShape = {"2","3","4","5","6","7","8","9","T","J","Q","K","A"};
	
	public static void print(List<Card> handCard)
	{
		System.out.println("┌────┐ ".repeat(handCard.size()));
		
		for(Card card : handCard)
		{
			System.out.printf("│  %s  │ ",card.getShape());
		}
		for(Card card : handCard)
		{
			System.out.printf("│  %s  │ ",numberShape[card.getNumber()-1]);
		}
		
		System.out.print("└────┘ ".repeat(handCard.size()));
	}
}

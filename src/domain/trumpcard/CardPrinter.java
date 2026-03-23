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
		System.out.println("┌─────┐ ".repeat(handCard.size()));
		
		for(Card card : handCard)
		{
			if(card.isOpen())
			{
				System.out.printf("│  %s  │ ",card.getShape());
			}
			else
			{
				System.out.printf("│  %s  │ ","?");
			}
		}
		
		System.out.println();
		
		for(Card card : handCard)
		{
			if(card.isOpen())
			{
				System.out.printf("│  %s  │ ",numberShape[card.getNumber()-2]);
			}
			else
			{
				System.out.printf("│  %s  │ ","?");
			}
		}
		
		System.out.println();
		
		System.out.print("└─────┘ ".repeat(handCard.size()));
	}
}

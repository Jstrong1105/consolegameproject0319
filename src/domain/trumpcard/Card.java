package domain.trumpcard;

/**
 * 카드 한장 객체
 * 패키지 프라이빗
 */
public class Card
{
	// 카드 상태
	private enum CardStatus
	{
		OPEN,HIDDEN
	}
	
	// 카드 모양
	enum CardShape
	{
		SPADE("♠"),
		HEART("♡"),
		DIA("◇"),
		CLUB("♣");
		
		CardShape(String shape)
		{
			this.shape = shape;
		}
		
		private final String shape;
		
		private String getShape(){return shape;}
	}
	
	// 속성
	private CardStatus status;
	private final CardShape shape;
	private final int number;
	
	static final int MIN_NUMBER = 2;
	static final int MAX_NUMBER = 14;
	
	// 생성자를 패키지 프라이빗으로 설정해서
	// 외부에서 생성하지 못하고
	// 같은 패키지에 존재하는 CardDeck 클래스를 통해서만
	// 생성하도록 만듬
	Card(CardShape shape, int number)
	{
		this.shape = shape;
		this.number = number;
		status = CardStatus.HIDDEN;
	}
	
	// getter
	public String getShape(){return shape.getShape();}
	
	public int getNumber(){return number;}
	
	public boolean isOpen(){return status == CardStatus.OPEN;}
	
	public boolean isHidden(){return status == CardStatus.HIDDEN;}
	
	// 오픈하기
	public void openCard(){status = CardStatus.OPEN;}
	
	// 숨기기
	public void hiddenCard(){status = CardStatus.HIDDEN;}
	
	// 복제하기
	// 숫자와 모양만 복사하고
	// 상태는 복사하지 않음
	public Card copy(){return new Card(shape,number);}
	
	// 오버라이딩
	// 숫자와 모양이 같다면 상태와 관계 없이
	// 동일 한 카드로 취급한다
	@Override
	public boolean equals(Object o)
	{
		if(o == null){return false;}
		if(this == o){return true;}
		if(this.getClass() != o.getClass()){return false;}
		Card card = (Card)o;
		return (this.number == card.number) && (this.shape == card.shape);
	}
	
	@Override
	public int hashCode()
	{
		return shape.ordinal() * 31 + number;
	}
}

package domain.pokergamble;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
	/* 
	 * 각각의 모양의 개수를 모아놓은 것
	 * ex : ♠(key) / 2(value) / ♥ : 1 / ◆ : 1 / ♣ : 1 
	 * 카드를 10장이상 받지 않는 다면 플러시를 만족하는 모양이 2개 이상일 수 없다
	 * 만약 하트 모양 카드가 하나도 존재하지 않는 다면
	 * ♥ : 0 이 아니고 ♥ 라는 키 자체가 존재하지 않는다. 
	 * */
	
	private HashMap<Integer, Integer> numberCount;
	/* 
	 * 각각의 숫자의 개수를 모아 놓은 것
	 * ex : 2(key) : 1(value) / 3 : 1 / 7 : 1 / 8 : 2 ....
	 * 개수의 최대 개수는 4개를 넘을 수 없고
	 * 키는 2 ~ 14 사이이며 
	 * 만약 2라는 숫자가 1장도 존재하지 않는다면
	 * 2 : 0 이 아니라 2라는 키 자체가 존재하지 않는다.
	 * */
	
	private HashMap<Integer, Integer> groupCount;
	/*
	 * 그룹의 개수를 모아 놓은것
	 * numberCount 의 value 가 키가 된다
	 * 1(key) : 1(value) / 2 : 1 / 3 : 1 / 4 : 1
	 * 키는 1 ~ 4까지 존재할 것이다
	 * 아예 존재하지 않는다면
	 * 0 이 아니라 키 자체가 존재하지 않는다.
	 */
	
	private List<Integer> numberList;
	/*
	 * 카드의 숫자를 모아서 내림차순 정렬 한것
	 * 중복 존재
	 */
	
	private List<Integer> key;
	/*
	 * numberList 에서 중복을 제거하고 내림차순 정렬 한것
	 */
	
	private List<Integer> flushNumberList;
	/*
	 * 플러시의 조건을 만족한다면 해당하는 모양을 가진
	 * 카드의 숫자를 모아서 내림차순 정렬 한것
	 * 중복 존재
	 * 플러시 조건을 만족하지 않는다면 null 상태이다.
	 */
	
	private int straightNumber;
	/*
	 * numberList 가 스트레이트 조건을 만족하는지 확인해서
	 * 만족한다면 스트레이트를 이루는 가장 큰 숫자를 담고 있는 변수
	 * 스트레이트 조건을 만족하지 않는다면 -1 이 담겨있다.
	 */
	
	private int flushStraightNumber;
	/*
	 * flushNumberList 가 스트레이트 조건을 만족하는 지 확인해서
	 * 만족한다면 스트레이트를 이루는 가장 큰 숫자를 담고 있는 변수
	 * 스트레이트 조건을 만족하지 않는다면 -1이 담겨있다.
	 */
	
	// 판별 순서 리스트
	// 해당 순서를 바르게 나열하지 않으면
	// 포카드와 원페어를 동시에 만족하는 족보가
	// 원페어를 반환하게 될 수 있다.
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
			
	// 외부에서 호출할 족보 판독 메소드
	// 5장 이상의 카드 조합을 건네야 한다.
	PokerRankingResult getResult(List<Card> handCard)
	{
		if(handCard.size() < 5)
		{
			throw new IllegalStateException("카드가 5장 미만입니다.");
		}
		
		this.handCard = handCard;
		
		PokerRankingResult result = null;
		
		prepareData();
		
		for(Supplier<PokerRankingResult> eval : evaluators)
		{
			result = eval.get();
			
			if(result != null)
			{
				return result;
			}
		}
		
		return null;
	}
	
	// 데이터 전처리
	private void prepareData()
	{
		// 초기화
		shapeCount = new HashMap<String, Integer>();
		numberCount = new HashMap<Integer, Integer>();
		groupCount = new HashMap<Integer, Integer>();
		numberList = new ArrayList<Integer>();
		flushNumberList = null;
		straightNumber = -1;
		flushStraightNumber = -1;
		
		for(Card card : handCard)
		{
			String shape = card.getShape();
			int number = card.getNumber();
			
			shapeCount.put(shape, shapeCount.getOrDefault(shape, 0) + 1);
			numberCount.put(number, numberCount.getOrDefault(number, 0) + 1);
			numberList.add(number);
		}
		
		Collections.sort(numberList,Collections.reverseOrder());
		
		key = numberList.stream().distinct().sorted(Comparator.reverseOrder()).toList();
		
		straightNumber = straightNumber(numberList);
		
		// 플러시 체크
		for(String shape : shapeCount.keySet())
		{
			if(shapeCount.get(shape) >= 5)
			{
				flushNumberList = new ArrayList<Integer>();
				
				for(Card card : handCard)
				{
					if(card.getShape().equals(shape))
					{
						flushNumberList.add(card.getNumber());
					}
				}
				
				Collections.sort(flushNumberList,Collections.reverseOrder());
				
				flushStraightNumber = straightNumber(flushNumberList);
				
				break;
			}
		}
		
		// 그룹 체크
		for(int group : numberCount.values())
		{
			groupCount.put(group, groupCount.getOrDefault(group, 0) + 1);
		}
	}
	
	// 스트레이트 여부 확인 및 스트레이트를 이루는 가장 큰 숫자 반환
	private int straightNumber(List<Integer> list)
	{
		list = list.stream()
				.distinct()
				.sorted(Comparator.reverseOrder())
				.toList();
		
		if(list.size() < 5)
		{
			return -1;
		}
		
		int count = 0;
		
		for(int i = 0; i < list.size() -1; i++)
		{
			if(list.get(i) - list.get(i+1) == 1)
			{
				count++;
			}
			else
			{
				count = 0;
			}
			if(count >= 4)
			{
				return list.get(i) + 3;
			}
			// 숫자  -> 14 13 12 11  9  8  7  6  5
			// i     ->  0  1  2  3  4  5  6  7  8
			// count ->  1  2  3  0  1  2  3  4  도달안함
		}
		
		if(list.contains(14) && list.contains(2) && list.contains(3) && list.contains(4) && list.contains(5))
		{
			return 5;
		}
		
		return -1;
	}
	
	private PokerRankingResult straightFlush()
	{
		if(flushStraightNumber != -1)
		{
			PokerRankingResult result;
			
			if(flushStraightNumber == 14)
			{
				result = PokerRankingResult.royalFlush();
			}
			else
			{
				result = PokerRankingResult.straightFlush();
			}
			
			result.addKicker(flushStraightNumber);
			
			return result;
		}
		
		return null;
	}
	
	private PokerRankingResult fourOfAKind()
	{
		// 4 개 이상의 개수를 가진 숫자가 존재하는가?
		if(groupCount.getOrDefault(4, 0) >= 1)
		{
			// 그렇다면 일단 포카드임
			PokerRankingResult result = PokerRankingResult.fourOfAKind();
			
			// 가지고 있는 숫자들을 큰 수부터 하나씩 확인하면서
			for(int i : key)
			{
				// 해당 숫자의 개수가 4개 이상인지 확인함
				if(numberCount.get(i) >= 4)
				{
					// 4개 이상인 숫자를 발견했다면
					// 해당 숫자를 키커로 추가함
					result.addKicker(i);
					
					// 4장을 이룬 숫자를 제외한
					// 나머지 숫자들 중에 가장 큰 숫자 한개를 골라서
					// 키커에 추가함
					numberList.stream()
					.filter(num -> num != i)
					.limit(1)
					.forEach(result::addKicker);
					
					return result;
				}
			}
		}
		
		return null;
	}
	
	private PokerRankingResult fullHouse()
	{
		int three = groupCount.getOrDefault(3, 0);
		int two = groupCount.getOrDefault(2, 0);
		
		// 3장 이상을 이룬 카드가 2개 이상 혹은
		// 3장 이상을 이룬 카드가 1개 2장을 이룬 카드가 1개 이상인가?
		if(three >= 2 || (three >= 1 && two >= 1))
		{
			// 그렇다면 일단 풀하우스임
			PokerRankingResult result = PokerRankingResult.fullHouse();
			
			// 가지고 있는 숫자들을 하나씩 확인하면서
			for(int i : key)
			{	// 해당 숫자가 3장 이상을 이룬 카드인지 확인함
				if(numberCount.get(i) >= 3)
				{
					// 3장 이상인 카드를 발견했다면 키커로 추가함
					result.addKicker(i);
					
					// 3장을 이룬 숫자를 제외한 
					// 나머지 숫자들 중에서 2장 이상을 이룬
					// 가장 큰 숫자 한개를 골라서
					// 키커에 추가함
					numberList.stream()
					.filter(num -> num != i && numberCount.get(num) >= 2)
					.limit(1)
					.forEach(result::addKicker);
					
					return result;
				}
			}
		}
		
		return null;
	}
	
	private PokerRankingResult flush()
	{
		if(flushNumberList != null)
		{
			PokerRankingResult result = PokerRankingResult.flush();
			
			flushNumberList.stream()
			.limit(5)
			.forEach(result::addKicker);
			
			return result;
		}
		
		return null;
	}
	
	private PokerRankingResult straight()
	{
		if(straightNumber != -1)
		{
			PokerRankingResult result;
			
			if(straightNumber == 14)
			{
				result = PokerRankingResult.mountain();
			}
			else if(straightNumber == 5)
			{
				result = PokerRankingResult.backStraight();
			}
			else
			{
				result = PokerRankingResult.straight();
			}
			
			result.addKicker(straightNumber);
			
			return result;
		}
		
		return null;
	}
	
	private PokerRankingResult threeOfAKind()
	{
		if(groupCount.getOrDefault(3, 0) >= 1)
		{
			PokerRankingResult result = PokerRankingResult.threeOfAKind();
			
			for(int i : key)
			{
				if(numberCount.get(i) >= 3)
				{
					result.addKicker(i);
					
					numberList.stream()
					.filter(num -> num != i)
					.limit(2)
					.forEach(result::addKicker);
					
					return result;
				}
			}
		}
		
		return null;
	}
	
	private PokerRankingResult twoPair()
	{
		if(groupCount.getOrDefault(2, 0) >= 2)
		{
			PokerRankingResult result = PokerRankingResult.twoPair();
			
			key.stream()
			.filter(num -> numberCount.get(num) >= 2)
			.limit(2)
			.forEach(result::addKicker);
			
			numberList.stream()
			.filter(num -> numberCount.get(num) < 2)
			.limit(1)
			.forEach(result::addKicker);
			
			return result;
		}
		
		return null;
	}
	
	private PokerRankingResult onePair()
	{
		if(groupCount.getOrDefault(2, 0) >= 1)
		{
			PokerRankingResult result = PokerRankingResult.onePair();
			
			for(int i : key)
			{
				if(numberCount.get(i) >= 2)
				{
					result.addKicker(i);
					
					numberList.stream().filter(num -> num != i).limit(3).forEach(result::addKicker);
					
					return result;
				}
			}
		}
		
		return null;
	}
	
	private PokerRankingResult highCard()
	{
		PokerRankingResult result = PokerRankingResult.highCard();
		
		numberList.stream().limit(5).forEach(result::addKicker);
		
		return result;
	}
}

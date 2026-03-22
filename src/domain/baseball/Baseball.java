package domain.baseball;

import java.util.ArrayList;
import domain.base.GameApp;
import util.InputUtil;

// GameApp 인터페이스를 상속받음
public class Baseball implements GameApp{
    
	// 전역 변수 구성 
	private boolean isRun;						// 게임 시작과 종료를 제어할 isRun
	private ArrayList<RoundRecord> record;		// 게임 기록(입력, 결과)이 저장될 record
	private GameOption option;					// 공 갯수와 난이도 설정값, 선공 여부가 담길 option
	private String correctNumber;				// 정답 숫자가 담길 correctNumber
	String userBalls;							// 유저 입력값 userBalls


    @Override
    public void run()		//GameApp의 run() 메소드를 상속받음
    {	
		// 입력값에 따라 옵션이 설정되고 그에따라 전역변수가 초기화됨
		gameOption();

        do
        {
			// 게임 시작,종료와 게임 기록 초기화
			initialize();
			
			// 옵션값에 따른 컴퓨터 객체 생성
            CpuAI cpu = new CpuAI(option);

			// 옵션에 따른 정답값 초기화
			correctNumber = Judge.madeCorrect(option.getBallCount());


			while(isRun)
			{	
				userBalls = userInput();
				play(userBalls);
			}
            
        } while (InputUtil.readBool("다시시작?", "y", "n"));
	    
    }

	// 초기화를 담당하는 initialize
	private void initialize()
	{
		isRun = true;
		record = new ArrayList<>();
	}

	// 사용자 입력값에 따라 게임 옵션 설정
	private void gameOption()
	{
		option = GameOptionManager.setOption();
	}

	// 게임 시작 후 사용자 공 체크
    private String userInput()
    {   
		while (true) 
		{
			// 입력값 스트링으로 받음
			String balls = InputUtil.readString("숫자 입력");		
		
			// 입력값 길이 검사( 0000 ~ 9999) 및 숫자 검사
			if(balls.length() == option.getBallCount() && balls.matches("[0-9]+"))
			{	
				// 올바른 입력값 시 리턴
				return balls;
			}
			System.out.println("올바른 형식으로 입력해주세요.");
		}
    }

	private void play(String userBalls)
	{
		RoundRecord rr = Judge.check(correctNumber, userBalls);
			
			// 기록 저장 및 출력
			record.add(rr);
			rr.printRecord();

			// 3. 종료 체크 (예: 4스트라이크)
			if (rr.getResult().equals(option.getBallCount() + "S0B")) 
			{ 
				System.out.println("\n4 스트라이크! 게임 종료!");
				isRun = false;
			}

	}

}

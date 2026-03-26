package domain.baseball;

import java.io.Console;
import java.util.ArrayList;
import domain.base.GameApp;
import util.ConsoleUtil;
import util.InputUtil;

// GameApp 인터페이스를 상속받음
public class Baseball implements GameApp{
    
	// 전역 변수 구성 
	private boolean isRun;						// 게임 시작과 종료를 제어할 isRun
	private ArrayList<RoundRecord> record;		// 게임 기록(입력, 결과)이 저장될 record
	private GameOption option;					// 공 갯수와 난이도 설정값, 선공 여부가 담길 option
	private String correctNumber;				// 정답 숫자가 담길 correctNumber
	String userBalls;							// 유저 입력값 userBalls
	String cpuBalls;							// 컴퓨터가 내뱉은 값 cpuBalls
	CpuAI cpu;

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
            cpu = new CpuAI(option);

			// 옵션에 따른 정답값 초기화
			correctNumber = Judge.madeCorrect(option.getBallCount());
			
			int part = 0;

			while(isRun)
			{	//선공을 정해서 매개변수로 넘기는데... 흠 여긴 좀 맘에 안드는데...
				//매개변수 이름이 맘에 안들지만... 일단 선공 누군지 정해서 넘긴다.	
				String first = option.isFirst() ? "USER" : "CPU";
				++part;

				System.out.println("\n=================" + part + "회차=========================");
				play(first);

			}
            
        } while (InputUtil.readBool("다시시작?", "y", "n"));
	    
    }

	// 초기화를 담당하는 initialize
	private void initialize()
	{
		ConsoleUtil.clear();
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

	// 플레이 메소드는 플레이만
	// 선, 후공 매개변수로 받음
	private void play(String first)
	{
		// 선공이 유저라면 유저 먼저, 아니면 CPU 먼저
    	if (first.equals("USER")) 
		{
        	userPlay();
			// 만약 게임이 안끝났으면
			if (isRun)
			{	 
				// cpu 턴
				cpuPlay();
			}	
    	} 
		
		else 
		{
        	cpuPlay();
			// 만약 게임이 안끝났으면
			if (isRun)
			{	 
				// 유저 턴
				userPlay();
			}	
    	}
	}
	
	// 사용자 로직
	private void userPlay() 
	{
		// 사용자가 던진 공
    	userBalls = userInput();

		// 유저의 레코드를 공통 레코드 record에 add함
    	RoundRecord ur = Judge.check(correctNumber, userBalls);
    	record.add(ur);

		// 판정값 출력
    	ur.printRecord("USER");

		// 종료 조건 체크
    	checkWin(ur, "USER");
	}

	// 컴퓨터 로직
	private void cpuPlay() 
	{
		// 컴퓨터는 레코드를 토대로 정답을 도출해야하기때문에 매개변수를 받음
		cpuBalls = cpu.cpuPlay(record);

		// cpu의 레코드를 공통 레코드 record에 add함
		RoundRecord cr = Judge.check(correctNumber, cpuBalls);
		record.add(cr);

		// 판정값 출력
		cr.printRecord("CPU");

		// 종료 조건 체크
		checkWin(cr, "CPU");
	}

	// 승리 조건 체크, 종료
	private void checkWin(RoundRecord rr, String player) 
	{
		// 얻어낸 사용자/ 컴퓨터 기록이 볼 갯수 + S라면(EX: 4S)
    	if (rr.getResult().equals(option.getBallCount() + "S0B")) 
		{
			// 출력 후 isRun false로 초기화(종료)
        	System.out.println(player + "승리~!!");
        	isRun = false;
    	}
	}


}

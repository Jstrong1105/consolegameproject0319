package domain.baseball;

import java.util.ArrayList;

import domain.base.GameApp;
import util.InputUtil;

// GameApp 인터페이스를 상속받음
public class Baseball implements GameApp{
    
	private boolean isRun;
	private ArrayList<RoundRecord> record;
	private GameOption option;


	String userBalls;

    @Override
    public void run()
    {	
		initialize();
		gameOption();
		
        do
        {
            CpuAI cpu = new CpuAI(option);

             while(isRun)
             {	
            	userBalls = userInput();
                play(userBalls);
				//check();
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
		String result = " ";
		RoundRecord rr = new RoundRecord(userBalls, result);

		record.add(rr);

	}



    

}

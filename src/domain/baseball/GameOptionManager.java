package domain.baseball;

import java.util.Random;
import util.InputUtil;

// 게임 옵션을 설정할 클래스
public class GameOptionManager
{
    public static GameOption setOption()
    {
        // 공 갯수와 난이도 입력
        System.out.println("● 숫자야구 게임 ●");

        int ballCount = InputUtil.readInt("공 갯수", 4, 6);
        String level;

        while(true)
        {   
            // 입력값 검사
            level = InputUtil.readString("난이도(상, 중, 하)");
                   System.out.println(level);

            if(level.equals("상") || level.equals("중") || level.equals("하"))
                break;

            System.out.println("상, 중, 하 중에서 입력하세요.");
        }

        // 랜덤 선후공 설정을 위한 rd 객체 생성
        Random rd = new Random();
        
        // 선 후공 여부를 담을 변수 선언
        boolean isFirst = rd.nextBoolean();
        System.out.println(isFirst ? "당신은 선공입니다!" : "당신은 후공입니다!");
        
        
        // 객체 생성 및 반환
        GameOption option = new GameOption(level, ballCount, isFirst);
        return option;
    }
}
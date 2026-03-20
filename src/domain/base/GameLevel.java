package domain.base;

// 게임 난이도 설정 전용 클래스
public class GameLevel {
    // 
    int ballCount;
    String level;

    public void setBallCount(int userNumInput)
    {
        this.ballCount = userNumInput;
    }

    public void setLevel(String userStrInput)
    {
        this.level = userStrInput;
    }


}

//분류해야하는 기준을 추상화시켜 묶는다
// 숫자부터 찾고 

// count 

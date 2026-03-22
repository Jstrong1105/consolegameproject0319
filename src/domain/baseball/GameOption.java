package domain.baseball;

// 게임 초기 옵션 DTO
public class GameOption
{
    private String level;   // 난이도 (상, 중, 하)
    private int ballCount;  // 공 개수
    private boolean isFirst; // 선공 여부

    // 생성자
    public GameOption(String level, int ballCount, boolean isFirst)
    {
        this.level = level;
        this.ballCount = ballCount;
        this.isFirst = isFirst;
    }

    // 게터
    public String getLevel()
    {
        return level;
    }

    public int getBallCount()
    {
        return ballCount;
    }

    public boolean isFirst()
    {
        return isFirst;
    }
}
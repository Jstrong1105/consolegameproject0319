package domain.baseball;

public class RoundRecord{

    private final String balls; 
    private final String result;

    // 입력값과 결과 세터
    public RoundRecord(String balls, String result)
    {
        this.balls = balls;
        this.result = result;
    }
    
    // 입력값 게터
    public String getBalls() {
        return balls;
    }

    // 결과 게터
    public String getResult() {
        return result;
    }

    // 입력한 값에 대한 결과를 출력하는 메소드
    public void printRecord(String str)
    {
        System.out.printf(str + " 입력 : %s, 결과 : %s\n", balls, result);
    }
 
}
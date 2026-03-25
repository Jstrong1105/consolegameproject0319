package domain.baseball;

import java.util.ArrayList;
import java.util.List;

// 게임 옵션에 따른 AI 객체
public class CpuAI
{
    private GameOption option;
    // 컴퓨터가 볼과 스트라이크의 우선 순위를 판단하기 위한 변수 

    // private double s_value = 2.1 ;
    // private double b_value = 1;
    // private String[] shuffleArr;

    // 정답의 경우의 수가 담길 correctList
    // private List<String> correctList = new ArrayList<String>();

    // 생성자로 레벨, 선공여부 초기화
    public CpuAI(GameOption option)
    {
        this.option = option;
        
        // 컴퓨터 정답 경우의 수를 담을 correctList에 값을 넣자.....
        // 우선 무식하게 9000개 넘게 넣긴 했지만....
        // 중복 제거를 시행하고도 처음에 더 줄일 방법이 있을까?..
        /* 
        for(int i=123; i<=9876; i++)
        {               
            String num = String.format("%04d",i);
            correctList.add(num);    
        }
        */

        // 공의 갯수 사이즈의 배열 생성...
        // 이 배열은 나중에 특정 조건에 만족할 시
        // 값을 섞기 위한 배열임...
        // shuffleArr = new String[option.getBallCount()];
    }

    // 난이도별로 읽어올 데이터 갯수를 정하고 읽어오는 readMemory 함수
    private List<RoundRecord> readMemory(ArrayList<RoundRecord> record)
    {
        int memory = 0;

        // 난이도 상이면 전체 리스트 다 읽어옴
        if(option.getLevel().equals("상"))
            memory = record.size();
        else if(option.getLevel().equals("중"))
            memory = 5;
        else
            memory = 3;

        // 기록의 n번째부터 전체까지 읽어와서 리턴
        return record.subList(record.size()-memory, record.size());

    }

  
    /*
    
        스트라이크와 볼에 밸류값을 매겨서 
        기록 중 밸류가 제일 높았던 기록을 기준으로
        밸류 값에 기준을 매겨서 조건 분기 처리를 하자..
        밸류가 몇 이상일 때는 어떤 처리를...
    
       /*
        전체 리스트가 있다
        1개의 데이터 1234 = 1B이라는 결과가 나왔다.
        5678이 정답이라고 가정 하면
        1234가 원볼이냐? 아님
        그럼 정답이 될 수 있는 경우의 수(전체 리스트) 에서 5678 제거
    */


    // 기록을 토대로 답을 도출하는 함수
    public String cpuPlay(ArrayList<RoundRecord> record, String correctNumber)
    {
        // int countS = 0;
        // int countB = 0;
        // double totalValue = 0;
        // double container = 0;

        String result = "";

        // 컴퓨터가 읽은 기억을 가져와서...
        List<RoundRecord> cpuMemory = readMemory(record);

        // 메모리에 저장되어있는 요소를 순회하는데
        // 우선 요소 하나의 결과값을 얻어오자...
        for (RoundRecord r : cpuMemory) {
            
            while (true) {
                // 랜덤하게 얻어온 결과값을 cpuBalls에 담음
                String cpuBalls = Judge.madeCorrect(option.getBallCount());
                
                // 비교용 변수 str에 메모리 하나의 숫자값을 담음(ex 1234)
                String str = r.getBalls();
                
                // 만약 컴퓨터가 랜덤하게 던진 공이 정답이라면 > 여기서 기록들 꺼내와서 컴퓨터가 던진 값으로 정답을 돌림
                if (cpuBalls.equals(correctNumber)) {
                    

                    

                }






            }
            
            
            
            


            // 1. 처음 생각했던 방안 --------------------------------------------------
            
            // 요소의 result와 balls를 담기 위한 변수
            // j_result, j_balls
            // String j_result = "";
            // String j_balls = "";
            
            // // 얻어온 결과값의 첫번째 요소와 세번째 요소 (EX : 1S3B  → 1과 3)
            // countS = Character.getNumericValue(j_result.charAt(0));
            // countB = Character.getNumericValue(j_result.charAt(2));
            
            // totalValue 중 가장 높은값을 컨테이너에 담아둠
            // 그 result와 balls도 같이 담음
            // totalValue = (countS * s_value) + (countB * b_value);
            // container = totalValue;
            // j_balls = r.getBalls();
            // j_result = r.getResult();

            // if (container> totalValue) {
            //     continue;
            // }
            // ------------------------------------------------------------------------
            







        }

        return result;
    }

    			
		
    




}
    
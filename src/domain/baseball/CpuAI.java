package domain.baseball;

import java.util.ArrayList;
import java.util.List;

// 게임 옵션에 따른 AI 객체
public class CpuAI
{
    private GameOption option;
    // 컴퓨터가 볼과 스트라이크의 우선 순위를 판단하기 위한 변수 
    private double s_value = 2.1 ;
    private double b_value = 1;
    // 정답의 경우의 수가 담길 correctList
    private List<String> correctList = new ArrayList<String>();

    // 생성자로 레벨, 선공여부 초기화
    public CpuAI(GameOption option)
    {
        this.option = option;
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
        볼의 갯수만큼 공을 돌린다
    
        2S  < 
        
        1S 2B  
    
    */ 
       /*
        전체 리스트가 있다
        1개의 데이터 1234 = 1B이라는 결과가 나왔다.
        5678이 정답이라고 가정 하면
        1234가 원볼이냐? 아님
        그럼 정답이 될 수 있는 경우의 수(전체 리스트) 에서 5678 제거

        12 = 1B이야
        34 을 입력하려고 했을때
        34는 원볼이야 ? 아님 그럼 34 제거
        23은 원볼이야 ? 맞음 그럼 이거는 안제거
        01 원볼이야 ? 맞음 그럼 안제거
        그러면 여기서 리스트
        

    */


    // 기록을 토대로 답을 도출하는 함수
    public String cpuPlay(ArrayList<RoundRecord> record)
    {
        int countS = 0;
        int countB = 0;
        int totalValue = 0;

        String result = "";

        // 컴퓨터가 읽은 기억을 가져와서...
        List<RoundRecord> cpuMemory = readMemory(record);

        // 메모리에 저장되어있는 요소를 순회하는데
        for (RoundRecord r : cpuMemory) {
            
            // 우선 요소 하나의 결과값을 얻어오자...
            String rec = r.getResult();
            
            // 얻어온 결과값의 첫번째 요소와 세번째 요소 (EX : 1S3B  → 1과 3)
            countS = Character.getNumericValue(rec.charAt(0));
            countB = Character.getNumericValue(rec.charAt(2));
            
            // 우선 
            for(int i=0; i<9999; i++)
            {                
                correctList.add(String.valueOf(i));
                
            }


        }

        return result;
    }

    			
		
    




}
    
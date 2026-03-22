package domain.baseball;

import java.util.ArrayList;
import java.util.List;

// 게임 옵션에 따른 AI 객체
public class CpuAI
{
    private GameOption option;

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


    // 기록을 토대로 답을 도출하는 함수
    public String cpuPlay(ArrayList<RoundRecord> record)
    {
        String result = "1234";
        // 컴퓨터가 읽은 기억을 가져와서...
        List<RoundRecord> cpuMemory = readMemory(record);

        // 여기서 아마 포문을 돌려야할텐데...
        for (RoundRecord r : cpuMemory) {
            
        }

        return result;
    }

}
    
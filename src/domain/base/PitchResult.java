package domain.base;

import java.util.HashMap;

public class PitchResult{

    // 키와 밸류를 가지는 HashMap 자료구조
    HashMap<Integer, String> pitchRecord = new HashMap<>();

    //공의 결과를 판단하는 pitchResult
    public PitchResult(String balls){
        ballsCheck();
        pitchRecord.put(1, balls);
    
    }

    public void ballsCheck(String balls){
        String[] checkArr = new String[balls.length()];


        
    }


    







}
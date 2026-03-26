package domain.baseball;

import java.util.Random;

public class Judge {

    // 랜덤한 정답을 생성하는 함수
    public static String madeCorrect(int count) {

        // 객체 생성
        Random rd = new Random();
        StringBuilder sb = new StringBuilder();
        
        // 매개변수 보다 작은동안 반복
        while (sb.length() < count) {

            // 랜덤한 숫자 생성 후 문자열 변수에 담음
            int num = rd.nextInt(10); 
            String str = String.valueOf(num);
            
            // 중복 숫자 방지
            if (!sb.toString().contains(str)) {
                sb.append(str);
            }
        }
        // 정답 문자열 리턴
        return sb.toString();
    }


    // 정답(correct)과 입력값(input)을 받아 판정 후 레코드 객체 반환
    public static RoundRecord check(String correct, String input) {

        int strike = 0;
        int ball = 0;

        for (int i = 0; i < correct.length(); i++) {
            if (correct.charAt(i) == input.charAt(i)) {
                strike++;
            } else if (correct.contains(String.valueOf(input.charAt(i)))) {
                ball++;
            }
        }

        // 결과를 문자열로 조립
        String result = (strike == 0 && ball == 0) ? "OUT" : strike + "S" + ball + "B";
        
        // 기록 객체 생성 후 리턴
        RoundRecord rr = new RoundRecord(input, result);
        return rr;
    }
}

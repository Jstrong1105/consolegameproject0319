package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 
 * @author SIST111
 * 콘솔 창에서 사용자에게 입력을 받는 기능을 모아둔 유틸리티
 */
public final class InputUtil
{
	// 유틸리티의 특성 상 클래스는 final 로
	// 생성자는 private 으로 설정
	// 외부에서 사용할 모든 메소드는 static 으로 설정
	private InputUtil() {}
	
	
	private static final BufferedReader BR = new BufferedReader(new InputStreamReader(System.in));
	// 성능을 중요시 여거 Scanner 가 아닌 BufferedReader 사용
	// 사실 현 상황에서 별 차이는 없지만...
	
	// 사용자에게 안내메시지를 띄우고 
	// 엔터를 누를때 까지 대기하는 메소드
	public static void pause(String prompt)
	{
		try
		{
			System.out.println(prompt);
			BR.readLine();
		} 
		catch (IOException e)
		{
			throw new RuntimeException("입력 스트림 에러 발생",e);
			// 발생할 일 없음
			// 아마도
		}
	}
	
	// 사용자에게 안내 메시지를 띄우고 문자를 입력받는 메소드
	// 공백과 : 자동 추가
	public static String readString(String prompt)
	{
		try
		{
			System.out.print(prompt + " : ");
			return BR.readLine();
		}
		catch (IOException e)
		{
			throw new RuntimeException("입력 스트림 에러 발생",e);
		}
	}
	
	// 사용자에게 안내 메시지를 띄우고 숫자를 입력받는 메소드
	// 숫자가 아닌 값을 입력하면 경고 출력 이후 무한 트라이
	public static int readInt(String prompt)
	{
		while(true)
		{
			try
			{
				return Integer.parseInt(readString(prompt));
			}
			catch (NumberFormatException e)
			{
				System.out.println("숫자만 입력하세요");
			}
		}
	}
	
	// 사용자에게 안내 메시지를 띄우고 숫자를 제한된 범위에서 입력받는 메소드
	// 숫자가 아닌 값을 입력하거나 범위를 벗어났다면 경고 출력 이후 무한 트라이
	// 안내 메시지는 자동으로 조합해준다.
	public static int readInt(String prompt, int min, int max)
	{
		while(true)
		{
			try
			{
				String msg = String.format(prompt + " (%d~%d)", min,max);
				int num = Integer.parseInt(readString(msg));
				
				if(num < min || num > max)
				{
					System.out.println(min + " ~ " + max + " 사이로 입력하세요");
				}
				else
				{
					return num;
				}
			}
			catch (NumberFormatException e)
			{
				System.out.println("숫자만 입력하세요");
			}
		}
	}
	
	// 사용자에게 안내 메시지를 띄우고 불런(true/false)를 입력받는 메소드
	// 해당하는 값이 아닌 값을 입력한다면 경고 출력 이후 무한 트라이
	// 안내 메시지는 자동으로 조합해준다.
	// 대소문자를 구분하지 않고 공백을 무시한다.
	public static boolean readBool(String prompt, String y, String n)
	{
		while(true)
		{
			String answer = readString(prompt).trim();
			
			if(answer.equalsIgnoreCase(y))
			{
				return true;
			}
			else if(answer.equalsIgnoreCase(n))
			{
				return false;
			}
			else
			{
				System.out.println(y + " 또는 " + n + " 만 입력해주세요.");
			}
		}
	}
}

package com.ace.web.pf.controller;

import java.util.Random;
import java.util.Scanner;

public class NumberChangeEngtoBurmese {
	public static String convertToEnglishDigits(String value)
	{
	     String newValue = value.replace("1", "၁").replace("2", "၂").replace("3", "၃").replace("4", "၄").replace("5", "၅")
	             .replace("6", "၆").replace("7", "၇").replace("8", "၈").replace("9", "၉").replace("0", "၀");
	         
	     return newValue;
	}
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.print("Enter Number : ");
		String num=sc.nextLine();
		
		System.out.print(convertToEnglishDigits(num));
	}

}

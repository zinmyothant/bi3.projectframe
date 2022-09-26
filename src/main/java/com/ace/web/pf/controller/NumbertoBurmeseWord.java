package com.ace.web.pf.controller;

import java.util.Random;
import java.util.Scanner;

public class NumbertoBurmeseWord {
	
	    public static final String[] units = {
	            "", "တစ်", "နှစ်", "သုံး", "လေး", "ငါး", "ခြောက်", "ခုနှစ်",
	            "ရှစ်", "ကိုး", "တစ်ဆယ်", "တစ်ဆယ့်တစ်", "တစ်ဆယ့်နှစ်", "တစ်ဆယ့်သုံး", "တစ်ဆယ့်လေး",
	            "တစ်ဆယ့်ငါး", "တစ်ဆယ့်ခြောက်", "တစ်ဆယ့်ခုနှစ်", "တစ်ဆယ့်ရှစ်", "တစ်ဆယ့်ကိုး"
	    };

	    public static final String[] tens = {
	            "",        // 0
	            "",        // 1
	            "နှစ်ဆယ်",  // 2
	            "သုံးဆယ်",  // 3
	            "လေးဆယ်",   // 4
	            "ငါးဆယ်",   // 5
	            "ခြောက်ဆယ်",   // 6
	            "ခုနှစ်ဆယ်", // 7
	            "ရှစ်ဆယ်",  // 8
	            "ကိုးဆယ်"   // 9
	    };

	    public static String convert(final int n) {
	        if (n < 0) {
	            return "minus " + convert(-n);
	        }

	        if (n < 20) {
	            return units[n];
	        }

	        if (n < 100) {
	            return tens[n / 10] + ((n % 10 != 0) ? " " : "") + units[n % 10];
	        }

	        if (n < 1000) {
	            return units[n / 100] + "ရာ"+ ((n % 100 != 0) ? " " : "") + convert(n % 100);
	        }

	        if (n < 10000) {
	            return convert(n / 1000) + " ထောင်" + ((n % 1000 != 0) ? " " : "") + convert(n % 1000);
	        }
	        if (n < 100000) { 
	            return convert(n / 10000) + " သောင်း" + ((n % 1000 != 0) ? " " : "") + convert(n % 10000);
	        }
	        if (n < 1000000) {
	            return convert(n / 100000) + " သိန်း" + ((n % 1000 != 0) ? " " : "") + convert(n % 100000);
	        }
	        if (n < 1000000000) {
	            return convert(n / 1000000) + " သန်း" + ((n % 1000000 != 0) ? " " : "") + convert(n % 1000000);
	        }

	        return convert(n / 1000000000) + " ကုဋေ"  + ((n % 1000000000 != 0) ? " " : "") + convert(n % 1000000000);
	    }

	    public static void main(final String[] args) {
	        final Random generator = new Random();
	        Scanner sc=new Scanner(System.in);
	       System.out.print("Enter Number: ");
	        int n=sc.nextInt();
	        System.out.printf("%10d = '%s'%n", n, convert(n));
//	        for (int i = 0; i < 20; i++) {
//	            n = generator.nextInt(Integer.MAX_VALUE);
//
//	            System.out.printf("%10d = '%s'%n", n, convert(n));
//	        }
//
//	        n = 1000;
//	        System.out.printf("%10d = '%s'%n", n, convert(n));
//
//	        n = 2000;
//	        System.out.printf("%10d = '%s'%n", n, convert(n));
//
//	        n = 10000;
//	        System.out.printf("%10d = '%s'%n", n, convert(n));
//
//	        n = 11000;
//	        System.out.printf("%10d = '%s'%n", n, convert(n));
//
//	        n = 999999999;
//	        System.out.printf("%10d = '%s'%n", n, convert(n));
//
//	        n = Integer.MAX_VALUE;
//	        System.out.printf("%10d = '%s'%n", n, convert(n));
	    }
	
}

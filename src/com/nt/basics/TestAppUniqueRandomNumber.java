package com.nt.basics;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class TestAppUniqueRandomNumber {
	
	public static void main(String[] args) {
		Random rad=null;
		int val=0;
		rad=new Random();
		val=rad.nextInt(100);
		System.out.println(val);
		
	}

}

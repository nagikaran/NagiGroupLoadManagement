package com.NagiGroup.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoadsAmountCalculation {
	
	public static void main(String[] args) {
		List<Double> list = new ArrayList<>(Arrays.asList(
				300.00,
				300.00,
				350.00,
				300.00,
				300.00,
				300.00,
				300.00,
				300.00,
				400.00,
				300.00,
				300.00,
				300.00,
				362.00,
				300.00,
				300.00
				));
		LoadsAmountCalculation.calculateTotal(list);
//		double target = 12245.00;
//		double finalAmount = LoadsAmountCalculation.finalAmount(target, list);
//		System.out.println("finalAmount: "+finalAmount);
	}
	 public static void  calculateTotal(List<Double> amounts) {
	        // Calculate the total sum of the amounts
	        double total = 0.0;
	        for (Double amount : amounts) {
	            total += amount;
	        }
	        System.out.println(total); 
	    }
	
	public static double finalAmount(double target,List<Double> deductValue) {
		double finalAomunt=target;
		for(Double d:deductValue) {
			finalAomunt-=d;
		}
		return finalAomunt;
	}
	

}

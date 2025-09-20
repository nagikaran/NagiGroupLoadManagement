package com.NagiGroup.utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Demo {
public static void main(String[] args) {
	LocalDate date = LocalDate.now();
    System.out.println("Try programiz.pro: "+date);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    System.out.println(date.format(formatter));
}
}

package br.com.tiagods.view;

import java.time.LocalDate;

public class Cronologico {

	public static void main(String[] args) {
		LocalDate local1 = LocalDate.now();
		LocalDate local2 = LocalDate.of(2018, 05, 10);
		
		System.out.println(local2.compareTo(local1));
	}

}

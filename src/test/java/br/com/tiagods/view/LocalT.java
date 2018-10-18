package br.com.tiagods.view;

import java.util.Calendar;

public class LocalT {

	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();

		Calendar calendar1 = calendar;
		//calendar1.set(2000,10,10,23,00,00);

		System.out.println(calendar1.getTime());

		System.out.println(calendar.compareTo(calendar1));
	}

}

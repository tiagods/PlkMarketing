package br.com.tiagods.model;

import java.util.*;
import java.text.*;

public class NumberFormatDemo {

  static public void displayNumber(Locale currentLocale) {
    Integer quantity = new Integer(123456);
    Double amount = new Double(345987.246);
    NumberFormat numberFormatter;
    String quantityOut;
    String amountOut;

    numberFormatter = NumberFormat.getNumberInstance(currentLocale);
    quantityOut = numberFormatter.format(quantity);
    amountOut = numberFormatter.format(amount);
    System.out.println(quantityOut + "   " + currentLocale.toString());
    System.out.println(amountOut + "   " + currentLocale.toString());
  }

  static public void displayCurrency(Locale currentLocale) {
    Double currencyAmount = new Double(9876543.21);
    Currency currentCurrency = Currency.getInstance(currentLocale);
    NumberFormat currencyFormatter = NumberFormat
        .getCurrencyInstance(currentLocale);
    System.out.println(currentLocale.getDisplayName() + ", "
        + currentCurrency.getDisplayName() + ": "
        + currencyFormatter.format(currencyAmount));
  }

  static public void displayPercent(Locale currentLocale) {
    Double percent = new Double(0.75);
    NumberFormat percentFormatter;
    String percentOut;

    percentFormatter = NumberFormat.getPercentInstance(currentLocale);
    percentOut = percentFormatter.format(percent);
    System.out.println(percentOut + "   " + currentLocale.toString());
  }

  static public void main(String[] args) {
//    ArrayList<Locale> locales = new ArrayList<>();
//    locales.add(0, new Locale.Builder().setLanguage("fr").setRegion("FR")
//        .build());
//    locales.add(1, new Locale.Builder().setLanguage("de").setRegion("DE")
//        .build());
//    locales.add(2, new Locale.Builder().setLanguage("en").setRegion("US")
//        .build());
//
//    for (int i = 0; i < locales.size(); i++) {
//      displayNumber(locales.get(i));
//    }
//    for (int i = 0; i < locales.size(); i++) {
//      displayCurrency(locales.get(i));
//    }
//    for (int i = 0; i < locales.size(); i++) {
//      displayPercent(locales.get(i));
//    }
	  NumberFormat number = NumberFormat.getInstance();
	  String c = number.format(new Double(123.455));
	  System.out.println(c);
  }
}

package br.com.tiagods.util;

import lombok.Getter;

import java.text.NumberFormat;
import java.util.Locale;

@Getter
public class MoedaUtil {
    static final NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
}

package br.com.tiagods.util;

import lombok.Getter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class MoedaUtil {
    static final NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public static String format(BigDecimal bigDecimal){
        BigDecimal value = bigDecimal==null?BigDecimal.ZERO:bigDecimal;
        return nf.format(value);
    }
}

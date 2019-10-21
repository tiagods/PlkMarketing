package br.com.tiagods.util;

import java.util.Arrays;

public abstract class AbreviacaoNome {
    public static boolean onList(String param){
        return Arrays.asList(new String[]{"de","da","das","a","e","do","dos"})
                .stream()
                .filter(c->c.equalsIgnoreCase(param))
                .findAny()
                .isPresent();
    }
}

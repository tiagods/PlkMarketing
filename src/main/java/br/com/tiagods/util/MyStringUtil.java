package br.com.tiagods.util;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class MyStringUtil {
    public static boolean onList(String param){
        return Arrays.asList(new String[]{"de","da","das","a","e","do","dos"})
                .stream()
                .filter(c->c.equalsIgnoreCase(param))
                .findAny()
                .isPresent();
    }

    static int tamanho = 64;

    public static String gerarSerial(String aux){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
        String data = sdf.format(new Date());
        int tamanhoDefinido = tamanho-aux.length()-data.length();//10 representa o tamanho da data
        String alfabeto = "abcdefghijklmnopqrstuvxiz";
        String maiuscula = alfabeto.toUpperCase();
        String numeros = "0123456789";
        String expressao = alfabeto+maiuscula+numeros;
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for(int i = 0 ; i <tamanhoDefinido; i++){
            int valor = random.nextInt(expressao.length());
            builder.append(expressao.substring(valor, valor+1));
        }
        return aux+builder.toString()+data;
    }
}

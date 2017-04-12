package br.com.tiagods.modeldao;


import java.util.Random;
/**
 *
 * @author Tiago
 */
public class Randomico {
    public String gerarSerial(String protocoloNumero){
        int tamanhoDefinido = 64-protocoloNumero.length();
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
        return builder.toString()+protocoloNumero;
    }
}

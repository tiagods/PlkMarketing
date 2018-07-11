package br.com.tiagods.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 * Created by Tiago on 08/08/2017.
 */
public class CriptografiaUtil {
			
	public String criptografar(String senha){
        String criptografia;
        StringBuilder builder = new StringBuilder();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
            byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));
            for (byte b : messageDigest) {
                builder.append(String.format("%02X", 0xFF & b));
            }
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        criptografia = builder.toString();
        return criptografia;
	}
	
	public String gerarSenhaAleatoria(){
		String caracteres = "abcdefghijklmnopqrstuvwxyz";
		String maiusculas = caracteres.toUpperCase();
		String numeros = "0123456789";
		
		Random random = new Random();
		List<String> lista = new ArrayList<>();
		int index = -1;
		for(int i=0;i<6; i++){
			index = random.nextInt(numeros.length());
			lista.add(numeros.substring(index,  index+1));
		}
		index = random.nextInt(maiusculas.length());
		lista.add(maiusculas.substring(index,  index+1));
		index = random.nextInt(caracteres.length());
		lista.add(caracteres.substring(index,  index+1));
		
		Collections.shuffle(lista);
		
		StringBuilder builder = new StringBuilder();
		lista.forEach(cons->{
			builder.append(cons);
		});
		return builder.toString();
	}	
}

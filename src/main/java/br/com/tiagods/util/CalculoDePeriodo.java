package br.com.tiagods.util;

public class CalculoDePeriodo {
    public enum Tipo{ DIA }
    public String getValue(Tipo tipo, long valor){
        String descricao = "Vencido há ";
        if(tipo.equals(Tipo.DIA)){
            long finalValor = 0;
            String epoca="";
            if(valor/365>0){
                finalValor = valor/365;
                epoca = finalValor==1?" ano": " anos";
            }
            else if(valor/30>0){
                finalValor = valor/30;
                epoca = finalValor==1?" mês": " meses";
            }
            else {
                finalValor = valor;
                epoca = finalValor==1?" dia": " dias";
            }
            descricao+= finalValor+epoca;
        }
        return descricao;
    }
}

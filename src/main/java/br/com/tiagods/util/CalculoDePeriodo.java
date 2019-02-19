package br.com.tiagods.util;

public class CalculoDePeriodo {
    public enum Tipo{ DIA }
    public String getValue(Tipo tipo, int valor){
        String descricao = "Vencido há ";
        if(tipo.equals(Tipo.DIA)){

            if(valor/365>0){
                descricao+= (valor / 365)+" anos ";
            }
            else if(valor/30>0){
                descricao+= (valor / 30)+" mes(es) ";
            }
            else
                descricao+= valor+" dias";

        }
        return descricao;
    }
}

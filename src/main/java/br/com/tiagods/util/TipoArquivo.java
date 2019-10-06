package br.com.tiagods.util;

public enum TipoArquivo {
    XLS("Microsoft Excel (.xls)"),
    HTML("Arquivo Web (.html)");

    private String descricao;
    TipoArquivo(String descricao){
        this.descricao=descricao;
    }
    @Override
    public String toString() {
        return descricao;
    }
}

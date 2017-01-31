package br.com.tiagods.modeldao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import br.com.tiagods.factory.ConnectionFactory;
import br.com.tiagods.model.DescricaoVersao;

public class VerificarAtualizacao {
	private String versaoDisponivel = "";
	private String detalhesVersao="";
	private String dataVersao;
	SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	
	public String receberStatus(DescricaoVersao versao){
		Connection con = new ConnectionFactory().getConnection();
		if(con==null){
			System.out.println("Sem link de comunicação");
			return "Sem Link de comunicação";
		}
		else{
			try{
			PreparedStatement ps = con.prepareStatement("select * from ATUALIZACAO");
			ResultSet rs = ps.executeQuery();
			if(rs!=null){
				if(rs.last()){
					if(rs.getString(2).equals(versao.getVersao())){
						return "Atualizado";
					}
					else{
						versaoDisponivel = rs.getString(2);
						detalhesVersao = rs.getString(3);
						dataVersao = sdf.format(rs.getDate(4));
						return "Desatualizado";
					}
				}
			}
			}catch(SQLException e){
				return "Comando invalido";
			}finally{
				if(con!=null){
					try{con.close();}catch(SQLException e){}
				}
			}
		}
		return "";
	}
	public String versaoDisponivel(){
		return versaoDisponivel;
	}
	public String detalhesVersao(){
		return detalhesVersao;
	}
	public String dataVersao(){
		return dataVersao;
	}
}

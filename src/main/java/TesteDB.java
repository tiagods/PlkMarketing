import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class TesteDB {
	public static void main(String[] args) throws ClassNotFoundException{
		Class.forName("org.postgresql.Driver");
		//out.println("Driver carregado com sucesso");    
		Connection con=null;
		try{
			con = DriverManager.getConnection("jdbc:postgresql://localhost/cursojsp","postgres","postgres");
			//out.println("Conexão realizada com sucesso");
			
			String sql = "insert into teste (data) values ('"+new Date()+"')";
			
			PreparedStatement ps = con.prepareStatement(sql);
			if(ps.executeUpdate()>1)
				System.out.println("Sucesso");
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
}

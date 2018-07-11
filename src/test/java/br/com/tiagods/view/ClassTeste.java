package br.com.tiagods.view;
import java.lang.reflect.*;

public class ClassTeste {
	private int COD;
	public String STATUS="1";
	private String COMPL_STS;
	private String ATENDIMENTO;
	private String PROCESSOS;
	private String SISTEMA;

	public static void main(String[] args) {

		ClassTeste c = new ClassTeste();
		Class cls = c.getClass();

		System.out.println("Field =");
		try {
			// string field
			Field sField = cls.getField("STATUS");
//			/System.out.println("Public field found: " + sField.toString());
			
			Field[] fields = ClassTeste.class.getFields();
			
		} catch (NoSuchFieldException e) {
			System.out.println(e.toString());
		}
	}
}

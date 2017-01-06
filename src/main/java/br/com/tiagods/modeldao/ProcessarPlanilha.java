package br.com.tiagods.modeldao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Set;
import java.util.TreeSet;

import javassist.bytecode.Descriptor.Iterator;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ProcessarPlanilha {

	public static void main(String[] args) throws BiffException{
		Workbook workbook = null;
		try{
			FileInputStream file = new FileInputStream(System.getProperty("user.home")+"/Dropbox/novos_negocios_pen_09.xls");
			workbook = Workbook.getWorkbook(file);
		}catch(IOException e){
			e.printStackTrace();
		}
		Sheet sheet = workbook.getSheet(3);
		int numberRows = sheet.getRows();
		int i = 1;
		Set<String> lista = new TreeSet<>();  
		do{
			Cell c3 = sheet.getCell(3, i);
			String string;
			try {
				string = new String(c3.getContents().getBytes("ISO-8859-1"));
				lista.add(string);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}while(i<numberRows);
		
		java.util.Iterator<String> it = lista.iterator();
		
		while(it.hasNext()){
			System.out.println(it.next());
		}
		
		workbook.close();
	}

}

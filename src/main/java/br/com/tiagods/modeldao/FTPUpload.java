/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiagods.modeldao;

import java.io.*;
import javax.swing.JOptionPane;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import br.com.tiagods.config.FTPConfig;

/**
 *
 * @author Tiago
 */
public class FTPUpload {
	String server="ftp.prolinkcontabil.com.br";
	int port=21;
	String user="prolinkcontabil";
	String pass="plk*link815";
	String dirFTP="documentos_negocio";
	String dirTemp="documentos_negocio_temp";
	
    public boolean uploadFile(File arquivo,String novoNome){
    	//FTPConfig cf = FTPConfig.getInstance();
    	FTPClient ftp = new FTPClient();
        try{
            ftp.connect(server,port);
            ftp.login(user,pass);
            ftp.enterLocalPassiveMode();
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            boolean done;
                try (InputStream stream = new FileInputStream(arquivo)) {
                    String remoteFile = novoNome;
                    System.out.println("Start uploading first file");
                    done = ftp.storeFile(dirFTP+"/"+remoteFile, stream);
                    stream.close();
                }
            if (done) {
                JOptionPane.showMessageDialog(null,"Arquivo enviado com sucesso!");
                return true;
            }
            return false;
        }catch(IOException e){
            System.out.println("Erro = "+e.getMessage());
            return false;
        }finally{
            try {
                if (ftp.isConnected()) {
                    ftp.logout();
                    ftp.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}

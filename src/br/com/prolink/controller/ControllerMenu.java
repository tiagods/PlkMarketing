/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.prolink.controller;

import static br.com.prolink.view.Menu.*;
import br.com.prolink.view.empresas.EmpresasView;
import br.com.prolink.view.inicio.InicioView;
import br.com.prolink.view.tarefas.TarefasSaveView;
import br.com.prolink.view.tarefas.TarefasView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JInternalFrame;
import javax.swing.plaf.basic.BasicInternalFrameUI;

/**
 *
 * @author User
 */
public class ControllerMenu implements ActionListener, MouseListener{
    InicioView inicio;
    EmpresasView empresas;
    TarefasSaveView tarefasSave;
    TarefasView tarefas;
    
    public void Inicia(){
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "Inicio":
                inicio = new InicioView();
                abrirCorpo(inicio);
                break;
            case "Empresas":
                empresas = new EmpresasView();
                abrirCorpo(empresas);
                break;
            case "Tarefas":
                tarefas = new TarefasView();
                abrirCorpo(tarefas);
                break;
            /*case "Pessoas":
                break;
            case "Relatorios":
                break;*/            /*case "Pessoas":
                break;
            case "Relatorios":
                break;*/
            
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    public void abrirCorpo(JInternalFrame jframe){
        jDBody.removeAll();
        ((BasicInternalFrameUI)jframe.getUI()).setNorthPane(null);
        jDBody.add(jframe);
        jframe.setVisible(true);
    }

}

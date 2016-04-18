/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.prolink.controller;

import static br.com.prolink.view.Menu.*;
import br.com.prolink.view.empresas.CorpoEmpresas;
import br.com.prolink.view.empresas.EmpresasCabecalho;
import br.com.prolink.view.inicio.CorpoInicio;
import br.com.prolink.view.inicio.InicioCabecalhoBranco;
import br.com.prolink.view.tarefas.CorpoTarefas;
import br.com.prolink.view.tarefas.CorpoTarefasSave;
import br.com.prolink.view.tarefas.TarefasCabecalho;
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
    CorpoInicio corpoInicio;
    CorpoEmpresas corpoEmpresas;
    CorpoTarefas corpoTarefas;
    CorpoTarefasSave corpoTarefasSave;
    
    TarefasCabecalho tarefasCabecalho;
    InicioCabecalhoBranco inicioCabecalho;
    EmpresasCabecalho empresasCabecalho;
    
    public void Inicia(){
        corpoInicio = new CorpoInicio();
        abrirCorpo(corpoInicio);
        inicioCabecalho = new InicioCabecalhoBranco();
        abrirCabecalho(inicioCabecalho);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "Inicio":
                corpoInicio = new CorpoInicio();
                abrirCorpo(corpoInicio);
                inicioCabecalho = new InicioCabecalhoBranco();
                abrirCabecalho(inicioCabecalho);
                break;
            case "Tarefas":
                corpoTarefas = new CorpoTarefas();
                abrirCorpo(corpoTarefas);
                tarefasCabecalho = new TarefasCabecalho();
                abrirCabecalho(tarefasCabecalho);
                break;
            case "Empresas":
                corpoEmpresas = new CorpoEmpresas();
                abrirCorpo(corpoEmpresas);
                empresasCabecalho = new EmpresasCabecalho();
                abrirCabecalho(empresasCabecalho);
                break;
            case "Pessoas":
                break;
            case "Relatorios":
                break;
            default:
                break;
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
    public void abrirCabecalho(JInternalFrame jframe){
        jDCabecalho.removeAll();
        ((BasicInternalFrameUI)jframe.getUI()).setNorthPane(null);
        jDCabecalho.add(jframe);
        jframe.setVisible(true);
    }

}

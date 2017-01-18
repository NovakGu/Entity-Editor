/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import model.*;

import javax.swing.*;
import java.awt.event.*;
import java.awt.GridLayout;
/**
 *
 * @author ChanghengGu
 */
public class arrowpopView extends JPanel{
    public String from = "";
    public int fromIndex = 0;
    private ERModel emodel;
    public String to = "";
    public int toIndex = 0;
    public String option = "Dashed Line";
    public int optionIndex = 0;
     private String[] comboxlist ={"Dashed Line", "Straight Line"};
    private JComboBox frombox = new JComboBox();
    private JComboBox tobox = new JComboBox();
    private JComboBox dashOption = new JComboBox(comboxlist);
    public arrowpopView(ERModel e){
        super();
        this.emodel=e;
        this.layoutView();
        this.setControllers();
    }
    private void layoutView(){
        String[] temp1 = emodel.entityArray();
                    //DefaultListModel model = new DefaultListModel();
                     for(String s : temp1){
                         frombox.addItem(s);
                         tobox.addItem(s);
                         //System.out.println(s);
                }

        this.setLayout(new GridLayout(3,2));
        this.add(new JLabel("From:"));
        this.add(this.frombox);
        this.add(new JLabel("To:"));
        this.add(this.tobox);
        this.add(new JLabel("Dash Option;"));
        this.add(this.dashOption);
        

    }
 
    private void setControllers(){
        frombox.setSelectedIndex(0);
        from = String.valueOf(frombox.getSelectedItem());
        fromIndex = frombox.getSelectedIndex();
        tobox.setSelectedIndex(0);
        to = String.valueOf(tobox.getSelectedItem());
        toIndex = tobox.getSelectedIndex();
        dashOption.setSelectedIndex(0);
        option = String.valueOf(dashOption.getSelectedItem());
        optionIndex = dashOption.getSelectedIndex();
        this.frombox.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent event){
                from = String.valueOf(frombox.getSelectedItem());
                fromIndex = frombox.getSelectedIndex();
           } 
        });
        this.tobox.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent event){
              to = String.valueOf(tobox.getSelectedItem());
              toIndex = tobox.getSelectedIndex();
           } 
        });
        this.dashOption.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent event){
                option = String.valueOf(dashOption.getSelectedItem());
                optionIndex = dashOption.getSelectedIndex();
           } 
        });
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import javax.swing.*;
import java.awt.event.*;
import java.awt.GridLayout;
import java.awt.Color;


/**
 *
 * @author ChanghengGu
 */
public class entitypopView extends JPanel {
   // private ERModel model;
    private JTextField name = new JTextField(10);
    public String nameValue="";
    public Color c = Color.green;
    public int colorIndex = 0;
    private String[] colorList={"Green","Blue","Pink","Yellow","Grey"};
    private JComboBox colorBox = new JComboBox(colorList);
    public entitypopView(){
        super();
       // this.model=e;
        this.layoutView();
        this.setControllers();
    }
    private void layoutView(){
        this.setLayout(new GridLayout(2,2));
        this.add(new JLabel("Name:"));
        this.add(this.name);
        this.add(new JLabel("Color:"));
        this.add(this.colorBox);
    }
    
    private void setControllers(){
    colorBox.setSelectedIndex(0);
    colorIndex = colorBox.getSelectedIndex();
    this.colorBox.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent event){
                
                colorIndex = colorBox.getSelectedIndex();
           } 
        });
    this.name.addActionListener(new nameController());
    this.name.addFocusListener(new nameFocusController());
    
    }
    private class nameController implements ActionListener{
        public void actionPerformed(ActionEvent event){
          String name1 =   name.getText();
          nameValue=name1;
         // model.newEntity(name1);
        }
    }
    
    private class nameFocusController implements FocusListener{
       public void focusGained(FocusEvent event){
           
       }
       public void focusLost(FocusEvent event){
       String name1 =   name.getText();
       nameValue=name1;
       }
    }
}

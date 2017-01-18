/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
/**
 *
 * @author ChanghengGu
 */
public class ListView extends JPanel{
   // private String[] TestEntityList ={""};
   // private String[] TestArrowList ={"Arrow 1", "Arrow 2", "Arrow 3"};
    private String[] comboxlist ={"Entity"};
    private String[] comboxlist2 ={"Arrow"};
    private ERModel model;
    
    private JComboBox box = new JComboBox(comboxlist);
    private JComboBox box2 = new JComboBox(comboxlist2);
    JScrollPane scrollPane1 = new JScrollPane();
    JScrollPane scrollPane2 = new JScrollPane();
    private JList list1 = new JList();
    private JList list2 = new JList();
    boolean selectingentity =false;
    boolean selectingarrow = false;

    
    
    public ListView (ERModel emodel){
        super();
        this.scrollPane1.setViewportView(list1);
        this.scrollPane2.setViewportView(list2);
        this.model = emodel;
        this.layoutView();
        this.setControllers();
        
        this.model.addView(new IView(){
            public void updateView(){
               // String selected = (String)box.getSelectedItem();
                    String[] temp1 = emodel.entityArray();
                    DefaultListModel model1 = new DefaultListModel();
                     for(String s : temp1){
                         model1.addElement(s);
                         //System.out.println(s);
                     
                    list1.setModel(model1);
                }
                    int temp = emodel.arrowList.size();
                    String[] nameArr = new String[temp];
                    DefaultListModel model2 = new DefaultListModel();
                     for(int i = 0; i< temp ; i++){
                        String root= model.entityList.get((model.arrowList.get(i).getRoot())).getName();
                        String target= model.entityList.get((model.arrowList.get(i).getTarget())).getName();
                        String arrow= " ---> ";
                        root=root+arrow;
                        root=root+target;
                        nameArr[i]=root;
                        model2.addElement(root);
                     }
                     list2.setModel(model2);
       if(model.selectedInGraph>=0){
           selectingarrow=true;
                selectingentity=true;
           selectsingleentity(model.selectedInGraph);
           model.selectedInGraph=-1;
           selectingarrow=false;
                selectingentity=false;
       }
                
            }
        });
    }
    
    private void layoutView(){
        GridBagLayout gb2 = new GridBagLayout();
        this.setLayout(gb2);
        this.add(box);
        this.add(scrollPane1);
        this.add(box2);
        this.add(scrollPane2);
        GridBagConstraints constraint = new GridBagConstraints();
        constraint.fill = GridBagConstraints.BOTH;
        constraint.gridwidth = 0;

        constraint.weightx = 1;
        constraint.weighty = 0;
        //constraint.anchor = GridBagConstraints.NORTHWEST;
        gb2.setConstraints(box, constraint);
        constraint.gridwidth = 0;
        constraint.gridheight = 2;
        constraint.weightx = 1;
        constraint.weighty = 1;
        gb2.setConstraints(scrollPane1, constraint);
        constraint.gridwidth = 0;
        constraint.gridheight = 2;
        constraint.weightx = 1;
        constraint.weighty = 0;
                gb2.setConstraints(box2, constraint);
        constraint.gridwidth = 0;
        constraint.gridheight = 2;
        constraint.weightx = 1;
        constraint.weighty = 1;
                gb2.setConstraints(scrollPane2, constraint);
        constraint.gridwidth = 0;
        constraint.gridheight = 2;
        constraint.weightx = 1;
        constraint.weighty = 0;
        
    }
    
    private void selectsingleentity(int entityNUM){
        list1.setSelectedIndex(entityNUM);
        int[] arrowConnected = model.EntityClicked(model.selectedInGraph);
           list2.setSelectedIndices(arrowConnected);
    }
    
    private void setControllers(){
        this.list1.addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                if(selectingentity){}
                else{
                model.entityHighlighted.clear();
                model.listArrowHighlighted.clear();
                int index = list1.getSelectedIndex();
                if(index==-1){}
                else{
               // model.entityList.get(index).setSelected(true);
                model.entityHighlighted.add(index);
                 for(int i = 0; i < model.entityList.size();i++){
                model.setSelected(false,i);
            }
                model.setSelected(true,index);
                int[] arrowConnected = model.EntityClicked(index);
                selectingarrow=true;
                selectingentity=true;
                list2.setSelectedIndices(arrowConnected);
                list1.setSelectedIndex(index);
                selectingarrow=false;
                selectingentity=false;
                }

                }
                
            }
        });
                this.list2.addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                if(selectingarrow){}
                else{
                model.entityHighlighted.clear();
                model.listArrowHighlighted.clear();
                int index = list2.getSelectedIndex();
                if(index==-1){}
                else{
               // model.entityList.get(index).setSelected(true);
                //model.entityHighlighted.add(index);
                for(int i = 0; i < model.entityList.size();i++){
                model.setSelected(false,i);
                }
                
                
                int[] entityConnected = model.arrowClicked(index);
                for(int number : entityConnected){
                    model.setSelected(true,number);
                }
                selectingentity = true;
                selectingarrow=true;
                list1.setSelectedIndices(entityConnected);
                list2.setSelectedIndex(index);
                selectingentity = false;
                 selectingarrow=false;
                //model.specialListUpdate();
                //model.specialListUpdate();
                //list2.setSelectedIndices(arrowindex);
                }
                }
            }
        });
        this.box.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent event){
                
           } 
        });
        
    }
}

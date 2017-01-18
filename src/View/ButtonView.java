/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;
import model.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.Icon;
import javax.swing.ImageIcon;


import java.awt.event.*;


/**
 *
 * @author ChanghengGu
 */
public class ButtonView extends JPanel {
    private ERModel model;
    Icon entityIcon = new ImageIcon(System.getProperty("user.dir")+"/resources/entity.png");
    Icon arrowIcon = new ImageIcon(System.getProperty("user.dir")+"/resources/arrow.png");
    Icon zoomin = new ImageIcon(System.getProperty("user.dir")+"/resources/zoom-in.png");
    Icon zoomout = new ImageIcon(System.getProperty("user.dir")+"/resources/zoom-out.png");
    private JButton newEntity = new JButton(entityIcon);
    private JButton newArrow = new JButton(arrowIcon);
    private JButton ZoomIn = new JButton(zoomin);
    private JButton ZoomOut = new JButton(zoomout);
    private JLabel Zoom = new JLabel("Zoom Percentage: 100%");
    GridLayout gd1 = new GridLayout(1,2);
    GridLayout gd2 = new GridLayout(1,4);
    private JPanel p = new JPanel();
    private JPanel p2 = new JPanel();
    static final int ZOOM_MIN = 30;
    static final int ZOOM_MAX = 500;
    static final int ZOOM_INIT = 100;
  //  JSlider zoomSlider = new JSlider(JSlider.HORIZONTAL, ZOOM_MIN, ZOOM_MAX,ZOOM_INIT);
    
    public ButtonView (ERModel emodel){
        super();
        this.model = emodel;
        this.layoutView();
        this.setControllers();
        
            newArrow.setEnabled(false);
            ZoomIn.setEnabled(false);
            ZoomOut.setEnabled(false);
        this.model.addView(new IView(){
            public void updateView(){
            int currentS = (int)(model.getScale()*100);
            String labelZoom ="Zoom Percentage: " + String.valueOf(currentS)+"%";
            Zoom.setText(labelZoom);
            newEntity.setEnabled(true);
            if(model.entityList.isEmpty()){
            newArrow.setEnabled(false);
            ZoomIn.setEnabled(false);
            ZoomOut.setEnabled(false);
            }
            else{
            newArrow.setEnabled(true);
            ZoomIn.setEnabled(true);
            ZoomOut.setEnabled(true);
            }
            
            
            
            newEntity.setVerticalTextPosition(SwingConstants.BOTTOM);
            newEntity.setHorizontalTextPosition(SwingConstants.CENTER);
            //newEntity.setText("New Entity");
            newEntity.setToolTipText("New Entity");
            newArrow.setToolTipText("New Arrow");
            ZoomIn.setToolTipText("Zoom In");
            ZoomOut.setToolTipText("Zoom Out");
            }
        });
    }
    private void layoutView(){
        
        this.setLayout(gd1);
        p.setLayout(gd2);
        this.add(p);
        this.add(p2);
        p2.add(Zoom);
        p.add(newEntity);
        p.add(newArrow);
        p.add(ZoomIn);
        p.add(ZoomOut);
        
           /* GridBagLayout gb1 = new GridBagLayout();
            this.setLayout(gb1);
            this.add(newEntity);
            this.add(newArrow);
            this.add(ZoomIn);
            this.add(ZoomOut);
            GridBagConstraints constraint = new GridBagConstraints();
            constraint.fill = GridBagConstraints.BOTH;
            constraint.gridwidth = 1;
            constraint.weightx = 0.25;
            constraint.weighty = 1;
            constraint.anchor = GridBagConstraints.WEST;
            gb1.setConstraints(newEntity,constraint);
            constraint.gridwidth=1;
            constraint.weightx = 0.25;
            constraint.weighty=1;
            constraint.anchor = GridBagConstraints.WEST;
            gb1.setConstraints(newArrow,constraint);
            constraint.gridwidth=1;
            constraint.weightx = 0.25;
            constraint.weighty=1;
            gb1.setConstraints(ZoomIn,constraint);
            constraint.gridwidth=1;
            constraint.weightx = 0.25;
            constraint.weighty=1;
            gb1.setConstraints(ZoomOut,constraint);
            constraint.gridwidth=0;
            constraint.weightx = 0.25;
            constraint.weighty=1;*/
            
        }
    
    private void setControllers(){


        this.newEntity.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                //model.newEntity("Test",0,0);
                entitypopView pope = new entitypopView();
                JFrame framepop = new JFrame("New Entity");
                JButton confirm = new JButton("Create");
                //confirm.setEnabled(false);
                framepop.getContentPane().setLayout(new GridLayout(2,1));
                framepop.getContentPane().add(pope);
                framepop.getContentPane().add(confirm);
                framepop.setSize(200,100);
                framepop.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		framepop.setVisible(true);
                framepop.setResizable(false);
                
                confirm.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent event){
                        if(pope.nameValue==""){
                            JFrame warn = new JFrame("Help");
                            warn.setSize(300,100);
                            JLabel label = new JLabel("please press enter after input(focus on textfield)");
                            warn.getContentPane().setLayout(new GridLayout(1,1));
                            warn.getContentPane().add(label);
                            warn.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            warn.setVisible(true);
                            warn.setResizable(false);
                        }
                        else{
                            if(pope.colorIndex==0){
                            model.newEntity(pope.nameValue,Color.green);
                            }
                            else if(pope.colorIndex==1){
                            model.newEntity(pope.nameValue,Color.blue);
                            }
                            else if(pope.colorIndex==2){
                            model.newEntity(pope.nameValue,Color.pink);
                            }
                            else if(pope.colorIndex==3){
                            model.newEntity(pope.nameValue,Color.YELLOW);
                            }
                            else if(pope.colorIndex==4){
                            model.newEntity(pope.nameValue,Color.gray);
                            }
                       
                        framepop.dispose();
                        }
                    }
                });
            }
        });
         this.newArrow.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                //model.
                arrowpopView popa = new arrowpopView(model);
                JFrame framepopa = new JFrame("New Entity");
                JButton confirm = new JButton("Create");
                //confirm.setEnabled(false);
                framepopa.getContentPane().setLayout(new GridLayout(2,1));
                framepopa.getContentPane().add(popa);
                framepopa.getContentPane().add(confirm);
                framepopa.setSize(200,400);
                framepopa.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		framepopa.setVisible(true);
                framepopa.setResizable(false);
                confirm.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent event){
                        if(popa.from==""){
                            JFrame warn = new JFrame("Help");
                            warn.setSize(300,100);
                            JLabel label = new JLabel("please select a root, same applies to default");
                            warn.getContentPane().setLayout(new GridLayout(1,1));
                            warn.getContentPane().add(label);
                            warn.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            warn.setVisible(true);
                            warn.setResizable(false);
                        }
                        else if(popa.to==""){
                            JFrame warn = new JFrame("Help");
                            warn.setSize(300,100);
                            JLabel label = new JLabel("please select a target, same applies to default");
                            warn.getContentPane().setLayout(new GridLayout(1,1));
                            warn.getContentPane().add(label);
                            warn.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            warn.setVisible(true);
                            warn.setResizable(false);
                        }
                        else if(popa.to==popa.from){
                            JFrame warn = new JFrame("Help");
                            warn.setSize(300,100);
                            JLabel label = new JLabel("cannot draw to itself");
                            warn.getContentPane().setLayout(new GridLayout(1,1));
                            warn.getContentPane().add(label);
                            warn.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            warn.setVisible(true);
                            warn.setResizable(false);
                        }
                        else{
                            if(popa.optionIndex==0){
                            model.newArrow(popa.toIndex, popa.fromIndex,true);
                            }
                            else if(popa.optionIndex==1){
                            model.newArrow(popa.toIndex, popa.fromIndex,false);
                            }
                        
                        framepopa.dispose();
                        }
                    }
                });
            }
            
            
        });
          this.ZoomIn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                double currentS = model.getScale();
                if( currentS > 5){
                }
                    else{
                model.setScale(currentS+0.1);
                for(int i =0; i<model.entityList.size();i++){
                        int tempx = model.entityList.get(i).getX();
                        int tempy = model.entityList.get(i).getY();
                        model.entityList.get(i).setX((int)Math.floor(tempx+(tempx/currentS)*0.1));
                        model.entityList.get(i).setY((int)Math.floor(tempy+(tempy/currentS)*0.1));
                    }
                    if(!model.arrowList.isEmpty()){
                    for(int i=0; i<model.arrowList.size();i++){
                        if(model.arrowList.get(i).getHasJog()){
                            int tempjx = model.arrowList.get(i).getJogx();
                            int tempjy = model.arrowList.get(i).getJogy();
                            model.arrowList.get(i).setJogx((int)Math.floor(tempjx+(tempjx/currentS)*0.1));
                            model.arrowList.get(i).setJogy((int)Math.floor(tempjy+(tempjy/currentS)*0.1));
                        }
                    }
                    }
                }
            }
        });
           this.ZoomOut.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                double currentS = model.getScale();
                if(currentS<0.3){
                }
                    else{
                model.setScale(currentS-0.1);
                for(int i =0; i<model.entityList.size();i++){
                        int tempx = model.entityList.get(i).getX();
                        int tempy = model.entityList.get(i).getY();
                        model.entityList.get(i).setX((int)Math.floor(tempx-(tempx/currentS)*0.1));
                        model.entityList.get(i).setY((int)Math.floor(tempy-(tempy/currentS)*0.1));
                    }
                    if(!model.arrowList.isEmpty()){
                    for(int i=0; i<model.arrowList.size();i++){
                        if(model.arrowList.get(i).getHasJog()){
                            int tempjx = model.arrowList.get(i).getJogx();
                            int tempjy = model.arrowList.get(i).getJogy();
                           model.arrowList.get(i).setJogx((int)Math.floor(tempjx-(tempjx/currentS)*0.1));
                            model.arrowList.get(i).setJogy((int)Math.floor(tempjy-(tempjy/currentS)*0.1));
                        }
                    }
                    }
                }
            }
        });
    }
    
}

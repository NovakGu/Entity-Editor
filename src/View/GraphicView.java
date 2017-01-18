/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import model.*;
import java.lang.Math;
import javax.swing.event.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;
/**
 *
 * @author ChanghengGu
 */
public class GraphicView extends JComponent {
    private ERModel model;
    private int selectedEntity=-1;
    private double entityWidth = 120.0;
    private double entityLength = 75.0;
    private int compWidth = 925;
    private int compLength = 615;
    private int selectedArrow=-1;
    
    public GraphicView(ERModel emodel){
        super();
        this.model = emodel;
        this.layoutView();
        this.setControllers();
        this.model.addView(new IView(){
            public void updateView(){
                repaint();

            }
        });
        
    }
    private void layoutView(){
        this.setFocusable(true);
        this.requestFocusInWindow();
    this.setPreferredSize(new Dimension(800, 600));
    this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }
    private void setControllers(){
        MouseInputAdapter mouse = new MSControl();

        KeyAdapter key = new KeyHandler();
        this.addKeyListener(key);
        this.setFocusable(true);
        this.requestFocusInWindow();
        
        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
        this.addMouseWheelListener(mouse);
    }
    
    

    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Insets insets= this.getInsets();
        if(model.entityList.size()!=0){
        double scale = model.getScale();
        double ScaledWidth=120*scale;
        double ScaledHeight=75*scale;
        int[] entityX = model.entityX();
        int[] entityY = model.entityY();
        int arrowArray = model.arrowList.size();
        int[] root = new int[arrowArray];
        int[] target = new int[arrowArray];
        for(int i = 0; i<arrowArray; i++){
            root[i]=model.arrowList.get(i).getRoot();
            target[i]=model.arrowList.get(i).getTarget();
        
        }
        int counter = 0;
        if(entityX!=null){
            for(int i : entityX){

            int x1 = entityX[counter];
            int y1 = entityY[counter];
            //if(selected){
            g.setColor(model.entityList.get(counter).getColor());
            g.fillRect(x1, y1, (int)(ScaledWidth), (int)(ScaledHeight));
            
            
            if(model.entityList.get(counter).getSelected()){
                g.setColor(Color.red);
                
            }
            else{
            g.setColor(Color.black);
            }
            g.drawRect(x1,y1, (int)(ScaledWidth), (int)(ScaledHeight));
            g.drawString(model.entityList.get(counter).getName(),x1+(int)(30*scale)/2,y1+(int)(75*scale)/2);
            counter++; 
            }
            
            int arrowCounter=0;
         if(root!=null){
             for(int i : root){
                 int rootx = model.entityList.get(root[arrowCounter]).getX();
                 int rooty = model.entityList.get(root[arrowCounter]).getY();
                 int targetx = model.entityList.get(target[arrowCounter]).getX();
                 int targety = model.entityList.get(target[arrowCounter]).getY();
                 int arootx=0,arooty=0,atargetx=0,atargety=0;
                 int jogx=0, jogy=0;
                 int cjogx = model.arrowList.get(arrowCounter).getJogx();
                 int cjogy = model.arrowList.get(arrowCounter).getJogy();
                 //1-3
                 if((rooty - targety)>entityLength*scale){
                     if((rootx-targetx)>entityWidth*scale){
                     //1
                     if(model.arrowList.get(arrowCounter).getHasJog() && 
                             cjogy>=targety && cjogy<=targety+(75*scale) && cjogx>=rootx && cjogx<=rootx+(120*scale)){
                        jogx = model.arrowList.get(arrowCounter).getJogx();
                        jogy = model.arrowList.get(arrowCounter).getJogy();
                        arootx = jogx;
                        arooty = rooty;
                        atargetx = targetx + (int)Math.floor(120*scale);
                        atargety = jogy;
                     }
                    
                     else{
                        arootx = rootx + (int)Math.floor(60*scale);
                        arooty = rooty;
                        atargetx = targetx + (int)Math.floor(scale*120);
                        atargety = targety + (int)Math.floor(scale*35);
                        jogx= rootx + (int)Math.floor(scale*60);
                        jogy = targety +(int)Math.floor(scale*35);
                        model.arrowList.get(arrowCounter).setHasJog(true);
                        model.arrowList.get(arrowCounter).setJogx(jogx);
                        model.arrowList.get(arrowCounter).setJogy(jogy);
                        model.arrowList.get(arrowCounter).setUpperLimit(arooty);
                        model.arrowList.get(arrowCounter).setLowerLimit(arooty+(int)Math.floor(scale*75));
                        model.arrowList.get(arrowCounter).setLeftLimit(atargetx);
                        model.arrowList.get(arrowCounter).setRightLimit(atargetx+(int)Math.floor(scale*120));
                     }
                     }
                     else if(Math.abs(rootx-targetx)<entityWidth*scale){
                     //2
                     //on center left
                     model.arrowList.get(arrowCounter).setHasJog(false);
                     model.arrowList.get(arrowCounter).setJogx(0);
                     model.arrowList.get(arrowCounter).setJogy(0);
                     model.arrowList.get(arrowCounter).setUpperLimit(0);
                     model.arrowList.get(arrowCounter).setLowerLimit(0);
                     model.arrowList.get(arrowCounter).setLeftLimit(0);
                     model.arrowList.get(arrowCounter).setRightLimit(0);
                     if(rootx-targetx>0){
                         arootx = rootx + (targetx + (int)Math.floor(scale*120) - rootx)/2;
                         arooty = rooty;
                         atargetx = targetx + (int)Math.floor(scale*120) - (targetx + (int)Math.floor(scale*120) - rootx)/2;
                         atargety = targety + (int)Math.floor(scale*75);
                     }
                     //on center right
                     else if(rootx - targetx<0){
                         arootx = rootx + (int)Math.floor(scale*120) - (rootx + (int)Math.floor(scale*120) - targetx)/2;
                         arooty = rooty;
                         atargetx = targetx + (rootx + (int)Math.floor(scale*120) - targetx)/2;
                         atargety = targety + (int)Math.floor(scale*75);
                     }
                     //on right center
                     else{
                        arootx = rootx + (int)Math.floor(scale*60);
                        arooty = rooty;
                        atargetx = targetx +(int)Math.floor(scale*60);
                        atargety = targety + (int)Math.floor(scale*75);
                     }
                     }
                     else if((rootx - targetx)<(-1*entityWidth*scale)){
                         
                     //3
                       if(model.arrowList.get(arrowCounter).getHasJog() &&
                               cjogy>targety&&cjogy<targety+(int)Math.floor(scale*75)&&cjogx>rootx&&cjogx<rootx+(int)Math.floor(scale*120)){
                           jogx = model.arrowList.get(arrowCounter).getJogx();
                           jogy = model.arrowList.get(arrowCounter).getJogy();
                           arootx = jogx;
                           arooty = rooty;
                           atargetx = targetx;
                           atargety = jogy;
                        }
                        else{
                            arootx = rootx + (int)Math.floor(scale*60);
                            arooty = rooty;
                            atargetx = targetx;
                            atargety = targety + (int)Math.floor(scale*30);
                            jogx = rootx + (int)Math.floor(scale*60);
                            jogy = targety + (int)Math.floor(scale*30);
                            model.arrowList.get(arrowCounter).setHasJog(true);
                            model.arrowList.get(arrowCounter).setJogx(jogx);
                            model.arrowList.get(arrowCounter).setJogy(jogy);
                            model.arrowList.get(arrowCounter).setUpperLimit(arooty);
                            model.arrowList.get(arrowCounter).setLowerLimit(arooty+(int)Math.floor(scale*75));
                            model.arrowList.get(arrowCounter).setLeftLimit(atargetx);
                            model.arrowList.get(arrowCounter).setRightLimit(atargetx+(int)Math.floor(scale*120));
                        }
                     }
                 }
                 //4-5
                 else if(Math.abs(rooty - targety)<entityLength*scale){
                     model.arrowList.get(arrowCounter).setHasJog(false);
                     model.arrowList.get(arrowCounter).setJogx(0);
                     model.arrowList.get(arrowCounter).setJogy(0);
                     model.arrowList.get(arrowCounter).setUpperLimit(0);
                     model.arrowList.get(arrowCounter).setLowerLimit(0);
                     model.arrowList.get(arrowCounter).setLeftLimit(0);
                     model.arrowList.get(arrowCounter).setRightLimit(0);
                     if((rootx-targetx)>entityWidth*scale){
                     if(rooty - targety >0){
                         arootx = rootx;
                         arooty = rooty + (targety + (int)Math.floor(scale*75) - rooty)/2;
                         atargetx = targetx + (int)Math.floor(scale*120);
                         atargety = arooty;
                     }
                     else if(rooty - targety <0){
                         arootx = rootx;
                         arooty = rooty + (int)Math.floor(scale*75) - (rooty + (int)Math.floor(scale*75) - targety)/2;
                         atargetx = targetx + (int)Math.floor(scale*120);
                         atargety = arooty;
                     }
                     else{
                        arootx = rootx;
                        arooty = rooty + (int)Math.floor(scale*35);
                        atargetx = targetx + (int)Math.floor(scale*120);
                        atargety = targety + (int)Math.floor(scale*35);
                     }
                     }
                     else if((rootx - targetx)<(-1*entityWidth*scale)){
                        if(rooty - targety > 0){
                            arootx = rootx + (int)Math.floor(scale*120);
                            arooty = rooty + (targety + (int)Math.floor(scale*75) - rooty)/2;
                            atargetx = targetx;
                            atargety = arooty;
                        }
                        else if(rooty - targety < 0){
                            arootx = rootx + (int)Math.floor(scale*120);
                            arooty = rooty + (int)Math.floor(scale*75) - (rooty + (int)Math.floor(scale*75) - targety)/2;
                            atargetx = targetx;
                            atargety = arooty;
                        }
                        else{
                           arootx = rootx + (int)Math.floor(scale*120);
                           arooty = rooty + (int)Math.floor(scale*35);
                           atargetx = targetx;
                           atargety = targety + (int)Math.floor(scale*35); 
                        }
                     }
                //6-8
                 }
                 else if((rooty - targety)<(-1*entityLength*scale)){
                     if((rootx-targetx)>entityWidth*scale){
                         if(model.arrowList.get(arrowCounter).getHasJog() &&
                                 cjogy>targety && cjogy<targety+(int)Math.floor(scale*75) && cjogx>rootx && cjogx<rootx+(int)Math.floor(scale*120)){
                             jogx = model.arrowList.get(arrowCounter).getJogx();
                             jogy = model.arrowList.get(arrowCounter).getJogy();
                             arootx = jogx;
                             arooty = rooty + (int)Math.floor(scale*75);
                             atargetx = targetx + (int)Math.floor(scale*120);
                             atargety = jogy;
                         }
                        else{
                            arootx = rootx + (int)Math.floor(scale*60);
                            arooty = rooty + (int)Math.floor(scale*75);
                            atargetx = targetx + (int)Math.floor(scale*120);
                            atargety = targety + (int)Math.floor(scale*30);
                            jogx = arootx;
                            jogy = atargety;
                            model.arrowList.get(arrowCounter).setHasJog(true);
                            model.arrowList.get(arrowCounter).setJogx(jogx);
                            model.arrowList.get(arrowCounter).setJogy(jogy);
                            model.arrowList.get(arrowCounter).setUpperLimit(atargety);
                            model.arrowList.get(arrowCounter).setLowerLimit(arooty+(int)Math.floor(scale*75));
                            model.arrowList.get(arrowCounter).setLeftLimit(arootx);
                            model.arrowList.get(arrowCounter).setRightLimit(arootx+(int)Math.floor(scale*120));
                         }
                     }
                     else if(Math.abs(rootx-targetx)<entityWidth*scale){
                         model.arrowList.get(arrowCounter).setHasJog(false);
                         model.arrowList.get(arrowCounter).setJogx(0);
                         model.arrowList.get(arrowCounter).setJogy(0);
                         model.arrowList.get(arrowCounter).setUpperLimit(0);
                        model.arrowList.get(arrowCounter).setLowerLimit(0);
                        model.arrowList.get(arrowCounter).setLeftLimit(0);
                        model.arrowList.get(arrowCounter).setRightLimit(0);
                        if(rootx - targetx > 0){
                            arootx = rootx + (targetx + (int)Math.floor(scale*120) - rootx)/2;
                            arooty = rooty + (int)Math.floor(scale*75);
                            atargetx = arootx;
                            atargety = targety;
                        }
                        else if(rootx - targetx < 0){
                            arootx = rootx + (int)Math.floor(scale*120) - (rootx + (int)Math.floor(scale*120) - targetx)/2;
                            arooty = rooty + (int)Math.floor(scale*75);
                            atargetx = arootx;
                            atargety = targety;
                        }
                        else{
                            arootx = rootx + (int)Math.floor(scale*60);
                            arooty = rooty + (int)Math.floor(scale*75);
                            atargetx = targetx +(int)Math.floor(scale*60);
                            atargety = targety;
                        }
                     }
                     else if((rootx - targetx)<(-1*entityWidth*scale)){
                         if(model.arrowList.get(arrowCounter).getHasJog()&&
                                 cjogy>targety && cjogy<targety+(int)Math.floor(scale*75) && cjogx>rootx && cjogx<rootx+(int)Math.floor(scale*120)){
                            jogx = model.arrowList.get(arrowCounter).getJogx();
                            jogy = model.arrowList.get(arrowCounter).getJogy();
                            arootx = jogx;
                            arooty = rooty + (int)Math.floor(scale*75);
                            atargetx = targetx;
                            atargety = jogy;
                         }
                         else{
                            arootx = rootx + (int)Math.floor(scale*60);
                            arooty = rooty + (int)Math.floor(scale*75);
                            atargetx = targetx;
                            atargety = targety + (int)Math.floor(scale*30);
                            jogx = arootx;
                            jogy = atargety;
                            model.arrowList.get(arrowCounter).setHasJog(true);
                            model.arrowList.get(arrowCounter).setJogx(jogx);
                            model.arrowList.get(arrowCounter).setJogy(jogy);
                            model.arrowList.get(arrowCounter).setUpperLimit(atargety);
                            model.arrowList.get(arrowCounter).setLowerLimit(atargety+(int)Math.floor(scale*75));
                            model.arrowList.get(arrowCounter).setLeftLimit(arootx);
                            model.arrowList.get(arrowCounter).setRightLimit(arootx+(int)Math.floor(scale*120));
                         }
                     }
                 }
                 
            Graphics2D g2 = (Graphics2D) g.create();
            if(jogx==0 || jogy==0){
                double x = atargetx - arootx;
                double y = atargety - arooty;
                int length = (int)Math.sqrt(x*x+y*y);
                double ang = Math.atan2(y,x);
                AffineTransform t = AffineTransform.getTranslateInstance(arootx, arooty);
                t.concatenate(AffineTransform.getRotateInstance(ang));
                g2.transform(t);
                if(model.arrowList.get(arrowCounter).getDashed()){
                Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
                g2.setStroke(dashed);
                }
                g2.setColor(Color.BLACK);
                g2.drawLine(0,0,length,0);
                g2.fillPolygon(new int[] {length, length-4, length-4, length}, new int[]{0, -4, 4, 0}, 4); 
            }
            else{
                if(model.arrowList.get(arrowCounter).getDashed()){
                Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
                g2.setStroke(dashed);
                }
                g2.setColor(Color.BLACK);
                g2.drawLine(arootx, arooty, jogx, jogy);
                if(arrowCounter==selectedArrow){
                    g2.fillRect(jogx-3, jogy-3, 6, 6);
                 }
                double x = atargetx - jogx;
                double y = atargety - jogy;
                int length = (int)Math.sqrt(x*x+y*y);
                double ang = Math.atan2(y,x);
                AffineTransform t = AffineTransform.getTranslateInstance(jogx, jogy);
                t.concatenate(AffineTransform.getRotateInstance(ang));
                g2.transform(t);
                g2.setColor(Color.BLACK);
                g2.drawLine(0,0,length,0);
                g2.fillPolygon(new int[] {length, length-4, length-4, length}, new int[]{0, -4, 4, 0}, 4);
                 
                 //else{
                 //}
                 
                }

            arrowCounter++;
            
             }
             
         }
        }
        }
        
        this.setPreferredSize(new Dimension((int)Math.floor(925*this.model.getScale()),(int)Math.floor(615*this.model.getScale())));
        
        this.revalidate();
        this.setFocusable(true);
        this.requestFocusInWindow();
        
    }
    
    private boolean InsideSelected(int x, int y, int entityNumber){
        return (x>this.model.entityList.get(entityNumber).getX() && x<this.model.entityList.get(entityNumber).getX()+entityWidth)
                && (y>this.model.entityList.get(entityNumber).getY() && y<this.model.entityList.get(entityNumber).getY()+entityLength);
    }
    
    private boolean insideJog(int x, int y, int arrowNum){
        int x1 = this.model.arrowList.get(arrowNum).getJogx();
        int y2 = this.model.arrowList.get(arrowNum).getJogy();
        boolean temp = x>(x1-5)&&x<(x1+5)&&y>(y2-5)&&y<(y2+5);
        return temp;
    }
    
    private boolean CloseTop(int x, int y, int arrowNum){
        int upperLimit = model.arrowList.get(arrowNum).getUpperLimit();
        if (Math.abs(upperLimit - y)<5){
        return true;
        }
        else{
        return false;
        }
    }
    
    private boolean CloseLeftSide(int x, int y, int arrowNum){
        int leftLimit = model.arrowList.get(arrowNum).getLeftLimit();
        if (Math.abs(leftLimit - x)<5){
        return true;
        }
        else{
        return false;
        }
    }
    
    private boolean CloseBottom(int x, int y, int arrowNum){
        int lowerLimit = model.arrowList.get(arrowNum).getLowerLimit();
        if(Math.abs(lowerLimit - y)<5){
        return true;
        }
        else{
        return false;
        }
    }
    
    private boolean CloseRight(int x, int y, int arrowNum){
        int rightLimit = model.arrowList.get(arrowNum).getRightLimit();
        if(Math.abs(rightLimit - x)<5){
            return true;
        }
        else{
            return false;
        }
    }
    
    private boolean checkJogClick (int x, int y){
        if(model.entityList!=null){
            for(int i =0; i<model.arrowList.size(); i++){
                if(model.arrowList.get(i).getHasJog()){
                    int tempjogx = model.arrowList.get(i).getJogx();
                    int tempjogy = model.arrowList.get(i).getJogy();
                    if(x>=tempjogx-5&&x<=tempjogx+5&&y>=tempjogy-5&&y<=tempjogy+5){
                        selectedArrow = i;
                        return true;
                    }
                    else{
                        selectedArrow = -1;
                    }
                }
            }
            selectedArrow = -1;
            return false;
            
        }
        else{
            return false;
        }
    }
    
    
    
    private void CheckMouseClick(int x, int y){
        //this x and y is within component
       // System.out.println(x);
        //System.out.println(y);
        if(model.entityList!=null){
            for (int i = 0; i <model.entityList.size(); i++){
                int tempx = model.entityList.get(i).getX();
                int tempy = model.entityList.get(i).getY();
                if(x>tempx&&x<tempx+entityWidth&&y>tempy&&y<tempy+entityLength){
                    this.selectedEntity = i;
                    model.selectedInGraph = i;
                    model.setSelected(true,i);
                    
                    break;
                }
                else{
                    model.setSelected(false,i);
                    this.selectedEntity = -1;
                    model.selectedInGraph = -1;
                }
            }
        }
        if(checkJogClick(x,y)){
            
        }
    }
    
     private class KeyHandler extends KeyAdapter{
            @Override
            public void keyPressed(KeyEvent event){
                //System.out.println("in");
                if(event.isControlDown()&&event.getKeyChar() != '[' && event.getKeyCode() == 91){
                    double cs = model.getScale();
                        if(cs > 5){}
                        else{
                            model.setScale(cs+0.1);
                            for(int i =0; i<model.entityList.size();i++){
                                int tempx = model.entityList.get(i).getX();
                                int tempy = model.entityList.get(i).getY();
                                model.entityList.get(i).setX((int)Math.floor(tempx+(tempx/cs)*0.1));
                                model.entityList.get(i).setY((int)Math.floor(tempy+(tempy/cs)*0.1));
                            }
                            if(!model.arrowList.isEmpty()){
                                for(int i=0; i<model.arrowList.size();i++){
                                    if(model.arrowList.get(i).getHasJog()){
                                        int tempjx = model.arrowList.get(i).getJogx();
                                        int tempjy = model.arrowList.get(i).getJogy();
                                        model.arrowList.get(i).setJogx((int)Math.floor(tempjx+(tempjx/cs)*0.1));
                                        model.arrowList.get(i).setJogy((int)Math.floor(tempjy+(tempjy/cs)*0.1));
                                    }
                                }
                            }
                        }
                }
                else if(event.isControlDown()&&event.getKeyChar() != ']' && event.getKeyCode() == 93){
                    double cs = model.getScale();
                    if(cs<0.3){}
                    else{
                        model.setScale(cs-0.1);
                        for(int i =0; i<model.entityList.size();i++){
                            int tempx = model.entityList.get(i).getX();
                            int tempy = model.entityList.get(i).getY();
                            model.entityList.get(i).setX((int)Math.floor(tempx-(tempx/cs)*0.1));
                            model.entityList.get(i).setY((int)Math.floor(tempy-(tempy/cs)*0.1));
                        }
                        if(!model.arrowList.isEmpty()){
                            for(int i=0; i<model.arrowList.size();i++){
                                if(model.arrowList.get(i).getHasJog()){
                                int tempjx = model.arrowList.get(i).getJogx();
                                int tempjy = model.arrowList.get(i).getJogy();
                                model.arrowList.get(i).setJogx((int)Math.floor(tempjx-(tempjx/cs)*0.1));
                                model.arrowList.get(i).setJogy((int)Math.floor(tempjy-(tempjy/cs)*0.1));
                                }
                            }
                        }
                    }
                }
            }
            }
    
    private class MSControl extends MouseInputAdapter{
        private boolean canDragX = false;
        private boolean canDragY = false;
        public void mousePressed(MouseEvent event){
            for(int i = 0; i < model.entityList.size();i++){
                model.setSelected(false,i);
            }
            CheckMouseClick(event.getX(),event.getY());
           // if(selectedEntity>=0){
              repaint();  
            //}
            
        }
        
        public void mouseMoved(MouseEvent event){
            if(selectedEntity>=0){
                if(InsideSelected(event.getX(),event.getY(),selectedEntity)){
                    
                   this.canDragX=true;
                   this.canDragY=true;
                   setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                }
                else{
        
                   this.canDragX=false;
                   this.canDragY=false;
                   setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            }
            if(selectedArrow>=0){
                if(insideJog(event.getX(),event.getY(),selectedArrow)){
                       this.canDragX = true;
                       this.canDragY = true;
                    
                        
                }
            }
        }
        
        public void mouseDragged(MouseEvent event){
            if(selectedEntity>=0){
            if(this.canDragX){
                model.entityList.get(selectedEntity).setX(event.getX());
                
            }
            if(this.canDragY){
                model.entityList.get(selectedEntity).setY(event.getY());
//                model.arrowList.get(selectedArrow).setJogy(event.getY());
                
            }
            }
            if(selectedArrow>=0){
            if(this.canDragX){
                //System.out.println("setx");
                model.arrowList.get(selectedArrow).setJogx(event.getX());
                
            }
            if(this.canDragY){
              //  System.out.println("sety");
                model.arrowList.get(selectedArrow).setJogy(event.getY());
                
            }
            }
        }
        @Override
        public void mouseWheelMoved(MouseWheelEvent event){
                int notches = event.getWheelRotation();
                if(!model.entityList.isEmpty()){
                if (notches < 0) {
                    double cs = model.getScale();
                    if(cs > 5){}
                    else{
                    model.setScale(cs+0.1);
                    for(int i =0; i<model.entityList.size();i++){
                        int tempx = model.entityList.get(i).getX();
                        int tempy = model.entityList.get(i).getY();
                        model.entityList.get(i).setX((int)Math.floor(tempx+(tempx/cs)*0.1));
                        model.entityList.get(i).setY((int)Math.floor(tempy+(tempy/cs)*0.1));
                    }
                    if(!model.arrowList.isEmpty()){
                    for(int i=0; i<model.arrowList.size();i++){
                        if(model.arrowList.get(i).getHasJog()){
                            int tempjx = model.arrowList.get(i).getJogx();
                            int tempjy = model.arrowList.get(i).getJogy();
                            model.arrowList.get(i).setJogx((int)Math.floor(tempjx+(tempjx/cs)*0.1));
                            model.arrowList.get(i).setJogy((int)Math.floor(tempjy+(tempjy/cs)*0.1));
                        }
                    }
                    }
                    }
                } else {
                    double cs = model.getScale();
                    if(cs<0.3){}
                    else{
                    model.setScale(cs-0.1);
                    for(int i =0; i<model.entityList.size();i++){
                        int tempx = model.entityList.get(i).getX();
                        int tempy = model.entityList.get(i).getY();
                        model.entityList.get(i).setX((int)Math.floor(tempx-(tempx/cs)*0.1));
                        model.entityList.get(i).setY((int)Math.floor(tempy-(tempy/cs)*0.1));
                    }
                    if(!model.arrowList.isEmpty()){
                    for(int i=0; i<model.arrowList.size();i++){
                        if(model.arrowList.get(i).getHasJog()){
                            int tempjx = model.arrowList.get(i).getJogx();
                            int tempjy = model.arrowList.get(i).getJogy();
                           model.arrowList.get(i).setJogx((int)Math.floor(tempjx-(tempjx/cs)*0.1));
                            model.arrowList.get(i).setJogy((int)Math.floor(tempjy-(tempjy/cs)*0.1));
                        }
                    }
                    }
                }
                }
                }
            }
        
    }
}

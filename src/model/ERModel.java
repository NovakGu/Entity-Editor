package model;

import java.awt.Color;
import java.util.*;
public class ERModel {
    int entityCounter;
    int arrowCounter;
    double scale;
   public List<Entity> entityList;
   public  List<Arrow> arrowList;
    List<String> entityName;
    List<String> arrowName;
    public List<Integer> entityHighlighted;
    public List<Integer> listEntityHighlighted;
    public List<Integer> listArrowHighlighted;
    public int selectedInGraph;
    
    private ArrayList<IView> views = new ArrayList<>();
    private int aNum = 0;
    
    public class Position { 
            public int x; 
            public int y; 
            
            public Position(int x, int y) { 
            this.x = x; 
            this.y = y; 
            }
            public int getX(){
                return x;
            }
            public int getY(){
                return y;
            }
            public void setX(int newX){
                this.x = newX;
            }
            public void setY(int newY){
                this .y = newY;
            }
        } 
    public class Arrow{
        int arrowNum;
        int target;
        int root;
        int jogx;
        int jogy;
        int upperLimit;
        int lowerLimit;
        int leftLimit;
        int rightLimit;
        boolean hasJog;
        boolean dashed;
        
        public Arrow(int newTarget, int newroot, boolean dash){
            this.arrowNum=fetchArrowCounter();
            target=newTarget;
            root=newroot;
            jogx = 0;
            jogy = 0;
            upperLimit = 0;
            lowerLimit = 0;
            leftLimit = 0;
            rightLimit = 0;
            hasJog = false;
            dashed = dash;
            
        }    
        
        public boolean getDashed(){
            return this.dashed;
        }
        
        public void setDashed(boolean d){
            this.dashed = d;
        }
        
        public int getUpperLimit(){
            return upperLimit;
        }
        public void setUpperLimit(int limit){
            upperLimit = limit;
        }
        public int getLowerLimit(){
            return lowerLimit;
        }
        public void setLowerLimit(int limit){
            lowerLimit = limit;
        }
        public int getLeftLimit(){
            return leftLimit;
        }
        public void setLeftLimit(int limit){
            leftLimit = limit;
        }
        public int getRightLimit(){
            return rightLimit;
        }
        public void setRightLimit(int limit){
            rightLimit = limit;
        }
        
        public int getArrowNum(){
            return arrowNum;
        }
        public int getTarget(){
            return target;
        }
        public int getRoot(){
            return root;
        }
        public int getJogx(){
            return jogx;
        }
        public int getJogy(){
            return jogy;
        }
        public boolean getHasJog(){
            return hasJog;
        }
        public void setJogx(int x){
            jogx = x;
            updateAllViews();
        }
        public void setJogy(int y){
            jogy = y;
            updateAllViews();
        }
        public void setHasJog(boolean jog){
            hasJog = jog;
        }
    }
    public class Entity{
        int entityNum;
        Color color;
        String name;
       public List<Arrow> toEntity;
       public List<Arrow> fromEntity;
        Position poi;
        boolean selected;
    
        public Entity(String newName, int x, int y){
            this.entityNum = fetchEntityCounter();
            //incrementEntityCounter();
            this.name = newName;
            this.poi = new Position(x,y);
            this.fromEntity = new ArrayList<Arrow>();
            this.toEntity = new ArrayList<Arrow>();
        }
        public Entity(String newName, Color c){
            this.entityNum = fetchEntityCounter();
            //incrementEntityCounter();
            this.name = newName;
            this.poi = new Position(0,0);
            this.fromEntity = new ArrayList<Arrow>();
            this.toEntity = new ArrayList<Arrow>();
            this.color = c;
    }
        public Entity(){
            this.entityNum = fetchEntityCounter();
            //incrementEntityCounter();
            this.name = "New Entity";
            this.poi = new Position(0,0);
            this.fromEntity = new ArrayList<Arrow>();
            this.toEntity = new ArrayList<Arrow>();
    }
    

        
        public Color getColor(){
            return this.color;
        }
        
        
        public String getName(){
        return name;
    }
        
        
        public void setName(String newName){
        name = newName;
        updateAllViews();
    }
    
        public int getX(){
        return poi.getX();
    }
        public void setX(int newX){
        poi.setX(newX);
        updateAllViews();
    }
    
        public int getY(){
        return poi.getY();
    }
        public void setY(int newY){
        poi.setY(newY);
        updateAllViews();
    }
        
        public void newPosition(int x, int y){
            setX(x);
            setY(y);
        }
        
        public void setSelected(boolean select){
            this.selected=select;
            //updateAllView();
        }
        public boolean getSelected(){
            return this.selected;
        }
        
        public int[] fetchArrowConnected(){
            int length = toEntity.size()+fromEntity.size();
            int arrowlist[] = new int[length];
            for(int i = 0; i<toEntity.size(); i++){
                arrowlist[i]=toEntity.get(i).getArrowNum();
            }
            for(int j = 0; j<fromEntity.size(); j++){
                arrowlist[j+toEntity.size()]=fromEntity.get(j).getArrowNum();
            }
            return arrowlist;
        }
        public int[] fetchEntityConnected(int targetArrowNum){
            int entitylist[] = new int[2];
            entitylist[0]=arrowList.get(targetArrowNum).getTarget(); 
            entitylist[1]=arrowList.get(targetArrowNum).getRoot(); 
            return entitylist;
        }
    }

    public ERModel(int n) {
        this.aNum = n;
        this.scale = 1.0;
        this.entityCounter=0;
        this.arrowCounter=0;
        this.entityList = new ArrayList<>();
        this.arrowList = new ArrayList<>();
        this.arrowName = new ArrayList<>();
        this.entityName = new ArrayList<>();
        this.entityHighlighted= new ArrayList<>();
        this.listEntityHighlighted = new ArrayList<>();
        this.listArrowHighlighted= new ArrayList<>();
        //this.entityName.add("1");
        //this.arrowName.add("2");
        
        
    }
    
        public double getScale(){
            return this.scale;
        }
        public void setScale(double scaleN){
            this.scale = scaleN;
            updateAllViews();
        }
    
    public int getNum() {
        return this.aNum;
    }
    public void setNum(int n) {
        this.aNum = n;
        this.updateAllViews();
	
    }
    public void incrementEntityCounter(){
        entityCounter = entityCounter + 1;
    }
    public void incrementArrowCounter(){
        arrowCounter = arrowCounter + 1;
    }
    public int fetchEntityCounter(){
        return entityCounter;
    }
    public int fetchArrowCounter(){
        return arrowCounter;
    }
    public void newEntity(String name, int x, int y){
        Entity newEntity = new Entity(name,x,y);
        incrementEntityCounter();
        entityList.add(newEntity);
        entityName.add(name);
        this.updateAllViews();
	
    }
    public void newEntity(String name, Color c){
        Entity newEntity = new Entity(name,c);
        incrementEntityCounter();
        entityList.add(newEntity);
        entityName.add(name);
        this.updateAllViews();
    }
    public void newEntity(){
        Entity newEntity = new Entity();
        incrementEntityCounter();
        entityList.add(newEntity);
        entityName.add("New Entity");
        this.updateAllViews();
    }
    public void newArrow(int target, int root, boolean dash){
        Arrow newArrow = new Arrow(target,root,dash);
        entityList.get(target).fromEntity.add(newArrow);
        entityList.get(root).toEntity.add(newArrow);
        incrementArrowCounter();
        arrowList.add(newArrow);
        
        this.updateAllViews();
    }
    public int[] EntityClicked(int entityNum){
        entityList.get(entityNum).setSelected(true);
        int[] arrowConnected = entityList.get(entityNum).fetchArrowConnected();
        return arrowConnected;
    }
    public int[] arrowClicked(int arrowNum){
       entityList.get(arrowList.get(arrowNum).getRoot()).setSelected(true);
       entityList.get(arrowList.get(arrowNum).getTarget()).setSelected(true);
       int entityConnected[] =  {arrowList.get(arrowNum).getRoot(), arrowList.get(arrowNum).getTarget()};
       return entityConnected;
    }
    public int detectPosition(int x, int y, int width, int height){
        return 0;
    }
    public void addView(IView view){
        this.views.add(view);
    }
    public void removeView(IView view){
        this.views.remove(view);
    }
    public void updateAllViews(){
        for(IView view : this.views){
            view.updateView();
        }
    }
    
    public int[] entityX(){
        int[] entityXArr = new int[entityList.size()];
        int counter =0;
        for(Entity en : this.entityList){
            entityXArr[counter] = en.getX();
            counter++;
        }
        return entityXArr;
    }
    
    public int[] entityY(){
        int[] entityYArr = new int[entityList.size()];
        int counter =0;
        for(Entity en : this.entityList){
            entityYArr[counter] = en.getY();
            counter++;
        }
        return entityYArr;
    }
    
    public String[] entityArray(){
        String[] entityArr = new String[entityName.size()];
        entityArr = entityName.toArray(entityArr);
        return entityArr;
    }
    public String[] arrowArray(){
        String[] arrowArr = new String[arrowList.size()];
        arrowArr = arrowName.toArray(arrowArr);
        return arrowArr;
    }
    public void specialListUpdate(){
        this.updateAllViews();
    }
    
    public boolean fetchselected(int entityNumber){
        boolean temp = entityList.get(entityNumber).getSelected();
        return temp;
    }
    public void setSelected(boolean selected, int entityNumber){
        entityList.get(entityNumber).setSelected(selected);
        this.updateAllViews();
    }

}

import View.*;
import model.ERModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.*;


public class EREdit {

    public static void main(String[] args){
                ERModel model = new ERModel(1);
                JFrame frame = new JFrame("EREdit");
                
               // frame.setResizable(false);
                JTextArea text = new JTextArea();
                text.setBackground(Color.PINK);
                JPanel panel = new JPanel();
		//create an instance of the model
		
		//create instances of the views/controllers, passing them a reference to the model
                ButtonView bView = new ButtonView(model);
                ListView lView = new ListView(model);
                
                GraphicView gView = new GraphicView(model);
                JScrollPane scrollPane = new JScrollPane(gView);
                GridBagLayout gb = new GridBagLayout();
		frame.setLayout(gb);
                frame.getContentPane().add(bView);
                frame.getContentPane().add(lView);
                frame.getContentPane().add(scrollPane);
                gView.setBackground(Color.PINK);
                GridBagConstraints s= new GridBagConstraints();
                s.fill = GridBagConstraints.BOTH;
                s.gridwidth=0;
                s.weightx = 2;
                s.weighty=1;
                
                gb.setConstraints(bView, s);
                s.gridwidth=1;
                s.gridheight = 1;
                s.weightx = 0;
                s.weighty=1;
                s.anchor = GridBagConstraints.FIRST_LINE_START;
                 //bottom of space

                gb.setConstraints(lView, s);
                s.gridwidth=0;
                s.gridheight = 10;
                s.weightx = 1;
                s.weighty= 10;
                s.anchor = GridBagConstraints.LINE_START;
                gb.setConstraints(scrollPane,s);
                s.gridwidth=0;
                s.gridheight = 10;
                s.weightx = 12;
                s.weighty=12;
                s.anchor = GridBagConstraints.EAST;           
                
	
		//display the view(s) in a frame
		frame.setSize(1024,768);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
                
                
    }
    
    
}

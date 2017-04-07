package View;
import Model.GameObject;

import java.awt.Color;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Window {
	private Map map = new Map();
	
	public Window(){	    
	    JFrame window = new JFrame("Game");
	    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    window.setBounds(0, 0, 1000, 1020);
	    window.getContentPane().setBackground(Color.gray);
	    window.getContentPane().add(this.map);
	    window.setVisible(true);		    
	}	
	
	public void setGameObjects(ArrayList<GameObject> objects){
		this.map.setObjects(objects);
		this.map.redraw();
	}
	
	public void update(){
		this.map.redraw();
	}
	
	public void setKeyListener(KeyListener keyboard){
	    this.map.addKeyListener(keyboard);
	}
}

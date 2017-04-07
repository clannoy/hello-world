package View;
import Model.GameObject;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Map extends JPanel {
	private ArrayList<GameObject> objects;
	
	public Map(){
		this.setFocusable(true);
		this.requestFocusInWindow();
	}
	
	public void paint(Graphics g) { 
		for(int i = 0; i<20; i++){							// Vire la valeur 20 et parametrer ca
			for(int j = 0; j<20; j++){
				int x = i;
				int y = j;
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(x*50, y*50, 48, 48); 
				g.setColor(Color.BLACK);
				g.drawRect(x*50, y*50, 48, 48); 
			}
		}
		
		for(GameObject object : this.objects){
			int x = object.getPosX();
			int y = object.getPosY();
			int color = object.getColor();			
			
			if(color == 0){
				g.setColor(Color.DARK_GRAY);
			}else if(color == 1){
				g.setColor(Color.GRAY);
			}else if(color == 2){
				g.setColor(Color.BLUE);
			}else if(color == 3){
				g.setColor(Color.GREEN);
			}else if(color == 4){
				g.setColor(Color.RED);
			}else if(color == 5){
				g.setColor(Color.ORANGE);
			}

			g.fillRect(x*50, y*50, 48, 48);
			g.setColor(Color.BLACK);
			g.drawRect(x*50, y*50, 48, 48); 
		}
	}
	
	public void setObjects(ArrayList<GameObject> objects){
		this.objects = objects;
	}
	
	public void redraw(){
		this.repaint();
	}
}


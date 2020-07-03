package myself;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
public class ToolImagePanel extends ImagePanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int width , height ;
	int QUANTITY = 1;	//实际为奇数,向上取奇
	
	public ToolImagePanel(){
		super();
	}
	
	public ToolImagePanel(String imgUrl){
		super(imgUrl);
				
	}
	
	public ToolImagePanel(Image img){
		super(img);
	}
	
	public void refreshImg(Image img){
		this.img = img;
		width = getWidth();
		height = getHeight();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.RED);
		g.drawLine(width/2 , 0, width/2 , height);
		g.drawLine(0, height/2  , width, height/2);
		for(int i=1; i < QUANTITY/2 + 1 ; i++){
			g.drawLine(width/2 - i, 0, width/2 - i, height);
			g.drawLine(0, height/2 - i , width, height/2 - i);
		}
		for(int i=1; i < QUANTITY/2 + 1 ; i++){
			g.drawLine(width/2 + i, 0, width/2 + i, height);
			g.drawLine(0, height/2 + i , width, height/2 + i);
		}
		
	}
}

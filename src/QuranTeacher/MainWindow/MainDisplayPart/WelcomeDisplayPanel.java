/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package QuranTeacher.MainWindow.MainDisplayPart;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

import QuranTeacher.WordInformation.SegmentColors;

public class WelcomeDisplayPanel extends JPanel {

	/**
	 * Panel that is displayed when the applicatio is started
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Create the panel.
	 */
	private Font font;
	private Point startPoint;
	private int lineHeight;
	private String[] shortCutStrings=
		{
			"SPACEBAR -- Go to next ayah",
			"Key RIGHT-- Speed Down",
			"Key LEFT -- Speed Up",
			"Key UP   -- Scroll Up",
			"Key DOWN -- Scroll Down",
			"Mouse Right Click-- Pause/Resume Display"
		};
	
	public WelcomeDisplayPanel() {
		font=new Font("Serif",Font.BOLD,40);
		startPoint=new Point(20,50);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		setBackground(Color.DARK_GRAY);
		g.setColor(Color.ORANGE);
		g.setFont(font);
		//g.drawString("Assalamu Alaikum", startPoint.x,startPoint.y);
		drawStringEffect(g,"Assalamu 'Alaikum", startPoint.x, startPoint.y);
		
		g.setColor(SegmentColors.getColor("segSeagreen"));
		drawStringEffect(g,"Welcome to Quran Teacher or Learn Arabic Application",
				startPoint.x, startPoint.y+100);
		g.setFont(font.deriveFont((float)20));
		
		g.setColor(Color.YELLOW);
		g.drawString("Click On 'Go' to play Animation", startPoint.x,startPoint.y+150);
		
		lineHeight=g.getFontMetrics().getHeight();
		
		g.setColor(Color.GREEN.darker());
		g.drawString("Shortcuts:", startPoint.x, startPoint.y+190);
		
		int gapFromStart=190+lineHeight*2;
		
		g.setFont(font.deriveFont((float)18));
		lineHeight=g.getFontMetrics().getHeight();
		
		for(int i=0;i<shortCutStrings.length;i++)
		{
			g.drawString(shortCutStrings[i], startPoint.x, startPoint.y+gapFromStart);
			gapFromStart+=lineHeight*1.5;
		}
	}
	
	private void drawStringEffect(Graphics g,String str,int x,int y)
	{
		Color color=g.getColor();
		Color tempColor=color;
		int elevation=1;
		for(int i=0;i<7;i++)
		{
			x+=elevation;
			y-=elevation;
			tempColor=tempColor.darker();
			g.setColor(tempColor);
			g.drawString(str, x, y);
		}
		g.setColor(color);
		g.drawString(str, x, y);
	}
}

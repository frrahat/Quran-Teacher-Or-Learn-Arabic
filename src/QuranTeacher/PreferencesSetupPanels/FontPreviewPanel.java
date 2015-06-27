/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package QuranTeacher.PreferencesSetupPanels;

import java.awt.BorderLayout;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import QuranTeacher.Preferences.Preferences;

public class FontPreviewPanel extends JPanel {

	/**
	 * For previewing the font to be selected 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Create the panel.
	 */
	private Preferences preferences;
	private Preferences secondPref;
	private String text="No text available";
	private String[] otherTexts;

	private JTextArea txtrPreviewtext;
	
	public FontPreviewPanel(Preferences preferences) {
		
		this.preferences=preferences;
		
		setLayout(new BorderLayout(0, 0));
	
		txtrPreviewtext = new JTextArea();
		txtrPreviewtext.setEditable(false);
		txtrPreviewtext.setLineWrap(true);
		txtrPreviewtext.setWrapStyleWord(true);

		//updateTextArea();
		
		add(txtrPreviewtext, BorderLayout.CENTER);
	}
	
    @Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		if(secondPref!=null)
		{
			drawFirstString(g, secondPref);
			int y=g.getFontMetrics().getHeight()*2;
			
			g.setFont(preferences.getFont());
			
			g.setColor(preferences.getBackGroundColor());
			g.drawString(otherTexts[0], 30, y);
			
			y+=g.getFontMetrics().getHeight()*2;
			
			g.setColor(preferences.getForeGroundColor());
			g.drawString(otherTexts[1], 30, y);
		}
		else
		{
			drawFirstString(g, preferences);
		}
	}
	
	public void setText(String text)
	{
		this.text=text;
	}
	
	public void setOtherTexts(String[] texts)
	{
		otherTexts=new String[text.length()];
		for(int i=0;i<texts.length;i++)
			otherTexts[i]=texts[i];
	}
	
	public void setTextAreaVisible(boolean b)
	{
		txtrPreviewtext.setVisible(b);
	}
	
	public boolean getTextAreaVisible()
	{
		return txtrPreviewtext.isVisible();
	}
	
	public void updateTextArea()
	{
		txtrPreviewtext.setBackground(preferences.getBackGroundColor());
		txtrPreviewtext.setForeground(preferences.getForeGroundColor());
		txtrPreviewtext.setFont(preferences.getFont());
		txtrPreviewtext.setText(text);
	}
	
	public void updateFontPreview()
	{
		if(txtrPreviewtext.isVisible())
		{
			updateTextArea();
		}
		else
		{
			repaint();
		}
	}
	
	public void setSecondPref(Preferences pref2)
	{
		this.secondPref=pref2;
	}
	
	private void drawFirstString(Graphics g, Preferences pref)
	{
		setBackground(pref.getBackGroundColor());
		g.setColor(pref.getForeGroundColor());
		g.setFont(pref.getFont());
		FontMetrics fontMetrics=g.getFontMetrics();
		g.drawString(text, 30, fontMetrics.getHeight()+fontMetrics.getDescent());
	}
}

/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package QuranTeacher.Preferences;

import java.awt.Color;
import java.awt.Font;



public class TranslationPreferences extends Preferences {
	
	private int primaryTextIndex = 0;
	private int secondaryTextIndex = -1;
	
	private static final String[] prefStrings={
		"BgColor=","FgColor=","Font=","PrimaryTextIndex=","SecondaryTextIndex="};
	
	public TranslationPreferences(String id){
		super(id);
		defaultFont=new Font("SolaimanLipiNormal", Font.PLAIN, 18);
	}
	
	public TranslationPreferences(String id, Color bgColor, Color fgColor,
			Font transFont, int primaryTextIndex,int secondaryTextIndex) {
		super(id);
		backGroundColor=bgColor;
		foreGroundColor=fgColor;
		font=transFont;
		this.primaryTextIndex=primaryTextIndex;
		this.secondaryTextIndex=secondaryTextIndex;
	}

	public int getPrimaryTextIndex()
	{
		return primaryTextIndex;
	}
	
	public int getSecondaryTextIndex(){
		return secondaryTextIndex;
	}
	
	public void setPrimaryTextIndex(int i)
	{
		this.primaryTextIndex=i;
	}
	
	public void setSecondaryTextIndex(int i)
	{
		this.secondaryTextIndex=i;
	}
	
	@Override
	public String toString()
	{
		
		return
				prefStrings[0]+getBackGroundColor().getRGB()
				+ "\n"+prefStrings[1]+getForeGroundColor().getRGB()
				+ "\n"+prefStrings[2]+getFont().getFontName()
				+ fontPartsSeparator+getFont().getStyle()
				+ fontPartsSeparator+getFont().getSize()
				+ "\n"+prefStrings[3]+getPrimaryTextIndex()
				+ "\n"+prefStrings[4]+getSecondaryTextIndex();
	}

	@Override
	public void checkAndGrab(String text) 
	{
		if(text.startsWith(prefStrings[0]))
			setBackGroundColor(new Color
					(Integer.parseInt(text.substring(text.indexOf("=")+1))));

		else if(text.startsWith(prefStrings[1]))
			setForeGroundColor(new Color
					(Integer.parseInt(text.substring(text.indexOf("=")+1))));

		else if(text.startsWith(prefStrings[2]))
			setFont(readFont(text.substring(text.indexOf("=")+1)));	

		else if(text.startsWith(prefStrings[3]))
			setPrimaryTextIndex(Integer.parseInt(text.substring(text.indexOf("=")+1)));
		
		else if(text.startsWith(prefStrings[4]))
			setSecondaryTextIndex(Integer.parseInt(text.substring(text.indexOf("=")+1)));
	}

	@Override
	public void resetToDefault() {
		backGroundColor=Color.BLACK;
		foreGroundColor=Color.WHITE;
		font=defaultFont;
		
		primaryTextIndex=0;
		secondaryTextIndex=-1;
	}
}

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


public class WordByWordFontPref extends Preferences {

	/*
	 * for word by word meaning font, bg Color is unimportant
	 */
	private static final String[] prefStrings={
		"BgColor=","FgColor=","Font="};
	
	public WordByWordFontPref(String id) {
		super(id);
		defaultFont=new Font("Tahoma",Font.BOLD,16);
	}
	

	
	public WordByWordFontPref(String id, Color wbwTrnslitrtionColor,
			Color wbwMeaningColor, Font wbwFont) {
		super(id);
		backGroundColor=wbwTrnslitrtionColor;
		foreGroundColor=wbwMeaningColor;
		font=wbwFont;
	}



	@Override
	public String toString()
	{
		
		return
				prefStrings[0]+getBackGroundColor().getRGB()
				+ "\n"+prefStrings[1]+getForeGroundColor().getRGB()
				+ "\n"+prefStrings[2]+getFont().getFontName()
				+ fontPartsSeparator+getFont().getStyle()
				+ fontPartsSeparator+getFont().getSize();
	}


	@Override
	public void checkAndGrab(String text) {
		
		if(text.startsWith(prefStrings[0]))
			setBackGroundColor(new Color
					(Integer.parseInt(text.substring(text.indexOf("=")+1))));

		else if(text.startsWith(prefStrings[1]))
			setForeGroundColor(new Color
					(Integer.parseInt(text.substring(text.indexOf("=")+1))));

		else if(text.startsWith(prefStrings[2]))
			setFont(readFont(text.substring(text.indexOf("=")+1)));	

	}



	@Override
	public void resetToDefault() {
		backGroundColor=Color.RED;
		foreGroundColor=Color.GREEN;
		font=defaultFont;
	}
		
}

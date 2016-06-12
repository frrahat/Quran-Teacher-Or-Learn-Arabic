package QuranTeacher.Preferences;

import java.awt.Color;
import java.awt.Font;

/**
 * @author Rahat
 * @since June 13, 2016
 */
public class SubtextPreferences extends Preferences {
	
	private static final String[] prefStrings={
		"BgColor=","FgColor=","Font="};
	
	public SubtextPreferences(String id) {
		super(id);
		defaultFont=new Font("Tahoma",Font.ITALIC,28);
	}

	public SubtextPreferences(String id, Color foreGroundColor, Font font) {
		super(id);
		this.backGroundColor=Color.BLACK;
		this.foreGroundColor=foreGroundColor;
		this.font=font;
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
	public void resetToDefault() {
		backGroundColor=Color.BLACK;
		foreGroundColor=Color.WHITE;
		font=defaultFont;
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

}

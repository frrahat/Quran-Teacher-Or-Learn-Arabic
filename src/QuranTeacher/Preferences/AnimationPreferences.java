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

public class AnimationPreferences extends Preferences 
{

	
	public static boolean continuous=false;
	private int restingTime=0;
	private boolean showPopUpBox=true;
	
	private boolean downloadImagesEnabled=false;
	
	private static final String[] prefStrings={
		"BgColor=","FgColor=","Font=","RestingTime=","ShowPopUpBox=","DwnldImgsEnbld="};
	
	public AnimationPreferences(String id) 
	{
		super(id);
		defaultFont=new Font("Tahoma",Font.PLAIN,40);
	}

	public AnimationPreferences(String id, boolean fontNotInitialized) 
	{
		super(id,fontNotInitialized);
		defaultFont=new Font("Tahoma",Font.PLAIN,40);
	}	
	
	public AnimationPreferences(String id, Color bgColor, Color fgColor,
			Font animFont, int restingTimeGap, boolean showPopUpInfoBox,
			boolean dwnldImagesEnbld) {
		super(id);
		backGroundColor=bgColor;
		foreGroundColor=fgColor;
		font=animFont;
		restingTime=restingTimeGap;
		showPopUpBox=showPopUpInfoBox;
		downloadImagesEnabled=dwnldImagesEnbld;
	}

	public int getRestingTime()
	{
		return restingTime;
	}
	
	public void setRestingTime(int time)
	{
		restingTime=time;
	}

	public boolean isShowPopUpBox() 
	{
		return showPopUpBox;
	}

	public void setShowPopUpBox(boolean showPopUpBox) 
	{
		this.showPopUpBox = showPopUpBox;
	}


	public void setDownloadImageEnabled(boolean b) {
		this.downloadImagesEnabled=b;
	}
	public boolean isDownloadImageEnabled() {
		return downloadImagesEnabled;
	}
	
	private int getIntValue(boolean b){
		return b?1:0;
	}
	
	private boolean getBoolValue(int k){
		return k==1?true:false;
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
				+ "\n"+prefStrings[3]+getRestingTime()
				+ "\n"+prefStrings[4]+Integer.toString(getIntValue(isShowPopUpBox()))
				+ "\n"+prefStrings[5]+Integer.toString(getIntValue(isDownloadImageEnabled()));
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

		else if(text.startsWith(prefStrings[3]))
			setRestingTime(Integer.parseInt(text.substring(text.indexOf("=")+1)));

		else if(text.startsWith(prefStrings[4]))
			setShowPopUpBox(getBoolValue(Integer.parseInt(text.substring(text.indexOf("=")+1))));
		
		else if(text.startsWith(prefStrings[5]))
			setDownloadImageEnabled(getBoolValue(Integer.parseInt(text.substring(text.indexOf("=")+1))));
	}

	@Override
	public void resetToDefault() {
		font=defaultFont;
		backGroundColor=Color.DARK_GRAY;
		foreGroundColor=Color.CYAN;
		
		restingTime=0;
		showPopUpBox=true;
		downloadImagesEnabled=false;
	}
	
}

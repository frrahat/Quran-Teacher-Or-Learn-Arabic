/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package QuranTeacher.Preferences;

/**
 * @author Rahat
 *@Date: 28-July-2015
 */
public class AdvancedAnimPref extends Preferences{

	private int lineGap;
	private int extraHeight;
	private int wordGap;
	private boolean hidePartial;
	
	private static final String[] prefStrings={
		"lineGap=","extraHeight=","wordGap=","hidePartial="};
	
	public AdvancedAnimPref(String id) {
		super(id);
	}
	public AdvancedAnimPref(String id,int lineGap,int extraHeight,int wordGap,boolean hidePartial) {
		super(id);
		this.lineGap=lineGap;
		this.extraHeight=extraHeight;
		this.wordGap=wordGap;
		this.hidePartial=hidePartial;
	}
	
	@Override
	public void resetToDefault() {
		// TODO Auto-generated method stub
		//nothing to do
	}

	@Override
	public void checkAndGrab(String text) {
		int value=Integer.parseInt(text.substring(text.indexOf("=")+1));
		if(text.startsWith(prefStrings[0]))
			lineGap=value;

		else if(text.startsWith(prefStrings[1]))
			extraHeight=value;

		else if(text.startsWith(prefStrings[2]))
			wordGap=value;	

		else if(text.startsWith(prefStrings[3]))
			hidePartial=value==0?false:true;
	}
	
	@Override
	public String toString() {
		int k=0;
		if(hidePartial)k=1;
		
		return prefStrings[0]+Integer.toString(lineGap)
				+"\n"+prefStrings[1]+Integer.toString(extraHeight)
				+"\n"+prefStrings[2]+Integer.toString(wordGap)
				+"\n"+prefStrings[3]+Integer.toString(k);
	}

	public int getLineGap() {
		return lineGap;
	}

	public int getExtraHeight() {
		return extraHeight;
	}

	public int getWordGap() {
		return wordGap;
	}

	public boolean isHidePartial() {
		return hidePartial;
	}
	
}

/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package QuranTeacher.Preferences;

public class AudioPreferences extends Preferences{
		
	private boolean isAudioON=true;
	private int audioSourceIndex;
	
	private static final String[] prefStrings={"isAudioOn=","AudioSI="};

	public AudioPreferences(String id) {
		super(id);
	}
	
	
	
	public AudioPreferences(String id, boolean isAnimAudioOn,
			int audioSIndex) {
		super(id);
		isAudioON=isAnimAudioOn;
		audioSourceIndex=audioSIndex;
	}



	@Override
	public String toString()
	{
		int k;
		if(isAudioON)k=1;
		else k=0;
		return prefStrings[0]+k
				+"\n"+prefStrings[1]+audioSourceIndex;
	}


	@Override
	public void checkAndGrab(String text) {

		if(text.startsWith(prefStrings[0]))
		{
			int k=Integer.parseInt(text.substring(text.indexOf("=")+1));
			if(k==0)setAudioON(false);
			else setAudioON(true);
		}

		else if(text.startsWith(prefStrings[1]))
			setAudioSourceIndex(Integer.parseInt(text.substring(text.indexOf("=")+1)));
		
	}



	public boolean isAudioON() {
		return isAudioON;
	}



	public void setAudioON(boolean isAudioON) {
		this.isAudioON = isAudioON;
	}



	public int getAudioSourceIndex() {
		return audioSourceIndex;
	}



	public void setAudioSourceIndex(int audioSourceIndex) {
		this.audioSourceIndex=audioSourceIndex;
	}



	@Override
	public void resetToDefault() {
		isAudioON=true;
		audioSourceIndex=0;
	}
	
	
}

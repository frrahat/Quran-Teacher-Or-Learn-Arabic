/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package QuranTeacher.Preferences;

import QuranTeacher.Basics.Ayah;

public class AudioPreferences extends Preferences{
		
	private boolean isAudioON=true;
	private int audioSourceIndex;
	private Ayah currentAyah;//not a property of this pref actually
	
	private static final String[] prefStrings={"isAudioOn=","AudioSI=","cur.ayah="};

	public AudioPreferences(String id) {
		super(id);
		currentAyah=new Ayah(0,0);
	}
	
	
	
	public AudioPreferences(String id, boolean isAnimAudioOn,
			int audioSIndex, Ayah currentAyah) {
		super(id);
		isAudioON=isAnimAudioOn;
		audioSourceIndex=audioSIndex;
		if(currentAyah!=null)
			this.currentAyah=currentAyah;
		else
			this.currentAyah=new Ayah(0,0);
	}



	@Override
	public String toString()
	{
		int k;
		if(isAudioON)k=1;
		else k=0;
		return prefStrings[0]+k
				+"\n"+prefStrings[1]+audioSourceIndex
				+"\n"+prefStrings[2]+currentAyah.toString();
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
		else if(text.startsWith(prefStrings[2])){
			try{
				String[] elements=(text.substring(text.indexOf("=")+1)).split(":");
				int ayahNo=Integer.parseInt(elements[1]);
				int surahNo=Integer.parseInt(elements[0]);
				currentAyah=new Ayah(surahNo-1,ayahNo-1);
			}catch(NumberFormatException | IndexOutOfBoundsException e){}
		}
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

	public Ayah getCurrentAyah(){
		return currentAyah;
	}

	@Override
	public void resetToDefault() {
		isAudioON=true;
		audioSourceIndex=0;
	}
	
	
}

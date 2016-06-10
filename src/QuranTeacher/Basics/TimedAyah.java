package QuranTeacher.Basics;

import java.util.ArrayList;

import QuranTeacher.WordInformation.WordInfoLoader;

public class TimedAyah{
	private Ayah ayah;
	private ArrayList<Integer> wordHitTimes;
	private ArrayList<String> hitStrings;
	private int startIndexOfAyah;
	
	public TimedAyah(Ayah ayah) {
		this.ayah=ayah;
		int indexOfFirstAyah=SurahInformationContainer.totalAyahsUpto[ayah.suraIndex];
		int indexOfSelectedAyah=indexOfFirstAyah+ayah.ayahIndex;
		startIndexOfAyah=WordInfoLoader.getStartIndexOfAyahRTWholeText(indexOfSelectedAyah);
		if(ayah.ayahIndex==-1)
			startIndexOfAyah=0;

		wordHitTimes=new ArrayList<Integer>();
		hitStrings=new ArrayList<String>();
	}
	
	public void addWordHitTime(int hitTime){
		wordHitTimes.add(hitTime);
		hitStrings.add("");
	}
	
	public void addHitLine(int hitTime,String hitString){
		wordHitTimes.add(hitTime);
		hitStrings.add(hitString);
	}
	
	public ArrayList<Integer> getWordHitTimes(){
		return wordHitTimes;
	}
	
	public ArrayList<String> getHitStrings(){
		return hitStrings;
	}
	
	public int getWordHitTime(int index){
		return wordHitTimes.get(index);
	}
	
	public String getHitString(int index){
		return hitStrings.get(index);
	}
	
	/*
	 * returns wordHitTimes(index)+" "+hitStrings(index)
	 */
	public String getEntry(int index){
		String hitString=getHitString(index);
		if(hitString.length()==0){
			return Integer.toString(getWordHitTime(index));
		}
		return Integer.toString(getWordHitTime(index))+" "+hitString;
	}
	
	public Ayah getAyah(){
		return ayah;
	}
	
	public int getTotalAddedWords(){
		return wordHitTimes.size();
	}
	
	public int getBigIndexOfWord(int index){
		return startIndexOfAyah+index;
	}
	
	public void resetWordTimes(){
		wordHitTimes.clear();
		hitStrings.clear();
	}
	
	public void setWordTime(int index,int value){
		wordHitTimes.set(index, value);
	}
	
	public void setHitString(int index,String hitString){
		hitStrings.set(index, hitString);
	}
}

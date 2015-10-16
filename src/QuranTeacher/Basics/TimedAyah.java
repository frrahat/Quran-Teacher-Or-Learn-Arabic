package QuranTeacher.Basics;

import java.util.ArrayList;

import QuranTeacher.WordInformation.WordInfoLoader;

public class TimedAyah{
	private Ayah ayah;
	private ArrayList<Integer> wordHitTimes;
	private int startIndexOfAyah;
	
	public TimedAyah(Ayah ayah) {
		this.ayah=ayah;
		int indexOfFirstAyah=SurahInformationContainer.totalAyahsUpto[ayah.suraIndex];
		int indexOfSelectedAyah=indexOfFirstAyah+ayah.ayahIndex;
		startIndexOfAyah=WordInfoLoader.getStartIndexOfAyahRTWholeText(indexOfSelectedAyah);
		if(ayah.ayahIndex==-1)
			startIndexOfAyah=0;

		wordHitTimes=new ArrayList<>();
	}
	
	public void addWordHitTime(int hitTime){
		wordHitTimes.add(hitTime);
	}
	
	public ArrayList<Integer> getWordHitTimes(){
		return wordHitTimes;
	}
	
	public int getWordHitTime(int index){
		return wordHitTimes.get(index);
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
	}
	
	public void setWordTime(int index,int value){
		wordHitTimes.set(index, value);
	}
}

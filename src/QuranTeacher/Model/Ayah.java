package QuranTeacher.Model;

import QuranTeacher.Model.Ayah;
import QuranTeacher.Model.AyahInformationContainer;
import QuranTeacher.Model.SurahInformationContainer;

/**
 * @author Rahat
 *
 */
public class Ayah{
	public int suraIndex;
	public int ayahIndex;
	
	public Ayah()
	{
		suraIndex=0;
		ayahIndex=0;
	}
	public Ayah(int suraIndex,int ayahIndex)
	{
		if(suraIndex>113){
			suraIndex=113;
		}
		if(ayahIndex>=SurahInformationContainer.totalAyahs[suraIndex]){
			ayahIndex=SurahInformationContainer.totalAyahs[suraIndex]-1;
		}
		this.suraIndex=suraIndex;
		this.ayahIndex=ayahIndex;
	}
	
	public Ayah getNextAyah()
	{
		if(ayahIndex+1<SurahInformationContainer.totalAyahs[suraIndex])
			return new Ayah(suraIndex,ayahIndex+1);
		
		else if(suraIndex<113){
			if(suraIndex!=7)
				return new Ayah(suraIndex+1,-1);//bismillah
			else
				return new Ayah(suraIndex+1,0);
		}
		
		return null;
	}
	
	public Ayah getPrevAyah()
	{
		if(ayahIndex>0 || (suraIndex!=8 && ayahIndex==0))
			return new Ayah(suraIndex,ayahIndex-1);
		
		else if(suraIndex>0)
			return new Ayah(suraIndex-1,SurahInformationContainer.totalAyahs[suraIndex-1]-1);
		
		return null;
	}
	
	@Override
	public String toString()
	{
		return (suraIndex+1)+":"+(ayahIndex+1);
	}
	
	public String toDetailedString(){
		String ayahString;
		ayahString="Surah "+SurahInformationContainer.getSuraInfo(suraIndex).title;
		ayahString+=","+this.toString();
		
		if(isAayatESajdah()){
			ayahString+=" "+AyahInformationContainer.aayatESajdahString;
		}
		
		return ayahString;
	}
	
	public boolean isAayatESajdah(){
		int aayateESazdahs[]=AyahInformationContainer.aayatESajdahs;
		
		for(int i=0;i<aayateESazdahs.length && (suraIndex+1)>=aayateESazdahs[i];i+=2){
			if(suraIndex+1==aayateESazdahs[i] && ayahIndex+1==aayateESazdahs[i+1]){
				return true;
			}
		}
		
		return false;
	}
}

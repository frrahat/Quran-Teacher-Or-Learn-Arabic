/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package QuranTeacher.WordInformation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import QuranTeacher.FilePaths;



public class WordInfoLoader {
	/*
	 * loads word informations such as meaning, grammar etc.
	 */
	private static WordInformation[] infoWords=new WordInformation[77430];//+1
	private static int totalWbWInfos;
	private static int[] startIndexOfSurah=new int[114];
	private static int[] startIndexOfAyahRTWholeText=new int[6238];//+1 (important)
	
	public static boolean isLoaded=false;
	
	public WordInfoLoader()
	{
		
	}
	
	public void load()
	{
		String url=FilePaths.wordByWordFilePath;
		InputStream inStream=this.getClass().getResourceAsStream(url);
		BufferedReader reader=null;
		System.out.println("Loading starts: "+url);
		
		totalWbWInfos=0;
		try
		{
			reader=new BufferedReader(new InputStreamReader(inStream,"utf-8"));
			String text;
			WordInformation tempInfo=null;
			int fieldsCovered=0; 
			String fields[]=new String[7];
			while((text=reader.readLine())!=null)
			{
				if(text.startsWith("#"))
					continue;
				
				else{
					fields[fieldsCovered]=text;
					fieldsCovered++;
					if(fieldsCovered==7){
						infoWords[totalWbWInfos]=
								new WordInformation(Integer.parseInt(fields[0]),//index
										fields[1],//wordId
										fields[2],//transliteration
										fields[3],//meaning
										fields[4].split(","),//parts of speeches
										fields[5].split(","),//segmentColors
										fields[6].split(","));//partsOfSpeechDetails
						totalWbWInfos++;
						
						fieldsCovered=0;
					}
				}
			}
			isLoaded=true;
			reader.close();
			System.out.println(url+" loading success");
		}catch(IOException ie)
		{
			ie.printStackTrace();
		}
		
		
		
		organizeWordInfo();
		System.out.println("total: "+totalWbWInfos);
	}
	
	private void organizeWordInfo()
	{	
		int i;
		int totalSurahs=0;
		int totalAyahs=0;
		for(i=0;i<totalWbWInfos;i++)
		{
			WordId tempId=formatWordId(infoWords[i].wordId);
			if(tempId.ayahNo==1 && tempId.wordNo==1)
			{
				startIndexOfSurah[totalSurahs]=i;
				totalSurahs++;
			}
			if(tempId.wordNo==1)
			{
				startIndexOfAyahRTWholeText[totalAyahs]=i;
				totalAyahs++;
			}
		}
		startIndexOfAyahRTWholeText[totalAyahs]=i;//for advantage, otherwise invalid ayahIndex
	}
	
	private WordId formatWordId(String wordId)
	{
		String withoutBracket=
				wordId.substring(wordId.indexOf('(')+1, wordId.indexOf(')',1));
		
		String[] numbers=withoutBracket.split(":");
		
		int suraNo=Integer.parseInt(numbers[0]);
		int ayahNo=Integer.parseInt(numbers[1]);
		int wordNo=Integer.parseInt(numbers[2]);
		
		return new WordId(suraNo, ayahNo, wordNo);
	}
	
	public static WordInformation getWordInfo(int index){
		return infoWords[index];
	}
	
	public static int getStartIndexOfSurah(int surahIndex){
		return startIndexOfSurah[surahIndex];
	}
	public static int[] getStartIndicesOfSurahs(){
		return startIndexOfSurah;
	}
	
	public static int getStartIndexOfAyahRTWholeText(int ayahIndexRTwholeText){
		return startIndexOfAyahRTWholeText[ayahIndexRTwholeText];
	}

	public static List<WordInformation> getWordInfos(int startIndex, int endIndex) {
		ArrayList<WordInformation>wordInfos=new ArrayList<>();
		for(int i=startIndex;i<=endIndex;i++){
			wordInfos.add(getWordInfo(i));
		}
		return wordInfos;
	}
}

class WordId
{
	int suraNo;
	int ayahNo;
	int wordNo;
	
	public WordId(int i, int j, int k) 
	{
		suraNo=i;
		ayahNo=j;
		wordNo=k;
	}
	
	public String toString()
	{
		return "\nSuraNo "+suraNo
				+"\nAyahNo "+ayahNo
				+"\nWordNo "+wordNo+"\n";
		
	}
}


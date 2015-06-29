/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package QuranTeacher.WordInformation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import QuranTeacher.FilePaths;
import QuranTeacher.Basics.Ayah;
import QuranTeacher.Basics.SurahInformationContainer;
import QuranTeacher.RenderTexts.QuranText;

public class CheckMismatch {

	/*
	 * For checking mismatch between English translated word count and Arabic word count 
	 */
	public static void main(String args) throws IOException {
		new WordInfoLoader().load();
		InputStream in=new FileInputStream(FilePaths.ArabicTextFilePath);
		QuranText quranText=new QuranText(in,true);
		File dir=new File(System.getProperty("user.dir")+"/tests");
		if(!dir.exists())
			dir.mkdir();
			
		FileOutputStream fsStream=new FileOutputStream(dir+"/misMatches.txt");
		BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(fsStream,"utf-8"));
		int totalMismatches=0;
		for(int i=0;i<114;i++)
		{
			for(int j=0;j<SurahInformationContainer.totalAyahs[i];j++)
			{
				Ayah ayah=new Ayah(i, j);
				
				String displayText=quranText.getQuranText(ayah);
				//displayText=new String(displayText.getBytes(Charset.forName("utf-8")));
				setInfoOfWords(ayah);
				int t=getTotalRegularWords(displayText);
				if(t!=infoOfWord.size())
				{
					totalMismatches++;
					//System.out.println(displayText);
					writer.write("\n#"+totalMismatches);
					writer.write("\nsura: "+i+"  ayah: "+j);
					writer.write("\nregWords: "+t+"  infoSize: "+infoOfWord.size());
					writer.write("\n"+displayText+"\n");
					for(int k=infoOfWord.size()-1;k>=0;k--)
						writer.write(infoOfWord.get(k).transLiteration+" ");
					
					writer.write("\n\n");
					
					/*try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}*/
				}
			}
		}
		writer.close();
		System.out.println("completed");
	}


	private static List<WordInformation> infoOfWord;
	
	
	private static void setInfoOfWords(Ayah ayah)
	{
		//index of first ayah of the sura in all ayah sets
		int indexOfFirstAyah=SurahInformationContainer.totalAyahsUpto[ayah.suraIndex];
		int indexOfSelectedAyah=indexOfFirstAyah+ayah.ayahIndex;
		//address in the info list of that selected ayah
		//index of the first word of this ayah
		int indxOfFirstWord=WordInfoLoader.getStartIndexOfAyahRTWholeText(indexOfSelectedAyah);
		//index of the first word of the next ayah
		int indxOfFWNextA=WordInfoLoader.getStartIndexOfAyahRTWholeText(indexOfSelectedAyah+1);
		
		//now listing all the word informations
		List<WordInformation>wordsOfAyah=new ArrayList<>();
		
		for(int i=indxOfFirstWord;i<indxOfFWNextA;i++)
		{
			wordsOfAyah.add(WordInfoLoader.getWordInfo(i));
		}
		
		//saving this to infoWord of animation.java
		infoOfWord=wordsOfAyah;
	}
	
	private static int getTotalRegularWords(String text)
	{
		String[] words=text.split(" ");
		int totalRegWords=words.length;
		for(int i=0;i<words.length;i++)
		{
			int k=words[i].charAt(0);
			if((k>='\u0610' && k<='\u0615') || (k>='\u06D6' && k<='\u06ED'))
			{
				totalRegWords--;
			}
		}
		
		return totalRegWords;
	}
}

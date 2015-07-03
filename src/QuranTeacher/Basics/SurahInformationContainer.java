/**
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */

package QuranTeacher.Basics;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import QuranTeacher.FilePaths;


/**
 * @author Rahat
 *	Edited : 25-06-2015
 */
public class SurahInformationContainer {
	
	private static ArrayList<SurahInformation>surahInformations;
	
	public static int[] totalAyahs = { 7, 286, 200, 176, 120, 165, 206, 75,
			129, 109, 123, 111, 43, 52, 99, 128, 111, 110, 98, 135, 112, 78,
			118, 64, 77, 227, 93, 88, 69, 60, 34, 30, 73, 54, 45, 83, 182, 88,
			75, 85, 54, 53, 89, 59, 37, 35, 38, 29, 18, 45, 60, 49, 62, 55, 78,
			96, 29, 22, 24, 13, 14, 11, 11, 18, 12, 12, 30, 52, 52, 44, 28, 28,
			20, 56, 40, 31, 50, 40, 46, 42, 29, 19, 36, 25, 22, 17, 19, 26, 30,
			20, 15, 21, 11, 8, 8, 19, 5, 8, 8, 11, 11, 8, 3, 9, 5, 4, 7, 3, 6,
			3, 5, 4, 5, 6 };
	
	public static final int totalAyahsUpto[] = { 0, 7, 293, 493, 669, 789, 954,
			1160, 1235, 1364, 1473, 1596, 1707, 1750, 1802, 1901, 2029, 2140,
			2250, 2348, 2483, 2595, 2673, 2791, 2855, 2932, 3159, 3252, 3340,
			3409, 3469, 3503, 3533, 3606, 3660, 3705, 3788, 3970, 4058, 4133,
			4218, 4272, 4325, 4414, 4473, 4510, 4545, 4583, 4612, 4630, 4675,
			4735, 4784, 4846, 4901, 4979, 5075, 5104, 5126, 5150, 5163, 5177,
			5188, 5199, 5217, 5229, 5241, 5271, 5323, 5375, 5419, 5447, 5475,
			5495, 5551, 5591, 5622, 5672, 5712, 5758, 5800, 5829, 5848, 5884,
			5909, 5931, 5948, 5967, 5993, 6023, 6043, 6058, 6079, 6090, 6098,
			6106, 6125, 6130, 6138, 6146, 6157, 6168, 6176, 6179, 6188, 6193,
			6197, 6204, 6207, 6213, 6216, 6221, 6225, 6230, 6236 };
	
	public static void loadAllSurahInfos(){
		InputStream in=SurahInformationContainer.class.getResourceAsStream(FilePaths.surahInfoFilePath);
		surahInformations=new ArrayList<>(114);
		
		if(in!=null)
		{
			BufferedReader reader=null;
			
			try
			{
				reader=new BufferedReader(new InputStreamReader(in,"utf-8"));
				String line;
				int total=0;
				int themeCount=0;
				while((line=reader.readLine())!=null && total<=114)
				{
					if(line.length()<3)
						continue;
					
					switch(line.substring(0, 2))
					{
					
					case "mt":
					
						line=line.substring(3);
						surahInformations.get(total-1).mainTheme[themeCount]=line;
						themeCount++;
						break;
					
					case "id":
						total++;
						themeCount=0;
						line=line.substring(3);
						int id=Integer.parseInt(line);
						
						SurahInformation surahInformation=new SurahInformation(id);
						surahInformations.add(surahInformation);
						
						break;
						
					case "ar":
						line=line.substring(3);
						surahInformations.get(total-1).title=line;
						break;
					
					case "en":
						line=line.substring(3);
						surahInformations.get(total-1).meaning=line;
						break;
					
					case "ac":
						/*line=line.substring(3);
						suraInformations[total].ayahCount=Integer.parseInt(line);*/
						
						//I am getting this from constant storage
						surahInformations.get(total-1).ayahCount=totalAyahs[total-1];
						break;
					
					case "dc":
						line=line.substring(3);
						surahInformations.get(total-1).descent=line;
						break;
					
					case "ro":
						line=line.substring(3);
						surahInformations.get(total-1).revealationOrder=Integer.parseInt(line);
						break;
					
					case "re":
						line=line.substring(3);
						surahInformations.get(total-1).titleReference=line;
						break;
					}
				}
				
				reader.close();
			}catch(Exception e){e.printStackTrace();}
		}
		else{
			System.err.println("SurahInfoContainer: Inputstream is null.");
		}

	}
	
	/*
	 * returns total ayahs before the first ayah of the sura Index
	 */
	
/*	public static int totalAyahsUpto(int surahNo)
	{
		int sum=0;
		for(int i=0;i<surahNo;i++)
			sum+=totalAyahs[i];
		
		return sum;
	}
	
	public static int getTotalAyahsUptoSurah114(){
		if (totalAyahsUptoSurah114==0){
			totalAyahsUptoSurah114=totalAyahsUpto(114);
		}
		
		return totalAyahsUptoSurah114;
	}*/
	
	public static ArrayList<SurahInformation> getSuraInformations(){
		return surahInformations;
	}
	
	public static SurahInformation getSuraInfo(int index){
		return surahInformations.get(index);
	}
}

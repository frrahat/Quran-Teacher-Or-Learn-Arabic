/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package QuranTeacher.RenderTexts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;




import java.util.ArrayList;

import javax.swing.JTextArea;

import QuranTeacher.Basics.Ayah;
import QuranTeacher.Basics.SurahInformationContainer;

public class QuranText {
	/*
	 * loads Quran Arabic Text
	 */
	private String[][] quranText; 
	
	public QuranText(InputStream in, boolean isArabic)// throws Exception 
	{
		if(in!=null)
		{
			BufferedReader reader=null;
			
			try
			{
				reader=new BufferedReader(new InputStreamReader(in,"utf-8"));
				
				String text;
				String firstAyah;
				
				quranText=new String[114][];
				int suraIndex=0;
				int ayahRead=0;
				int ayahCount=SurahInformationContainer.totalAyahs[0];
				quranText[0]=new String[ayahCount];
				
				while((text=reader.readLine())!=null)
				{
					if(isArabic && ayahRead==0)//first ayah
					{
						if(suraIndex!=8)//sura at Tawba
							firstAyah=filterBismillah(text);
						else
							firstAyah=text;
						
						quranText[suraIndex][ayahRead]=firstAyah;
					}
					else
					{
						quranText[suraIndex][ayahRead]=text;
					}
					
					ayahRead++;
					
					if(ayahRead==ayahCount)
					{
						if(suraIndex==113)
							break;
						suraIndex++;
						ayahRead=0;
						ayahCount=SurahInformationContainer.totalAyahs[suraIndex];
						quranText[suraIndex]=new String[ayahCount];
					}
				}
				System.out.println("QuranText , isArabic : "+isArabic+", Reading Success");
				reader.close();
			}
			catch(IOException ie)
			{
				ie.printStackTrace();
			}
		}
		else
		{
			System.out.println("inpustStream is null.");
		}
	}
	
	/**
	 * @return the quranText
	 */
	public String getQuranText(Ayah ayah) {
		return quranText[ayah.suraIndex][ayah.ayahIndex];
	}
	
	public String filterBismillah(String firstAyah) {
		int sp = -1;
		for (int i = 0; i < 4; i++) {
			// pass 4 whitespaces. in sura fatiha has 3 spaces, so ultimate sp=-1
			sp = firstAyah.indexOf(' ', sp + 1);
		}
		return firstAyah.substring(sp + 1);
	}
	
	public ArrayList<Ayah> search(String query){
		//ArrayList<Integer> ranks=new ArrayList<>();
		ArrayList<Ayah> matchedAyahs=new ArrayList<>();
		
		int queryLength=query.length();
		//build table
		
		int Table[]=new int[queryLength];

		//define variables:
	    int pos = 2 ;//(the current position we are computing in T)
	    int cnd = 0 ;//(the zero-based index in W of the next 
	    			//character of the current candidate substring)

	    //(the first few values are fixed but different from what the algorithm 
	    //might suggest)
	    Table[0] = -1;
	    Table[1] = 0;
	    
	    while (pos < queryLength){
	        //(first case: the substring continues)
	        if (query.charAt(pos-1) == query.charAt(cnd)){
	            cnd++;
	        	Table[pos] = cnd;
	        	pos++;
	        }

	        //(second case: it doesn't, but we can fall back)
	        else if (cnd > 0){
	            cnd = Table[cnd];
	        }

	        //(third case: we have run out of candidates.  Note cnd = 0)
	        else{
	            Table[pos] = 0;
	            pos++;
	        }
	    }
	    //return the length of S
	    
	    for(int i=0;i<114;i++){
	    	for(int j=0,k=SurahInformationContainer.totalAyahs[i];j<k;j++){
	    		Ayah ayah=new Ayah(i,j);
	    		if(searchInAyah(ayah, query, Table)){
	    			matchedAyahs.add(ayah);
	    		}
	    	}
	    }
		return matchedAyahs;
	}
	
	private boolean searchInAyah(Ayah ayah, String query, int[] Table){
		
		int queryLength=query.length();
		String S=getQuranText(ayah);
	    //search
	    
	    //define variables:
	    int m = 0;// (the beginning of the current match in S)
	    int i = 0;// (the position of the current character in W)
	        //an array of integers, T (the table, computed elsewhere)

	    while (m + i < S.length()){
	        if (areCharsEqualIgnoreCase(query.charAt(i), S.charAt((m+i)))){
	            if (i == queryLength - 1){
	                return true;
	            }
	            i++;
	        }
	        else{
	            if (Table[i] > -1){
	                m = m + i - Table[i];
	                i = Table[i];
	            }
	            else{
	                i = 0;
	                m++;
	            }
	        }
	    }
	    //(if we reach here, we have searched all of S unsuccessfully)
		return false;
	}
	
	public int searchAndPrint(String query, JTextArea textArea){
		
		int queryLength=query.length();
		//build table
		if(queryLength<2)
			return 0;
		
		int Table[]=new int[queryLength];

		//define variables:
	    int pos = 2 ;//(the current position we are computing in T)
	    int cnd = 0 ;//(the zero-based index in W of the next 
	    			//character of the current candidate substring)

	    //(the first few values are fixed but different from what the algorithm 
	    //might suggest)
	    Table[0] = -1;
	    Table[1] = 0;
	    
	    while (pos < queryLength){
	        //(first case: the substring continues)
	        if (areCharsEqualIgnoreCase(query.charAt(pos-1),query.charAt(cnd))){
	            cnd++;
	        	Table[pos] = cnd;
	        	pos++;
	        }

	        //(second case: it doesn't, but we can fall back)
	        else if (cnd > 0){
	            cnd = Table[cnd];
	        }

	        //(third case: we have run out of candidates.  Note cnd = 0)
	        else{
	            Table[pos] = 0;
	            pos++;
	        }
	    }
	    //return the length of S
	    int count=0;
	    for(int i=0;i<114;i++){
	    	for(int j=0,k=SurahInformationContainer.totalAyahs[i];j<k;j++){
	    		Ayah ayah=new Ayah(i,j);
	    		if(searchInAyah(ayah, query, Table)){
	    			count++;
	    			textArea.append(ayah.toDetailedString()+"\n\t"+getQuranText(ayah));
	    			textArea.append("\n===========================\n\n");
	    		}
	    	}
	    }
	    
	    return count;
	}
	
	private boolean areCharsEqualIgnoreCase(char a, char b){
		if(a<'a')
			a=(char) ('a'+'A'-a);
		
		if(b<'a')
			b=(char) ('a'+'A'-b);
		
		if(a==b)
			return true;
		else
			return false;
	}
	
}

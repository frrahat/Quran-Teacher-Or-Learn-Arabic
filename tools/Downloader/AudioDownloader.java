/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package Downloader;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;


public class AudioDownloader extends Downloader{
	
	int totalAyahRead;

	private String sourceUrl;
	private boolean overwrite;

	private int totalThreads;
	
	private int startSurahIndex;
	private int endSurahIndex;

	private ArrayList<Thread> audioLoadThreads;
	
	static int[] totalAyas = { 7, 286, 200, 176, 120, 165, 206, 75, 129, 109, 123,
			111, 43, 52, 99, 128, 111, 110, 98, 135, 112, 78, 118, 64, 77, 227,
			93, 88, 69, 60, 34, 30, 73, 54, 45, 83, 182, 88, 75, 85, 54, 53,
			89, 59, 37, 35, 38, 29, 18, 45, 60, 49, 62, 55, 78, 96, 29, 22, 24,
			13, 14, 11, 11, 18, 12, 12, 30, 52, 52, 44, 28, 28, 20, 56, 40, 31,
			50, 40, 46, 42, 29, 19, 36, 25, 22, 17, 19, 26, 30, 20, 15, 21, 11,
			8, 8, 19, 5, 8, 8, 11, 11, 8, 3, 9, 5, 4, 7, 3, 6, 3, 5, 4, 5, 6 };
	
	public AudioDownloader(JButton btnStart, JTextArea txtrAudioprogresstext,
			String address,boolean audioOverwriteSelected, JLabel kbLabel, JLabel progressLabel,
			int startSurahIndex, int endSurahIndex) {
		
		super(btnStart, txtrAudioprogresstext, kbLabel, progressLabel);

		sourceUrl=address;
		overwrite=audioOverwriteSelected;

		
		this.startSurahIndex=startSurahIndex;
		this.endSurahIndex=endSurahIndex;
		
		if(sourceUrl.charAt(sourceUrl.length()-1)=='/')
			sourceUrl=sourceUrl.substring(0, sourceUrl.length()-2);
		overwrite=audioOverwriteSelected;
		
		storageFolder=System.getProperty("user.dir")+"/res/QuranAudio";
		itemId="Audio";
		
		totalFilesToDownload=0;
		for(int i=startSurahIndex;i<=endSurahIndex;i++){
			totalFilesToDownload+=totalAyas[i];
		}
		maxValString=Integer.toString(totalFilesToDownload);
		
		totalThreads=Math.min(10,totalFilesToDownload/2);
		
		audioLoadThreads=new ArrayList<>(totalThreads);
		
		File directory=new File(storageFolder);
		if(!directory.exists())
		{
			directory.mkdirs();
			publish("New directory "+directory+" created.");
		}
		else
		{
			publish("Output directory "+directory);
		}
		
		totalAyahRead=0;
	}

	@Override
	protected Boolean doInBackground() throws Exception {
		for (int i = startSurahIndex+1; i <= endSurahIndex+1; i++) 
		{
			audioLoadThreads.clear();
			for(int t=1;t<=totalThreads;t++)
			{
				audioLoadThreads.add(new Thread(new Runner(i, t,totalThreads)));
				audioLoadThreads.get(t-1).start();
			}
			for (int t = 1; t <= totalThreads; t++) 
			{
				audioLoadThreads.get(t-1).join();
			}
		}
		if(totalAyahRead==totalFilesToDownload)
			return true;
		else
			return false;
	}

	
	private String getFileName(int suraIndex, int ayahIndex) {
		String a = "";
		String b = "";

		if (suraIndex < 10)
			a = "00";
		else if (suraIndex < 100)
			a = "0";
		if (ayahIndex < 10)
			b = "00";
		else if (ayahIndex < 100)
			b = "0";

		return a + Integer.toString(suraIndex) + b
				+ Integer.toString(ayahIndex) + ".mp3";
	}

	
	class Runner implements Runnable
	{
		int start;
		int gap;
		private int suraNo;

		public Runner(int suraNo,int start,int gap) {
			this.suraNo=suraNo;
			this.start=start;
			this.gap=gap;
		}
		@Override
		public void run() 
		{
			for(int i=start;i<=totalAyas[suraNo-1];i+=gap)
			{
				if(shouldStop)
				{
					return;
				}
				
				String name=getFileName(suraNo, i);
				
				String storageDir=storageFolder+"/"+Integer.toString(suraNo);
				File outFile = new File(storageDir+"/" + name);
				
				
				String audioUrl=sourceUrl+"/"+name;
				
				if(!overwrite && outFile.exists() && outFile.length()!=0)
				{
					//publish(name+" already exists.");
					increaseAyaRead();
				}
				else
				{
					if(download(audioUrl, outFile))
					{
						//publish(name+" downloaded");
						if(outFile.length()!=0){
							increaseAyaRead();
						}
					}
					else{
						//publish(name +" failed ");
						return;
					}
				}
				
				if(shouldStop)
				{
					return;
				}
				
				try
				{
					Thread.sleep(5);
				}
				catch(InterruptedException ie){}
			
				publish("ppp"+Integer.toString(totalAyahRead));
				setProgress(100*totalAyahRead/totalFilesToDownload);
			}
		}
	}
	
	private synchronized void increaseAyaRead()
	{
		totalAyahRead++;
		notify();
	}
}


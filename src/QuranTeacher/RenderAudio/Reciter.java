/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package QuranTeacher.RenderAudio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;


import QuranTeacher.FilePaths;
import QuranTeacher.Basics.Ayah;
import QuranTeacher.MainWindow.SidePart.AudioNavigationPanel;
import javazoom.jl.decoder.JavaLayerException;

public class Reciter implements Runnable {

	public static String DefaultURL = "http://www.everyayah.com/data/Alafasy_64kbps";

	private static Ayah ayah;
	private static ZPlayer zplayer;
	private static int connectionTimeout=5000;
	
	private Thread t;
	
	
	public Reciter(Ayah ayah) {		
		Reciter.ayah = ayah;
		
		t=new Thread(this,"reciterThread");
		t.start();
	}

	public void reciteAyat(Ayah ayah) throws JavaLayerException,
			IOException {

		FileInputStream Fstream = null;

		String name = getAyatmp3Name(ayah);
		File playFile = new File(FilePaths.audioStorageDir +"/"+ 
				Integer.toString(ayah.suraIndex+1)+"/"+name);

		if (playFile.exists()) 
		{
			try 
			{
				Fstream = new FileInputStream(playFile);
				AudioNavigationPanel.setProgressText("Playing...");

			} catch (Exception e) {e.printStackTrace();}
		}
		else
		{

			AudioNavigationPanel.setProgressText("DownLoading from internet...");
			try {

				if(download(DefaultURL+"/"+name, playFile))
				{
					AudioNavigationPanel.setProgressText("Downloading completed. Playing...");
				}
				else
				{
					AudioNavigationPanel.setProgressText("Downloading Failed.");
					return;
				}
				Fstream = new FileInputStream(playFile);

			}
			catch (Exception ex) 
			{
				ex.printStackTrace();
				AudioNavigationPanel.setProgressText("Downloading Failed.");
				return;
			}
		}

		zplayer = new ZPlayer(Fstream);
	}

	public String getAyatmp3Name(Ayah ayah) {
		String a = "";
		String b = "";

		int suraIndex = ayah.suraIndex + 1;
		int ayahIndex = ayah.ayahIndex + 1;

		if (suraIndex < 10)
			a = "00";
		else if (suraIndex < 100)
			a = "0";
		if (ayahIndex < 10)
			b = "00";
		else if (ayahIndex < 100)
			b = "0";

		String mp3Name = a + String.valueOf(suraIndex) + b
				+ String.valueOf(ayahIndex) + ".mp3";
		return mp3Name;
	}

	@Override
	public void run() {
		try {
			//System.out.println("Running Thread :"+Thread.currentThread().getName());
			reciteAyat(Reciter.ayah);
		} catch (Exception e) {// e.printStackTrace();
			AudioNavigationPanel.setProgressText("Player failed");
		}

	}

	public static boolean isAlive() {
		if(zplayer==null)
			return false;
		return !zplayer.isComplete();
	}

	public boolean download(String audioUrl, File outputFile)
	{
		URL url = null;
		try 
		{
			url = new URL(audioUrl);
		} 
		catch (MalformedURLException e) 
		{
			//e.printStackTrace();
		}
		
		ReadableByteChannel rbc = null;
		try 
		{
			URLConnection connection=url.openConnection();
			connection.setConnectTimeout(connectionTimeout);
			rbc = Channels.newChannel(connection.getInputStream());
			FileOutputStream foStream = new FileOutputStream(outputFile);
			foStream.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			
			foStream.close();
			return true;
		} 
		catch (IOException e) 
		{
			//e.printStackTrace();
		}
		return false;
	}
	
	public static void setDefaultUrl(String url)
	{
		DefaultURL=url;
	}
	
	public static void createSuraWiseDirectoryFor(int surahIndex, String atDirName)
	{
		File directory=new File(atDirName+"/"+
	Integer.toString(surahIndex+1));
		if(!directory.exists())
		{
			directory.mkdirs();
		}
	}
   
   public void pause(){
        zplayer.pause();
    }
    
    public void resume(){
        
        zplayer.resume();
    }
    
    public void stop(){
        
        zplayer.stop();   
    }
    
	private static int getSuraNumFromFileName(String audioFileName){
		int id=0;
		String nameOnly=audioFileName.substring(0, audioFileName.length()-4);
		if(nameOnly.length()!=6)
			return 0;
		
		try{
			id=Integer.parseInt(nameOnly);//check if it contains all integer digits					
			id/=1000;
			return id;
		}catch(NumberFormatException ne){ne.printStackTrace();}
		
		return 0;
	}
    
    public static void manageStorageDirForAudios(){
    	for(int surahIndex=0;surahIndex<114;surahIndex++){
			createSuraWiseDirectoryFor(surahIndex,FilePaths.audioStorageDir);
		}
		
		//organizing mp3 files if they exists out of the respective folders
		File[] files=new File(FilePaths.audioStorageDir).listFiles();
		
		if(files.length<=114)
			return;
		
		for(int i=0;i<files.length;i++){
			if(files[i].isFile()){
				String fileName= files[i].getName();
				if(fileName.endsWith(".mp3")){
					int id=getSuraNumFromFileName(fileName);
					if(id>0){

						//moving the file
						File destFile=new File(FilePaths.audioStorageDir+"/"+
								Integer.toString(id)+"/"+fileName);
						
						FilePaths.move(files[i], destFile);
					}
				}
			}
		}
    }
	
}

/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package QuranTeacher.RenderImages;

import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import QuranTeacher.FilePaths;
import QuranTeacher.MainWindow.SidePart.SelectionPanel;
import QuranTeacher.RenderAudio.Reciter;
import QuranTeacher.WordInformation.WordInfoLoader;

public class ImageLoader 
{
	/*
	 * Loads images from file
	 */
	//public static List<Image>images=new ArrayList<>();
	private final static String ImageFileType=".img";
	private static int[] startIndicesOfSurahs=new int[114];
	private static final int connectionTimeout=3000;
	private static final String parentSource="http://corpus.quran.com/wordimage?";
	//private final int lastImageId=77429;
	
	public ImageLoader(boolean initialize) {
		if(initialize){
			if(!WordInfoLoader.isLoaded){
				System.err.println("Word Info Not Loaded yet");
			}
			else{
				startIndicesOfSurahs=WordInfoLoader.getStartIndicesOfSurahs();
			}
		}
	}
	//this wasn't called
/*	public void load()
	{
		BufferedImage img = null;
		File imageFile=null;
		
		for(int i=1;i<=lastImageId;i++)
		{
			imageFile=new File(defaultDir+"/"+Integer.toString(i)+ImageFileType);
			
			if(imageFile.exists())
			{
				try
				{
					img = ImageIO.read(imageFile);
					images.add(img);
				}
				catch(IOException io)
				{
					//io.printStackTrace();
					System.out.println(imageFile.getName()+" loading failed");
				}
			}
			else
				break;
		}
		
		System.out.println("Images loaded :"+images.size());
	}
	
	public static Image getImage(int imageId)
	{
		if(images.size()>=imageId)
			return images.get(imageId-1);
		return null;
	}*/
	
	private Image getImageFromFile(File imageFile)
	{
		if(imageFile.exists()){
			try
			{
				return ImageIO.read(imageFile);
			}
			catch(IOException io)
			{
				//io.printStackTrace();
				System.out.println(imageFile.getName()+" loading failed");
			}
		}
		
		return null;
	}
	
	public Image[] getImagesSameSurah(int startIndex,
			int endIndex,boolean downloadEnabled){
		
		int totalTobeLoaded=endIndex-startIndex+1;
		Image[] images=new Image[totalTobeLoaded];
		File[] imageFiles=new File[totalTobeLoaded];
		
		//boolean stillDownloadable=downloadEnabled;
		Thread[] imageDownloaderThreads=new Thread[totalTobeLoaded];
		//getImageFiles
		String surahDirString=FilePaths.wbwImageStorageDir+"/"+
				Integer.toString(getSuraNumFromImageIndex(startIndex));
		
		for(int i=0,imageIndex=startIndex;i<totalTobeLoaded;imageIndex++,i++)
		{
			imageFiles[i]=new File(surahDirString+"/"+
					Integer.toString(imageIndex+1)+ImageFileType);
			
			/*if(!imageFiles[i].exists() && stillDownloadable){
				stillDownloadable=download(parentSource+"id="+Integer.toString(imageIndex+1),
						imageFiles[i]);
			}*/
			if(!imageFiles[i].exists() && downloadEnabled){
				
				File outputFile=imageFiles[i];
				imageDownloaderThreads[i]=new Thread(
						new ImageDownloader(parentSource+"id="+Integer.toString(imageIndex+1),
								outputFile));
				imageDownloaderThreads[i].start();
				//System.out.println("satrted for : "+outputFile.getName());
			}
		}
		
		//wait for finish()
		for(int i=0;i<totalTobeLoaded;i++){
			if(imageDownloaderThreads[i]!=null){
				try {
					imageDownloaderThreads[i].join();
				} catch (InterruptedException e) {
				}
			}
		}
		
		//set images
		for(int i=0;i<totalTobeLoaded;i++){
			images[i]=getImageFromFile(imageFiles[i]);
		}
		return images;
	}

	
	public static boolean download(String imageUrl, File outputFile)
	{	
		URL url = null;
		try 
		{
			url = new URL(imageUrl);
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
	
	public void createDir(){
		File directory=new File(FilePaths.wbwImageStorageDir);
		if(!directory.exists())
		{
			directory.mkdirs();
		}
	}
	

	private static int getSuraNumFromImageIndex(int index){
		int mid,low,high;
		low=0;
		high=114;//not 113, (important) 
		
		while(low<high){
			mid=(low+high)/2;
			
			if(mid==low)
				return mid+1;
			
			if(startIndicesOfSurahs[mid]==index)
				return mid+1;
			
			if(index > startIndicesOfSurahs[mid]){
				low=mid;
			}else{
				high=mid;
			}
		}
		
		return 0;
	}
	private int getSuraNumFromFileName(String imageFileName) {
		String nameOnly=imageFileName.substring(0, imageFileName.length()-ImageFileType.length());
		try{
			int wordInfoIndex=Integer.parseInt(nameOnly)-1;
			//or equivalently imageIndex
			return getSuraNumFromImageIndex(wordInfoIndex);
			
		}catch(NumberFormatException ne){}
		return 0;
	}
	
	public void manageStorageDirForImages(){
		for(int surahIndex=0;surahIndex<114;surahIndex++){
			Reciter.createSuraWiseDirectoryFor(surahIndex, FilePaths.wbwImageStorageDir);
		}
		
		//organizing mp3 files if they exists out of the respective folders
		File[] files=new File(FilePaths.wbwImageStorageDir).listFiles();
		
		if(files.length<=114)
			return;
		
		for(int i=0;i<files.length;i++){
			if(files[i].isFile()){
				String fileName= files[i].getName();
				if(fileName.endsWith(ImageFileType)){		
					int id=getSuraNumFromFileName(fileName);
					if(id>0){
						File destFile=new File(FilePaths.wbwImageStorageDir+"/"+
								Integer.toString(id)+"/"+fileName);
						
						FilePaths.move(files[i], destFile);
					}
				}
			}
		}
	}
	
	
	class ImageDownloader implements Runnable{
		File outputFile;
		String imgUrl;
		public ImageDownloader(String imgUrl,File outputFile){
			this.imgUrl=imgUrl;
			this.outputFile=outputFile;
		}
		public void run() {
			download(imgUrl, outputFile);
		}
	}
	
}

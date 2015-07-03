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
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class ImageDownloader extends Downloader{

	private boolean overwrite;
	public static String parentSource;
	private static String imageFileType=".img";
	//from WordInfoLoader.startIndexOfSurah
	private final int[] startIndicesOfSurahs = { 0, 29, 6145, 9626, 13373,
			16177, 19227, 22547, 23780, 26278, 28111, 30028, 31805, 32658,
			33488, 34143, 35987, 37543, 39122, 40083, 41418, 42587, 43861,
			44911, 46227, 47120, 48438, 49589, 51019, 51995, 52812, 53358,
			53730, 55017, 55900, 56675, 57400, 58260, 58993, 60165, 61384,
			62178, 63038, 63868, 64214, 64702, 65345, 65884, 66444, 66791,
			67164, 67524, 67836, 68196, 68538, 68889, 69268, 69842, 70314,
			70759, 71107, 71328, 71503, 71683, 71924, 72211, 72460, 72793,
			73093, 73351, 73568, 73794, 74079, 74278, 74533, 74697, 74940,
			75121, 75294, 75473, 75606, 75710, 75790, 75959, 76066, 76175,
			76236, 76308, 76400, 76537, 76619, 76673, 76744, 76784, 76811,
			76845, 76917, 76947, 77041, 77077, 77117, 77153, 77181, 77195,
			77228, 77251, 77268, 77293, 77303, 77329, 77348, 77371, 77386,
			77409 };
	
	private List<Thread>imageLoadThreads;
	private int totalThreads;
	
	private int totalCompleted;
	
	
	
	public ImageDownloader(JButton btnStart_1,JTextArea txtrImageprogresstext,
			boolean imageOverwriteSelected, JLabel kbLabel, JLabel progressLabel,
			int startSurahIndex,int endSurahIndex) {
		
		super(btnStart_1, txtrImageprogresstext, kbLabel, progressLabel);
		
		parentSource="http://corpus.quran.com/wordimage?";
		overwrite=imageOverwriteSelected;
		
		storageFolder=System.getProperty("user.dir")+"/res/WbWImages";
		itemId="Image";

		int startId=startIndicesOfSurahs[startSurahIndex]+1;
		int endId=0;
		if(endSurahIndex==113){
			endId=77429;
		}else{
			endId=startIndicesOfSurahs[endSurahIndex+1];
		}
		
		totalFilesToDownload=endId-startId+1;;
		maxValString=Integer.toString(totalFilesToDownload);

		
		//int endId;==maxVal
		totalThreads=Math.min(100,totalFilesToDownload/2);
		imageLoadThreads=new ArrayList<>(totalThreads);
		
		
		for(int i=0,sId=startId;i<totalThreads;i++)
		{
			imageLoadThreads.add(new Thread(new Runner(sId+i, totalThreads,endId)));	
		}
		
		totalCompleted=0;
		shouldStop=false;
		
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
	}

	@Override
	protected Boolean doInBackground() throws Exception {
		for(int i=0;i<totalThreads;i++)
		{
			imageLoadThreads.get(i).start();	
		}
		
		for(int i=0;i<totalThreads;i++)
		{
			imageLoadThreads.get(i).join();
		}
		if(totalCompleted==totalFilesToDownload)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/*private String getFileName(String imageUrl)
	{
		return imageUrl.split("=")[1]+".png";
	}*/
	
	class Runner implements Runnable
	{
		int startId;
		int gap;
		int endId;

		public Runner(int start,int gap,int endId) {
			this.startId=start;
			this.gap=gap;
			this.endId=endId;
		}
		@Override
		public void run() 
		{
			for(int i=startId;i<=endId;i+=gap)
			{
				//System.out.println(Thread.currentThread().getName());
				if(shouldStop)
				{
					//System.out.println(isCancelled());
					return;
				}
				
				int imageIndex=i-1;
				String outId=Integer.toString(i);
				String outName=outId+imageFileType;
				File outFile=new File(storageFolder+"/"+
						Integer.toString(getSuraNumFromImageIndex(imageIndex))+"/"+
						outName);
				//System.out.println(outFile.getName()+" "+parentSource+"id="+outId);
				if(!overwrite && outFile.exists())
				{
					//publish(outName+" already exists.");
					incrreaseCompleted();
				}
				else
				{
					if(download(parentSource+"id="+outId, outFile))
					{
						//publish(outName+" downloaded");
						incrreaseCompleted();
					}
					else
						return;
				}
				
				if(shouldStop)
				{
					//System.out.println(isCancelled());
					return;
				}
				//sleep
				try
				{
					Thread.sleep(5);
				}
				catch(InterruptedException ie){ie.printStackTrace();}
				
				publish("ppp"+Integer.toString(totalCompleted));
				setProgress(100*totalCompleted/totalFilesToDownload);
			}
		}
		
	}

	private synchronized void incrreaseCompleted()
	{
		totalCompleted++;
		notify();
	}
	
	private int getSuraNumFromImageIndex(int index){
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
}

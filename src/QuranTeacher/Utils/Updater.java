package QuranTeacher.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import QuranTeacher.FilePaths;
import QuranTeacher.Interfaces.UpdateActivityReturnListener;
import QuranTeacher.MainWindow.MainFrame;

/**
 * @author Rahat
 * 27-06-15
 */
public class Updater {
	public final String DefaultURLString = "https://raw.githubusercontent.com/frrahat/"
			+ "Quran-Teacher-Or-Learn-Arabic/master/src/QuranTeacher/QT_new_update_info.txt";
	private int connectionTimeout=5000;
	private UpdateActivityReturnListener updateActivityReturnListener;
	
	public void startUpdateActivity(){
		
		Thread infoDownloader=new Thread(new Runnable() {
			
			@Override
			public void run() {
				VersionInfo newVersionInfo=null;
				boolean wasDownloadSuccess=false;
				
				File writingFile=new File(FilePaths.updateInfoStorageFile);
				
				wasDownloadSuccess=download(DefaultURLString,writingFile);
				
				if( !wasDownloadSuccess && !writingFile.exists()){
					return;
				}
					
				BufferedReader reader=null;
				String[] text=new String[50];
				int line=0;
				try {
					reader= new BufferedReader(new FileReader(writingFile));
					while(line<50 && (text[line]=reader.readLine())!=null){
						line++;
					}
				} catch (IOException e ) {
					//e.printStackTrace();
				}
				
				if(line!=0){
					VersionInfo fetchedVersionInfo=new VersionInfo(text,line);
					if(fetchedVersionInfo.isNewerThan(MainFrame.version)){
						newVersionInfo=fetchedVersionInfo;
					}
				}
				
				updateActivityReturnListener.nextToDo(wasDownloadSuccess,newVersionInfo);
			}
		});
		
		infoDownloader.start();
	}
	
	
	private boolean download(String urlString, File outputFile)
	{
		URL url = null;
		try 
		{
			url = new URL(urlString);
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
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
		} 
		catch (IOException e) 
		{
			//e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void setUpdateActivityReturnListener(UpdateActivityReturnListener updateActivityReturnListener2){
		this.updateActivityReturnListener=updateActivityReturnListener2;
	}

}

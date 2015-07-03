/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package Downloader;

import java.awt.HeadlessException;
import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;


public class Downloader extends SwingWorker<Boolean, String> {

	private JButton btnControl;
	private JTextArea progressTextArea;
	private JLabel kbDownloadedLabel;
	private JLabel progressLabel;
	
	protected String storageFolder;
	protected String itemId;
	protected int totalFilesToDownload;
	protected String maxValString;
	
	private long downloadSize;
	private String problemString;
	protected int connectionTimeout;
	
	protected boolean shouldStop;

	public Downloader(JButton btnStart, JTextArea txtrprogresstext,
	JLabel kbLabel, JLabel progressLabel) {
		
		this.btnControl = btnStart;
		this.progressTextArea = txtrprogresstext;
		this.kbDownloadedLabel = kbLabel;
		this.progressLabel=progressLabel;
		
		problemString="Undefined Problem.";
		connectionTimeout=30000;
		shouldStop=false;
	}

	@Override
	protected Boolean doInBackground() throws Exception {
		return null;
	}
	
	protected void process(List<String>publishedVals)
	{
		for(int i=0;i<publishedVals.size();i++)
		{
			String text=publishedVals.get(i);
			if(text.startsWith("sss"))
			{
				kbDownloadedLabel.setText(text.substring(3)+" KB Downloaded");
			}
			else if(text.startsWith("ppp"))
			{
				progressLabel.setText(text.substring(3)+"/"+maxValString+" Files Completed.");
			}
			else
				progressTextArea.append(text+"\n");
		}
	}
	
	protected void done()
	{
		btnControl.setText("Start");
		publish("\nStopped\n");
		try {
			if(get())
			{
				JOptionPane.showMessageDialog
				(null,itemId+" Download completed", "Done",
					JOptionPane.INFORMATION_MESSAGE,null);
			}
			else
			{
				publish("\n\nA problem occured.");
	        	publish(problemString);
	        	//e.printStackTrace();
	        	publish("\nIt is probable that, it had been stopped manually or your internet connection has been disconnected");
	        	publish("\nTo avoid redownload untick 'Overwrite Old "+itemId+" Files' if it was ticked.");
	        	publish("Then Start again.");
	        	
	        	JOptionPane.showMessageDialog(null, itemId+" Download Failed","Failure",JOptionPane.ERROR_MESSAGE);
			}
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	protected boolean download(String fileUrl, File outputFile)
	{
		if(shouldStop)
			return false;
		
        //Open a URL Stream
		URL url = null;
		try 
		{
			url = new URL(fileUrl);
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
			return false;
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
			
			increaseDwnldSize(outputFile.length()/1000);
	        publish("sss"+Long.toString(downloadSize));
	        return true;
		} 
		
		
        catch(Exception e)
        {
        	//e.printStackTrace();
        	/*publish("\n\nA problem occured.");
        	publish(e.toString());
        	e.printStackTrace();
        	publish("\nIt is probable that, your internet connection has been disconnected");
        	publish("\nTo avoid redownload untick 'Overwrite Old "+itemId+" Files' if it was ticked.");
        	publish("Then Start again.");
        	
        	//JOptionPane.showMessageDialog(null, itemId+" Download Failed","Failure",JOptionPane.ERROR_MESSAGE);
        	return false;*/
        	problemString=e.toString();
        	return false;
        }
	}
	
	public void stop()
	{
		if(shouldStop)
			return;
		
		publish("\nTrying to stop...\nWait for at most "+Integer.toString(connectionTimeout/1000) +" seconds.");
		shouldStop=true;
		//notifyAll();
	}
	
	private synchronized void increaseDwnldSize(Long size)
	{
		downloadSize+=size;
		notify();
	}
}

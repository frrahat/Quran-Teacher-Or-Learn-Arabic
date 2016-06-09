/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package QuranTeacher.Preferences;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import QuranTeacher.FilePaths;


public abstract class Preferences {
	
	protected String id;
	protected Font font;
	protected Color backGroundColor;
	protected Color foreGroundColor;
	
	public static final String storageFolder=FilePaths.preferencesStorageDir;
	private String storageFileName;
	
	protected final String fontPartsSeparator="|";
	protected Font defaultFont;

	
	public Preferences(String id)
	{
		this.id=id;
		
		//System.out.println("preferences called from ="+ id);
	}
	
	public Preferences(String id, boolean fontsNotInitialized)
	{
		this.id=id;
		
		//System.out.println("preferences called from ="+ id);
		loadNecessaryFonts();
	}

	private void loadNecessaryFonts() {
		MyFontsContainer.addFont(FilePaths.tahomaFontPath);
		MyFontsContainer.addFont(FilePaths.me_quranFontPath);
		MyFontsContainer.addFont(FilePaths.solaimanLipiFontPath);
		MyFontsContainer.refresh();
	}
	
	public Font getFont() {
		return font;
	}
	public void setFont(Font font)
	{
		this.font=font;
	}

	public Color getBackGroundColor() {
		return backGroundColor;
	}
	public void setBackGroundColor(Color backGroundColor) {
		this.backGroundColor = backGroundColor;
	}
	public Color getForeGroundColor() {
		return foreGroundColor;
	}
	public void setForeGroundColor(Color foreGroundColor) {
		this.foreGroundColor = foreGroundColor;
	}
	
	public String getId()
	{
		return id;
	}
	
	public abstract void resetToDefault();
	public abstract void checkAndGrab(String text);
	
	public boolean setPrefFromFile() {
		
		storageFileName=storageFolder+"/"+id;
		BufferedReader reader=null;
    	try {
    		reader=new BufferedReader(new FileReader(storageFileName));
    		String text;
    		while((text=reader.readLine())!=null)
    		{
    			checkAndGrab(text);
    		}
    		
    		//System.out.println("pref set for "+id);
    		return true;
    		
    	} catch (IOException ex) {
    		ex.printStackTrace();
    		//System.out.println(ex.getMessage());
    		return false;
        } finally
        {
        	try
        	{
        		if(reader!=null)
        			reader.close();
        	}catch(IOException io)
        	{
        		io.printStackTrace();
        	}
        }
    }
	
	public void savePrefToFile() 
	{
		storageFileName=storageFolder+"/"+id;
		PrintWriter writer=null;
		try {
			
			writer = new PrintWriter(storageFileName);
			writer.write(toString());
		
		} catch (IOException io) {
			//io.printStackTrace();
			System.out.println(io.getMessage());
		} finally
		{
			if(writer!=null)
				writer.close();
		}
	}
	
	protected Font readFont(String text)
	{
		String[] parts=text.split("\\"+fontPartsSeparator);
		try
		{
			return new Font(parts[0],Integer.parseInt(parts[1]),Integer.parseInt(parts[2]));
		}catch(Exception e)
		{
			System.err.println("font returned null");
			return defaultFont;
		}
	}
}

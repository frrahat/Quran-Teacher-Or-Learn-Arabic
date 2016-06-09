/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package QuranTeacher.Preferences;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import QuranTeacher.FilePaths;

public class MyFontsContainer {
	
	private static GraphicsEnvironment env;
	private static ArrayList<Font> myFontList;
	private static int[] fontStyles={
			Font.PLAIN,
			Font.BOLD,
			Font.ITALIC};
	
	private static boolean initialized=false;
	
	private static String[] fontStyleNames={
		"PLAIN",
		"BOLD",
		"ITALIC"};
	
	private static void init()
	{
		env=GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font[] fonts=env.getAllFonts();
		initialized=true;
		myFontList=new ArrayList<>();
		for(int i=0;i<fonts.length;i++){
			myFontList.add(fonts[i]);
		}
		int addedFiles=MyFontsContainer.loadAdditionalFonts();
        if(addedFiles!=0){
			System.out.println(addedFiles+" font file(s) added.");
        }
	}
	
	public static void refresh()
	{
		Font[] fonts=env.getAllFonts();
		myFontList=new ArrayList<>();
		for(int i=0;i<fonts.length;i++){
			myFontList.add(fonts[i]);
		}
	}

	public static ArrayList<Font> getMyFontList(){
		if(!initialized)
			init();
		return myFontList;
	}
	
	public static Font getMyFont(int index){
		if(!initialized)
			init();
		return myFontList.get(index);
	}
	/*public static Font[] getFonts() {
		if(!initialized)
			init();
		return fonts;
	}
	
	public static String[] getFontNames()
	{
		if(!initialized)
			init();
		
		if(fontNames==null){
			fontNames = new String[fonts.length];
			for (int i = 0; i < fonts.length; i++)
				fontNames[i] = fonts[i].getName();
		}

		return fontNames;
	}*/

	public static int[] getFontStyles() {
		return fontStyles;
	}


	public static String[] getFontStyleNames() {
		return fontStyleNames;
	}
	
	public static int getFontIndex(String fontName)
	{
		if(!initialized)
			init();
		//System.out.println(fontName);
		for(int i=0;i<myFontList.size();i++)
			if(fontName.equals(myFontList.get(i).getName()))
				return i;
		return 0;
	}
	
	public static int getFontStyleIndex(int style)
	{
		for(int i=0;i<fontStyles.length;i++)
			if(style==fontStyles[i])
				return i;
		return 0;
	}
	
	public static void addFont(String internalPath)
	{
		//int k=Integer.parseInt("foo");
		if(!initialized)
			init();
		InputStream is=MyFontsContainer.class.getResourceAsStream(internalPath);
		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, is);
			env.registerFont(font);
			System.out.println("Font created :"+font.getName());
			
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private static boolean addFontFile(File fontFile){
		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
			env.registerFont(font);
			System.out.println("Font created :"+font.getName());
			return true;
			
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static int loadAdditionalFonts(){
		int addedFiles=0;
		if(!initialized)
			init();
		File storageFolder=new File(FilePaths.additionalFontsDir);
		if(!storageFolder.exists()){
			storageFolder.mkdirs();
		}
		else{
			File[] files=storageFolder.listFiles();
			for(int i=0;i<files.length;i++){
				if(files[i].getName().endsWith(".ttf")){
					if(addFontFile(files[i])){
						addedFiles++;
					}
				}
			}
		}
		return addedFiles;
	}
	
	
}

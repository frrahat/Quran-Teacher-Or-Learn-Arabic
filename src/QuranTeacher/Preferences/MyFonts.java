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
import java.io.IOException;
import java.io.InputStream;

public class MyFonts {
	
	private static GraphicsEnvironment env;
	private static Font[] fonts;
	private static String[] fontNames;
	private static int[] fontStyles={
			Font.PLAIN,
			Font.BOLD,
			Font.ITALIC};
	
	private static String[] fontStyleNames={
		"PLAIN",
		"BOLD",
		"ITALIC"};
		
	
	public MyFonts()
	{
		init();
	}
	
	public static void init()
	{
		env=GraphicsEnvironment.getLocalGraphicsEnvironment();
		fonts=env.getAllFonts();
		fontNames=getFontNames(false);
	}
	
	public static void refresh()
	{
		fonts=env.getAllFonts();
		fontNames=getFontNames(false);
	}

	public static Font[] getFonts() {
		return fonts;
	}


	public static String[] getFontNames() 
	{
		return fontNames;
	}
	
	public static String[] getFontNames(boolean fontNamesInitialized)
	{
		if(!fontNamesInitialized)
		{
			fontNames=new String[fonts.length];
			for(int i=0;i<fonts.length;i++)
				fontNames[i]=fonts[i].getName();
		}
		return fontNames;
	}

	public static int[] getFontStyles() {
		return fontStyles;
	}


	public static String[] getFontStyleNames() {
		return fontStyleNames;
	}
	
	public static int getFontIndex(String fontName)
	{
		//System.out.println(fontName);
		for(int i=0;i<fontNames.length;i++)
			if(fontName.equals(fontNames[i]))
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
	
	public static void addFont(String path)
	{
		InputStream is=MyFonts.class.getResourceAsStream(path);
		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, is);
			env.registerFont(font);
			System.out.println("Font created :"+font.getName());
			
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}
}

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
	
	private static boolean initialized=false;
	
	private static String[] fontStyleNames={
		"PLAIN",
		"BOLD",
		"ITALIC"};
	
	private static void init()
	{
		env=GraphicsEnvironment.getLocalGraphicsEnvironment();
		fonts=env.getAllFonts();
		initialized=true;
		
		fontNames=getFontNames();
	}
	
	public static void refresh()
	{
		fonts=env.getAllFonts();
		fontNames=null;
		fontNames=getFontNames();
	}

	public static Font[] getFonts() {
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
	}

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
		//int k=Integer.parseInt("foo");
		if(!initialized)
			init();
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

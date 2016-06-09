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

public class MyColorsContainer {
	private static Color[] colors={
			Color.BLACK,
			Color.WHITE,
			Color.CYAN,
			Color.DARK_GRAY,
			Color.GRAY,
			Color.GREEN,
			Color.LIGHT_GRAY,
			Color.MAGENTA,
			Color.ORANGE,
			Color.PINK,
			Color.YELLOW,
			Color.RED};
	
	
	private static String[] colorNames={
			"Black",
			"White",
			"Cyan",
			"Dark Gray",
			"Gray",
			"Green",
			"Light Gray",
			"Magenta",
			"Orange",
			"Pink",
			"Yellow",
			"Red"};
	
	public MyColorsContainer()
	{
		
	}
	
	public static Color[] getColors()
	{
		return colors;
	}
	
	public static String[] getColorNames()
	{
		return colorNames;
	}
	
	public static String getColorName(Color color)
	{
		return colorNames[getColorIndex(color)];
	}
	
	public static Color getColor(String colorName)
	{
		return colors[getColorIndex(colorName)];
	}
	
	public static int getColorIndex(Color color)
	{
		for(int i=0;i<colors.length;i++)
			if(color.equals(colors[i]))
				return i;
		
		return 0;
	}
	
	public static int getColorIndex(String colorName)
	{
		for(int i=0;i<colorNames.length;i++)
			if(colorName.equals(colorNames[i]))
				return i;
		
		return 0;
	}
}

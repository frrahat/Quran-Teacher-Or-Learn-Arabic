/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package QuranTeacher.WordInformation;

import java.awt.Color;

public class SegmentColors {
	/*
	 * Colors to highlight arabic word segments (colored by corpus.quran.com)
	 */
	public static String[] colorNames={
		"segGray", 
		"segSky", 
		"segBlue", 
		"segPurple", 
		"segBrown", 
		"segMetal", 
		"segGold",
		"segSeagreen",
		"segGreen", 
		"segRed", 
		"segRust", 
		"segPink", 
		"segRose", 
		"segNavy", 
		"segOrange", 
		"segSilver"
	};
	
	public static Color[] Colors=
		{ 
		new Color(87, 87, 87),
		new Color(84, 141, 212),
		new Color(37, 126, 156),
	new Color(129, 38, 192),
	new Color(191, 159, 62),
	new Color(92, 112, 133),
	new Color(129, 116, 24),
	new Color(50, 189, 47),
	new Color(29, 105, 20),
	new Color(244, 64, 11),
	new Color(173, 35, 35),
	new Color(168, 1, 123),
	new Color(253, 81, 98),
	new Color(19, 1, 184),
	new Color(227, 112, 16),
	new Color(180, 180, 180)
	};

	public static Color getColor(String colorName)
	{
		for(int i=0;i<colorNames.length;i++)
		{
			if(colorNames[i].equals(colorName))
				return Colors[i];
		}
		return null;
	}
}

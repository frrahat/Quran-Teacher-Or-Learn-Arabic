/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package QuranTeacher.RenderAnimation;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

public class FocusCheckRunnable implements Runnable
{
	/*
	 * For checking mouse focus on any word appeared in the display
	 */
	
	static List<Rectangle>rectangles=Animation.rectangles;
	Point cursor;
	
	public FocusCheckRunnable(Point p) 
	{
		cursor=p;
	}

	@Override
	public void run() 
	{
		//System.out.println(Thread.currentThread().getName());
		for(int i=0;i<rectangles.size();i++)
		{
			if(rectangles.get(i).contains(cursor))
			{
				Animation.mouseFocusedOn=i;
				return;
			}
		}
		Animation.mouseFocusedOn=-1;
	}
}

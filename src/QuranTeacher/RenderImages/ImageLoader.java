/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package QuranTeacher.RenderImages;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import QuranTeacher.FilePaths;

public class ImageLoader 
{
	/*
	 * Loads images from file
	 */
	//public static List<Image>images=new ArrayList<>();
	public static String ImageFileType=".img";
	//private final int lastImageId=77429;
	
	//this wasn't called
/*	public void load()
	{
		BufferedImage img = null;
		File imageFile=null;
		
		for(int i=1;i<=lastImageId;i++)
		{
			imageFile=new File(defaultDir+"/"+Integer.toString(i)+ImageFileType);
			
			if(imageFile.exists())
			{
				try
				{
					img = ImageIO.read(imageFile);
					images.add(img);
				}
				catch(IOException io)
				{
					//io.printStackTrace();
					System.out.println(imageFile.getName()+" loading failed");
				}
			}
			else
				break;
		}
		
		System.out.println("Images loaded :"+images.size());
	}
	
	public static Image getImage(int imageId)
	{
		if(images.size()>=imageId)
			return images.get(imageId-1);
		return null;
	}*/
	
	public static Image getImageFromFile(int imageIndex)
	{
		BufferedImage img = null;
		File imageFile=null;
		
		imageFile=new File(FilePaths.wbwImageStorageDir+"/"+Integer.toString(imageIndex+1)+ImageFileType);
		
		if(imageFile.exists())
		{
			try
			{
				img = ImageIO.read(imageFile);
				//images.add(img);
			}
			catch(IOException io)
			{
				//io.printStackTrace();
				System.out.println(imageFile.getName()+" loading failed");
			}
		}
		return img;
	}
	
	public static void createDir(){
		File directory=new File(FilePaths.wbwImageStorageDir);
		if(!directory.exists())
		{
			directory.mkdirs();
		}
	}
}

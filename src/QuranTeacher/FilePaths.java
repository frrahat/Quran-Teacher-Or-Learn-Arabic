package QuranTeacher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FilePaths {
	public static final String ArabicTextFilePath="/QuranTeacher/files/texts/quran-uthmani.txt";
	public static final String EnglishTextFilePathYusuf="/QuranTeacher/files/texts/en.yusufali.txt";
	public static final String EnglishTextFilePathSahih="/QuranTeacher/files/texts/en.sahih.txt";
	public static final String BengaliTextFilePath="/QuranTeacher/files/texts/bn.bengali.txt";
	
	
	public static final String surahInfoFilePath="/QuranTeacher/files/texts/sura-information.txt";
	public static final String wordByWordFilePath="/QuranTeacher/files/texts/WbWInfo.txt";
	public static final String audioLinksFile="/QuranTeacher/files/texts/AudioLinks";
	public static final String tahomaFontPath = "/QuranTeacher/files/fonts/Tahoma.ttf";
	public static final String me_quranFontPath = "/QuranTeacher/files/fonts/me_quran_volt_mark2.ttf";
	public static final String solaimanLipiFontPath = "/QuranTeacher/files/fonts/SolaimanLipi_22-02-2012.ttf";
	
	public static final String audioStorageDir= System.getProperty("user.dir")+ "/res/QuranAudio";
	public static final String wbwImageStorageDir = System.getProperty("user.dir")+"/res/WbWImages";
	public static final String preferencesStorageDir = System.getProperty("user.dir")+"/.preferences";
	
	public static final String additionalTextsDir= System.getProperty("user.dir")+ "/res/Additional Texts";
	public static final String additionalFontsDir= System.getProperty("user.dir")+ "/res/Additional Fonts";
	
	public static final String updateInfoStorageFile= System.getProperty("user.dir")+ "/.preferences/updateInfo";
	
	public static final String hitFileDirName= System.getProperty("user.dir")+"/res/hitFiles";
	
	public static boolean move(File srcFile,File destFile){
		//moving the file
		Path src=srcFile.toPath();
		Path dest=destFile.toPath();
		
		try {
			Files.move(src, dest);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}

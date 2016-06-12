package QuranTeacher.Model;

public class SurahInformation {
	public int id;
	public String title;
	public String meaning;
	public int ayahCount;
	public String descent;
	public int revealationOrder;
	public String titleReference;
	public String[] mainTheme;
	
	public SurahInformation(int id,String title,String meaning,int ayahCount)
	{
		this.id=id;
		this.title=title;
		this.meaning=meaning;
		this.ayahCount=ayahCount;
	}

	public SurahInformation(int id) {
		this.id=id;
		this.mainTheme=new String[10];
	}
}

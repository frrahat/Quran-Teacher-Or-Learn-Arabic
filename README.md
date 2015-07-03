# QuranTeacher or Learn Arabic
Quran Teacher or Learn Arabic is a computer program that shows animation of every word of an ayah with its translation and grammatical information.

This was intended to be used for learning the translations of 'Quranic words' And thus understanding the Quran and at the same time accelerating learning arabic.

All word by word transliteration, meaning, images and grammatical descriptions were collected from "http://corpus.quran.com/wordbyword.jsp" which is "an annotated linguistic resource which shows the Arabic grammar, syntax and morphology for each word in the Holy Quran."

The Quran text is Uthmani Text and has been collceted from tanzil.net which follows The Medina Mushaf (officially: Mushaf al-Madinah an-Nabawiyyah, Arabic: مصحف المدينة النبوية) is an authentic copy of the holy quran printed by King Fahad Complex for Printing of the Holy Quran).

English translation text is from Yusuf Ali translation.
Bengali translation text is from Muhiuddin Khan.

The audio recitations are from everyayah.com.

Meaning of the title of sura, themes were collected from wikipedia.org and then they were revised (Some Bibilic words were modified ie. Abraham has been changed to Ibraheem (PBUH) etc.) before they were implemented.

For the developers:
================
Programming language was : Java.

Each word taken from the Quran Text file was matched by index with the information parts taken from corpus.quran.com. There were no OCR (Optical Charecter Recognition) or high level algorithms used. There has been found mismatch only at sura no. 37, ayah no. 130 (with programming approach). Here إِلْ يَاسِينَ are two words separated by a space in Quran Text, But regarded as a single word in corpus.quran.com website. As, changing one of them will be regarded as breaking license agreement, this problem has been left unsolved. And it will be revised again, only when any one of the source makes an update to the words.

Required libraries for building for your own:
javax.swing,
jsoup and
javazoom.

/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package QuranTeacher.MainWindow.SidePart;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;




import java.awt.GridLayout;

import javax.swing.JTable;

import java.awt.BorderLayout;

import javax.swing.JTextArea;

import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import java.awt.Color;

import javax.swing.UIManager;

import QuranTeacher.Basics.SurahInformation;
import QuranTeacher.Basics.SurahInformationContainer;

public class InformationPanel extends JPanel {
	/**
	 * Sura information such as ayah count, revelation order, title, meaning etc.
	 */
	private static final long serialVersionUID = 1L;

	private JTable table;

	/**
	 * Create the panel.
	 */
	private String[][] rowData={{"foo",null}};
	private final String[] columnNames={"Name","Value"};
	private JTextArea textArea;
	
	private boolean infoIsSet;
	
	public InformationPanel() {
		setForeground(Color.WHITE);
		setBackground(Color.DARK_GRAY);
		setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Information", TitledBorder.LEADING, TitledBorder.TOP, null, Color.GREEN));
		setLayout(new GridLayout(0, 1, 0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setForeground(Color.RED);
		tabbedPane.setBackground(Color.LIGHT_GRAY);
		tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 18));
		add(tabbedPane);
		
		JPanel tablePanel=new JPanel();
		JPanel themePanel=new JPanel();
		
		tabbedPane.addTab("Basic Info", tablePanel);
		tablePanel.setLayout(new BorderLayout(0, 0));
		
		textArea = new JTextArea();
		textArea.setForeground(Color.ORANGE);
		textArea.setBackground(Color.DARK_GRAY);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		setRowNames();
		table = new JTable(rowData,columnNames);
		table.setBackground(Color.DARK_GRAY);
		table.setForeground(Color.ORANGE);
		//setInfo(0);
		
		table.setEnabled(false);
		table.setRowSelectionAllowed(false);
		table.setRowHeight(30);
		table.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tablePanel.add(table);
		tabbedPane.addTab("Theme",themePanel);
		themePanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		themePanel.add(scrollPane, BorderLayout.CENTER);
		scrollPane.setViewportView(textArea);
		
		infoIsSet=false;
	}
	
	
	public void setInfo(int suraIndex)
	{
		//"Number","Name","Meaning","Aya Count","Descent","Revealation order","Title refers to"
		SurahInformation info=SurahInformationContainer.getSuraInfo(suraIndex);
		rowData[0][1]=Integer.toString(info.id);
		rowData[1][1]=info.title;
		rowData[2][1]=info.meaning;
		rowData[3][1]=Integer.toString(info.ayahCount);
		rowData[4][1]=info.descent;
		rowData[5][1]=Integer.toString(info.revealationOrder);
		rowData[6][1]=info.titleReference;
		
		table.updateUI();
		
		int i=0;
		textArea.setText(info.id+". Sura "+info.title+" ("+info.meaning+")\n\n");
		textArea.append("Main Theme:\n\n");

		while(info.mainTheme[i]!=null)
		{
			textArea.append((i+1)+")"+info.mainTheme[i]+"\n\n");
			i++;
		}
		
		infoIsSet=true;
	}
	
	private void setRowNames()
	{
		String[] rowNames={"Number","Name","Meaning(English)","Aya Count",
				"Descent","Revealation order","Title refers to"};
		rowData=new String[rowNames.length][2];
		for(int i=0;i<rowNames.length;i++)
		{
			rowData[i][0]=rowNames[i];
			rowData[i][1]=null;
		}
	}
	
	public boolean isInfoSet(){
		return infoIsSet;
	}
}

package QuranTeacher.Dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Font;

import javax.swing.JRadioButton;

import QuranTeacher.FilePaths;

import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

public class UpdateSettingDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JRadioButton rdbtnNeverCheckForUpdate;
	private JRadioButton rdbtnCheckForUpdatePerWeek;
	private JRadioButton rdbtnCheckForUpdateAlways;
	private static int optionIndex=-1;
	
	private final static String updatePrefFileName="update.pref";
	private static String storageFileName=FilePaths.preferencesStorageDir+"/"+updatePrefFileName;

	/**
	 * Create the dialog.
	 */
	public UpdateSettingDialog() {
		setTitle("Update Settings");
		setModal(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.BLACK);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblUpdatecheckingoption = new JLabel("Update Checking Option");
			lblUpdatecheckingoption.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lblUpdatecheckingoption.setForeground(Color.ORANGE);
			GridBagConstraints gbc_lblUpdatecheckingoption = new GridBagConstraints();
			gbc_lblUpdatecheckingoption.gridwidth = 0;
			gbc_lblUpdatecheckingoption.insets = new Insets(0, 0, 5, 0);
			gbc_lblUpdatecheckingoption.gridx = 0;
			gbc_lblUpdatecheckingoption.gridy = 0;
			contentPanel.add(lblUpdatecheckingoption, gbc_lblUpdatecheckingoption);
		}
		{
			
			rdbtnCheckForUpdateAlways = new JRadioButton("Check For Update Automatically (Recommended)");
			rdbtnCheckForUpdateAlways.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					optionIndex=0;
				}
			});
			rdbtnCheckForUpdateAlways.setForeground(Color.ORANGE);
			rdbtnCheckForUpdateAlways.setBackground(Color.DARK_GRAY);
			rdbtnCheckForUpdateAlways.setFont(new Font("Tahoma", Font.PLAIN, 16));
			GridBagConstraints gbc_rdbtnCheckForUpdate = new GridBagConstraints();
			gbc_rdbtnCheckForUpdate.fill = GridBagConstraints.HORIZONTAL;
			gbc_rdbtnCheckForUpdate.anchor = GridBagConstraints.WEST;
			gbc_rdbtnCheckForUpdate.gridwidth = 0;
			gbc_rdbtnCheckForUpdate.insets = new Insets(0, 0, 5, 0);
			gbc_rdbtnCheckForUpdate.gridx = 0;
			gbc_rdbtnCheckForUpdate.gridy = 1;
			contentPanel.add(rdbtnCheckForUpdateAlways, gbc_rdbtnCheckForUpdate);
		}
		{
			rdbtnCheckForUpdatePerWeek = new JRadioButton("Check For Update Once In A Week");
			rdbtnCheckForUpdatePerWeek.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					optionIndex=1;
				}
			});
			rdbtnCheckForUpdatePerWeek.setForeground(Color.ORANGE);
			rdbtnCheckForUpdatePerWeek.setBackground(Color.DARK_GRAY);
			rdbtnCheckForUpdatePerWeek.setFont(new Font("Tahoma", Font.PLAIN, 16));
			GridBagConstraints gbc_rdbtnCheckForUpdate_1 = new GridBagConstraints();
			gbc_rdbtnCheckForUpdate_1.fill = GridBagConstraints.HORIZONTAL;
			gbc_rdbtnCheckForUpdate_1.anchor = GridBagConstraints.WEST;
			gbc_rdbtnCheckForUpdate_1.gridwidth = 0;
			gbc_rdbtnCheckForUpdate_1.insets = new Insets(0, 0, 5, 0);
			gbc_rdbtnCheckForUpdate_1.gridx = 0;
			gbc_rdbtnCheckForUpdate_1.gridy = 2;
			contentPanel.add(rdbtnCheckForUpdatePerWeek, gbc_rdbtnCheckForUpdate_1);
		}
		{
			rdbtnNeverCheckForUpdate = new JRadioButton("Never Check For Update (Not Recommended )");
			rdbtnNeverCheckForUpdate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					optionIndex=2;
				}
			});
			rdbtnNeverCheckForUpdate.setForeground(Color.ORANGE);
			rdbtnNeverCheckForUpdate.setBackground(Color.DARK_GRAY);
			rdbtnNeverCheckForUpdate.setFont(new Font("Tahoma", Font.PLAIN, 16));
			GridBagConstraints gbc_rdbtnNeverCheckFor = new GridBagConstraints();
			gbc_rdbtnNeverCheckFor.fill = GridBagConstraints.HORIZONTAL;
			gbc_rdbtnNeverCheckFor.anchor = GridBagConstraints.WEST;
			gbc_rdbtnNeverCheckFor.gridwidth = 0;
			gbc_rdbtnNeverCheckFor.gridx = 0;
			gbc_rdbtnNeverCheckFor.gridy = 3;
			contentPanel.add(rdbtnNeverCheckForUpdate, gbc_rdbtnNeverCheckFor);
			
			
			ButtonGroup buttonGroup=new ButtonGroup();
			buttonGroup.add(rdbtnCheckForUpdateAlways);
			buttonGroup.add(rdbtnCheckForUpdatePerWeek);
			buttonGroup.add(rdbtnNeverCheckForUpdate);
			
			updateChoice(getOption());
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.BLACK);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						saveUpdatePrefToFile();
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

	private static void setOptionFromFile() {
		optionIndex=0;
		BufferedReader reader=null;
    	try {
    		reader=new BufferedReader(new FileReader(storageFileName));
    		String text=reader.readLine();
    		if(text!=null){
    			optionIndex=Integer.parseInt(text);
    		}
    		
    	} catch (IOException ex) {
    		ex.printStackTrace();
        } finally
        {
        	try
        	{
        		if(reader!=null)
        			reader.close();
        	}catch(IOException io)
        	{
        		io.printStackTrace();
        	}
        }
	}
	
	public static int getOption(){
		if(optionIndex==-1){
			setOptionFromFile();
		}
		return optionIndex;
	}

	public void saveUpdatePrefToFile() {
		PrintWriter writer=null;
		try {
			
			writer = new PrintWriter(storageFileName);
			writer.write(Integer.toString(optionIndex));
		
		} catch (IOException io) {
			//io.printStackTrace();
			System.out.println(io.getMessage());
		} finally
		{
			if(writer!=null)
				writer.close();
		}
	}
	
	
	private void updateChoice(int choice){
		if(choice==0){
			rdbtnCheckForUpdateAlways.setSelected(true);
		}else if(choice==1){
			rdbtnCheckForUpdatePerWeek.setSelected(true);
		}else{
			rdbtnNeverCheckForUpdate.setSelected(true);
		}
	}
	
	public static boolean isToCheckForUpdate(){
		if(getOption()==0)
			return true;
		else if(optionIndex==1){
			Calendar cal= Calendar.getInstance();
			int dayOfWeek=cal.get(cal.DAY_OF_WEEK);
			
			if(dayOfWeek==Calendar.FRIDAY){
				return true;
			}
		}
		return false;
	}
}

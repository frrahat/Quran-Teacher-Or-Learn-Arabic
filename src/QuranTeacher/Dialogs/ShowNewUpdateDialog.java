package QuranTeacher.Dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import QuranTeacher.MainWindow.MainFrame;
import QuranTeacher.Utils.VersionInfo;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.URL;

import javax.swing.border.TitledBorder;
import javax.swing.UIManager;

public class ShowNewUpdateDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;

	/**
	 * Create the dialog.
	 * @param newVersionInfo 
	 */
	
	private VersionInfo versionInfo;
	
	public ShowNewUpdateDialog(VersionInfo newVersionInfo) {
		this.versionInfo=newVersionInfo;
		
		setTitle("New Version Found!");
		setBounds(100, 100, 450, 400);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPanel.setBackground(Color.BLACK);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblCurrentversionlabel = new JLabel("Current Version :");
			lblCurrentversionlabel.setForeground(Color.ORANGE);
			lblCurrentversionlabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
			GridBagConstraints gbc_lblCurrentversionlabel = new GridBagConstraints();
			gbc_lblCurrentversionlabel.weighty = 0.1;
			gbc_lblCurrentversionlabel.anchor = GridBagConstraints.NORTHEAST;
			gbc_lblCurrentversionlabel.insets = new Insets(0, 0, 5, 5);
			gbc_lblCurrentversionlabel.gridx = 0;
			gbc_lblCurrentversionlabel.gridy = 0;
			contentPanel.add(lblCurrentversionlabel, gbc_lblCurrentversionlabel);
		}
		{
			JLabel lblCurrentversionstring = new JLabel(MainFrame.version);
			lblCurrentversionstring.setForeground(Color.ORANGE);
			lblCurrentversionstring.setFont(new Font("Tahoma", Font.PLAIN, 16));
			GridBagConstraints gbc_lblCurrentversionstring = new GridBagConstraints();
			gbc_lblCurrentversionstring.weighty = 0.1;
			gbc_lblCurrentversionstring.anchor = GridBagConstraints.NORTHWEST;
			gbc_lblCurrentversionstring.gridwidth = 0;
			gbc_lblCurrentversionstring.insets = new Insets(0, 0, 5, 5);
			gbc_lblCurrentversionstring.gridx = 1;
			gbc_lblCurrentversionstring.gridy = 0;
			contentPanel.add(lblCurrentversionstring, gbc_lblCurrentversionstring);
		}
		{
			JLabel lblVersionStringLabel = new JLabel("New Version :");
			lblVersionStringLabel.setForeground(Color.ORANGE);
			lblVersionStringLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
			GridBagConstraints gbc_lblVersionStringLabel = new GridBagConstraints();
			gbc_lblVersionStringLabel.anchor = GridBagConstraints.EAST;
			gbc_lblVersionStringLabel.weightx = 0.2;
			gbc_lblVersionStringLabel.insets = new Insets(0, 0, 5, 5);
			gbc_lblVersionStringLabel.gridx = 0;
			gbc_lblVersionStringLabel.gridy = 1;
			contentPanel.add(lblVersionStringLabel, gbc_lblVersionStringLabel);
		}
		{
			JLabel lblVersionString = new JLabel(versionInfo.getVersionString());
			lblVersionString.setForeground(Color.GREEN);
			lblVersionString.setFont(new Font("Tahoma", Font.PLAIN, 18));
			GridBagConstraints gbc_lblVersionString = new GridBagConstraints();
			gbc_lblVersionString.insets = new Insets(0, 0, 5, 0);
			gbc_lblVersionString.weightx = 0.8;
			gbc_lblVersionString.anchor = GridBagConstraints.WEST;
			gbc_lblVersionString.gridwidth = 0;
			gbc_lblVersionString.gridx = 1;
			gbc_lblVersionString.gridy = 1;
			contentPanel.add(lblVersionString, gbc_lblVersionString);
		}
		{
			JLabel lblReleasedatelabel = new JLabel("Released On :");
			lblReleasedatelabel.setForeground(Color.ORANGE);
			lblReleasedatelabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
			GridBagConstraints gbc_lblReleasedatelabel = new GridBagConstraints();
			gbc_lblReleasedatelabel.anchor = GridBagConstraints.EAST;
			gbc_lblReleasedatelabel.weightx = 0.2;
			gbc_lblReleasedatelabel.insets = new Insets(0, 0, 5, 5);
			gbc_lblReleasedatelabel.gridx = 0;
			gbc_lblReleasedatelabel.gridy = 2;
			contentPanel.add(lblReleasedatelabel, gbc_lblReleasedatelabel);
		}
		{
			JLabel lblReleasedate = new JLabel(versionInfo.getReleaseDateString());
			lblReleasedate.setForeground(Color.GREEN);
			lblReleasedate.setFont(new Font("Tahoma", Font.PLAIN, 18));
			GridBagConstraints gbc_lblReleasedate = new GridBagConstraints();
			gbc_lblReleasedate.gridwidth = 0;
			gbc_lblReleasedate.anchor = GridBagConstraints.WEST;
			gbc_lblReleasedate.insets = new Insets(0, 0, 5, 5);
			gbc_lblReleasedate.gridx = 1;
			gbc_lblReleasedate.gridy = 2;
			contentPanel.add(lblReleasedate, gbc_lblReleasedate);
		}
		{
			JLabel lblLinklabel = new JLabel("Download Link :");
			lblLinklabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lblLinklabel.setForeground(Color.ORANGE);
			GridBagConstraints gbc_lblLinklabel = new GridBagConstraints();
			gbc_lblLinklabel.weightx = 0.2;
			gbc_lblLinklabel.insets = new Insets(0, 0, 5, 5);
			gbc_lblLinklabel.anchor = GridBagConstraints.EAST;
			gbc_lblLinklabel.gridx = 0;
			gbc_lblLinklabel.gridy = 3;
			contentPanel.add(lblLinklabel, gbc_lblLinklabel);
		}
		{
			textField = new JTextField();
			textField.setForeground(Color.GREEN);
			textField.setBackground(Color.BLACK);
			textField.setFont(new Font("Tahoma", Font.PLAIN, 18));
			textField.setEditable(false);
			GridBagConstraints gbc_textField = new GridBagConstraints();
			gbc_textField.insets = new Insets(0, 0, 5, 5);
			gbc_textField.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField.gridx = 1;
			gbc_textField.gridy = 3;
			contentPanel.add(textField, gbc_textField);
			textField.setColumns(10);
			
			textField.setText(newVersionInfo.getDownloadLink());
		}
		{
			JButton btnCopylink = new JButton("Copy Link To ClipBoard");
			btnCopylink.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					StringSelection stringSelection = new StringSelection(versionInfo.getDownloadLink());
					Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
					clpbrd.setContents(stringSelection, null);
					
					JOptionPane.showMessageDialog(getParent(), "Copied to clipboard. "
							+ "Now Paste the link in your browser/downloader.");
				}
			});
			{
				JButton btnGo = new JButton("Go");
				btnGo.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
						    Desktop.getDesktop().browse(new URL(versionInfo.getDownloadLink()).toURI());
						} catch (Exception i) {
							JOptionPane.showMessageDialog(getParent(), "Failed to open browser."
									+ "\nPlease manually open your browser and"
									+ " then enter the link in the addressbar of the browser.","Failure",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				btnGo.setFont(new Font("Tahoma", Font.PLAIN, 14));
				GridBagConstraints gbc_btnGo = new GridBagConstraints();
				gbc_btnGo.insets = new Insets(0, 0, 5, 0);
				gbc_btnGo.gridx = 2;
				gbc_btnGo.gridy = 3;
				contentPanel.add(btnGo, gbc_btnGo);
			}
			btnCopylink.setFont(new Font("Tahoma", Font.PLAIN, 16));
			GridBagConstraints gbc_btnCopylink = new GridBagConstraints();
			gbc_btnCopylink.insets = new Insets(0, 0, 5, 5);
			gbc_btnCopylink.gridx = 1;
			gbc_btnCopylink.gridy = 4;
			contentPanel.add(btnCopylink, gbc_btnCopylink);
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			GridBagConstraints gbc_scrollPane = new GridBagConstraints();
			gbc_scrollPane.gridwidth = 0;
			gbc_scrollPane.fill = GridBagConstraints.BOTH;
			gbc_scrollPane.gridx = 0;
			gbc_scrollPane.gridy = 5;
			contentPanel.add(scrollPane, gbc_scrollPane);
			{
				JTextArea txtrMessagearea = new JTextArea();
				txtrMessagearea.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Information", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 200, 0)));
				txtrMessagearea.setBackground(Color.BLACK);
				txtrMessagearea.setForeground(Color.GREEN);
				txtrMessagearea.setFont(new Font("Tahoma", Font.BOLD, 18));
				txtrMessagearea.setEditable(false);
				txtrMessagearea.setLineWrap(true);
				txtrMessagearea.setWrapStyleWord(true);
				txtrMessagearea.setText(versionInfo.getMessage());
				scrollPane.setViewportView(txtrMessagearea);
				txtrMessagearea.setCaretPosition(0);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.BLACK);
			buttonPane.setForeground(Color.GRAY);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

}

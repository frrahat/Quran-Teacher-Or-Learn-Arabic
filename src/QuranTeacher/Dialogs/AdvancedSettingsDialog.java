package QuranTeacher.Dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.border.TitledBorder;
import javax.swing.JLabel;

import QuranTeacher.RenderAnimation.Animation;

import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;

/**
 * @author Rahat
 *	18-06-15
 */
public class AdvancedSettingsDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lblHidePWOnOff;
	private JButton btnDecreaseextra;
	private JButton btnIncreaseextra;
	private JLabel lblExtralineheight;
	private JButton btnDecreaselineheight;
	private JButton btnIncreaseLineHeight;
	private JLabel lblLineheight;

	/**
	 * Create the dialog.
	 */
	public AdvancedSettingsDialog() {
		setBounds(80, 250, 450, 300);
		setAlwaysOnTop(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.BLACK);
		contentPanel.setBorder(new TitledBorder(null, "Additional Settings For Advanced Users", TitledBorder.LEADING, TitledBorder.TOP, null, Color.YELLOW));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblLineheightlabel = new JLabel("Line Gap :");
			lblLineheightlabel.setToolTipText("Gap in pixel between the lines."
					+ " Note: At least 3 lines need to be displayed on the screen to "
					+ "see the effect of changing.");
			lblLineheightlabel.setForeground(Color.ORANGE);
			lblLineheightlabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
			GridBagConstraints gbc_lblLineheightlabel = new GridBagConstraints();
			gbc_lblLineheightlabel.weightx = 0.1;
			gbc_lblLineheightlabel.insets = new Insets(0, 0, 5, 5);
			gbc_lblLineheightlabel.gridx = 0;
			gbc_lblLineheightlabel.gridy = 0;
			contentPanel.add(lblLineheightlabel, gbc_lblLineheightlabel);
		}
		{
			lblLineheight = new JLabel(Integer.toString(Animation.getLineHeight()));
			lblLineheight.setForeground(Color.ORANGE);
			lblLineheight.setFont(new Font("Tahoma", Font.PLAIN, 18));
			GridBagConstraints gbc_lblLineheight = new GridBagConstraints();
			gbc_lblLineheight.weightx = 0.1;
			gbc_lblLineheight.insets = new Insets(0, 0, 5, 5);
			gbc_lblLineheight.gridx = 1;
			gbc_lblLineheight.gridy = 0;
			contentPanel.add(lblLineheight, gbc_lblLineheight);
		}
		{
			btnIncreaseLineHeight = new JButton("+");
			btnIncreaseLineHeight.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(Animation.changeLineHeight(true)){
						lblLineheight.setText(Integer.toString(Animation.getLineHeight()));
					}
				}
			});
			btnIncreaseLineHeight.setFont(new Font("Tahoma", Font.BOLD, 18));
			GridBagConstraints gbc_btnIncreaseLineHeight = new GridBagConstraints();
			gbc_btnIncreaseLineHeight.insets = new Insets(0, 0, 5, 5);
			gbc_btnIncreaseLineHeight.gridx = 2;
			gbc_btnIncreaseLineHeight.gridy = 0;
			contentPanel.add(btnIncreaseLineHeight, gbc_btnIncreaseLineHeight);
		}
		{
			btnDecreaselineheight = new JButton("-");
			btnDecreaselineheight.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(Animation.changeLineHeight(false)){
						lblLineheight.setText(Integer.toString(Animation.getLineHeight()));
					}
				}
			});
			btnDecreaselineheight.setFont(new Font("Tahoma", Font.BOLD, 18));
			GridBagConstraints gbc_btnDecreaselineheight = new GridBagConstraints();
			gbc_btnDecreaselineheight.insets = new Insets(0, 0, 5, 0);
			gbc_btnDecreaselineheight.gridx = 3;
			gbc_btnDecreaselineheight.gridy = 0;
			contentPanel.add(btnDecreaselineheight, gbc_btnDecreaselineheight);
		}
		{
			JLabel lblExtralinegaplabel = new JLabel("Extra Font Height :");
			lblExtralinegaplabel.setToolTipText("Extra font height to avoid overlapping of texts for some font selection."
					+ " Note: At least 2 lines need to be displayed on the screen to "
					+ "see the effect of changing.");
			lblExtralinegaplabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lblExtralinegaplabel.setForeground(Color.ORANGE);
			GridBagConstraints gbc_lblExtralinegaplabel = new GridBagConstraints();
			gbc_lblExtralinegaplabel.weightx = 0.1;
			gbc_lblExtralinegaplabel.insets = new Insets(0, 0, 5, 5);
			gbc_lblExtralinegaplabel.gridx = 0;
			gbc_lblExtralinegaplabel.gridy = 1;
			contentPanel.add(lblExtralinegaplabel, gbc_lblExtralinegaplabel);
		}
		{
			lblExtralineheight = new JLabel(Integer.toString(Animation.getExtraHeight()));
			lblExtralineheight.setForeground(Color.ORANGE);
			lblExtralineheight.setFont(new Font("Tahoma", Font.PLAIN, 18));
			GridBagConstraints gbc_lblExtralineheight = new GridBagConstraints();
			gbc_lblExtralineheight.weightx = 0.1;
			gbc_lblExtralineheight.insets = new Insets(0, 0, 5, 5);
			gbc_lblExtralineheight.gridx = 1;
			gbc_lblExtralineheight.gridy = 1;
			contentPanel.add(lblExtralineheight, gbc_lblExtralineheight);
		}
		{
			btnIncreaseextra = new JButton("+");
			btnIncreaseextra.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(Animation.changeExtraHeight(true)){
						lblExtralineheight.setText(Integer.toString(Animation.getExtraHeight()));
					}
				}
			});
			btnIncreaseextra.setFont(new Font("Tahoma", Font.BOLD, 18));
			GridBagConstraints gbc_btnIncreaseextra = new GridBagConstraints();
			gbc_btnIncreaseextra.insets = new Insets(0, 0, 5, 5);
			gbc_btnIncreaseextra.gridx = 2;
			gbc_btnIncreaseextra.gridy = 1;
			contentPanel.add(btnIncreaseextra, gbc_btnIncreaseextra);
		}
		{
			btnDecreaseextra = new JButton("-");
			btnDecreaseextra.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(Animation.changeExtraHeight(false)){
						lblExtralineheight.setText(Integer.toString(Animation.getExtraHeight()));
					}
				}
			});
			btnDecreaseextra.setFont(new Font("Tahoma", Font.BOLD, 18));
			GridBagConstraints gbc_btnDecreaseextra = new GridBagConstraints();
			gbc_btnDecreaseextra.insets = new Insets(0, 0, 5, 0);
			gbc_btnDecreaseextra.gridx = 3;
			gbc_btnDecreaseextra.gridy = 1;
			contentPanel.add(btnDecreaseextra, gbc_btnDecreaseextra);
		}
		{
			JLabel lblHidepartialword = new JLabel("Hide Partial Word :");
			lblHidepartialword.setToolTipText("Hide Part Of the word while animating");
			lblHidepartialword.setForeground(Color.ORANGE);
			lblHidepartialword.setFont(new Font("Tahoma", Font.PLAIN, 18));
			GridBagConstraints gbc_lblHidepartialword = new GridBagConstraints();
			gbc_lblHidepartialword.weightx = 0.1;
			gbc_lblHidepartialword.insets = new Insets(0, 0, 0, 5);
			gbc_lblHidepartialword.gridx = 0;
			gbc_lblHidepartialword.gridy = 2;
			contentPanel.add(lblHidepartialword, gbc_lblHidepartialword);
		}
		{
			lblHidePWOnOff = new JLabel();
			if(Animation.isAnimatePartialWord()){
				lblHidePWOnOff.setText("On");
			}else{
				lblHidePWOnOff.setText("Off");
			}
			lblHidePWOnOff.setForeground(Color.ORANGE);
			lblHidePWOnOff.setFont(new Font("Tahoma", Font.PLAIN, 18));
			GridBagConstraints gbc_lblHidePWOnOff = new GridBagConstraints();
			gbc_lblHidePWOnOff.weightx=0.1;
			gbc_lblHidePWOnOff.insets = new Insets(0, 0, 0, 5);
			gbc_lblHidePWOnOff.gridx = 1;
			gbc_lblHidePWOnOff.gridy = 2;
			contentPanel.add(lblHidePWOnOff, gbc_lblHidePWOnOff);
		}
		{
			JButton btnToggler = new JButton("Toggle");
			btnToggler.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(lblHidePWOnOff.getText().equals("On")){
						lblHidePWOnOff.setText("Off");
						Animation.setHidePartialWord(false);
					}else{
						lblHidePWOnOff.setText("On");
						Animation.setHidePartialWord(true);
					}
				}
			});
			btnToggler.setFont(new Font("Tahoma", Font.PLAIN, 18));
			GridBagConstraints gbc_btnToggler = new GridBagConstraints();
			gbc_btnToggler.gridwidth = 0;
			gbc_btnToggler.insets = new Insets(0, 0, 0, 5);
			gbc_btnToggler.gridx = 2;
			gbc_btnToggler.gridy = 2;
			contentPanel.add(btnToggler, gbc_btnToggler);
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
						setVisible(false);
					}
				});
				okButton.setActionCommand("Exit");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

}

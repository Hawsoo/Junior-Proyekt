package me.hawsoo.leveleditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import me.hawsoo.leveleditor.res.LEresources;

public class AboutApp extends JDialog
{
	private static final long serialVersionUID = 1L;

	/**
	 * Create the dialog.
	 */
	public AboutApp(JFrame frame)
	{
		super(frame, "About " + LevelEditorApp.NAME);
		initialize();
		setupWindow();
	}
	
	/**
	 * Creates the GUI.
	 */
	private void initialize()
	{
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		JLabel lblLeveleditorlogo = new JLabel();
		lblLeveleditorlogo.setIcon(new ImageIcon(LEresources.imgBanner));
		springLayout.putConstraint(SpringLayout.NORTH, lblLeveleditorlogo, 0, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblLeveleditorlogo, 0, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lblLeveleditorlogo, 0, SpringLayout.SOUTH, getContentPane());
		getContentPane().add(lblLeveleditorlogo);
		
		JLabel lblTitle = new JLabel("Junior Proyekt Level Editor");
		lblTitle.setFont(new Font("Modern No. 20", Font.PLAIN, 28));
		springLayout.putConstraint(SpringLayout.NORTH, lblTitle, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblTitle, 6, SpringLayout.EAST, lblLeveleditorlogo);
		getContentPane().add(lblTitle);
		
		JLabel lblSubtitle = new JLabel("Created for \"Junior Proyekt\"");
		lblSubtitle.setForeground(Color.LIGHT_GRAY);
		springLayout.putConstraint(SpringLayout.NORTH, lblSubtitle, 0, SpringLayout.SOUTH, lblTitle);
		lblSubtitle.setFont(new Font("Modern No. 20", Font.ITALIC, 20));
		springLayout.putConstraint(SpringLayout.WEST, lblSubtitle, 0, SpringLayout.WEST, lblTitle);
		getContentPane().add(lblSubtitle);
		
		JLabel lblCredits = new JLabel(
				"<html>This program is created for the sole purpose of creating and editing levels"
				+ " for \"Junior Proyekt\". In it are the very same building blocks used to create"
				+ " the original version of the game, and is completely open-source for use by anybody.</html>");
		springLayout.putConstraint(SpringLayout.WEST, lblCredits, 6, SpringLayout.WEST, lblSubtitle);
		lblCredits.setHorizontalAlignment(SwingConstants.LEFT);
		springLayout.putConstraint(SpringLayout.NORTH, lblCredits, 16, SpringLayout.SOUTH, lblSubtitle);
		springLayout.putConstraint(SpringLayout.EAST, lblCredits, -10, SpringLayout.EAST, getContentPane());
		lblCredits.setForeground(Color.DARK_GRAY);
		lblCredits.setFont(new Font("Modern No. 20", Font.PLAIN, 16));
		getContentPane().add(lblCredits);
		
		JButton btnOkay = new JButton("OK");
		btnOkay.setFocusable(false);
		springLayout.putConstraint(SpringLayout.SOUTH, btnOkay, -10, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnOkay, -10, SpringLayout.EAST, getContentPane());
		btnOkay.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						// Exit
						AboutApp.this.dispose();
					}
				}
				);
		getContentPane().add(btnOkay);
		btnOkay.setPreferredSize(new Dimension(96, 24));
		getRootPane().setDefaultButton(btnOkay);
	}
	
	/**
	 * Configures the window of the GUI.
	 */
	private void setupWindow()
	{
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		getContentPane().setPreferredSize(new Dimension(640, 360));
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}

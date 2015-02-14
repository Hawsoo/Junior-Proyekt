package me.hawsoo.leveleditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;

public class LevelEditorApp
{
	public static final String NAME = "JP Level Editor";
	public static final Dimension DEFAULT_DIMENSIONS = new Dimension(1024, 576);
	
	private JFrame frame;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] argv)
	{
		// Setup look and feel
		try
		{
			UIManager.setLookAndFeel(
					UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}
		
		// Run leveleditor
		EventQueue.invokeLater(
				new Runnable()
				{
					public void run()
					{
						new LevelEditorApp();
					}
				}
				);
	}
	
	/**
	 * Create the application window.
	 */
	public LevelEditorApp()
	{
		initialize();
		setupWindow();
	}
	
	/**
	 * Initialize the contents of the window.
	 */
	private void initialize()
	{
		/*
		 * Setup application's main frame.
		 */
		frame = new JFrame(NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		/*
		 * Menubar for the application.
		 */
		JMenuBar menuBar = new JMenuBar();
		springLayout.putConstraint(SpringLayout.NORTH, menuBar, 0, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, menuBar, 0, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, menuBar, 0, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(menuBar);
		
		/*
		 * File section of the menubar.
		 */
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mnFile.add(mntmNew);
		
		mnFile.addSeparator();
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save As...");
		mnFile.add(mntmSaveAs);
		
		mnFile.addSeparator();
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						// LATER make the program ask if user wants to save, but only if the user hasn't saved yet
						
						// Quit the game
						System.exit(0);
					}
				}
				);
		mnFile.add(mntmExit);
		
		/*
		 * Help section of the menubar.
		 */
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmHelpMenu = new JMenuItem("Help menu...");
		mnHelp.add(mntmHelpMenu);
		
		JMenuItem mntmAboutProgram = new JMenuItem("About JP Level Editor");
		mnHelp.add(mntmAboutProgram);
		
		/*
		 * Topbar which contains basic controls.
		 */
		JLabel lblLevelName = new JLabel("<LEVEL NAME>");
		springLayout.putConstraint(SpringLayout.NORTH, lblLevelName, 0, SpringLayout.SOUTH, menuBar);
		springLayout.putConstraint(SpringLayout.WEST, lblLevelName, 10, SpringLayout.WEST, frame.getContentPane());
		lblLevelName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frame.getContentPane().add(lblLevelName);
		
		JPanel pnlToolBar = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, pnlToolBar, 21, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lblLevelName, 0, SpringLayout.SOUTH, pnlToolBar);
		springLayout.putConstraint(SpringLayout.WEST, pnlToolBar, 6, SpringLayout.EAST, lblLevelName);
		frame.getContentPane().add(pnlToolBar);
		
		/*
		 * Layer options of the topbar.
		 */
		JCheckBox chckbxForelayer = new JCheckBox("Foreground");
		chckbxForelayer.setFocusable(false);
		pnlToolBar.add(chckbxForelayer);
		springLayout.putConstraint(SpringLayout.WEST, chckbxForelayer, 12, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, chckbxForelayer, 49, SpringLayout.NORTH, frame.getContentPane());
		chckbxForelayer.setSelected(true);
		
		JCheckBox chckbxContentlayer = new JCheckBox("Content Layer");
		chckbxContentlayer.setFocusable(false);
		pnlToolBar.add(chckbxContentlayer);
		chckbxContentlayer.setSelected(true);
		springLayout.putConstraint(SpringLayout.SOUTH, chckbxContentlayer, 0, SpringLayout.SOUTH, chckbxForelayer);
		springLayout.putConstraint(SpringLayout.NORTH, chckbxContentlayer, 0, SpringLayout.NORTH, chckbxForelayer);
		springLayout.putConstraint(SpringLayout.WEST, chckbxContentlayer, 6, SpringLayout.EAST, chckbxForelayer);
		
		JCheckBox chckbxBacklayer = new JCheckBox("Background");
		chckbxBacklayer.setFocusable(false);
		pnlToolBar.add(chckbxBacklayer);
		chckbxBacklayer.setSelected(true);
		springLayout.putConstraint(SpringLayout.SOUTH, chckbxBacklayer, 0, SpringLayout.SOUTH, chckbxForelayer);
		springLayout.putConstraint(SpringLayout.NORTH, chckbxBacklayer, 0, SpringLayout.NORTH, chckbxContentlayer);
		springLayout.putConstraint(SpringLayout.WEST, chckbxBacklayer, 6, SpringLayout.EAST, chckbxContentlayer);
		
		JTabbedPane tabPalettePane = new JTabbedPane(JTabbedPane.TOP);
		tabPalettePane.setFocusable(false);
		springLayout.putConstraint(SpringLayout.NORTH, tabPalettePane, 6, SpringLayout.SOUTH, menuBar);
		
		tabPalettePane.setPreferredSize(new Dimension(256, 0));
		springLayout.putConstraint(SpringLayout.EAST, tabPalettePane, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(tabPalettePane);
		
		JPanel pnlLevelOverview = new JPanel();
		pnlLevelOverview.setBorder(new LineBorder(Color.GRAY));
		springLayout.putConstraint(SpringLayout.SOUTH, tabPalettePane, -6, SpringLayout.NORTH, pnlLevelOverview);
		pnlLevelOverview.setPreferredSize(new Dimension(256, 256));
		springLayout.putConstraint(SpringLayout.SOUTH, pnlLevelOverview, -10, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, pnlLevelOverview, 0, SpringLayout.EAST, tabPalettePane);
		
		JScrollPane scrPalette1 = new JScrollPane();
		tabPalettePane.addTab("Palette 1", null, scrPalette1, null);
		frame.getContentPane().add(pnlLevelOverview);
		
		/*
		 * The main view of the application.
		 */
		JScrollPane scrMainView = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrMainView, 0, SpringLayout.SOUTH, lblLevelName);
		springLayout.putConstraint(SpringLayout.WEST, scrMainView, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrMainView, 0, SpringLayout.SOUTH, pnlLevelOverview);
		springLayout.putConstraint(SpringLayout.EAST, scrMainView, -6, SpringLayout.WEST, pnlLevelOverview);
		frame.getContentPane().add(scrMainView);
	}
	
	/**
	 * Sets up the window after components are added.
	 */
	private void setupWindow()
	{
		frame.getContentPane().setPreferredSize(DEFAULT_DIMENSIONS);
//		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);					// BETA uncomment this when not using WindowBuilder
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}

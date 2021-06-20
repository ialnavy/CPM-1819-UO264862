package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;

import org.jvnet.substance.SubstanceLookAndFeel;

import logic.Article;
import logic.Collator;
import logic.Invoice;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.text.NumberFormat;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;

/**
 * 'MainWindow' GUI class. This class displays the main window of the
 * application, where the user can search products & purchase them.
 * 
 * @author Iván Álvarez López - 71741444M
 * @version January 2019
 *
 */
public class MainWindow extends JFrame {

	private static final long serialVersionUID = -6545233961183195246L;

	private JPanel contentPane;
	protected static WelcomeWindow welcomeWindow;
	Locale location;
	private JPanel panelHeader;
	private JPanel panelMain;
	private JPanel panelUserInfo;
	private JLabel labelUsername;
	private JLabel labelAnonymous;
	private JPanel panelLabels;
	private JMenu mnFile;
	private JMenuItem mntmExit;
	private JMenu mnLanguage;
	private JRadioButtonMenuItem rdbtnmntmSpanish;
	private JRadioButtonMenuItem rdbtnmntmEnglish;
	private JRadioButtonMenuItem rdbtnmntmGalician;
	private JMenu mnHelp;
	private JMenuItem mntmHelp;
	private JMenuItem mntmAbout;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JMenuBar menuBar;
	Collator collator;
	private JPanel panelPeople;
	private JLabel labelPeople;
	private JFormattedTextField formattedTextFieldPeople;
	private JLabel labelIcon;
	private JTabbedPane tabbedPane;
	Invoice invoice;
	private JPanel panelShoppingCart;
	private JLabel labelShoppingCart;
	private JButton buttonShoppingCart;
	private JPanel panelPurchase;
	private JLabel labelPurchase;
	private JButton buttonPurchase;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame.setDefaultLookAndFeelDecorated(true);
					JDialog.setDefaultLookAndFeelDecorated(true);
					SubstanceLookAndFeel.setSkin("org.jvnet.substance.skin.RavenSkin");
					MainWindow frame = new MainWindow(new WelcomeWindow());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow(WelcomeWindow welcomeWindow) {
		this.location = welcomeWindow.location;
		ResourceBundle resource = ResourceBundle.getBundle("rcs/mainWindow", location);
		this.invoice = new Invoice();
		this.collator = new Collator();
		setResizable(true);
		MainWindow.welcomeWindow = welcomeWindow;
		setTitle(resource.getString("mainTitle"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/img/RPI.png")));
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (JOptionPane.showConfirmDialog(null, resource.getString("closeWindowConfirmation"),
						resource.getString("closeWindow"), JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					welcomeWindow.setVisible(true);
					setVisible(false);
					dispose();
				}
			}
		});
		setBounds(100, 100, 450, 300);
		setJMenuBar(getMenuBar_1());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPanelHeader(), BorderLayout.NORTH);
		contentPane.add(getPanelMain(), BorderLayout.CENTER);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		loadHelp();
	}

	private void locate(Locale location) {
		// Locate login window.
		MainWindow.welcomeWindow.location = location;
		MainWindow.welcomeWindow.locate(location);

		// Locate main window.
		this.location = location;
		ResourceBundle resource = ResourceBundle.getBundle("rcs/mainWindow", location);
		this.setTitle(resource.getString("mainTitle"));

		labelUsername.setText(resource.getString("welcome") + ", " + welcomeWindow.logedUser);

		if (welcomeWindow.anonymous)
			labelAnonymous.setText(resource.getString("anonymousLogin"));
		else
			labelAnonymous.setText(resource.getString("correctLogin"));

		resource = ResourceBundle.getBundle("rcs/welcomeWindow", location);

		mnFile.setText(resource.getString("file"));
		if (location.getLanguage().equals("es"))
			mnFile.setMnemonic('c');
		else if (location.getLanguage().equals("gl"))
			mnFile.setMnemonic('q');
		else
			mnFile.setMnemonic('F');

		mntmExit.setText(resource.getString("exit"));
		mntmExit.setToolTipText(resource.getString("exitToolTip"));
		if (location.getLanguage().equals("es") || location.getLanguage().equals("gl"))
			mntmExit.setMnemonic('S');
		else
			mntmExit.setMnemonic('E');

		mnLanguage.setText(resource.getString("language"));
		if (location.getLanguage().equals("es"))
			mnLanguage.setMnemonic('I');
		else if (location.getLanguage().equals("gl"))
			mnLanguage.setMnemonic('I');
		else
			mnLanguage.setMnemonic('L');

		rdbtnmntmSpanish.setText(resource.getString("spanish"));
		if (location.getLanguage().equals("es"))
			rdbtnmntmSpanish.setMnemonic('E');
		else if (location.getLanguage().equals("gl"))
			rdbtnmntmSpanish.setMnemonic('C');
		else
			rdbtnmntmSpanish.setMnemonic('S');

		rdbtnmntmEnglish.setText(resource.getString("english"));
		if (location.getLanguage().equals("es") || location.getLanguage().equals("gl"))
			rdbtnmntmEnglish.setMnemonic('I');
		else
			rdbtnmntmEnglish.setMnemonic('E');

		rdbtnmntmGalician.setText(resource.getString("galician"));
		rdbtnmntmGalician.setMnemonic('G');

		mnHelp.setText(resource.getString("help"));
		if (location.getLanguage().equals("es"))
			mnHelp.setMnemonic('y');
		else if (location.getLanguage().equals("gl"))
			mnHelp.setMnemonic('x');
		else
			mnHelp.setMnemonic('H');

		mntmHelp.setText(resource.getString("help"));
		if (location.getLanguage().equals("es"))
			mntmHelp.setMnemonic('y');
		else if (location.getLanguage().equals("gl"))
			mntmHelp.setMnemonic('x');
		else
			mntmHelp.setMnemonic('H');

		mntmAbout.setText(resource.getString("about"));
		if (location.getLanguage().equals("es") || location.getLanguage().equals("gl"))
			mntmAbout.setMnemonic('c');
		else
			mntmAbout.setMnemonic('A');

		resource = ResourceBundle.getBundle("rcs/mainWindow", location);

		labelPeople.setText(resource.getString("people"));

		formattedTextFieldPeople.setToolTipText(resource.getString("textFieldPeopleToolTip"));

		labelShoppingCart.setText(resource.getString("shoppingCartMessage"));

		buttonShoppingCart.setToolTipText(resource.getString("shoppingCartToolTip"));

		labelPurchase.setText(resource.getString("proceedPurchase"));
	}

	/**
	 * 'displayShoppingCartWindow()' method. Enters to the shopping cart window of
	 * the application.
	 */
	private void displayShoppingCartWindow() {
		ShoppingCartWindow scw = new ShoppingCartWindow(this);
		scw.setLocationRelativeTo(this);
		scw.setVisible(true);
		this.setEnabled(false);
	}

	/**
	 * 'displayFormWindow()' method. Enters to the form window of the application.
	 */
	private void displayFormWindow() {
		FormWindow fw = new FormWindow(this);
		fw.setLocationRelativeTo(this);
		fw.setVisible(true);
		this.setEnabled(false);
	}

	private void loadHelp() {

		URL hsURL;
		HelpSet hs;

		try {
			File fichero = new File("help/Help.hs");
			hsURL = fichero.toURI().toURL();
			hs = new HelpSet(null, hsURL);
		}

		catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Help Not Found!");
			return;
		}

		HelpBroker hb = hs.createHelpBroker();
		hb.initPresentation();

		hb.enableHelpKey(getRootPane(), "purchase", hs);
		hb.enableHelpOnButton(this.mntmHelp, "purchase", hs);
		hb.enableHelp(this.buttonPurchase, "form", hs);
		hb.enableHelp(this.buttonShoppingCart, "check", hs);
	}

	// ##############
	// Getter methods
	// ##############

	private JPanel getPanelHeader() {
		if (panelHeader == null) {
			panelHeader = new JPanel();
			panelHeader.setLayout(new GridLayout(0, 4, 0, 0));
			panelHeader.add(getPanelUserInfo());
			panelHeader.add(getPanelPeople());
			panelHeader.add(getPanelShoppingCart());
			panelHeader.add(getPanelPurchase());
		}
		return panelHeader;
	}

	private JPanel getPanelMain() {
		if (panelMain == null) {
			panelMain = new JPanel();
			panelMain.setLayout(new GridLayout(0, 1, 0, 0));
			panelMain.add(getTabbedPane());
		}
		return panelMain;
	}

	private JPanel getPanelUserInfo() {
		if (panelUserInfo == null) {
			panelUserInfo = new JPanel();
			panelUserInfo.setLayout(new GridLayout(0, 2, 0, 0));
			panelUserInfo.add(getLabelIcon());
			panelUserInfo.add(getPanelLabels());
		}
		return panelUserInfo;
	}

	private JLabel getLabelUsername() {
		if (labelUsername == null) {
			ResourceBundle resource = ResourceBundle.getBundle("rcs/mainWindow", location);
			labelUsername = new JLabel(resource.getString("welcome") + ", " + welcomeWindow.logedUser);
			labelUsername.setFont(new Font("Arial", Font.BOLD, 12));
		}
		return labelUsername;
	}

	private JLabel getLabelAnonymous() {
		if (labelAnonymous == null) {
			ResourceBundle resource = ResourceBundle.getBundle("rcs/mainWindow", location);
			labelAnonymous = new JLabel();
			labelAnonymous.setFont(new Font("Arial", Font.BOLD, 12));
			if (welcomeWindow.anonymous)
				labelAnonymous.setText(resource.getString("anonymousLogin"));
			else
				labelAnonymous.setText(resource.getString("correctLogin"));
		}
		return labelAnonymous;
	}

	private JPanel getPanelLabels() {
		if (panelLabels == null) {
			panelLabels = new JPanel();
			panelLabels.setLayout(new GridLayout(2, 1, 0, 0));
			panelLabels.add(getLabelUsername());
			panelLabels.add(getLabelAnonymous());
		}
		return panelLabels;
	}

	/**
	 * 'getMnFile()' method.
	 * 
	 * @return mnFile
	 */
	private JMenu getMnFile() {
		ResourceBundle resource = ResourceBundle.getBundle("rcs/welcomeWindow", location);
		if (mnFile == null) {
			mnFile = new JMenu(resource.getString("file"));
			mnFile.add(getMntmExit());
			if (location.getLanguage().equals("es"))
				mnFile.setMnemonic('c');
			else if (location.getLanguage().equals("gl"))
				mnFile.setMnemonic('q');
			else
				mnFile.setMnemonic('F');
		}
		return mnFile;
	}

	/**
	 * 'getMntmExit()' method.
	 * 
	 * @return
	 */
	private JMenuItem getMntmExit() {
		ResourceBundle resource = ResourceBundle.getBundle("rcs/welcomeWindow", location);
		if (mntmExit == null) {
			mntmExit = new JMenuItem(resource.getString("exit"));
			mntmExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					System.exit(0);
				}
			});
			if (location.getLanguage().equals("es") || location.getLanguage().equals("gl"))
				mntmExit.setMnemonic('S');
			else
				mntmExit.setMnemonic('E');
			mntmExit.setToolTipText(resource.getString("exitToolTip"));
		}
		return mntmExit;
	}

	/**
	 * 'getMnLanguage()' method.
	 * 
	 * @return mnLanguage
	 */
	private JMenu getMnLanguage() {
		ResourceBundle resource = ResourceBundle.getBundle("rcs/welcomeWindow", location);
		if (mnLanguage == null) {
			mnLanguage = new JMenu(resource.getString("language"));
			mnLanguage.add(getRdbtnmntmSpanish());
			mnLanguage.add(getRdbtnmntmEnglish());
			mnLanguage.add(getRdbtnmntmGalician());
			if (location.getLanguage().equals("es") || location.getLanguage().equals("gl"))
				mnLanguage.setMnemonic('I');
			else
				mnLanguage.setMnemonic('L');
		}
		if (location.getLanguage().equals("es"))
			getRdbtnmntmSpanish().setSelected(true);
		else if (location.getLanguage().equals("gl"))
			getRdbtnmntmGalician().setSelected(true);
		else
			getRdbtnmntmEnglish().setSelected(true);
		return mnLanguage;
	}

	/**
	 * 'getRdbtnmntmSpanish()' method.
	 * 
	 * @return rdbtnmntm
	 */
	private JRadioButtonMenuItem getRdbtnmntmSpanish() {
		ResourceBundle resource = ResourceBundle.getBundle("rcs/welcomeWindow", location);
		if (rdbtnmntmSpanish == null) {
			rdbtnmntmSpanish = new JRadioButtonMenuItem(resource.getString("spanish"));
			rdbtnmntmSpanish.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					locate(new Locale("es"));
				}
			});
			if (location.getLanguage().equals("es"))
				rdbtnmntmSpanish.setMnemonic('E');
			else if (location.getLanguage().equals("gl"))
				rdbtnmntmSpanish.setMnemonic('C');
			else
				rdbtnmntmSpanish.setMnemonic('S');
			buttonGroup.add(rdbtnmntmSpanish);
		}
		return rdbtnmntmSpanish;
	}

	/**
	 * 'getRdbtnmntmEnglish()' method.
	 * 
	 * @return rdbtnmntmEnglish
	 */
	private JRadioButtonMenuItem getRdbtnmntmEnglish() {
		ResourceBundle resource = ResourceBundle.getBundle("rcs/welcomeWindow", location);
		if (rdbtnmntmEnglish == null) {
			rdbtnmntmEnglish = new JRadioButtonMenuItem(resource.getString("english"));
			rdbtnmntmEnglish.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					locate(new Locale("en"));
				}
			});
			if (location.getLanguage().equals("es") || location.getLanguage().equals("gl"))
				rdbtnmntmEnglish.setMnemonic('I');
			else
				rdbtnmntmEnglish.setMnemonic('E');
			buttonGroup.add(rdbtnmntmEnglish);
		}
		return rdbtnmntmEnglish;
	}

	/**
	 * 'getRdbtnmntmGalician()' method.
	 * 
	 * @return rdbtnmntmGalician
	 */
	private JRadioButtonMenuItem getRdbtnmntmGalician() {
		ResourceBundle resource = ResourceBundle.getBundle("rcs/welcomeWindow", location);
		if (rdbtnmntmGalician == null) {
			rdbtnmntmGalician = new JRadioButtonMenuItem(resource.getString("galician"));
			rdbtnmntmGalician.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					locate(new Locale("gl"));
				}
			});
			rdbtnmntmGalician.setMnemonic('G');
			buttonGroup.add(rdbtnmntmGalician);
		}
		return rdbtnmntmGalician;
	}

	/**
	 * 'getMnHelp()' method.
	 * 
	 * @return mnHelp
	 */
	private JMenu getMnHelp() {
		ResourceBundle resource = ResourceBundle.getBundle("rcs/welcomeWindow", location);
		if (mnHelp == null) {
			mnHelp = new JMenu(resource.getString("help"));
			mnHelp.add(getMntmHelp());
			mnHelp.add(getMntmAbout());
			if (location.getLanguage().equals("es"))
				mnHelp.setMnemonic('y');
			else if (location.getLanguage().equals("gl"))
				mnHelp.setMnemonic('x');
			else
				mnHelp.setMnemonic('H');
		}
		return mnHelp;
	}

	/**
	 * 'getMntmHelp()' method.
	 * 
	 * @return mntmHelp
	 */
	private JMenuItem getMntmHelp() {
		ResourceBundle resource = ResourceBundle.getBundle("rcs/welcomeWindow", location);
		if (mntmHelp == null) {
			mntmHelp = new JMenuItem(resource.getString("help"));
			if (location.getLanguage().equals("es"))
				mntmHelp.setMnemonic('y');
			else if (location.getLanguage().equals("gl"))
				mntmHelp.setMnemonic('x');
			else
				mntmHelp.setMnemonic('H');
		}
		return mntmHelp;
	}

	/**
	 * 'getMntmAbout()' method.
	 * 
	 * @return mntmAbout
	 */
	private JMenuItem getMntmAbout() {
		ResourceBundle resource = ResourceBundle.getBundle("rcs/welcomeWindow", location);
		if (mntmAbout == null) {
			mntmAbout = new JMenuItem(resource.getString("about"));
			if (location.getLanguage().equals("es") || location.getLanguage().equals("gl"))
				mntmAbout.setMnemonic('c');
			else
				mntmAbout.setMnemonic('A');
			mntmAbout.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null,
							resource.getString("mainTitle") + "\n" + "Iván Álvarez López - 71741444M",
							resource.getString("about"), JOptionPane.INFORMATION_MESSAGE);
				}
			});
		}
		return mntmAbout;
	}

	private JMenuBar getMenuBar_1() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			menuBar.add(getMnFile());
			menuBar.add(getMnLanguage());
			menuBar.add(getMnHelp());
		}
		return menuBar;
	}

	private JPanel getPanelPeople() {
		if (panelPeople == null) {
			panelPeople = new JPanel();
			panelPeople.setLayout(new GridLayout(2, 5, 0, 0));
			panelPeople.add(getLabelPeople());
			panelPeople.add(getFormattedTextFieldPeople());
		}
		return panelPeople;
	}

	private JLabel getLabelPeople() {
		if (labelPeople == null) {
			ResourceBundle resource = ResourceBundle.getBundle("rcs/mainWindow", location);
			labelPeople = new JLabel();
			labelPeople.setFont(new Font("Arial", Font.BOLD, 12));
			labelPeople.setText(resource.getString("people"));
		}
		return labelPeople;
	}

	JFormattedTextField getFormattedTextFieldPeople() {
		if (formattedTextFieldPeople == null) {
			ResourceBundle resource = ResourceBundle.getBundle("rcs/mainWindow", location);
			NumberFormat intFormat = NumberFormat.getIntegerInstance();
			NumberFormatter numberFormatter = new NumberFormatter(intFormat);
			numberFormatter.setValueClass(Integer.class);
			numberFormatter.setAllowsInvalid(false);
			numberFormatter.setMinimum(1);
			numberFormatter.setMaximum(1000000);
			formattedTextFieldPeople = new JFormattedTextField(numberFormatter);
			formattedTextFieldPeople.setFont(new Font("Arial", Font.BOLD, 22));
			formattedTextFieldPeople.setToolTipText(resource.getString("textFieldPeopleToolTip"));
			formattedTextFieldPeople.setValue(50);
		}
		return formattedTextFieldPeople;
	}

	private JLabel getLabelIcon() {
		if (labelIcon == null) {
			labelIcon = new JLabel();
			Image imgOriginal = new ImageIcon(getClass().getResource("/img/RPI.png")).getImage();
			Image imgEscalada = imgOriginal.getScaledInstance(100, 100, Image.SCALE_FAST);
			ImageIcon icon = new ImageIcon(imgEscalada);
			labelIcon.setIcon(icon);
			labelIcon.setDisabledIcon(icon);
		}
		return labelIcon;
	}

	private JTabbedPane getTabbedPane() {
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.setEnabled(true);
			int tab = 0;
			if (!this.collator.isEmpty()) {
				for (String type : this.collator.articleCategories) {
					JPanel localPanel = new JPanel();
					localPanel.setLayout(new GridLayout(this.collator.articleCategories.size(), 1));
					JScrollPane localScrollPane = new JScrollPane();
					localScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
					localScrollPane.setViewportView(panelProcess(localPanel, type));
					tabbedPane.add(localScrollPane);
					tabbedPane.setTitleAt(tab, type);
					tabbedPane.setMnemonicAt(tab, type.charAt(0));
					tab++;
				}
			} else
				JOptionPane.showMessageDialog(null, "Empty Catalogue!");
		}
		return tabbedPane;
	}

	private JPanel panelProcess(JPanel localPanel, String type) {

		List<Article> articles = this.collator.articlesByType(type);
		// Imagen - Name - Description - Price per unit - Price per group
		String ppu = "PPU";
		String ppg = "PPG";
		String[] headers = { "", "", "Info.", "Pr." };
		UneditableTableModel tableModel = new UneditableTableModel(headers, 0);
		JTable tableVentas = new JTable(tableModel);

		tableVentas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// tableVentas.setFont(new Font("Arial", Font.PLAIN, 24));
		tableVentas.setRowHeight(100);
		tableVentas.getTableHeader().setReorderingAllowed(false);
		tableVentas.getTableHeader().getColumnModel().getColumn(0).setPreferredWidth(100);

		tableVentas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 2) {
					ResourceBundle resource = ResourceBundle.getBundle("rcs/mainWindow", location);
					do {
						String articleName = (String) tableVentas.getValueAt(tableVentas.getSelectedRow(), 1);
						String response = null;
						for (Article localArticle : collator.getArticleList()) {
							if (localArticle.getName().equals(articleName)) {
								if (localArticle.getPriceUd() != 0)
									response = JOptionPane.showInputDialog(null, resource.getString("articleQuantity"),
											1);
								else {
									int confirm = JOptionPane.showConfirmDialog(null,
											resource.getString("groupArticlePurchase"), resource.getString("mainTitle"),
											JOptionPane.YES_NO_OPTION);
									if (confirm == JOptionPane.YES_OPTION)
										response = "1";
								}
							} else
								new RuntimeException("Wow! Something Went Wrong!");
						}
						if (response == null)
							break;
						else {
							int quantity = 0;
							try {
								quantity = Integer.parseInt(response);
							} catch (NumberFormatException nfe) {
							}
							// Maximum integer limit: 2147483647
							// Quantity between: [0, 2147483647]
							if (quantity > 0) {
								for (Article localArticle1 : collator.getArticleList()) {
									if (localArticle1.getName().equals(articleName))
										invoice.add(localArticle1, quantity);
									else
										new RuntimeException("Wow! Something Went Wrong!");
								}
								JOptionPane.showMessageDialog(null, resource.getString("purchasedArticles"));
								break;
							}
						}
					} while (true);
				}
			}
		});
		Object[] newRow = new Object[4];
		for (int i = 0; i < articles.size(); i++) {
			Image imgOriginal = new ImageIcon(getClass().getResource("/img/" + articles.get(i).getCode() + ".jpg"))
					.getImage();
			Image imgEscalada = imgOriginal.getScaledInstance(100, 100, Image.SCALE_FAST);
			ImageIcon icon = new ImageIcon(imgEscalada);
			tableVentas.getColumnModel().getColumn(0).setCellRenderer(tableVentas.getDefaultRenderer(ImageIcon.class));

			newRow[0] = icon;
			newRow[1] = articles.get(i).getName();
			newRow[2] = "<html>" + (String) articles.get(i).getDescription() + "</html>";
			if (articles.get(i).getPriceUd() != 0)
				newRow[3] = "<html>" + ppu + ": " + articles.get(i).getPriceUd() + " €" + "</html>";
			else
				newRow[3] = "<html>" + ppg + ": " + articles.get(i).getPriceGr() + " €" + "</html>";
			tableModel.addRow(newRow);
		}

		localPanel.add(tableVentas);

		return localPanel;
	}

	/**
	 * 'UneditableTableModel' internal class. This internal class forbids every
	 * table from being edited.
	 * 
	 * @author Iván Álvarez López - 71741444M
	 * @version January 2019
	 *
	 */
	public class UneditableTableModel extends DefaultTableModel {

		private static final long serialVersionUID = 827239316983512101L;

		/**
		 * 'UneditableTableModel' internal class constructor.
		 * 
		 * @param columnNames
		 *            Name of every column.
		 * @param rowCount
		 *            Quantity of rows.
		 */
		public UneditableTableModel(Object[] columnNames, int rowCount) {
			super(columnNames, rowCount);
		}

		/**
		 * 'isCellEditable(int row, int column)' overwritten method.
		 * 
		 * @param row
		 *            Row position.
		 * @param column
		 *            Column position.
		 * 
		 * @return Is that cell editable?
		 */
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	}

	private JPanel getPanelShoppingCart() {
		if (panelShoppingCart == null) {
			panelShoppingCart = new JPanel();
			panelShoppingCart.setLayout(new GridLayout(2, 1, 0, 0));
			panelShoppingCart.add(getLabelShoppingCart());
			panelShoppingCart.add(getButtonShoppingCart());
		}
		return panelShoppingCart;
	}

	private JLabel getLabelShoppingCart() {
		if (labelShoppingCart == null) {
			ResourceBundle resource = ResourceBundle.getBundle("rcs/mainWindow", location);
			labelShoppingCart = new JLabel();
			labelShoppingCart.setFont(new Font("Arial", Font.BOLD, 12));
			labelShoppingCart.setText(resource.getString("shoppingCartMessage"));
		}
		return labelShoppingCart;
	}

	private JButton getButtonShoppingCart() {
		if (buttonShoppingCart == null) {
			ResourceBundle resource = ResourceBundle.getBundle("rcs/mainWindow", location);
			buttonShoppingCart = new JButton();
			buttonShoppingCart.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					displayShoppingCartWindow();
				}
			});
			Image imgOriginal = new ImageIcon(getClass().getResource("/img/CDC.png")).getImage();
			Image imgEscalada = imgOriginal.getScaledInstance(100, 50, Image.SCALE_FAST);
			ImageIcon icon = new ImageIcon(imgEscalada);
			buttonShoppingCart.setIcon(icon);
			buttonShoppingCart.setDisabledIcon(icon);
			buttonShoppingCart.setToolTipText(resource.getString("shoppingCartToolTip"));
		}
		return buttonShoppingCart;
	}

	private JPanel getPanelPurchase() {
		if (panelPurchase == null) {
			panelPurchase = new JPanel();
			panelPurchase.setLayout(new GridLayout(2, 1, 0, 0));
			panelPurchase.add(getLabelPurchase());
			panelPurchase.add(getButtonPurchase());
		}
		return panelPurchase;
	}

	private JLabel getLabelPurchase() {
		if (labelPurchase == null) {
			ResourceBundle resource = ResourceBundle.getBundle("rcs/mainWindow", location);
			labelPurchase = new JLabel();
			labelPurchase.setFont(new Font("Arial", Font.BOLD, 12));
			labelPurchase.setText(resource.getString("proceedPurchase"));
		}
		return labelPurchase;
	}

	private JButton getButtonPurchase() {
		if (buttonPurchase == null) {
			buttonPurchase = new JButton();
			buttonPurchase.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (!invoice.getShoppingCart().isEmpty())
						displayFormWindow();
				}
			});
			Image imgOriginal = new ImageIcon(getClass().getResource("/img/PAC.png")).getImage();
			Image imgEscalada = imgOriginal.getScaledInstance(100, 50, Image.SCALE_FAST);
			ImageIcon icon = new ImageIcon(imgEscalada);
			buttonPurchase.setIcon(icon);
		}
		return buttonPurchase;
	}
}

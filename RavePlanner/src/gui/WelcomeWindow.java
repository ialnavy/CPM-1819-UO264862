package gui;

import java.net.*;
import javax.help.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jvnet.substance.SubstanceLookAndFeel;

import logic.Registrator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.BoxLayout;
import javax.swing.JPasswordField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.ButtonGroup;
import java.awt.FlowLayout;

/**
 * 'WelcomeWindow' GUI class. This is the first opened window of the
 * application. There the user can login, register & unregister.
 * 
 * @author Iván Álvarez López - 71741444M
 * @version January 2019
 *
 */
public class WelcomeWindow extends JFrame {

	private static final long serialVersionUID = -7356609098225392179L;

	private static final String MAIN_ICON = "/img/RPI.png";
	private static final String MAIN_BACKGROUND = "/img/RPB.jpg";
	private JPanel contentPane;
	private JPanel panelButtons;
	private JPanel panelCenter;
	private JButton btnLogin;
	private JButton btnRegister;
	private JPanel panelTextFields;
	private JPanel panelUsername;
	private JPanel panelPassword;
	private JLabel lblUsername;
	private JTextField textFieldUsername;
	private JLabel lblPassword;
	private JPanel panelMainIcon;
	private JLabel lblMainIcon;
	private JPasswordField passwordField;
	private JButton btnUnregister;
	private JButton btnLoginAnon;
	String logedUser;
	private JMenuBar menuBar;
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
	boolean anonymous;

	Locale location;

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
					WelcomeWindow frame = new WelcomeWindow();
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
	public WelcomeWindow() {
		location = Locale.getDefault(Locale.Category.FORMAT);
		ResourceBundle resource = ResourceBundle.getBundle("rcs/welcomeWindow", location);
		setResizable(true);
		setTitle(resource.getString("windowTitle"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(WelcomeWindow.class.getResource(MAIN_ICON)));
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (JOptionPane.showConfirmDialog(null, resource.getString("closeWindowConfirmation"),
						resource.getString("closeWindow"), JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		setBounds(100, 100, 775, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setLocationRelativeTo(null);
		setJMenuBar(getMenuBar_1());
		setContentPane(contentPane);
		contentPane.add(getPanelButtons(), BorderLayout.SOUTH);
		contentPane.add(getPanelCenter(), BorderLayout.CENTER);
		loadHelp();
	}

	/**
	 * 'register()' method. Performs the register process.
	 */
	private void register() {
		ResourceBundle resource = ResourceBundle.getBundle("rcs/welcomeWindow", location);
		if (!(textFieldUsername.getText().length() >= Registrator.USERNAME_LENGTH_MIN
				&& passwordField.getPassword().length >= Registrator.USERNAME_LENGTH_MIN))
			JOptionPane.showMessageDialog(null, resource.getString("shortArguments"));
		else if (Registrator.userAlreadyExists(textFieldUsername.getText()))
			JOptionPane.showMessageDialog(null, resource.getString("takenUsername"));
		else {
			Registrator.register(textFieldUsername.getText(), passwordParser(passwordField.getPassword()));
			JOptionPane.showMessageDialog(null, resource.getString("registerMessage"));
		}
		textFieldUsername.setText("");
		passwordField.setText("");
	}

	/**
	 * 'unregister()' method. Performs the unregister process.
	 */
	private void unregister() {
		ResourceBundle resource = ResourceBundle.getBundle("rcs/welcomeWindow", location);
		if (textFieldUsername.getText().length() == 0 || passwordField.getPassword().length == 0)
			JOptionPane.showMessageDialog(null, resource.getString("emptyForUnregister"));
		else if (!(textFieldUsername.getText().length() >= Registrator.USERNAME_LENGTH_MIN
				&& passwordField.getPassword().length >= Registrator.USERNAME_LENGTH_MIN))
			JOptionPane.showMessageDialog(null, resource.getString("badArguments"));
		else if (Registrator.login(textFieldUsername.getText(), passwordParser(passwordField.getPassword()))) {
			Registrator.unregister(textFieldUsername.getText(), passwordParser(passwordField.getPassword()));
			JOptionPane.showMessageDialog(null, resource.getString("unregisterMessage"));
		} else
			JOptionPane.showMessageDialog(null, resource.getString("badLogin"));
		textFieldUsername.setText("");
		passwordField.setText("");
	}

	/**
	 * 'login()' method. Performs the login process.
	 */
	private void login() {
		ResourceBundle resource = ResourceBundle.getBundle("rcs/welcomeWindow", location);
		if (textFieldUsername.getText().length() == 0 || passwordField.getPassword().length == 0)
			JOptionPane.showMessageDialog(null, resource.getString("emptyForLogin"));
		else if (!(textFieldUsername.getText().length() >= Registrator.USERNAME_LENGTH_MIN
				&& passwordField.getPassword().length >= Registrator.USERNAME_LENGTH_MIN))
			JOptionPane.showMessageDialog(null, resource.getString("badArguments"));
		else if (!(Registrator.login(textFieldUsername.getText(), passwordParser(passwordField.getPassword()))))
			JOptionPane.showMessageDialog(null, resource.getString("badLogin"));
		else {
			this.logedUser = textFieldUsername.getText();
			displayMainWindow();
		}
	}

	/**
	 * 'loginAnon()' method. Performs the anonymous login process.
	 */
	private void loginAnon() {
		do {
			this.logedUser = Registrator.randomUsername();
		} while (Registrator.userAlreadyExists(logedUser));
		this.anonymous = true;
		displayMainWindow();
	}

	/**
	 * 'displayMainWindow()' method. Enters to the main window of the application.
	 */
	private void displayMainWindow() {
		MainWindow vr = new MainWindow(this);
		vr.setLocationRelativeTo(this);
		vr.setVisible(true);
		this.setVisible(false);
	}

	/**
	 * 'passwordParser(char[] password)' method. Transforms a character array into a
	 * String.
	 * 
	 * @param password
	 *            Character array.
	 * @return String.
	 */
	private String passwordParser(char[] password) {
		String pass = "";
		for (int i = 0; i < password.length; i++) {
			pass = pass + password[i];
		}
		return pass;
	}

	/**
	 * 'locate(Locale location)' method. Given a specific location, changes the
	 * language of the 'WelcomeWindow' to the main language of the given location.
	 * 
	 * @param location
	 *            Given location.
	 */
	void locate(Locale location) {
		this.location = location;
		ResourceBundle resource = ResourceBundle.getBundle("rcs/welcomeWindow", location);

		setTitle(resource.getString("windowTitle"));

		btnLogin.setText(resource.getString("enter"));
		btnLogin.setToolTipText(resource.getString("enterToolTip"));
		if (location.getLanguage().equals("es") || location.getLanguage().equals("gl"))
			btnLogin.setMnemonic('E');
		else
			btnLogin.setMnemonic('L');

		btnRegister.setText(resource.getString("register"));
		btnRegister.setToolTipText(resource.getString("registerToolTip"));

		lblUsername.setText(resource.getString("usernameLabel"));
		lblPassword.setText(resource.getString("passwordLabel"));

		btnUnregister.setText(resource.getString("unregister"));
		btnUnregister.setToolTipText(resource.getString("unregisterToolTip"));
		if (location.getLanguage().equals("es") || location.getLanguage().equals("gl"))
			btnUnregister.setMnemonic('D');
		else
			btnUnregister.setMnemonic('U');

		btnLoginAnon.setText(resource.getString("loginAnonymously"));
		btnLoginAnon.setToolTipText(resource.getString("anonToolTip"));

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

		hb.enableHelpKey(getRootPane(), "login", hs);
		hb.enableHelpOnButton(this.mntmHelp, "login", hs);
		hb.enableHelp(this.btnLogin, "login", hs);
		hb.enableHelp(this.btnRegister, "login", hs);
		hb.enableHelp(this.btnLoginAnon, "login", hs);
		hb.enableHelp(this.btnUnregister, "login", hs);
	}

	// ##############
	// Getter methods
	// ##############

	/**
	 * 'getPanelButtons()' method.
	 * 
	 * @return panelButtons
	 */
	private JPanel getPanelButtons() {
		if (panelButtons == null) {
			panelButtons = new JPanel();
			panelButtons.setLayout(new GridLayout(0, 2, 0, 0));
			panelButtons.add(getBtnLogin());
			panelButtons.add(getBtnRegister());
			panelButtons.add(getBtnLoginAnon());
			panelButtons.add(getBtnUnregister());
		}
		return panelButtons;
	}

	/**
	 * 'getPanelCenter()' method.
	 * 
	 * @return panelCenter
	 */
	private JPanel getPanelCenter() {
		if (panelCenter == null) {
			panelCenter = new JPanel();
			panelCenter.setLayout(new BorderLayout(0, 0));
			panelCenter.add(getPanelTextFields(), BorderLayout.SOUTH);
			panelCenter.add(getPanelMainIcon(), BorderLayout.CENTER);
		}
		return panelCenter;
	}

	/**
	 * 'getBtnLogin()' method.
	 * 
	 * @return btnLogin
	 */
	private JButton getBtnLogin() {
		ResourceBundle resource = ResourceBundle.getBundle("rcs/welcomeWindow", location);
		if (btnLogin == null) {
			btnLogin = new JButton(resource.getString("enter"));
			btnLogin.setToolTipText(resource.getString("enterToolTip"));
			btnLogin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					login();
					getTextFieldUsername().setText("");
					getPasswordField().setText("");
				}
			});
			if (location.getLanguage().equals("es") || location.getLanguage().equals("gl"))
				btnLogin.setMnemonic('E');
			else
				btnLogin.setMnemonic('L');
		}
		return btnLogin;
	}

	/**
	 * 'getBtnRegister()' method.
	 * 
	 * @return btnRegister
	 */
	private JButton getBtnRegister() {
		ResourceBundle resource = ResourceBundle.getBundle("rcs/welcomeWindow", location);
		if (btnRegister == null) {
			btnRegister = new JButton(resource.getString("register"));
			btnRegister.setToolTipText(resource.getString("registerToolTip"));
			btnRegister.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					register();
				}
			});
			btnRegister.setMnemonic('R');
		}
		return btnRegister;
	}

	/**
	 * 'getPanelTextFields()' method.
	 * 
	 * @return panelTextFields
	 */
	private JPanel getPanelTextFields() {
		if (panelTextFields == null) {
			panelTextFields = new JPanel();
			panelTextFields.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			panelTextFields.add(getPanelUsername());
			panelTextFields.add(getPanelPassword());
		}
		return panelTextFields;
	}

	/**
	 * 'getPanelUsername()' method.
	 * 
	 * @return panelUsername
	 */
	private JPanel getPanelUsername() {
		if (panelUsername == null) {
			panelUsername = new JPanel();
			panelUsername.add(getLblUsername());
			panelUsername.add(getTextFieldUsername());
		}
		return panelUsername;
	}

	/**
	 * 'getPanelPassword()' method.
	 * 
	 * @return panelPassword
	 */
	private JPanel getPanelPassword() {
		if (panelPassword == null) {
			panelPassword = new JPanel();
			panelPassword.add(getLblPassword());
			panelPassword.add(getPasswordField());
		}
		return panelPassword;
	}

	/**
	 * 'getLblUsername()' method.
	 * 
	 * @return lblUsername
	 */
	private JLabel getLblUsername() {
		ResourceBundle resource = ResourceBundle.getBundle("rcs/welcomeWindow", location);
		if (lblUsername == null) {
			lblUsername = new JLabel(resource.getString("usernameLabel"));
		}
		return lblUsername;
	}

	/**
	 * 'getTextFieldUsername()' method.
	 * 
	 * @return textFieldUsername
	 */
	private JTextField getTextFieldUsername() {
		if (textFieldUsername == null) {
			textFieldUsername = new JTextField();
			textFieldUsername.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent arg0) {
					if (textFieldUsername.getText().length() >= Registrator.USERNAME_LENGTH_MAX)
						arg0.consume();
				}
			});
			textFieldUsername.setColumns(10);
		}
		return textFieldUsername;
	}

	/**
	 * 'getLblPassword()' method.
	 * 
	 * @return lblPassword
	 */
	private JLabel getLblPassword() {
		ResourceBundle resource = ResourceBundle.getBundle("rcs/welcomeWindow", location);
		if (lblPassword == null) {
			lblPassword = new JLabel(resource.getString("passwordLabel"));
		}
		return lblPassword;
	}

	/**
	 * 'getPanelMainIcon()' method.
	 * 
	 * @return panelMainIcon
	 */
	private JPanel getPanelMainIcon() {
		if (panelMainIcon == null) {
			panelMainIcon = new JPanel();
			panelMainIcon.addComponentListener(new ComponentAdapter() {
				@Override
				public void componentResized(ComponentEvent arg0) {
					Image imgOriginal = new ImageIcon(getClass().getResource(MAIN_BACKGROUND)).getImage();
					Image imgEscalada = imgOriginal.getScaledInstance((int) (panelMainIcon.getWidth()),
							(int) (panelMainIcon.getHeight()), Image.SCALE_FAST);
					ImageIcon icon = new ImageIcon(imgEscalada);
					getLblMainIcon().setIcon(icon);
					getLblMainIcon().setDisabledIcon(icon);
				}
			});
			panelMainIcon.setLayout(new BoxLayout(panelMainIcon, BoxLayout.X_AXIS));
			panelMainIcon.add(getLblMainIcon());
		}
		return panelMainIcon;
	}

	/**
	 * 'getLblMainIcon()' method.
	 * 
	 * @return lblMainIcon
	 */
	private JLabel getLblMainIcon() {
		if (lblMainIcon == null) {
			lblMainIcon = new JLabel("");
		}
		return lblMainIcon;
	}

	/**
	 * 'getPasswordField()' method.
	 * 
	 * @return passwordField
	 */
	private JPasswordField getPasswordField() {
		if (passwordField == null) {
			passwordField = new JPasswordField();
			passwordField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					if (passwordField.getPassword().length >= Registrator.USERNAME_LENGTH_MAX)
						e.consume();
				}
			});
			passwordField.setColumns(10);
		}
		return passwordField;
	}

	/**
	 * 'getBtnUnregister()' method.
	 * 
	 * @return btnUnregister
	 */
	private JButton getBtnUnregister() {
		ResourceBundle resource = ResourceBundle.getBundle("rcs/welcomeWindow", location);
		if (btnUnregister == null) {
			btnUnregister = new JButton(resource.getString("unregister"));
			btnUnregister.setToolTipText(resource.getString("unregisterToolTip"));
			btnUnregister.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					unregister();
				}
			});
			if (location.getLanguage().equals("es") || location.getLanguage().equals("gl"))
				btnUnregister.setMnemonic('D');
			else
				btnUnregister.setMnemonic('U');
		}
		return btnUnregister;
	}

	/**
	 * 'getBtnLoginAnon()' method.
	 * 
	 * @return btnLoginAnon
	 */
	private JButton getBtnLoginAnon() {
		ResourceBundle resource = ResourceBundle.getBundle("rcs/welcomeWindow", location);
		if (btnLoginAnon == null) {
			btnLoginAnon = new JButton(resource.getString("loginAnonymously"));
			btnLoginAnon.setToolTipText(resource.getString("anonToolTip"));
			btnLoginAnon.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					loginAnon();
					getTextFieldUsername().setText("");
					getPasswordField().setText("");
				}
			});
			btnLoginAnon.setMnemonic('a');
		}
		return btnLoginAnon;
	}

	/**
	 * 'getMenuBar_1()' method.
	 * 
	 * @return menuBar
	 */
	private JMenuBar getMenuBar_1() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			menuBar.add(getMnFile());
			menuBar.add(getMnLanguage());
			menuBar.add(getMnHelp());
		}
		return menuBar;
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
}

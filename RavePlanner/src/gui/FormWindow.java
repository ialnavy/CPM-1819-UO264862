package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import org.jvnet.substance.SubstanceLookAndFeel;
import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 'FormWindow' GUI class. Requests the user's information.
 * 
 * @author Iván Álvarez López - 71741444M
 * @version January 2019
 *
 */
public class FormWindow extends JFrame {

	private static final long serialVersionUID = -8373638729518943726L;
	private JPanel contentPane;
	MainWindow mainWindow;
	Locale location;
	private JPanel panelForm;
	private JLabel labelName;
	private JPanel panelUserData;
	private JTextField textFieldName;
	private JPanel panelName;
	private JPanel panelSurname;
	private JLabel labelSurname;
	private JTextField textFieldSurname;
	private JPanel panelDocumentation;
	private JLabel labelID;
	private JTextField textFieldID;
	private JPanel panelID;
	private JPanel panelDate;
	private JLabel labelDate;
	private JFormattedTextField formattedTextFieldDate;
	private JPanel panelCenter;
	private JLabel labelObservations;
	private JTextPane textPaneObservations;
	private JPanel panelButtons;
	private JButton buttonAccept;
	private JButton buttonCancel;

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
					FormWindow frame = new FormWindow(new MainWindow(new WelcomeWindow()));
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
	public FormWindow(MainWindow mainWindow) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(FormWindow.class.getResource("/img/RPI.png")));
		this.mainWindow = mainWindow;
		this.location = this.mainWindow.location;
		ResourceBundle resource = ResourceBundle.getBundle("rcs/formWindow", location);
		this.setTitle(resource.getString("windowTitle"));
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				cancelOperation();
			}
		});
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPanelForm(), BorderLayout.NORTH);
		contentPane.add(getPanelCenter(), BorderLayout.CENTER);
		contentPane.add(getPanelButtons(), BorderLayout.SOUTH);
	}

	private void cancelOperation() {
		ResourceBundle resource = ResourceBundle.getBundle("rcs/formWindow", location);
		if (JOptionPane.showConfirmDialog(null, resource.getString("cancelWindowConfirmation"),
				resource.getString("cancelWindow"), JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			mainWindow.setEnabled(true);
			setVisible(false);
			dispose();
		}
	}

	/**
	 * 'displayFormWindow()' method. Enters to the form window of the application.
	 */
	private void displayInvoiceWindow() {
		InvoiceWindow fw = new InvoiceWindow(this);
		fw.setLocationRelativeTo(this);
		fw.setVisible(true);
		this.setEnabled(false);
	}

	private JPanel getPanelForm() {
		if (panelForm == null) {
			panelForm = new JPanel();
			panelForm.setLayout(new GridLayout(2, 1, 0, 0));
			panelForm.add(getPanelUserData());
			panelForm.add(getPanelDocumentation());
		}
		return panelForm;
	}

	private JLabel getLabelName() {
		if (labelName == null) {
			ResourceBundle resource = ResourceBundle.getBundle("rcs/formWindow", location);
			labelName = new JLabel();
			labelName.setText(resource.getString("name"));
			labelName.setFont(new Font("Arial", Font.BOLD, 12));
		}
		return labelName;
	}

	private JPanel getPanelUserData() {
		if (panelUserData == null) {
			panelUserData = new JPanel();
			panelUserData.setLayout(new GridLayout(0, 2, 0, 0));
			panelUserData.add(getPanelName());
			panelUserData.add(getPanelSurname());
		}
		return panelUserData;
	}

	JTextField getTextFieldName() {
		if (textFieldName == null) {
			textFieldName = new JTextField();
			textFieldName.setColumns(10);
		}
		return textFieldName;
	}

	private JPanel getPanelName() {
		if (panelName == null) {
			panelName = new JPanel();
			panelName.setLayout(new GridLayout(1, 2, 0, 0));
			panelName.add(getLabelName());
			panelName.add(getTextFieldName());
		}
		return panelName;
	}

	private JPanel getPanelSurname() {
		if (panelSurname == null) {
			panelSurname = new JPanel();
			panelSurname.setLayout(new GridLayout(1, 2, 0, 0));
			panelSurname.add(getLabelSurname());
			panelSurname.add(getTextFieldSurname());
		}
		return panelSurname;
	}

	private JLabel getLabelSurname() {
		if (labelSurname == null) {
			ResourceBundle resource = ResourceBundle.getBundle("rcs/formWindow", location);
			labelSurname = new JLabel();
			labelSurname.setText(resource.getString("surname"));
			labelSurname.setFont(new Font("Arial", Font.BOLD, 12));
		}
		return labelSurname;
	}

	JTextField getTextFieldSurname() {
		if (textFieldSurname == null) {
			textFieldSurname = new JTextField();
			textFieldSurname.setColumns(10);
		}
		return textFieldSurname;
	}

	private JPanel getPanelDocumentation() {
		if (panelDocumentation == null) {
			panelDocumentation = new JPanel();
			panelDocumentation.setLayout(new GridLayout(1, 2, 0, 0));
			panelDocumentation.add(getPanelID());
			panelDocumentation.add(getPanelDate());
		}
		return panelDocumentation;
	}

	private JLabel getLabelID() {
		if (labelID == null) {
			ResourceBundle resource = ResourceBundle.getBundle("rcs/formWindow", location);
			labelID = new JLabel();
			labelID.setText(resource.getString("id"));
			labelID.setFont(new Font("Arial", Font.BOLD, 12));
		}
		return labelID;
	}

	JTextField getTextFieldID() {
		if (textFieldID == null) {
			textFieldID = new JTextField();
			textFieldID.setColumns(10);
		}
		return textFieldID;
	}

	private JPanel getPanelID() {
		if (panelID == null) {
			panelID = new JPanel();
			panelID.setLayout(new GridLayout(1, 2, 0, 0));
			panelID.add(getLabelID());
			panelID.add(getTextFieldID());
		}
		return panelID;
	}

	private JPanel getPanelDate() {
		if (panelDate == null) {
			panelDate = new JPanel();
			panelDate.setLayout(new GridLayout(1, 2, 0, 0));
			panelDate.add(getLabelDate());
			panelDate.add(getFormattedTextFieldDate());
		}
		return panelDate;
	}

	private JLabel getLabelDate() {
		if (labelDate == null) {
			ResourceBundle resource = ResourceBundle.getBundle("rcs/formWindow", location);
			labelDate = new JLabel();
			labelDate.setText("<html>" + resource.getString("date") + "</html>");
			labelDate.setFont(new Font("Arial", Font.BOLD, 12));
		}
		return labelDate;
	}

	JFormattedTextField getFormattedTextFieldDate() {
		if (formattedTextFieldDate == null) {
			DateFormat df = null;
			if (location.getLanguage().equals("es") || location.getLanguage().equals("gl"))
				df = new SimpleDateFormat("dd/MM/yyyy");
			else
				df = new SimpleDateFormat("MM/dd/yyyy");
			formattedTextFieldDate = new JFormattedTextField(df);
		}
		return formattedTextFieldDate;
	}

	private JPanel getPanelCenter() {
		if (panelCenter == null) {
			panelCenter = new JPanel();
			panelCenter.setLayout(new BorderLayout(0, 0));
			panelCenter.add(getLabelObservations(), BorderLayout.NORTH);
			panelCenter.add(getTextPaneObservations(), BorderLayout.CENTER);
		}
		return panelCenter;
	}

	private JLabel getLabelObservations() {
		if (labelObservations == null) {
			ResourceBundle resource = ResourceBundle.getBundle("rcs/formWindow", location);
			labelObservations = new JLabel();
			labelObservations.setText(resource.getString("observations"));
			labelObservations.setFont(new Font("Arial", Font.BOLD, 12));
		}
		return labelObservations;
	}

	JTextPane getTextPaneObservations() {
		if (textPaneObservations == null) {
			textPaneObservations = new JTextPane();
		}
		return textPaneObservations;
	}

	private JPanel getPanelButtons() {
		if (panelButtons == null) {
			panelButtons = new JPanel();
			panelButtons.setLayout(new GridLayout(0, 2, 0, 0));
			panelButtons.add(getButtonAccept());
			panelButtons.add(getButtonCancel());
		}
		return panelButtons;
	}

	private JButton getButtonAccept() {
		if (buttonAccept == null) {
			ResourceBundle resource = ResourceBundle.getBundle("rcs/formWindow", location);
			buttonAccept = new JButton();
			buttonAccept.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getTextPaneObservations().grabFocus();
					boolean textField = true;
					@SuppressWarnings("unused")
					MaskFormatter formatter = null;
					try {
						formatter = new MaskFormatter(getFormattedTextFieldDate().getText());
					} catch (java.text.ParseException exc) {
						textField = false;
					}
					if (!getTextFieldName().getText().equals("") && !getTextFieldSurname().getText().equals("")
							&& !getTextFieldID().getText().equals("") && textField
							&& !getFormattedTextFieldDate().getText().equals(""))
						displayInvoiceWindow();
					else
						JOptionPane.showMessageDialog(null, resource.getString("nonFilledMarkedFields"));
				}
			});
			buttonAccept.setText(resource.getString("accept"));
			buttonAccept.setMnemonic(resource.getString("accept").charAt(0));
			buttonAccept.setToolTipText(resource.getString("acceptToolTip"));
		}
		return buttonAccept;
	}

	private JButton getButtonCancel() {
		if (buttonCancel == null) {
			buttonCancel = new JButton();
			buttonCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					cancelOperation();
				}
			});
			ResourceBundle resource = ResourceBundle.getBundle("rcs/formWindow", location);
			buttonCancel.setText(resource.getString("cancel"));
			buttonCancel.setMnemonic(resource.getString("cancel").charAt(0));
			buttonCancel.setToolTipText(resource.getString("cancelToolTip"));
		}
		return buttonCancel;
	}
}

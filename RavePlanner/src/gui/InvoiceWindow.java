package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jvnet.substance.SubstanceLookAndFeel;

import logic.Invoice;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JTextPane;

/**
 * 'InvoiceWindow' GUI class. Finishes the purchase process.
 * 
 * @author Iván Álvarez López - 71741444M
 * @version January 2019
 *
 */
public class InvoiceWindow extends JFrame {

	private static final long serialVersionUID = -3859954524190333789L;
	private JPanel contentPane;
	private FormWindow formWindow;
	private Locale location;
	private Invoice invoice;
	private JPanel panelSouth;
	private JPanel panelCenter;
	private JButton buttonAccept;
	private JButton buttonCancel;
	private JTextPane textPaneInvoice;

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
					InvoiceWindow frame = new InvoiceWindow(new FormWindow(new MainWindow(new WelcomeWindow())));
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
	public InvoiceWindow(FormWindow formWindow) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(FormWindow.class.getResource("/img/RPI.png")));
		this.formWindow = formWindow;
		this.location = this.formWindow.location;
		this.invoice = this.formWindow.mainWindow.invoice;
		ResourceBundle resource = ResourceBundle.getBundle("rcs/formWindow", location);
		this.setTitle(resource.getString("invoiceTitle"));
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
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanelSouth(), BorderLayout.SOUTH);
		contentPane.add(getPanelCenter(), BorderLayout.CENTER);
	}

	private void cancelOperation() {
		ResourceBundle resource = ResourceBundle.getBundle("rcs/formWindow", location);
		if (JOptionPane.showConfirmDialog(null, resource.getString("cancelWindowConfirmation"),
				resource.getString("cancelWindow"), JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			formWindow.setEnabled(true);
			setVisible(false);
			dispose();
		}
	}

	private JPanel getPanelSouth() {
		if (panelSouth == null) {
			panelSouth = new JPanel();
			panelSouth.setLayout(new GridLayout(1, 2, 0, 0));
			panelSouth.add(getButtonAccept());
			panelSouth.add(getButton_1());
		}
		return panelSouth;
	}

	private JPanel getPanelCenter() {
		if (panelCenter == null) {
			panelCenter = new JPanel();
			panelCenter.setLayout(new GridLayout(0, 1, 0, 0));
			panelCenter.add(getTextPaneInvoice());
		}
		return panelCenter;
	}

	private JButton getButtonAccept() {
		if (buttonAccept == null) {
			ResourceBundle resource = ResourceBundle.getBundle("rcs/formWindow", location);
			buttonAccept = new JButton();
			buttonAccept.addActionListener(new ActionListener() {
				@SuppressWarnings("static-access")
				public void actionPerformed(ActionEvent e) {
					if (JOptionPane.showConfirmDialog(null, resource.getString("terminateOperationWindowConfirmation"),
							resource.getString("terminateOperationWindow"), JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
						invoice.typeInvoice(formWindow.mainWindow.welcomeWindow.logedUser,
								formWindow.getTextFieldName().getText(), formWindow.getTextFieldSurname().getText(),
								formWindow.getTextFieldID().getText(), formWindow.getFormattedTextFieldDate().getText(),
								Integer.parseInt(formWindow.mainWindow.getFormattedTextFieldPeople().getText()),
								formWindow.getTextPaneObservations().getText(), location,
								formWindow.mainWindow.welcomeWindow.anonymous);
						JOptionPane.showMessageDialog(null, resource.getString("endOfOperation"));
						formWindow.mainWindow.welcomeWindow.setVisible(true);
						formWindow.mainWindow.setVisible(false);
						formWindow.mainWindow.dispose();
						formWindow.setVisible(false);
						formWindow.dispose();
						setVisible(false);
						dispose();
					}
				}
			});
			buttonAccept.setText(resource.getString("accept"));
			buttonAccept.setMnemonic(resource.getString("accept").charAt(0));
			buttonAccept.setToolTipText(resource.getString("acceptToolTip"));
		}
		return buttonAccept;
	}

	private JButton getButton_1() {
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

	@SuppressWarnings("static-access")
	private JTextPane getTextPaneInvoice() {
		if (textPaneInvoice == null) {
			textPaneInvoice = new JTextPane();
			textPaneInvoice.setEditable(false);
			textPaneInvoice.setText(this.invoice.typeInvoice(formWindow.mainWindow.welcomeWindow.logedUser,
					formWindow.getTextFieldName().getText(), formWindow.getTextFieldSurname().getText(),
					formWindow.getTextFieldID().getText(), formWindow.getFormattedTextFieldDate().getText(),
					Integer.parseInt(formWindow.mainWindow.getFormattedTextFieldPeople().getText()),
					formWindow.getTextPaneObservations().getText(), location,
					formWindow.mainWindow.welcomeWindow.anonymous));
		}
		return textPaneInvoice;
	}
}

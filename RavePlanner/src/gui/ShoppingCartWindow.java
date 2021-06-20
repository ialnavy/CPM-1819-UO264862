package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.jvnet.substance.SubstanceLookAndFeel;

import logic.Article;
import logic.Collator;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

/**
 * 'ShoppingCartWindow' GUI class. Shows the articles the user has been
 * selecting.
 * 
 * @author Iván Álvarez López - 71741444M
 * @version January 2019
 *
 */
public class ShoppingCartWindow extends JFrame {

	private static final long serialVersionUID = 4610451649792448898L;
	private JPanel contentPane;
	private MainWindow mainWindow;
	private Locale location;
	private JScrollPane scrollPaneShoppingCart;
	private Collator collator;

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
					ShoppingCartWindow frame = new ShoppingCartWindow(new MainWindow(new WelcomeWindow()));
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
	public ShoppingCartWindow(MainWindow mainWindow) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ShoppingCartWindow.class.getResource("/img/RPI.png")));
		this.mainWindow = mainWindow;
		this.collator = mainWindow.collator;
		this.location = mainWindow.getLocale();
		ResourceBundle resource = ResourceBundle.getBundle("rcs/shoppingCartWindow", location);
		setTitle(resource.getString("windowTitle"));
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				mainWindow.setEnabled(true);
				setVisible(false);
				dispose();
			}
		});
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 1, 0, 0));
		contentPane.add(getScrollPaneShoppingCart());
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

	/**
	 * 'emptyShoppingCart()' method. Displays an empty shopping cart.
	 * 
	 * @return Panel with a message.
	 */
	private JPanel emptyShoppingCart() {
		ResourceBundle resource = ResourceBundle.getBundle("rcs/shoppingCartWindow", location);
		JPanel localPanel = new JPanel();
		JLabel labelInvoice = new JLabel("<html>" + resource.getString("emptyShoppingCart") + "</html>");
		labelInvoice.setFont(new Font("Arial", Font.PLAIN, 24));
		localPanel.setLayout(new GridLayout(1, 1, 0, 0));
		localPanel.add(labelInvoice);
		return localPanel;
	}

	/**
	 * 'previewInvoiceProcess()' method. Displays the shopping cart.
	 * 
	 * @return Panel with the shopping cart.
	 */
	private JPanel previewInvoiceProcess() {
		JPanel localPanel = new JPanel();
		Map<Article, Integer> shoppingCart = this.mainWindow.invoice.getShoppingCart();
		// Imagen - Name - Description - Price per unit - Price per group
		String[] headers = { "Photo", "Name", "Quantity", "Price" };
		UneditableTableModel tableModel = new UneditableTableModel(headers, 0);
		JTable tableInvoice = new JTable(tableModel);

		tableInvoice.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// tableVentas.setFont(new Font("Arial", Font.PLAIN, 24));
		tableInvoice.setRowHeight(100);
		tableInvoice.getTableHeader().setReorderingAllowed(false);
		tableInvoice.getTableHeader().getColumnModel().getColumn(0).setPreferredWidth(100);

		tableInvoice.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 2) {
					ResourceBundle resource = ResourceBundle.getBundle("rcs/shoppingCartWindow", location);
					Article article = null;
					do {
						String articleName = (String) tableInvoice.getValueAt(tableInvoice.getSelectedRow(), 1);
						String response = null;
						for (Article localArticle : collator.getArticleList()) {
							if (localArticle.getName().equals(articleName)) {
								article = localArticle;
								if (localArticle.getPriceUd() != 0)
									response = JOptionPane.showInputDialog(null, resource.getString("articleQuantity"),
											1);
								else {
									int confirm = JOptionPane.showConfirmDialog(null,
											resource.getString("groupArticleRemove"), resource.getString("mainTitle"),
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
							if (quantity > 0 && quantity <= shoppingCart.get(article)) {
								for (Article localArticle1 : mainWindow.collator.getArticleList()) {
									if (localArticle1.getName().equals(articleName))
										mainWindow.invoice.remove(localArticle1, quantity);
									else
										new RuntimeException("Wow! Something Went Wrong!");
								}
								JOptionPane.showMessageDialog(null, resource.getString("shoppingCartUpdated"));
								mainWindow.setEnabled(true);
								setVisible(false);
								dispose();
								break;
							}
						}
					} while (true);
				}
			}
		});

		Object[] newRow = new Object[4];
		for (Map.Entry<Article, Integer> entry : shoppingCart.entrySet()) {
			Image imgOriginal = new ImageIcon(getClass().getResource("/img/" + entry.getKey().getCode() + ".jpg"))
					.getImage();
			Image imgEscalada = imgOriginal.getScaledInstance(100, 100, Image.SCALE_FAST);
			ImageIcon icon = new ImageIcon(imgEscalada);
			tableInvoice.getColumnModel().getColumn(0)
					.setCellRenderer(tableInvoice.getDefaultRenderer(ImageIcon.class));

			newRow[0] = icon;
			newRow[1] = entry.getKey().getName();
			newRow[2] = entry.getValue() + " uds.";
			if (entry.getKey().getPriceUd() != 0)
				newRow[3] = entry.getValue() * entry.getKey().getPriceUd() + " €";
			else
				newRow[3] = entry.getKey().getPriceGr() + " €";
			tableModel.addRow(newRow);
		}
		localPanel.setLayout(new GridLayout(1, 1, 0, 0));

		localPanel.add(tableInvoice);

		return localPanel;
	}

	// ##############
	// Getter methods
	// ##############

	/**
	 * 'getScrollPaneShoppingCart()' getter method.
	 * 
	 * @return Main scroll pane.
	 */
	private JScrollPane getScrollPaneShoppingCart() {
		if (scrollPaneShoppingCart == null) {
			scrollPaneShoppingCart = new JScrollPane();
			scrollPaneShoppingCart.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			if (this.mainWindow.invoice.getShoppingCart().isEmpty())
				scrollPaneShoppingCart.setViewportView(emptyShoppingCart());
			else
				scrollPaneShoppingCart.setViewportView(previewInvoiceProcess());
		}
		return scrollPaneShoppingCart;
	}
}

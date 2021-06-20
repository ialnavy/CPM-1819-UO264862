package logic;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 'Invoice' class. Saves all the products the user is purchasing.
 * 
 * @author Iván Álvarez López - 71741444M
 * @version January 2019
 *
 */
public class Invoice {

	private Map<Article, Integer> shoppingCart;
	private String generatedInvoice;

	/**
	 * 'Invoice' class constructor.
	 */
	public Invoice() {
		this.shoppingCart = new HashMap<Article, Integer>();
	}

	// ##############
	// Getter methods
	// ##############

	/**
	 * 'getShoppingCart()' getter method.
	 * 
	 * @return Shopping cart.
	 */
	public Map<Article, Integer> getShoppingCart() {
		return this.shoppingCart;
	}

	/**
	 * 'add(Article article, Integer quantity)' method. Gets a quantity of an
	 * article into the shopping cart.
	 * 
	 * @param article
	 *            Article to be added.
	 * @param quantity
	 *            Quantity of this article to be added.
	 */
	public void add(Article article, Integer quantity) {
		if (article.getPriceUd() != 0) {
			if (exists(article))
				getShoppingCart().replace(article, getShoppingCart().get(article),
						getShoppingCart().get(article) + quantity);
			else
				getShoppingCart().put(article, quantity);
		} else if (!exists(article))
			getShoppingCart().put(article, quantity);
	}

	/**
	 * 'remove(Article article, Integer quantity)' method. Removes a quantity of an
	 * article from the shopping cart.
	 * 
	 * @param article
	 *            Article to be removed.
	 * @param quantity
	 *            Quantity of this article to be removed.
	 */
	public void remove(Article article, Integer quantity) {
		if (exists(article)) {
			if (article.getPriceUd() != 0) {
				if (getShoppingCart().get(article).equals(quantity))
					getShoppingCart().remove(article, quantity);
				else
					getShoppingCart().replace(article, getShoppingCart().get(article),
							getShoppingCart().get(article) - quantity);
			} else
				getShoppingCart().remove(article, quantity);
		}
	}

	/**
	 * 'exists(Article article)' method. Returns whether an article already exists
	 * in the shopping cart.
	 * 
	 * @param article
	 *            Article to be searched.
	 * @return Does it exist?
	 */
	private boolean exists(Article article) {
		return getShoppingCart().containsKey(article);
	}

	/**
	 * 'generateInvoice(String username, String name, String surname, String id,
	 * String date, int people, String observations, Locale location, boolean
	 * anonymous)' method. Generates the final invoice.
	 * 
	 * @param username
	 *            User's username.
	 * @param name
	 *            User's name.
	 * @param surname
	 *            User's surname.
	 * @param id
	 *            User's identification.
	 * @param date
	 *            Party's date.
	 * @param people
	 *            Quantity of assistants to the party.
	 * @param observations
	 *            Observations given by the customer.
	 * @param location
	 *            Language manager.
	 * @param anonymous
	 *            Did the user loged anonymously?
	 */
	private void generateInvoice(String username, String name, String surname, String id, String date, int people,
			String observations, Locale location, boolean anonymous) {
		ResourceBundle resource = ResourceBundle.getBundle("rcs/invoice", location);
		StringBuilder sB = new StringBuilder();
		String title = resource.getString("title");
		sB.append(title + "\n");
		StringBuilder headSeparator = new StringBuilder();
		for (int i = 0; i < title.length() * (3 / 2); i++) {
			headSeparator.append('-');
		}
		sB.append(headSeparator.toString() + "\n");
		String client = resource.getString("client");
		name = name.trim();
		surname = surname.trim();
		username = username.trim();
		id = id.trim();
		// Third Line
		StringBuilder head = new StringBuilder();
		for (int i = 0; i < 2; i++) {
			head.append('*');
		}
		sB.append(head.toString() + " " + client + ": " + name + " " + surname + " ("
				+ resource.getString("registeredClient") + ": " + username + ")" + "\n");
		// Fourth Line
		sB.append(head.toString() + " " + resource.getString("id") + ": " + id + "\n");
		// Fifth Line
		sB.append(head.toString() + " " + resource.getString("date") + ": " + date + "\n");
		// Sixth Line
		sB.append(head.toString() + " " + resource.getString("people") + ": " + people + "\n");
		// Seventh Line
		sB.append("\n");
		// Eighth Line
		String cS = resource.getString("list") + ": " + resource.getString("name") + " / " + resource.getString("code")
				+ " / " + resource.getString("units") + " / " + resource.getString("totalPrice");
		sB.append(cS + "\n");
		// Ninth Line
		StringBuilder separator = new StringBuilder();
		for (int i = 0; i < cS.length() * (3 / 2); i++) {
			separator.append('-');
		}
		sB.append(separator.toString() + "\n");

		// Categories Order
		ArrayList<String> categories = new ArrayList<String>();
		for (Map.Entry<Article, Integer> entry : this.shoppingCart.entrySet()) {
			String category = entry.getKey().getType();
			if (!categories.contains(category))
				categories.add(category);
		}
		// Body
		for (String category : categories) {
			sB.append(category + ":" + "\n");
			for (Map.Entry<Article, Integer> entry : this.shoppingCart.entrySet()) {
				if (entry.getKey().getType().equals(category)) {
					Float price = 0f;
					if (entry.getKey().getPriceUd() != 0)
						price = entry.getKey().getPriceUd();
					else
						price = entry.getKey().getPriceGr();

					sB.append("* " + entry.getKey().getName() + " / " + entry.getKey().getCode() + " / "
							+ entry.getValue() + " / " + (price * entry.getValue().floatValue()) + "€" + "\n");
				}
			}
		}
		sB.append("\n");
		// Observations
		String observs = resource.getString("observations");
		sB.append(observs + "\n");
		separator = new StringBuilder();
		for (int i = 0; i < observs.length() * (3 / 2); i++) {
			separator.append('-');
		}
		sB.append(separator.toString() + "\n");
		sB.append(observations + "\n");
		sB.append("\n");

		// Final Price
		float totalPrice = 0f;
		for (Map.Entry<Article, Integer> entry : this.shoppingCart.entrySet()) {
			if (entry.getKey().getPriceUd() != 0)
				totalPrice += entry.getKey().getPriceUd() * entry.getValue().floatValue();
			else
				totalPrice += entry.getKey().getPriceGr();
		}
		if (anonymous)
			sB.append(resource.getString("anonymousInvoice") + ": " + totalPrice + "€" + "\n");
		else {
			float discount = (15f / 100f) * totalPrice;
			sB.append(resource.getString("logedInvoice") + ": " + totalPrice + "€" + " - " + discount + "€ = "
					+ (totalPrice - discount) + "€" + "\n");
		}

		this.generatedInvoice = sB.toString();
	}

	/**
	 * 'typeInvoice(String username, String name, String surname, String id, String
	 * date, int people, String observations, Locale location, boolean anonymous)'
	 * method. Generates the final invoice & returns it as a String value.
	 * 
	 * @param username
	 *            User's username.
	 * @param name
	 *            User's name.
	 * @param surname
	 *            User's surname.
	 * @param id
	 *            User's identification.
	 * @param date
	 *            Party's date.
	 * @param people
	 *            Quantity of assistants to the party.
	 * @param observations
	 *            Observations given by the customer.
	 * @param location
	 *            Language manager.
	 * @param anonymous
	 *            Did the user loged anonymously?
	 * @return Invoice, as a String value.
	 */
	public String typeInvoice(String username, String name, String surname, String id, String date, int people,
			String observations, Locale location, boolean anonymous) {
		generateInvoice(username, name, surname, id, date, people, observations, location, anonymous);
		String destination = "files/invoice/" + ((id + date).replaceAll("[^A-Za-z0-9]", "")) + ".txt";
		try {
			FileOutputStream os = new FileOutputStream(destination);
			PrintStream ps = new PrintStream(os);
			ps.println(this.generatedInvoice);
			ps.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.generatedInvoice;
	}
}
package logic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.processing.FilerException;
import javax.swing.JOptionPane;

/**
 * 'Collator' class. Collator class classifies every article in its list &
 * manages them.
 * 
 * @author Iván Álvarez López - 71741444M
 * @version January 2019
 *
 */
public class Collator {

	public static final int MAX_PEOPLE = 5000;
	public static final int MAX_UNITS = 5000;

	private List<Article> articleList;
	private int people;
	public List<String> articleCategories;

	/**
	 * Collator class constructor. Creates a new list as an ArrayList & collates all
	 * the articles among it.
	 */
	public Collator() {
		this.articleList = new ArrayList<Article>();
		this.articleCategories = new ArrayList<String>();
		collate();
	}

	/**
	 * 'collate()' method. Collates every article in the list.
	 */
	public void collate() {
		String line = "";
		int counter = 1;
		try {
			BufferedReader reader = new BufferedReader(new FileReader("files/fiesta.txt"));
			while (reader.ready()) {
				line = reader.readLine();
				try {
					String[] components = line.split("@");
					if (components.length != 6) {
						reader.close();
						throw new FilerException("While reading the source file: Specification error in line number "
								+ counter
								+ ". The quantity of parameters especified are not the appropiate. Reader closed.");
					}
					for (int i = 0; i < components.length; i++) {
						components[i] = components[i].trim();
						if (components[i] == "") {
							reader.close();
							throw new FilerException(
									"While reading the source file: Specification error in line number " + counter
											+ ". The parameter number " + i + " is empty. Reader closed.");
						}
					}
					counter++;
					articleList.add(new Article(components[0], components[1], components[2], components[3],
							Float.parseFloat(components[4]), Float.parseFloat(components[5])));
					// codigo@tipo@denominación@descripción@precioUnidad@precioGrupo
				} catch (FilerException fe) {
					JOptionPane.showMessageDialog(null, fe.getMessage());
				}
			}
			reader.close();
		} catch (FileNotFoundException fnfe) {
			JOptionPane.showMessageDialog(null, "File Not Found!");
		} catch (IOException ioe) {
			new RuntimeException("IO error!");
		}
		sortCategories();
	}

	/**
	 * 'sortCategories()' method. Establish a list of String values which describe
	 * every existing article type.
	 */
	private void sortCategories() {
		for (Article article : this.articleList) {
			String type = article.getType();
			if (!this.articleCategories.contains(type))
				this.articleCategories.add(type);
		}
		Collections.sort(this.articleCategories);
	}

	/**
	 * 'isEmpty()' method. Returns whether the article list is empty.
	 * 
	 * @return Is it empty?
	 */
	public boolean isEmpty() {
		return this.articleList.isEmpty();
	}

	/**
	 * 'articlesByType(String type)' method. Returns a list with the articles of a
	 * type.
	 * 
	 * @param type
	 *            Type searched.
	 * @return Article list.
	 */
	public List<Article> articlesByType(String type) {
		ArrayList<Article> articles = new ArrayList<Article>();
		for (Article article : this.articleList) {
			if (article.getType().equals(type))
				articles.add(article);
		}
		return articles;
	}

	// #######################
	// Getter & setter methods
	// #######################

	/**
	 * 'getArticleList()' getter method.
	 * 
	 * @return List of articles.
	 */
	public List<Article> getArticleList() {
		return articleList;
	}

	/**
	 * 'getPeople()' getter method.
	 * 
	 * @return People quantity.
	 */
	public int getPeople() {
		return people;
	}

	/**
	 * 'setPeople(int people)' setter method.
	 * 
	 * @param people
	 *            People quantity.
	 */
	public void setPeople(int people) {
		this.people = people;
	}

	/**
	 * 'setArticleList(List<Article> articleList)' setter method.
	 * 
	 * @param articleList
	 *            List of articles.
	 */
	public void setArticleList(List<Article> articleList) {
		this.articleList = articleList;
	}

}

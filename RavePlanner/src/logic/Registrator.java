package logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.swing.JOptionPane;

/**
 * 'Registrator' class. Manages all the users information.
 * 
 * @author Iván Álvarez López - 71741444M
 * @version January 2019
 *
 */
public class Registrator {

	public static final String ALPHANUMERICAL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	private static final String USER_DATABASE = "files/clientes.dat";
	private static final char SEPARATOR = '@';
	public static final byte USERNAME_LENGTH_MAX = 12;
	public static final byte USERNAME_LENGTH_MIN = 3;

	/**
	 * 'register(String username, String password)' method. Creates a new entry in
	 * the user database, if the typed username is not taken yet.
	 * 
	 * @param username
	 *            Typed username.
	 * @param password
	 *            Typed password.
	 */
	public static void register(String username, String password) {
		String userEntry = username + SEPARATOR + password;
		try {
			File oldUserDatabase = new File(USER_DATABASE);
			File newUserDatabase = new File(oldUserDatabase.getAbsolutePath() + ".tmp");
			BufferedReader readOldUserDatabase = new BufferedReader(new FileReader(USER_DATABASE));
			PrintWriter writtenNewUserDatabase = new PrintWriter(new FileWriter(newUserDatabase));
			String currentLine = null;
			while ((currentLine = readOldUserDatabase.readLine()) != null) {
				writtenNewUserDatabase.println(currentLine);
				writtenNewUserDatabase.flush();
			}
			writtenNewUserDatabase.println(userEntry);
			writtenNewUserDatabase.close();
			readOldUserDatabase.close();
			if (!oldUserDatabase.delete() || !newUserDatabase.renameTo(oldUserDatabase))
				new RuntimeException("Critical Error!");
		} catch (FileNotFoundException fnfe) {
			JOptionPane.showMessageDialog(null, "File Not Found!");
		} catch (IOException ioe) {
			new RuntimeException("IO Error!");
		}
	}

	/**
	 * 'unregister(String username, String password)' method. Deletes an entry in
	 * the user database, if the typed username & the typed password are established
	 * for a user.
	 * 
	 * @param username
	 *            Typed username.
	 * @param password
	 *            Typed password.
	 */
	public static void unregister(String username, String password) {
		try {
			String removedEntry = username + SEPARATOR + password;
			File oldUserDatabase = new File(USER_DATABASE);
			File newUserDatabase = new File(oldUserDatabase.getAbsolutePath() + ".tmp");
			BufferedReader readOldUserDatabase = new BufferedReader(new FileReader(USER_DATABASE));
			PrintWriter writtenNewUserDatabase = new PrintWriter(new FileWriter(newUserDatabase));
			String currentLine = null;
			while ((currentLine = readOldUserDatabase.readLine()) != null) {
				if (!currentLine.trim().equals(removedEntry)) {
					writtenNewUserDatabase.println(currentLine);
					writtenNewUserDatabase.flush();
				}
			}
			writtenNewUserDatabase.close();
			readOldUserDatabase.close();
			if (!oldUserDatabase.delete() || !newUserDatabase.renameTo(oldUserDatabase))
				new RuntimeException("Critical Error!");
		} catch (FileNotFoundException fnfe) {
			JOptionPane.showMessageDialog(null, "File Not Found!");
		} catch (IOException ioe) {
			new RuntimeException("IO Error!");
		}
	}

	/**
	 * 'login(String username, String password)' method. Searches in the database
	 * whether the username & the password correlate.
	 * 
	 * @param username
	 *            Typed username.
	 * @param password
	 *            Typed password.
	 * @return Consistent?
	 */
	public static boolean login(String username, String password) {
		return ((userAlreadyExists(username)) && (validPassword(username, password)));
	}

	/**
	 * 'randomUsername()' method. Generates a random username, for anonymous login.
	 * 
	 * @return Random username.
	 */
	public static String randomUsername() {
		Random random = new Random();
		int count = random.nextInt(Registrator.USERNAME_LENGTH_MAX - Registrator.USERNAME_LENGTH_MIN + 1)
				+ Registrator.USERNAME_LENGTH_MIN;
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * Registrator.ALPHANUMERICAL.length());
			builder.append(Registrator.ALPHANUMERICAL.charAt(character));
		}

		return builder.toString();
	}

	/**
	 * 'validPassword(String username, String password)' method. Evaluates whether a
	 * password belongs to an specified username.
	 * 
	 * @param username
	 *            Typed username.
	 * @param password
	 *            Typed password.
	 * @return Consistent?
	 */
	private static boolean validPassword(String username, String password) {
		boolean validPassword = false;
		try {
			String userEntry = username + SEPARATOR + password;
			String iteratedUserEntry = "";
			BufferedReader reader = new BufferedReader(new FileReader(USER_DATABASE));
			while (reader.ready()) {
				iteratedUserEntry = reader.readLine();
				if (iteratedUserEntry.equals(userEntry))
					validPassword = true;
			}
			reader.close();
		} catch (FileNotFoundException fnfe) {
			JOptionPane.showMessageDialog(null, "File Not Found!");
		} catch (IOException ioe) {
			new RuntimeException("IO Error!");
		}

		return validPassword;
	}

	/**
	 * 'userAlreadyExists(String username)' method. Evaluates whether an username is
	 * already taken.
	 * 
	 * @param username
	 *            Typed username.
	 * @return Does it already exist?
	 */
	public static boolean userAlreadyExists(String username) {
		boolean exists = false;
		String userEntry = "";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(USER_DATABASE));
			while (reader.ready()) {
				userEntry = reader.readLine();
				String[] components = userEntry.split("@");
				if (components[0].equals(username))
					exists = true;
			}
			reader.close();
		} catch (FileNotFoundException fnfe) {
			JOptionPane.showMessageDialog(null, "File Not Found!");
		} catch (IOException ioe) {
			new RuntimeException("IO Error!");
		}
		return exists;
	}
}

package com.kaizerwebdesign.hotelapp.forms;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Helper class that contains form validation methods to validate user inputs
 **/
public final class FieldValidation {

	// private constructor to prevent instanciation
	private FieldValidation() {

	}

	public static String isEmailValid(String email) throws Exception {

		// trim text
		email = trimmer(email);

		// declare regular expression
		String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

		if (email != null) {
			if (email.matches(regex)) {
				// return positive result if email matches regex
				return email;
			} else {
				// if it does not match we return negative result
				throw new Exception("Invalid email, please enter a valid email");
			}
		} else {
			// return error if field is empty
			throw new Exception("Please enter an email");
		}
	}

	public static Date isValidDate(String pdate) throws Exception {
		// trim text
		pdate = trimmer(pdate);

		// test if null
		if (pdate != null) {
			/// set preferred date format for validation
			SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
			// set strict date validation
			dformat.setLenient(false);
			try {
				Date date = dformat.parse(pdate);
				return date;
			} catch (Exception e) {
				// invalid date
				throw new Exception("Invalid date format, please enter a valid date format");
			}
		} else {
			// return negative
			throw new Exception("Please enter a valid date");
		}
	}

	public static Date isValidDateTime(String pdate) throws Exception {
		// trim text
		pdate = trimmer(pdate);

		// test if null
		if (pdate != null) {
			// set preferred date format for validation
			SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			// set strict date validation
			dformat.setLenient(false);
			try {
				// parse string to date
				Date date = dformat.parse(pdate);
				return date;
			} catch (Exception e) {
				// invalid date
				throw new Exception("Invalid date time format, please enter a valid date");
			}
		} else {
			// return negative
			throw new Exception("Please enter a valid date time");
		}
	}

	public static String isValidName(String name) throws Exception {
		// trim text
		name = trimmer(name);

		// test if empty
		if (name != null) {
			// test input with regex
			if (name.matches("^[\\p{L}\\p{M}' \\.\\-]+$")) {
				return name;
			} else {
				// if not throw an exception
				throw new Exception("Invalid name input");
			}
		} else {
			throw new Exception("Please enter a name");
		}
	}

	public static String confirmPassword(String pass, String conf) throws Exception {
		// trim both inputs
		pass = trimmer(pass);
		conf = trimmer(conf);
		// test if both strings are not null
		if (pass != null || conf != null) {
			// test if password and confirmation are equal
			if (pass.equals(conf)) {
				// test password length
				if (pass.matches("\\A(?=\\S*?[0-9])(?=\\S*?[a-z])(?=\\S*?[A-Z])(?=\\S*?[@#$;%?^&+=])\\S{8,}\\z")) {
					return pass;
				} else {
					throw new Exception(
							"Passwords must contain at least 8 characters and should contain at least a digit, a lower case"
									+ " character, an upper case character, a special character(@#$;%?^&+=) and no space  .");
				}
			} else {
				throw new Exception("The passwords entered are different, please try again.");
			}
		} else {
			throw new Exception("Empty field not allowed, please enter a password !");
		}
	}

	public static String usernameConfirmation(String username) throws Exception {

		// trim input
		username = trimmer(username);

		// test if input is not null
		if (username != null) {
			// validate input with regex
			if (username.matches("^[a-z0-9_-]{5,20}$")) {
				return username;
			} else {
				throw new Exception(
						"Invalid username, username must contain at least 5 characters, with no spaces and special characters");
			}
		} else {
			throw new Exception("Empty input, please enter a username");
		}
	}

	public static String trimmer(String txt) {

		// test if string is empty or is made up of all white spaces
		if (txt == null || txt.trim().length() == 0) {
			return null;
		} else {
			// else return trimmed text
			return txt.trim().replaceAll(" +", " ");
		}
	}

	public static Integer numberValidation(String number) throws Exception {
		// trim input
		number = trimmer(number);

		/// test if input is not null
		if (number != null) {
			// validate input with regular expression
			if (number.matches("\\d+")) {
				return Integer.parseInt(number);
			} else {
				throw new Exception("Invalid input");
			}
		} else {
			throw new Exception("Invalid input");
		}
	}

	public static String idValidation(String number) throws Exception {
		// trim input
		number = trimmer(number);

		/// test if input is not null
		if (number != null) {
			// validate input with regular expression
			if (number.matches("\\d+")) {
				return number;
			} else {
				throw new Exception("Invalid id input");
			}
		} else {
			return null;
		}
	}

}

package nl.mfarr.supernova.helpers;

import nl.mfarr.supernova.exceptions.NoMatchingPasswordsException;

public class MatchingPasswordHelper {

        public static boolean isMatching(String password, String confirmPassword) {

            if (!password.equals(confirmPassword)) {
                throw new NoMatchingPasswordsException("Passwords do not match");
            }
            return password.equals(confirmPassword);
        }
}

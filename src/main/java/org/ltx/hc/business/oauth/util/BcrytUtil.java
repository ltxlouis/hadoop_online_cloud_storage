package org.ltx.hc.business.oauth.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * @author ltxlouis
 * @since 4/7/2018
 */
public class BcrytUtil {

    public static String bEncrypt(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public static boolean bCheckPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}

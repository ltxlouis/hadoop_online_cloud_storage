package org.ltx.hc.business.oauth.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author ltxlouis
 * @since 4/7/2018
 */
public class Md5Util {

    private static final String MD5_PREFIX = "";

    private static final ThreadLocal LOCAL = new ThreadLocal();

    private Md5Util() {
        super();
    }

    public static Md5Util getEncrypt() {
        Md5Util encrypt = (Md5Util) LOCAL.get();
        if (encrypt == null) {
            encrypt = new Md5Util();
            LOCAL.set(encrypt);
        }
        return encrypt;
    }

    public static String encode(String s) {
        if (s == null) {
            return null;
        }
        return DigestUtils.md5Hex(MD5_PREFIX + s);
    }

    public static String encode(String prefix, String s, String suffix) {
        if (s == null) {
            return null;
        }
        return DigestUtils.md5Hex(prefix + s + suffix);
    }

    public static String encodeWithoutPrefix(String s) {
        if (s == null) {
            return null;
        }
        return DigestUtils.md5Hex(s);
    }

    public static void localRemove() {
        LOCAL.remove();
    }
}

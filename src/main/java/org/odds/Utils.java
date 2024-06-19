package org.odds;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.StandardCharsets;

public class Utils {
    /**
     * takes a string and converts it to a MariaDB password
     * see: https://mariadb.com/kb/en/password/
     *
     * @param p text to convert
     * @return hashed string
     */
    public static String mariaDBPassword(String p) {
        byte[] t = p.getBytes(StandardCharsets.UTF_8);
        byte[] s = DigestUtils.sha1(DigestUtils.sha1(t));

        return "*".concat(Hex.encodeHexString(s)).toUpperCase();
    }
}

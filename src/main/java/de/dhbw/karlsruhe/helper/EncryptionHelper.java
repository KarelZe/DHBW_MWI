package de.dhbw.karlsruhe.helper;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Diese Klasse stellt Bequemlichkeitsmethoden zur Verschlüsselung von Strings zur Verfügung.
 *
 * @author Christian Fix
 */

public class EncryptionHelper {

    /**
     * Methode zur Verschlüsselung eines Strings mittels MD5-Algorithmus.
     *
     * @param input input im Klartext
     * @return Wert im MD5-Format; im Fehlerfall {@code null}.
     */
    public static String getStringAsMD5(String input) {
        String md5 = null;
        if (null == input) return null;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(input.getBytes(), 0, input.length());
            md5 = new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5;
    }
}

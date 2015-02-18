package io.ingenieux.sqoopeizer;

import org.apache.commons.codec.binary.Base64;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Decryptor {
  private static String PROPERTY_CRYPTO_ALG = "org.apache.sqoop.credentials.loader.crypto.alg";

  private static String PROPERTY_CRYPTO_SALT = "org.apache.sqoop.credentials.loader.crypto.salt";

  private static String PROPERTY_CRYPTO_ITERATIONS = "org.apache.sqoop.credentials.loader.crypto.iterations";

  private static String PROPERTY_CRYPTO_KEY_LEN = "org.apache.sqoop.credentials.loader.crypto.salt.key.len";

  private static String PROPERTY_CRYPTO_PASSPHRASE = "org.apache.sqoop.credentials.loader.crypto.passphrase";

  public static void main(String[] args) throws Exception {
    String encoded = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
    
    System.out.println(decryptPassword(Base64.decodeBase64(encoded)));
  }

  public static String decryptPassword(byte[] encrypted) throws Exception {
    String passPhrase = System.getProperty(PROPERTY_CRYPTO_PASSPHRASE, "PASSWORD");

    String alg = System.getProperty(PROPERTY_CRYPTO_ALG, "AES/ECB/PKCS5Padding");
    String algOnly = alg.split("/")[0];
    String salt = System.getProperty(PROPERTY_CRYPTO_SALT, "SALT");
    int iterations = Integer.getInteger(PROPERTY_CRYPTO_ITERATIONS, 10000);
    int keyLen = Integer.getInteger(PROPERTY_CRYPTO_KEY_LEN, 128);

    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

    SecretKeySpec
        key = new SecretKeySpec(factory.generateSecret(new PBEKeySpec(passPhrase.toCharArray(), salt.getBytes(), iterations, keyLen)).getEncoded(), algOnly);

    Cipher crypto = Cipher.getInstance(alg);

    crypto.init(Cipher.DECRYPT_MODE, key);
    
    byte[] decryptedBytes = crypto.doFinal(encrypted);
    
    return new String(decryptedBytes);
  }
}

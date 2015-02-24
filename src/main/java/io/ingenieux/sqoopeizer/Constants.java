package io.ingenieux.sqoopeizer;

/**
 * Created by Aldrin on 24/02/2015.
 */
public interface Constants {

  public static final String DEFAULT_ALG = "AES/ECB/PKCS5Padding";
  public static final String DEFAULT_SALT = "SALT";
  public static final int DEFAULT_ITER = 10000;
  public static final String DEFAULT_PASSPHRASE = "PASSWORD";
  public static final String
      PASSPHRASE_PROPERTY =
      "org.apache.sqoop.credentials.loader.crypto.passphrase";
  public static final String PROPERTY_ALG = "org.apache.sqoop.credentials.loader.crypto.alg";
  public static final String PROPERTY_SALT = "org.apache.sqoop.credentials.loader.crypto.salt";
  public static final String
      PROPERTY_ITER =
      "org.apache.sqoop.credentials.loader.crypto.iterations";
  public static final String
      PROPERTY_KEYLEN =
      "org.apache.sqoop.credentials.loader.crypto.salt.key.len";
  public static final int DEFAULT_KEYLEN = 128;
  public static final String KEYFACTORY = "PBKDF2WithHmacSHA1";

}

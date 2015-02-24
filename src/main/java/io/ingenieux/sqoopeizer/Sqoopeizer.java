package io.ingenieux.sqoopeizer;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Sqoopeizer implements Constants {

  public String alg; // = "AES/ECB/PKCS5Padding";

  final String passphrase; // = "PASSWORD";

  final String salt; // = "SALT";

  final int iterations; // = 10000;

  final int keyLen; // = 128;

  public Sqoopeizer() {
    this(System.getProperty(PASSPHRASE_PROPERTY,
                            DEFAULT_PASSPHRASE),
         System.getProperty(PROPERTY_ALG, DEFAULT_ALG),
         System.getProperty(PROPERTY_SALT, DEFAULT_SALT),
         Integer.getInteger(PROPERTY_ITER, DEFAULT_ITER),
         Integer.getInteger(PROPERTY_KEYLEN, DEFAULT_KEYLEN)
    );
  }

  public Sqoopeizer(String passphrase, String alg, String salt, int iterations, int keyLen) {
    this.passphrase = passphrase;
    this.alg = alg;
    this.salt = salt;
    this.iterations = iterations;
    this.keyLen = keyLen;
  }

  public SqoopeizerResult cryptPassword(String password) throws Exception {
    SecretKeyFactory factory = SecretKeyFactory.getInstance(KEYFACTORY);

    String algOnly = alg.split("/")[0];

    SecretKeySpec
        key =
        new SecretKeySpec(factory.generateSecret(
            new PBEKeySpec(passphrase.toCharArray(), salt.getBytes(), iterations, keyLen))
                              .getEncoded(), algOnly);

    Cipher crypto = Cipher.getInstance(alg);

    crypto.init(Cipher.ENCRYPT_MODE, key);

    byte[] encryptedBytes = crypto.doFinal(password.getBytes());

    return new SqoopeizerResult(passphrase, salt, iterations, keyLen, encryptedBytes);
  }
}

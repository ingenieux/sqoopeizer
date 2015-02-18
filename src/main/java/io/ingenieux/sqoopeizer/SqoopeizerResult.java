package io.ingenieux.sqoopeizer;

import org.apache.commons.codec.binary.Base64;

/**
 * Created by Aldrin on 18/02/2015.
 */
public class SqoopeizerResult {

  final String passphrase;

  final int saltKeyLength;

  final byte[] password;
  
  final int iterations;
  
  final String salt;

  public SqoopeizerResult(String passphrase, String salt, int iterations, int saltKeyLength, byte[] password) {
    this.passphrase = passphrase;
    this.salt = salt;
    this.iterations = iterations;
    this.saltKeyLength = saltKeyLength;
    this.password = password;
  }

  @Override
  public String toString() {
    return String.format(
        "-Dorg.apache.sqoop.credentials.loader.class=org.apache.sqoop.util.password.CryptoFileLoader\n"
        + "-Dorg.apache.sqoop.credentials.loader.crypto.salt=%s\n"
        + "-Dorg.apache.sqoop.credentials.loader.crypto.iterations=%d\n"
        + "-Dorg.apache.sqoop.credentials.loader.crypto.passphrase=%s\n"
        + "-Dorg.apache.sqoop.credentials.loader.crypto.alg=AES/ECB/PKCS5Padding\n"
        + "-Dorg.apache.sqoop.credentials.loader.crypto.salt.key.len=%d\n"
        + "--password-file <file> # %s\n"
        , salt, iterations, passphrase, saltKeyLength, Base64.encodeBase64String(password));

  }
}

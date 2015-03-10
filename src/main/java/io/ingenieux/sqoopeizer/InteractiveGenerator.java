package io.ingenieux.sqoopeizer;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.docopt.Docopt;

import java.io.BufferedReader;
import java.io.Console;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Aldrin on 24/02/2015.
 */
public class InteractiveGenerator implements Constants {

  private static final String USAGE =
      "sqoopeizer.\n"
      + "\n"
      + "Usage:\n"
      + "  sqoopeizer [options] <jdbcurl> <username> [PREFIX]\n"
      + "\n"
      + "Options:\n"
      + "  --alg=<alg>\n"
      + "  --salt=<salt>\n"
      + "  --iter=<iter>\n"
      + "  --passphrase=<passphrase>\n"
      + "  --keylen=<keylen>\n"
      + "  --verbose\n"
      + "\n";

  public static void main(String[] args) throws Exception {
    String prefix = null;
    int keyLen = 128;
    String alg = DEFAULT_ALG;
    String passphrase = generateWord(16);
    String salt = generateWord(4);
    int iterations = DEFAULT_ITER;
    String jdbcUrl = null;
    String username = null;

    Docopt docopt = new Docopt(USAGE).withHelp(true).withOptionsFirst(true);

    final Map<String, Object> parsedArgs = docopt.parse(args);
    
    boolean bVerbose = Boolean.TRUE.equals(parsedArgs.get("--verbose"));

    for (Map.Entry<String, Object> e : parsedArgs.entrySet()) {
      if (bVerbose) {
        String type = "N/A";
        
        if (null != e.getValue()) {
          type = e.getValue().getClass().getCanonicalName();
        }
        
        System.err.println("e: " + e + " [type: " + type + "]");
      }
      
      if (null == e.getValue() || "--verbose".equals(e.getKey())) {
        continue;
      }

      String value = String.class.cast(e.getValue());

      switch (e.getKey()) {
        case "--keylen": {
          keyLen = Integer.parseInt(value);
          continue;
        }
        case "--alg": {
          alg = value;
          continue;
        }
        case "--salt": {
          salt = value;
          continue;
        }
        case "--iter": {
          iterations = Integer.parseInt(value);
          continue;
        }
        case "--passphrase": {
          passphrase = value;
          continue;
        }
        case "PREFIX": {
          prefix = value;
          continue;
        }
        case "<jdbcurl>": {
          jdbcUrl = value;
          continue;
        }
        case "<username>": {
          username = value;
          continue;
        }
      }
    }

    Sqoopeizer sq = new Sqoopeizer(passphrase, alg, salt, iterations, keyLen);

    String password = readPassword();

    SqoopeizerResult result = sq.cryptPassword(password);

    if (null == prefix) {
      System.err.println(result);
    } else {
      writeIntoPrefix(jdbcUrl, username, prefix, sq, result);
    }

  }

  private static void writeIntoPrefix(String jdbcUrl, String username, String prefix, Sqoopeizer sq, SqoopeizerResult result) throws Exception {
    List<String> lines = new ArrayList<>(Arrays.asList(result.toString().split("\n")));
    byte[] password = result.password;
    
    lines.remove(-1 + lines.size());
    lines.add(String.format("--connect"));
    lines.add(jdbcUrl);
    lines.add("--username");
    lines.add(username);

    IOUtils.write(StringUtils.join(lines.iterator(), "\n"), new FileOutputStream(prefix + ".config"));
    IOUtils.write(password, new FileOutputStream(prefix + ".password"));
  }

  private static final SecureRandom random = new SecureRandom();

  private static String generateWord(int l) {
    StringBuilder result = new StringBuilder();

    result.append(new BigInteger(8 * l, random).toString(32).substring(0, l));

    return result.toString();
  }

  private static String readPassword() throws IOException {
    Console c = System.console();

    if (null != c) {
      return new String(c.readPassword()).trim();
    }

    return new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
  }
}

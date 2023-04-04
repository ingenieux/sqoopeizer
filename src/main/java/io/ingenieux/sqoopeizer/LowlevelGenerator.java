package io.ingenieux.sqoopeizer;

import io.openpixee.security.BoundedLineReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class LowlevelGenerator {

  public static void main(String[] args) throws Exception {
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    
    String password = BoundedLineReader.readLine(in, 1000000);
    
    SqoopeizerResult result = new Sqoopeizer().cryptPassword(password.trim());

    System.out.println(result);
  }
}

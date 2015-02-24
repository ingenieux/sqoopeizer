package io.ingenieux.sqoopeizer;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class LowlevelGenerator {

  public static void main(String[] args) throws Exception {
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    
    String password = in.readLine();
    
    SqoopeizerResult result = new Sqoopeizer().cryptPassword(password.trim());

    System.out.println(result);
  }
}

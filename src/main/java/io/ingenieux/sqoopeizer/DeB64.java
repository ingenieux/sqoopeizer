package io.ingenieux.sqoopeizer;

import org.apache.commons.codec.binary.Base64;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DeB64 {
  public static void main(String[] args) throws Exception {
    String in = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
    
    System.out.println(Base64.decodeBase64(in));
  }

}

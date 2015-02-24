package io.ingenieux.sqoopeizer;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DeB64 {
  public static void main(String[] args) throws Exception {
    String in = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();

    IOUtils.write(Base64.decodeBase64(in), System.out);
  }

}

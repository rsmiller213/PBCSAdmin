/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emtecinc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Luis.Castillo
 */
public class PBCSGetPropsFile {

    ArrayList<String> result = new ArrayList<String>();
    FileInputStream inputStream;
    private static SecretKeySpec secretKey;
    private static byte[] key;

    private static String decryptedString;
    private static String encryptedString;

    public static void setKey(String myKey) {

        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            //System.out.println(key.length);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); // use only first 128 bit
            //System.out.println(key.length);
            //System.out.println(new String(key, "UTF-8"));
            secretKey = new SecretKeySpec(key, "AES");

        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public ArrayList<String> getPropValues(String action) throws IOException {

        try {
            File jarPath = new File(PBCSAdmin.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            String propertiesPath = jarPath.getParentFile().getAbsolutePath();
            Properties prop = new Properties();
            String propFileName = "./PBCSEnv.properties";

            //inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            inputStream = new FileInputStream((propertiesPath + "/PBCSEnv.properties"));

            if (inputStream != null) {
                prop.load(inputStream);
                inputStream.close();
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            Date time = new Date(System.currentTimeMillis());

            // get the property value and print it out
//			String pbcsURL = prop.getProperty("PBCS_URL");
//                        String pbcsDomain = prop.getProperty("PBCS_DOMAIN");
//                        String pbcsUser = prop.getProperty("PBCS_USER");
//			String pbcsPassword = prop.getProperty("PBCS_PASS");
            result.add(prop.getProperty("PBCS_URL"));
            result.add(prop.getProperty("PBCS_DOMAIN"));
            result.add(prop.getProperty("PBCS_USER"));
//            result.add(prop.getProperty("PBCS_PASS"));
            setKey(prop.getProperty("PBCS_DOMAIN") + "." + prop.getProperty("PBCS_USER"));
            if (action.equals(pbcsConstants.ENCRYPT)) {
                encrypt(prop.getProperty("PBCS_PASS"));
                setEncryptedPassConfigFile(result);
            } else {
                setEncryptedString(prop.getProperty("PBCS_PASS"));
                decrypt(PBCSGetPropsFile.encryptedString);
                result.add(PBCSGetPropsFile.decryptedString);
            }
            //decrypt(PBCSGetPropsFile.encryptedString);
            //decrypt("Hello");
            //System.out.println("decrypt " + PBCSGetPropsFile.decryptedString);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return result;
    }

    private void setEncryptedPassConfigFile(ArrayList<String> propValues) {
        try {
            File jarPath = new File(PBCSAdmin.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            String propertiesPath = jarPath.getParentFile().getAbsolutePath();
            Properties prop = new Properties();
            String propFileName = "./PBCSEnv.properties";
            inputStream = new FileInputStream((propertiesPath + "/PBCSEnv.properties"));
            FileOutputStream outStream = new FileOutputStream(propertiesPath + "/PBCSEnv.properties");

            //inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            

            if (inputStream != null) {
                prop.load(inputStream);
                inputStream.close();
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
            prop.setProperty("PBCS_URL", result.get(0));
            prop.setProperty("PBCS_DOMAIN", result.get(1));
            prop.setProperty("PBCS_USER", result.get(2));
            prop.setProperty("PBCS_PASS", getEncryptedString());
            prop.store(outStream, null);
            outStream.close();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    public static String getDecryptedString() {
        return decryptedString;
    }

    public static void setDecryptedString(String decryptedString) {
        PBCSGetPropsFile.decryptedString = decryptedString;
    }

    public static String getEncryptedString() {
        return encryptedString;
    }

    public static void setEncryptedString(String encryptedString) {
        PBCSGetPropsFile.encryptedString = encryptedString;
    }

    public static String encrypt(String strToEncrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            //setEncryptedString(Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes("UTF-8"))));
            setEncryptedString(Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8"))));
            //setEncryptedString(new sun.misc.BASE64Encoder().encode(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {

            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static String decrypt(String strToDecrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");

            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            //setDecryptedString(new String(cipher.doFinal(Base64.decodeBase64(strToDecrypt))));
            setDecryptedString(new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt))));
        } catch (Exception e) {

            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

}

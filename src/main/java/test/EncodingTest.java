package test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncodingTest {
    public static void main(String[] args) {
        System.out.println(encrypt("1234","admin"));
    }
    public static String encrypt(String password, String salt){
        // 1. 암호화 Hashing
        MessageDigest md = null;
        byte[] encrypted = null;
        try{
            md = MessageDigest.getInstance("SHA-512");
            byte[] input = password.getBytes("utf-8");
            byte[] saltBytes = salt.getBytes("utf-8");

            //salt 값으로 MessageDigest 객체 갱신
            md.update(saltBytes);

            // MessageDigest 객체에 raw passoword 전달 및 hashing
            encrypted = md.digest(input);
        }catch (NoSuchAlgorithmException | UnsupportedEncodingException e){
            System.out.println(e);
        }

        // 2. 인코딩 (단순 문자 변환)
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(encrypted);
    }
}

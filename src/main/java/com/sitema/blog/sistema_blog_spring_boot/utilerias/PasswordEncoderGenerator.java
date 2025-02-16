package com.sitema.blog.sistema_blog_spring_boot.utilerias;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordEncoderGenerator {

    public static void main (String[] args) throws NoSuchAlgorithmException {

        //PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //System.out.println(passwordEncoder.encode("myPassword123"));

        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA512");
        keyGen.init(512);
        SecretKey secretKey = keyGen.generateKey();
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println("Clave segura: " + encodedKey);
    }
}

package com.lenovo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants {

    public static String SECRET_KEY;
    public static String STATE = "state";
    public static String SUCCESS = "success";
    public static String FALSE = "false";
    @Autowired
    private  void setSecretKey(@Value("${secret.key}") String secretKey) {
        Constants.SECRET_KEY = secretKey;
    }
}

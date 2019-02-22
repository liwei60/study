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

    public static final String MQ_DATA_PREPROCESS_KEY = "dataPreprocess";

    public static final String MQ_DATA_PREPROCESS_FAIL_KEY = "dataPreprocessFail";

    public static final String REDIS_KEYWORD_CHECK_QUEUE ="keyWord:checkAppInfo";

    public static final String REDIS_KEYWORD_CHECK_FAIL_QUEUE="keyWord:checkFailAppInfo";
    @Autowired
    private  void setSecretKey(@Value("${secret.key}") String secretKey) {
        Constants.SECRET_KEY = secretKey;
    }
}

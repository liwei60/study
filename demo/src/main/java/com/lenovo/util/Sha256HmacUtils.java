package com.lenovo.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Sha256HmacUtils {

    private final static Log logger = LogFactory.getLog(Sha256HmacUtils.class);


    /**
     * 签名算法
     * 
     * @param message
     * @param secret
     * @return
     */
    public static String sha256_HMAC(String message,
                                     String secret) {
        String hash = "";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] bytes = sha256_HMAC.doFinal(message.getBytes());
            hash = byteArrayToHexString(bytes);
            logger.info("HmacSHA256 hash : " + hash + " message: " + message + " secret: " + secret);

        } catch (Exception e) {
            logger.error("Error HmacSHA256 =========== : ", e);

        }
        return hash;
    }

    public static String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }
}

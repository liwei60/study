package com.lenovo.filter;

import com.lenovo.service.RedisService;
import com.lenovo.util.Sha256HmacUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

public class ValidAndResponseHelper {

    private static final Log logger = LogFactory.getLog(ValidAndResponseHelper.class);

    @Autowired
    private static RedisService redisService;
    /**
     * 获取除sign外的所有参数并转化成String用&连接
     * @param param
     * @return
     */
    public  static String getParameterMapToString(Map param){
        Map<String,String> returnMap = new HashMap<>();
        Iterator entries = param.entrySet().iterator();
        Map.Entry entry;
        String key="";
        String value = "";
        while(entries.hasNext()){
            entry = (Map.Entry) entries.next();
            key = (String)entry.getKey();
            if("sign".equalsIgnoreCase(key)){
                continue;
            }
            Object obj = entry.getValue();
            if(obj instanceof String[] ){
                String[] values = (String[]) obj;
                for(int i=0;i<values.length;i++){
                    value = values[i];
                }
            }else {
                value = obj.toString();
            }
            returnMap.put(key,value);
        }
        String message = returnMap.entrySet().stream().map(object -> object.getKey() + "=" + object.getValue()).collect
                (Collectors.joining("&", "", ""));
        return message;
    }

    /**
     * 验证签名是否正确
     * @param originSignString  客户端签名
     * @param message  参数
     * @param secretKey   签名key
     * @return
     */
    public static boolean validAndReturn(String originSignString,
                                  String message,
                                  String secretKey){
      String SignValidString =   Sha256HmacUtils.sha256_HMAC(message,secretKey);
      logger.info("originSignString :"+originSignString+"   SignValidString: "+ SignValidString);
      if(!originSignString.equals(SignValidString)){
          logger.info(" 验证签名不一致  ");
          return true;
      }
      return false;
    }

    /**
     * 存储nonce  防止重放
     * @param nonce
     */
    public static void saveNonce(String nonce){
        logger.info("nonce  :" +nonce );
        redisService.set(nonce,nonce);
    }

    /**
     * 判断nonce是否存在，验证是否为重放攻击
     * @param nonce
     * @return
     */
    public static Boolean checkIsRepeatingRequest(String nonce){
        String value = redisService.get(nonce);
        if(value == null){
            return true;
        }
        logger.info("checkIsRepeatingRequest  重复请求！");
        return false;
    }

}

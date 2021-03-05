package com.xiaozhu.washcar.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author hans
 * @Date 2020/12/6 15:20
 * @Version 1.0
 */
public class Switch {
    private static final String CLIENT_ID = "19856";
    private static final String CLIENT_SECRET = "e93ec19f89";
    private static final String CLIENT_USERNAME = "14020";
    private static final String CLIENT_PASSWORD = "ab223d76ed";
    private static final String CLIENT_GRANT_TYPE = "password";

    private static final String SwitchToken = "https://www.bigiot.net/oauth/token";
    private static final String SwitchTurnOnOff = "https://www.bigiot.net/oauth/say";
    private static String token = null;
    private static Integer IN_USE = 0;

    public void refreshToken(){
        Map<String,Object> map = new HashMap<>();
        map.put("client_id",CLIENT_ID);
        map.put("client_secret",CLIENT_SECRET);
        map.put("username",CLIENT_USERNAME);
        map.put("password",CLIENT_PASSWORD);
        map.put("grant_type",CLIENT_GRANT_TYPE);
        String s = HttpClient.sendPost(SwitchToken, map);
        System.out.println("refresh WifiSwitch TOKEN:" + s);
        token = s;
    }

    // TODO : 缺少获取当前状态的接口

    public static Boolean turnOn(){
        if(IN_USE == 1){
            return true;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("access_token",token);
        map.put("id",CLIENT_ID);
        map.put("c","");
        map.put("sign","");
        String s = HttpClient.sendPost(SwitchTurnOnOff, map);
        if("扫码洗车".equals(s)){
            IN_USE = 1;
            return true;
        } else {
            return turnOn();
        }
    }

    public static Boolean turnOff(){
        if(IN_USE == 0){
            return true;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("access_token",token);
        map.put("id",CLIENT_ID);
        map.put("c","");
        map.put("sign","");
        String s = HttpClient.sendPost(SwitchTurnOnOff, map);
        if(!"扫码洗车".equals(s)){
            IN_USE = 0;
            return true;
        } else {
            return turnOn();
        }
    }
}

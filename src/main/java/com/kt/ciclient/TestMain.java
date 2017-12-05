package com.kt.ciclient;

import com.kt.ciclient.command.response.GenWbxSAMLRespResponse;
import com.kt.ciclient.command.response.LoginResponse;
import com.kt.ciclient.entity.Principal;
import com.kt.ciclient.exception.CiApiException;

/**
 * Created by Vega Zhou on 2015/10/12.
 */
public class TestMain {
    public static void main(String args[]) throws CiApiException {
//        CiClient client = CiClientBuilder.build("http://www.kt.com:8080/sso/json",
//                "AQIC5wM2LY4SfczJeVPKO3hAtTVyWaejxnQ8dMnPBC3RTbE.*AAJTSQACMDIAAlNLABM5MTIxNDk5NTc5NTI5MTkzNzI3AAJTMQACMDE.*");
        CiClient client = CiClientBuilder.build("http://szci.ketianyun.com:8080/sso/json");
        LoginResponse loginResponse = client.login("amAdmin", "11111111");
        GenWbxSAMLRespResponse samlResponse = client.generateWebExSAMLResponse("https://ectest.webex.com.cn", "weijia.zhou@tcl.com1");
        client.logout();
        client.logout();
//        Principal profile = client.validateToken("AQIC5wM2LY4SfcxiJmZDKFsTYeuYZOv-J8P4CtyIy9KwQLk.*AAJTSQACMDEAAlNLABM4Mzg2MjUxNDgxMzM5OTM3NzU0*");
//        UserProfile profile = client.validateToken("AQC5wM2LY4SfczQ49vFZNwN-eitfg4Rl2fgrYZFqgHmPac.*AAJTSQACMDIAAlNLABMyNTY5ODI5MDQ2NzAxNDg0NzcxAAJTMQACMDE.*");
        return;
    }
}

package com.kt.ciclient;

/**
 * Created by Vega Zhou on 2015/10/12.
 */
public class CiClientBuilder {

    public static CiClient build(String ciServerUrl) {
        return new CiClientImpl(ciServerUrl);
    }

    public static CiClient build(String ciServerUrl, String token) {
        return new CiClientImpl(ciServerUrl, token);
    }
}

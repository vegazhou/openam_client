package com.kt.ciclient;

import com.kt.ciclient.command.response.GenWbxSAMLRespResponse;
import com.kt.ciclient.command.response.LoginResponse;
import com.kt.ciclient.entity.Principal;
import com.kt.ciclient.exception.CiApiException;

/**
 * Created by Vega Zhou on 2015/10/12.
 */
public interface CiClient {

    Principal validateToken(String token) throws CiApiException;

    GenWbxSAMLRespResponse generateWebExSAMLResponse(String spId, String uid) throws CiApiException;

    LoginResponse login(String user, String password) throws CiApiException;

    void logout() throws CiApiException;
}

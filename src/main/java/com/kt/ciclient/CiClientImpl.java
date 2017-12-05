package com.kt.ciclient;

import com.kt.ciclient.command.request.GenWbxSAMLRespRequest;
import com.kt.ciclient.command.request.LoginRequest;
import com.kt.ciclient.command.request.LogoutRequest;
import com.kt.ciclient.command.request.ValidateSessionRequest;
import com.kt.ciclient.command.response.GenWbxSAMLRespResponse;
import com.kt.ciclient.command.response.LoginResponse;
import com.kt.ciclient.command.response.LogoutResponse;
import com.kt.ciclient.command.response.ValidateSessionResponse;
import com.kt.ciclient.entity.Principal;
import com.kt.ciclient.exception.CiApiException;

/**
 * Created by Vega Zhou on 2015/10/12.
 */
public class CiClientImpl implements CiClient {

    private CiRequestExecutor executor;


    public CiClientImpl(String ciServerUrl) {
        this(ciServerUrl, null);
    }

    public CiClientImpl(String ciServerUrl, String token) {
        this.executor = new CiRequestExecutor(ciServerUrl, token);
    }

    public Principal validateToken(String token) throws CiApiException {
        ValidateSessionResponse response = executor.executeRequest(new ValidateSessionRequest(token));
        return new Principal(response);
    }

    public LoginResponse login(String user, String password) throws CiApiException {
        LoginResponse loginResponse = executor.executeRequest(new LoginRequest(user, password));
        if (loginResponse.isSuccess()) {
            executor.setToken(loginResponse.getTokenId());
        }
        return loginResponse;
    }

    public void logout() throws CiApiException {
        LogoutResponse logoutResponse = executor.executeRequest(new LogoutRequest());
    }

    public GenWbxSAMLRespResponse generateWebExSAMLResponse(String spId, String uid) throws CiApiException {
        GenWbxSAMLRespRequest request = new GenWbxSAMLRespRequest();
        request.setSpid(spId);
        request.setUid(uid);
        GenWbxSAMLRespResponse response = executor.executeRequest(request);
        return response;
    }
}

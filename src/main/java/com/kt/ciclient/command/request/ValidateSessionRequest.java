package com.kt.ciclient.command.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.kt.ciclient.command.CiRequest;
import com.kt.ciclient.command.RequestMethod;
import com.kt.ciclient.command.response.ValidateSessionResponse;

/**
 * Created by Vega Zhou on 2015/10/12.
 */
public class ValidateSessionRequest extends CiRequest<ValidateSessionResponse> {

    @JsonProperty
    private String token;

    public ValidateSessionRequest(String token) {
        this.token = token;
    }

    @Override
    public RequestMethod getMethod() {
        return RequestMethod.POST;
    }

    @Override
    public String getEndPoint() {
        return "/sessions?_action=validateSession";
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

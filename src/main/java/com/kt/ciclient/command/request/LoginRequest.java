package com.kt.ciclient.command.request;

import com.kt.ciclient.command.CiRequest;
import com.kt.ciclient.command.RequestMethod;
import com.kt.ciclient.command.response.LoginResponse;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * Created by Vega Zhou on 2015/10/12.
 */
public class LoginRequest extends CiRequest<LoginResponse> {

    private String userName;

    private String password;

    public LoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    @Override
    public RequestMethod getMethod() {
        return RequestMethod.POST;
    }

    @Override
    public String getEndPoint() {
        return "/authenticate";
    }

    /**
     * Login is a special request, it contains username/password in headers
     */
    @Override
    public String generateBody() {
        return "";
    }

    @Override
    public void assembleHeaders(HttpRequestBase httpRequest) {
        httpRequest.setHeader("X-OpenAM-Username", userName);
        httpRequest.setHeader("X-OpenAM-Password", password);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

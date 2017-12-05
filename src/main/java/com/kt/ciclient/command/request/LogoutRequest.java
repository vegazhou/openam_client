package com.kt.ciclient.command.request;

import com.kt.ciclient.command.CiRequest;
import com.kt.ciclient.command.RequestMethod;
import com.kt.ciclient.command.response.LogoutResponse;

/**
 * Created by Vega Zhou on 2015/11/30.
 */
public class LogoutRequest extends CiRequest<LogoutResponse> {

    @Override
    public RequestMethod getMethod() {
        return RequestMethod.POST;
    }

    @Override
    public String getEndPoint() {
        return "/sessions?_action=logout";
    }

    @Override
    public String generateBody() {
        return "";
    }
}

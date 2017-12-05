package com.kt.ciclient.command.request;

import com.kt.ciclient.command.CiRequest;
import com.kt.ciclient.command.RequestMethod;
import com.kt.ciclient.command.response.GenWbxSAMLRespResponse;

/**
 * Created by Vega Zhou on 2015/11/30.
 */
public class GenWbxSAMLRespRequest extends CiRequest<GenWbxSAMLRespResponse> {

    private String spid;

    private String uid;

    @Override
    public RequestMethod getMethod() {
        return RequestMethod.POST;
    }

    @Override
    public String getEndPoint() {
        return "/wbxsamlresp?_action=genwbxsamlresp";
    }

    public String getSpid() {
        return spid;
    }

    public void setSpid(String spid) {
        this.spid = spid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

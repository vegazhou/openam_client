package com.kt.ciclient.command.response;

import com.kt.ciclient.command.CiResponse;

/**
 * Created by Vega Zhou on 2015/11/30.
 */
public class GenWbxSAMLRespResponse extends CiResponse {
    private int code;

    private String samlresponse;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getSamlresponse() {
        return samlresponse;
    }

    public void setSamlresponse(String samlresponse) {
        this.samlresponse = samlresponse;
    }


}

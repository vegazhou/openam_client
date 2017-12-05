package com.kt.ciclient.command.response;

import com.kt.ciclient.command.CiResponse;

/**
 * Created by Vega Zhou on 2015/11/30.
 */
public class LogoutResponse extends CiResponse {
    private String result;

    private int code = 0;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

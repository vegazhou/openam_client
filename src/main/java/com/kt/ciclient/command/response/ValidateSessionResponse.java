package com.kt.ciclient.command.response;

import com.kt.ciclient.command.CiResponse;

/**
 * Created by Vega Zhou on 2015/10/12.
 */
public class ValidateSessionResponse extends CiResponse {

    private boolean valid;

    private String uid;

    private String cn;

    private long expiredIn;

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public long getExpiredIn() {
        return expiredIn;
    }

    public void setExpiredIn(long expiredIn) {
        this.expiredIn = expiredIn;
    }
}

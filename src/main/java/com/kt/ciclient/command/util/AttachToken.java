package com.kt.ciclient.command.util;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.log4j.Logger;

/**
 * Created by Vega Zhou on 2015/10/12.
 */
public class AttachToken {

    private static final Logger logger = Logger.getLogger(AttachToken.class);

    private static final String TOKEN_COOKIE_NAME = "iPlanetDirectoryPro";

    public static void attachToken(String token, HttpUriRequest request) {
        if (request != null) {
            logger.debug("iPlanetDirectoryPro=" + token);
            request.setHeader(TOKEN_COOKIE_NAME, token);
        }
    }
}

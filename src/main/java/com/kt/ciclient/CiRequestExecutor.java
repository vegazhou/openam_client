package com.kt.ciclient;

import com.kt.ciclient.command.CiRequest;
import com.kt.ciclient.command.CiResponse;
import com.kt.ciclient.command.util.AttachToken;
import com.kt.ciclient.exception.CiApiException;
import com.kt.ciclient.exception.CiTokenExpiredException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URI;

/**
 * Created by Vega Zhou on 2015/10/12.
 */
public class CiRequestExecutor {

    private static final Logger logger = Logger.getLogger(CiRequestExecutor.class);

    private static final String JSON_CONTENT_TYPE = "application/json";

    private String ciServerUrl;

    private String token;

    public CiRequestExecutor(String ciServerUrl, String token) {
        this.ciServerUrl = ciServerUrl;
        this.token = token;
    }


    public <R extends CiResponse> R executeRequest(CiRequest<R> r) throws CiApiException {
        try {
            HttpUriRequest httpUriRequest = buildHttpRequest(r);
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpResponse httpResponse = httpClient.execute(httpUriRequest);
            return processResponse(httpResponse, r);
        } catch (IOException e) {
            throw new CiApiException(e);
        }
    }

    private <R extends CiResponse> R processResponse(HttpResponse httpResponse, CiRequest<R> command) throws CiApiException {
        try {
            String json = EntityUtils.toString(httpResponse.getEntity());
            logger.debug(json);
            processStatusCode(httpResponse.getStatusLine().getStatusCode());
            return command.processResponse(json);
        } catch (IOException e) {
            throw new CiApiException(e);
        }
    }

    private void processStatusCode(int statusCode) throws CiApiException {
        if (statusCode != 200) {
            if (statusCode == 401 || statusCode == 403) {
                throw new CiTokenExpiredException();
            }
            throw new CiApiException("API call returns invalid " +
                    "status code: " + statusCode);
        }
    }

    private HttpUriRequest buildHttpRequest(CiRequest command) {
        HttpRequestBase req = null;
        switch (command.getMethod()) {
            case POST:
                req = buildPostRequest(command);
                break;
            case GET:
                req = buildGetRequest(command);
                break;
            case PUT:
                req = buildPutRequest(command);
                break;
            case DELETE:
                req = buildDeleteRequest(command);
        }

        if (req != null) {
            if (StringUtils.isNotBlank(token)) {
                AttachToken.attachToken(token, req);
            }
            req.setURI(URI.create(this.ciServerUrl + command.getEndPoint()));
            command.assembleHeaders(req);
        }
        return req;
    }

    private HttpPost buildPostRequest(CiRequest command) {
        HttpPost post = new HttpPost();
        post.setHeader("Content-Type", JSON_CONTENT_TYPE);
        post.setEntity(new StringEntity(command.generateBody(), "UTF-8"));
        logger.debug(command.generateBody());
        return post;
    }

    private HttpGet buildGetRequest(CiRequest command) {
        HttpGet get = new HttpGet();
        return get;
    }

    private HttpPut buildPutRequest(CiRequest command) {
        HttpPut put = new HttpPut();
        return put;
    }

    private HttpDelete buildDeleteRequest(CiRequest command) {
        HttpDelete delete = new HttpDelete();
        return delete;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

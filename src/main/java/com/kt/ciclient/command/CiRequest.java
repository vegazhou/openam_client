package com.kt.ciclient.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Vega Zhou on 2015/10/12.
 */
@JsonIgnoreProperties({"endPoint", "method"})
public abstract class CiRequest<R extends CiResponse> {

    private static final Logger LOGGER = Logger.getLogger(CiRequest.class);

    public abstract RequestMethod getMethod();

    public abstract String getEndPoint();

    public String generateBody() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    public void assembleHeaders(HttpRequestBase httpRequest) {
        // do nothing by default;
    }

    public R processResponse(String response) {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Class resultClass = (Class) params[0];
        try {

            ObjectMapper mapper = new ObjectMapper();
            R result = (R) mapper.readValue(response, resultClass);
            return result;
        } catch (JsonMappingException e) {
            LOGGER.warn("fail to mapping json response :" + response, e);
        } catch (JsonParseException e) {
            LOGGER.warn("invalid json response :" + response, e);
        } catch (IOException e) {
            LOGGER.warn("fail to mapping response :" + response, e);
        }
        return null;
    }
}

package com.py.contiappapi;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Api {
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    public String makeGetRequest(String url) throws IOException {
        HttpGet request = new HttpGet(url);
        return executeRequest(request);
    }

    public String makePostRequest(String url, String jsonPayload) throws IOException {
        HttpPost request = new HttpPost(url);
        StringEntity entity = new StringEntity(jsonPayload);
        request.setEntity(entity);
        request.setHeader("Content-type", "application/json");
        return executeRequest(request);
    }

    public String makePutRequest(String url, String jsonPayload) throws IOException {
        HttpPut request = new HttpPut(url);
        StringEntity entity = new StringEntity(jsonPayload);
        request.setEntity(entity);
        request.setHeader("Content-type", "application/json");
        return executeRequest(request);
    }

    public String makeDeleteRequest(String url) throws IOException {
        HttpDelete request = new HttpDelete(url);
        return executeRequest(request);
    }

    private String executeRequest(HttpUriRequest request) throws IOException {
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            return entity != null ? EntityUtils.toString(entity) : null;
        }
    }
}

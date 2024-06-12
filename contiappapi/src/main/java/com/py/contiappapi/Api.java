package com.py.contiappapi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

// Interfaces para interceptores
interface RequestInterceptor {
    void intercept(HttpURLConnection connection) throws Exception;
}

interface ResponseInterceptor {
    void intercept(HttpURLConnection connection, String response) throws Exception;
}

public class Api {
    private String baseUrl;
    private List<RequestInterceptor> requestInterceptors;
    private List<ResponseInterceptor> responseInterceptors;
    private List<String[]> defaultHeaders;

    public Api(String baseUrl) {
        this.baseUrl = baseUrl;
        this.requestInterceptors = new ArrayList<>();
        this.responseInterceptors = new ArrayList<>();
        this.defaultHeaders = new ArrayList<>();
    }

    // Método para agregar interceptores de solicitud
    public void addRequestInterceptor(RequestInterceptor interceptor) {
        this.requestInterceptors.add(interceptor);
    }

    // Método para agregar interceptores de respuesta
    public void addResponseInterceptor(ResponseInterceptor interceptor) {
        this.responseInterceptors.add(interceptor);
    }

    // Método para agregar encabezados por defecto
    public void addDefaultHeaders(String[] headers) {
        this.defaultHeaders.add(headers);
    }

    // Método GET optimizado
    public String get(String endpoint, String[][] customHeaders) throws Exception {
        return executeRequest("GET", endpoint, null, customHeaders);
    }

    // Método POST optimizado
    public String post(String endpoint, String jsonInputString, String[][] customHeaders) throws Exception {
        return executeRequest("POST", endpoint, jsonInputString, customHeaders);
    }

    // Método PUT optimizado
    public String put(String endpoint, String jsonInputString, String[][] customHeaders) throws Exception {
        return executeRequest("PUT", endpoint, jsonInputString, customHeaders);
    }

    // Método DELETE optimizado
    public String delete(String endpoint, String[][] customHeaders) throws Exception {
        return executeRequest("DELETE", endpoint, null, customHeaders);
    }

    // Método genérico para ejecutar solicitudes HTTP
    private String executeRequest(String method, String endpoint, String requestBody, String[][] customHeaders) throws Exception {
        URL url = new URL(baseUrl + endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);

        applyRequestInterceptors(connection);

        // Aplicar encabezados por defecto si no se proporcionan encabezados personalizados
        if (customHeaders == null || customHeaders.length == 0) {
            for (String[] header : defaultHeaders) {
                connection.setRequestProperty(header[0], header[1]);
            }
        } else { // Si se proporcionan encabezados personalizados, aplicarlos
            for (String[] header : customHeaders) {
                connection.setRequestProperty(header[0], header[1]);
            }
        }

        if (method.equals("POST") || method.equals("PUT")) {
            connection.setDoOutput(true);
            if (requestBody != null) {
                writeRequestBody(connection, requestBody);
            }
        }

        String response = getResponse(connection);
        applyResponseInterceptors(connection, response);

        return response;
    }

    // Método para escribir el cuerpo de la solicitud
    private void writeRequestBody(HttpURLConnection connection, String jsonInputString) throws Exception {
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
    }

    // Método para obtener la respuesta
    private String getResponse(HttpURLConnection connection) throws Exception {
        int status = connection.getResponseCode();
        BufferedReader reader;

        if (status > 299) {
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream(), "utf-8"));
        } else {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
        }

        StringBuilder content = new StringBuilder();
        String inputLine;
        while ((inputLine = reader.readLine()) != null) {
            content.append(inputLine);
        }
        reader.close();
        connection.disconnect();

        if (status != 200) {
            throw new Exception("Error: " + status + " - " + content.toString());
        }

        return content.toString();
    }

    // Aplicar interceptores de solicitud
    private void applyRequestInterceptors(HttpURLConnection connection) throws Exception {
        for (RequestInterceptor interceptor : requestInterceptors) {
            interceptor.intercept(connection);
        }
    }

    // Aplicar interceptores de respuesta
    private void applyResponseInterceptors(HttpURLConnection connection, String response) throws Exception {
        for (ResponseInterceptor interceptor : responseInterceptors) {
            interceptor.intercept(connection, response);
        }
    }
}
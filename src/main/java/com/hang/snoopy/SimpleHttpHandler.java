package com.hang.snoopy;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class SimpleHttpHandler implements HttpHandler
{
    final Logger logger = LoggerFactory.getLogger(SimpleHttpHandler.class);

    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        String method = exchange.getRequestMethod();
        URI uri = exchange.getRequestURI();
        String path = uri.getPath();
        String query = uri.getQuery();
        logger.info("{} {}:{}", method, path, query);
        Headers headers = exchange.getResponseHeaders();
        headers.set("Context-Type", "text/html;charset=utf-8");
        headers.set("Cache-Control", "no-cache");
        exchange.sendResponseHeaders(200, 0);
        String helloTxt = "<h1>Hello World</h1> <p>" + LocalDateTime.now().withNano(0) + "</p>";

        try(OutputStream outputStream = exchange.getResponseBody()){
            outputStream.write(helloTxt.getBytes(StandardCharsets.UTF_8));
        }
    }
}

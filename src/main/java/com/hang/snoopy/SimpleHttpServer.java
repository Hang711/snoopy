package com.hang.snoopy;

import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class SimpleHttpServer implements AutoCloseable {

    final Logger logger = LoggerFactory.getLogger(SimpleHttpServer.class);

    final HttpServer httpServer;

    final String host;

    final int port;

    public static void main(String[] args)
    {
        String host = "0.0.0.0";
        int port = 8000;
        try (SimpleHttpServer simpleHttpServer = new SimpleHttpServer(host, port)) {
            for (; ; ) {
                TimeUnit.SECONDS.sleep(5);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SimpleHttpServer(String host, int port) throws IOException
    {
        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", 8000), 0);
        server.createContext("/", new SimpleHttpHandler());
        this.httpServer = server;
        this.host = host;
        this.port = port;
        this.httpServer.start();
        logger.info("start snoopy http server at {}:{}", host, port);
    }

    @Override
    public void close() throws Exception
    {
        httpServer.stop(3);
    }
}
package org.srpc.server.impl;

import org.srpc.server.Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.concurrent.Executors.*;

/**
 * <pre>
 *     the server center impl
 * </pre>
 * @auth BillWu
 * @since 2018-01-01
 */
public class ServiceCenter implements Server {

    private static ExecutorService executor = newFixedThreadPool(Runtime.getRuntime().availableProcessors() >> 1);

    public static ConcurrentHashMap<String, Class> serviceRegistry = new ConcurrentHashMap<String, Class>();

    private int port;

    public ServiceCenter(int port) {
        this.port = port;
    }

    public void stop() {
        executor.shutdown();
    }

    public void start() throws IOException {
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress(port));
        Socket accept;
        System.out.println("ServiceCenter has started , ..... port is " + port);
        try {
            while (true) {
                accept = server.accept();
                executor.execute(new ServiceTask(accept));     //call the ServiceTask
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    public void register(Class Interface, Class impl) {
        serviceRegistry.put(Interface.getName(), impl);
    }

    public int getPort() {
        return this.port;
    }
}

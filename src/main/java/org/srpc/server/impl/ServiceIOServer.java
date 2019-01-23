package org.srpc.server.impl;

import org.srpc.server.AbstractServer;
import org.srpc.server.Server;
import org.srpc.server.task.ServiceIOTask;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.*;

/**
 * <pre>
 *     the server center impl
 *     1.base on the io
 * </pre>
 * @auth BillWu
 * @since 2018-01-01
 */
public class ServiceIOServer extends AbstractServer implements Server {

    private static ExecutorService executor = newFixedThreadPool(Runtime.getRuntime().availableProcessors() >> 1);

    //must it
    public ServiceIOServer(int port) {
        super(port);
    }

    @Override
    public void stop() {
        super.stop();
        executor.shutdown();
    }

    @Override
    public void start() throws IOException {
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress(getPort()));
        Socket accept;
        System.out.println("ServiceIOServer has started , ..... port is " + getPort());
        try {
            while (true) {
                accept = server.accept();
                executor.execute(new ServiceIOTask(accept));     //call the ServiceIOTask
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

}

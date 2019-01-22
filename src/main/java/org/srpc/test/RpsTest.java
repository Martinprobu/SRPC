package org.srpc.test;

import org.srpc.api.ApiService;
import org.srpc.api.impl.ApiServiceImpl;
import org.srpc.client.impl.ServiceClient;
import org.srpc.server.Server;
import org.srpc.server.impl.ServiceCenter;

import java.io.IOException;
import java.net.InetSocketAddress;

public class RpsTest {

    static int serverPort = 8080;

    public static void main(String[] args) throws IOException {

        new Thread(new Runnable() {
            public void run() {
                //run the rpc server
                Server serviceServer = new ServiceCenter(serverPort);
                serviceServer.register(ApiService.class, ApiServiceImpl.class);
                try {
                    serviceServer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //run the api rpc invote
        ApiService test = ServiceClient.getRpc(ApiService.class, new InetSocketAddress("localhost", serverPort));
//        ApiService test = ServiceClient.getRpc(ApiService.class, new InetSocketAddress(serverPort));
        String testRes = test.getServer("billtest rpc ");
        System.out.println(testRes);
    }

}

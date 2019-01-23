package org.srpc.test;

import org.srpc.api.ApiService;
import org.srpc.api.impl.ApiServiceImpl;
import org.srpc.client.impl.ServiceIOClient;
import org.srpc.client.impl.ServiceNIOClient;
import org.srpc.server.Server;
import org.srpc.server.impl.ServiceIOServer;
import org.srpc.server.impl.ServiceNIOServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class RpsTest {

    static String hostName = "localhost";
    static int serverPort = 8080;

    public static void main(String[] args) throws IOException {

//        ArrayList aa;
        new Thread(new Runnable() {
            @Override
            public void run() {
                //run the rpc server
//                Server serviceServer = new ServiceIOServer(serverPort);
                Server serviceServer = new ServiceNIOServer(serverPort);
                serviceServer.register(ApiService.class, ApiServiceImpl.class);
                try {
                    serviceServer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //run the api rpc invote
//        ApiService test = ServiceIOClient.getRpc(ApiService.class, hostName, serverPort);
        ApiService test = ServiceNIOClient.getRpc(ApiService.class, hostName, serverPort);
        String testRes = test.getServer("billtest rpc ");
        System.out.println("test.getServer result : " + testRes);
    }

}

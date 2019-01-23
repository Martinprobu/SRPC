package org.srpc.server.impl;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import org.srpc.server.AbstractServer;
import org.srpc.server.Server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 * <pre>
 *     the server center impl
 *     1.base on the Netty Nio
 * </pre>
 * @auth BillWu
 * @since 2018-01-02
 */
public class ServiceNIOServer extends AbstractServer implements Server {

    private static ServerBootstrap serverBoot = new ServerBootstrap();
    private static NioEventLoopGroup boos = new NioEventLoopGroup();
    private static NioEventLoopGroup worker = new NioEventLoopGroup();

    //must it
    public ServiceNIOServer(int port) {
        super(port);
    }

    @Override
    public void stop() {
        super.stop();
        boos.shutdownGracefully();
        worker.shutdownGracefully();
    }

    @Override
    public void start() throws IOException {
        System.out.println("ServiceNIOServer has started , ..... port is " + getPort());

        serverBoot.group(boos, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
                                System.out.println("ServiceNIOServer msg : " + msg);
                                System.out.println("ServiceNIOServer End ");
//                                Object result = method.invoke(serviceClass.newInstance(), arguments);

//                                OutputStream output = new ObjectOutputStream();
//                                output.writeObject(result);
                            }
                        });
                    }
                })
                .bind(getPort());

    }

}

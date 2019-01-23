package org.srpc.client.impl;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import org.srpc.api.impl.ApiServiceImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;

/**
 * <pre>
 *     server interface
 * </pre>
 *
 * @auth BillWu
 * @since 2018-01-01
 */
public class ServiceNIOClient<T> {

    public static <T> T getRpc(final Class serviceInterface, final String hostName, final int port) throws IOException {
        return (T)Proxy.newProxyInstance(serviceInterface.getClassLoader(), new Class[]{serviceInterface},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("NIO Proxy.newProxyInstance proxy method is " + method + method.getParameterTypes());
                        Bootstrap bootstrap = new Bootstrap();
                        NioEventLoopGroup group = new NioEventLoopGroup();
                        try {
                            //nio client logic
                            bootstrap.group(group)
                                    .channel(NioSocketChannel.class)
                                    .handler(new ChannelInitializer<Channel>() {
                                        @Override
                                        protected void initChannel(Channel ch) {
                                            ch.pipeline().addLast(new StringEncoder());
                                        }
                                    });

                            Channel channel = bootstrap.connect(hostName, port).channel();
                            channel.writeAndFlush("NIO Client : " + new Date() + ": hello world!");
                            return ApiServiceImpl.class;    //test
//                        Thread.sleep(2000);
                        } finally {
                            group.shutdownGracefully();
                        }
                    }
                }
        );

    }

}

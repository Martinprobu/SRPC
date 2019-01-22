package org.srpc.client.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * <pre>
 *     server interface
 * </pre>
 *
 * @auth BillWu
 * @since 2018-01-01
 */
public class ServiceClient<T> {

    public static <T> T getRpc(final Class serviceInterface, final InetSocketAddress add) throws IOException {
        return (T)Proxy.newProxyInstance(serviceInterface.getClassLoader(), new Class[]{serviceInterface},
                new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("Proxy.newProxyInstance proxy method is " + method + method.getParameterTypes());
                        Socket socket = null;
                        ObjectOutputStream output = null;
                        ObjectInputStream input = null;
                        try {
                            socket = new Socket();
                            socket.connect(add);
                            output = new ObjectOutputStream(socket.getOutputStream());
                            output.writeUTF(serviceInterface.getName());
                            output.writeUTF(method.getName());
                            output.writeObject(method.getParameterTypes());
                            output.writeObject(args);

                            input = new ObjectInputStream(socket.getInputStream());
                            return input.readObject();
                        } finally {
                            if (socket != null) {
                                socket.close();
                            }
                            if (output != null) {
                                output.close();
                            }
                            if (input != null) {
                                input.close();
                            }
                        }
                    }
                }
        );

    }

}

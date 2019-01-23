package org.srpc.server;

import java.util.concurrent.ConcurrentHashMap;

/**
 * <pre>
 *     the abstract server center impl
 * </pre>
 * @auth BillWu
 * @since 2018-01-01
 */
public abstract class AbstractServer implements Server {

    public static ConcurrentHashMap<String, Class> serviceRegistry = new ConcurrentHashMap<String, Class>();

    private int port;

    public AbstractServer(int port) {
        this.port = port;
    }

    @Override
    public int getPort() {
        return this.port;
    }

    @Override
    public void stop() {
        serviceRegistry.clear();
    }

    @Override
    public void register(Class Interface, Class impl) {
        serviceRegistry.put(Interface.getName(), impl);
    }

}

package org.srpc.server;

import java.io.IOException;

/**
 * <pre>
 *     server interface
 * </pre>
 *
 * @auth BillWu
 * @since 2018-01-01
 */
public interface Server {

    /**
     * stop the server
     */
    public void stop();

    /**
     * start the server
     */
    public void start() throws IOException;

    /**
     * register the server
     */
    public void register(Class Interface, Class impl);

    /**
     * get the port of server
     * @return
     */
    public int getPort();

}

package org.srpc.api.impl;

import org.srpc.api.ApiService;

/**
 * @auth BillWu
 * @since 2018-01-01
 */
public class ApiServiceImpl implements ApiService {
    public String getServer(String name) {
        System.out.println("ApiServiceImpl.getServer : " + name);
        return name;
    }

//    public static void main(String[] args) {
//        System.out.println(Runtime.getRuntime().availableProcessors());
//    }
}

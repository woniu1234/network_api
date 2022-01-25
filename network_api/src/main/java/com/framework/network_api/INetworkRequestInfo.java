package com.framework.network_api;


import java.util.HashMap;

/**
 * @author lst
 * @Description: 全局header(header)
 * @date 2021/6/21
 */
public interface INetworkRequestInfo {
    HashMap<String, String> getRequestHeaderMap();

    boolean isDebug();
}

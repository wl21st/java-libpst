package com.sf.common;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.util.Iterator;
import java.util.List;

public class ProxyConfig {

  private static String host;
  private static int port;

  public static void init() {
    System.setProperty("java.net.useSystemProxies", "true");
    Proxy proxy = getProxy();
    if (proxy != null) {
      InetSocketAddress addr = (InetSocketAddress) proxy.address();

      if (addr != null) {

        host = addr.getHostName();
        port = addr.getPort();

        System.setProperty("java.net.useSystemProxies", "false");
        System.setProperty("http.proxyHost", host);
        System.setProperty("http.proxyPort", "" + port);

      }

    }
    System.setProperty("java.net.useSystemProxies", "false");
  }

  public static String getHost() {
    return host;
  }

  public static int getPort() {
    return port;
  }

  private static Proxy getProxy() {
    List<Proxy> l = null;
    try {
      ProxySelector def = ProxySelector.getDefault();

      l = def.select(new URI("http://foo/bar"));
      ProxySelector.setDefault(null);
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (l != null) {
      for (Iterator<Proxy> iter = l.iterator(); iter.hasNext();) {
        java.net.Proxy proxy = iter.next();
        return proxy;
      }
    }
    return null;
  }
}
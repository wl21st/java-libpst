package com.sf.common;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetworkUtils {

  private static final Logger logger = LoggerFactory.getLogger(NetworkUtils.class);

  private NetworkUtils() {
  }

  public static void main(String[] args) {

    Map<InetAddress, NetworkInterface> addrs = getIPAddrs();

    List<NetworkInterface> nicList = getAllNetworkInterfaces();

    for (InetAddress addr : addrs.keySet()) {
      NetworkInterface nic = addrs.get(addr);
      for (InterfaceAddress address : nic.getInterfaceAddresses()) {
        if (address.getAddress() instanceof Inet4Address) {
          logger.info(nic + "-->netmask:" + address.getNetworkPrefixLength());
        }
      }
      logger.info(addr.toString() + ":" + nic);
    }

    // for (NetworkInterface nic : nicList) {
    // logger.info(nic.toString());
    // }

  }

  private static List<NetworkInterface> getAllNetworkInterfaces() {

    List<NetworkInterface> result = new ArrayList<NetworkInterface>();
    try {
      Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();

      while (en.hasMoreElements()) {
        result.add(en.nextElement());
      }
    } catch (SocketException e) {
      logger.error("Failed to get the network interfaces!", e);
    }

    return result;
  }

  /**
   * Return the primary hostname(or unknown host).
   * 
   * @return the hostname.
   */
  public static String getHostname() {
    InetAddress ipAddr;
    try {
      ipAddr = InetAddress.getLocalHost();
    } catch (UnknownHostException e) {
      return "Unknown host";
    }

    return ipAddr.getCanonicalHostName();
  }

  /**
   * Get the all the IP Address(V4 only with loopback).
   * 
   * @return all the IPs.
   */
  public static Map<InetAddress, NetworkInterface> getIPAddrs() {

    Map<InetAddress, NetworkInterface> result = new LinkedHashMap<InetAddress, NetworkInterface>();
    try {
      Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();

      while (en.hasMoreElements()) {

        NetworkInterface nic = en.nextElement();
        Enumeration<InetAddress> enAddr = nic.getInetAddresses();

        while (enAddr.hasMoreElements()) {

          InetAddress addr = enAddr.nextElement();

          if (addr instanceof Inet4Address) {
            result.put(addr, nic);
          }
        }

      }

    } catch (SocketException e) {
      logger.error("Failed to get the network interfaces!", e);
    }

    return result;
  }
}

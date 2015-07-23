package com.sf.common;

import java.io.IOException;
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

public class CmdUtils {

  private static final Logger logger = LoggerFactory.getLogger(CmdUtils.class);

  private CmdUtils() {

  }

  public static void main(String[] args) {
    try {
      Runtime.getRuntime()
          .exec("cmd /c start sfvpn.bat");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
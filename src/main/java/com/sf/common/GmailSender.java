package com.sf.common;

import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GmailSender {

  private static final Logger logger = LoggerFactory.getLogger(GmailSender.class);

  private static final void handleProxy() {

    System.setProperty("java.net.useSystemProxies", "true");
    System.out.println("detecting proxies");
    List l = null;
    try {
      l = ProxySelector.getDefault()
          .select(new URI("http://www.google.com/"));
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    if (l != null) {
      for (Iterator iter = l.iterator(); iter.hasNext();) {
        java.net.Proxy proxy = (java.net.Proxy) iter.next();
        System.out.println("proxy type: " + proxy.type());

        InetSocketAddress addr = (InetSocketAddress) proxy.address();

        if (addr == null) {
          System.out.println("No Proxy");
        } else {
          System.out.println("proxy hostname: " + addr.getHostName());
          System.setProperty("http.proxyHost", addr.getHostName());
          System.out.println("proxy port: " + addr.getPort());
          System.setProperty("http.proxyPort", Integer.toString(addr.getPort()));
        }
      }
    }
  }

  private static final void setProxy() {
    System.setProperty("proxySet", "true");
    System.setProperty("http.proxyHost", "proxy");
    System.setProperty("http.proxyPort", "8080");
    System.setProperty("https.proxyHost", "proxy");
    System.setProperty("https.proxyPort", "8080");
    // System.setProperty("socksProxyHost", "proxy");
    // System.setProperty("socksProxyPort", "8080");
  }

  public static void main(String[] args) {

    // handleProxy();
    // ProxyConfig.init();

    setProxy();

    final String username = "wlandy83@gmail.com";
    final String password = "tangerine2002";

    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.debug", "true");
    props.put("mail.smtp.port", "587");

    props.put("proxySet", "true");
    props.put("socksProxyHost", "proxyv");
    props.put("socksProxyPort", "8080");

    Session session = Session.getInstance(props,
        new javax.mail.Authenticator() {

          /*
           * (non-Javadoc)
           * 
           * @see javax.mail.Authenticator#getPasswordAuthentication()
           */
          @Override
          protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
            return new javax.mail.PasswordAuthentication(username, password);
          }

        });

    try {

      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress("wlandy83@gmail.com"));
      message.setRecipients(Message.RecipientType.TO,
          InternetAddress.parse("wlandy83@gmail.com"));
      message.setSubject("Testing Subject");
      message.setText("Dear Mail Crawler,"
          + "\n\n No spam to my email, please!");

      Transport.send(message);

      System.out.println("Done");

    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }
}
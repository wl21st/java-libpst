package com.sf.common;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mluo
 *
 */
public class MailSender {

  /** Logger */
  private static final Logger logger = LoggerFactory.getLogger(MailSender.class);

  /**
   * 
   */
  public MailSender() {

  }

  public void start() throws Exception {

    Properties props = new Properties();
    // props.put("mail.smtp.host", "mail.successfactors.com");
    props.put("mail.smtp.host", "mail.sap.corp");

    Session session = Session.getInstance(props, null);

    try {
      MimeMessage msg = new MimeMessage(session);
      msg.setFrom(new InternetAddress("lu.luo@sap.com"));
      msg.setRecipients(Message.RecipientType.TO, "lu.luo@sap.com");
      msg.setSubject("JavaMail hello world example");
      msg.setSentDate(new Date());
      msg.setText("Hello, world!\n", "utf-8");
      Transport.send(msg);

      logger.info("Message sent: " + msg);
    } catch (MessagingException e) {
      logger.error("send failed, exception: " + e);
      throw e;
    }
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    MailSender app = new MailSender();
    try {
      app.start();
    } catch (Exception e) {
      logger.error("Exception", e);
    }
  }

}

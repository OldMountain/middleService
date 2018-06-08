package com.nxd.middle.util;

import com.nxd.middle.entity.Email;
import com.nxd.middle.entity.Image;
import com.sun.mail.smtp.SMTPTransport;

import javax.activation.DataHandler;
import javax.activation.URLDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Date;
import java.util.Properties;

/**
 * EmailUtil
 *
 * @author luhangqi
 * @date 2017/12/7
 */
public class EmailUtil {

    public static void send(Email email, Image image, boolean all) throws IOException, MessagingException {
        Properties props = PropertiesUtils.getProperties("mail.properties");
        // 根据参数配置，创建会话对象（为了发送邮件准备的）
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("", "");
            }
        };
        Session session = Session.getInstance(props);
        // 创建邮件对象
        MimeMessage message = new MimeMessage(session);
        createMail(message, email, image, props);
        // 3. To: 收件人
        if (all) {
            //群发
            String receiveMail = email.getReceiveMail();
            if (receiveMail != null && !receiveMail.equals("")) {
                String[] receiveMails = receiveMail.split(";");
                if (receiveMails != null && receiveMails.length > 0) {
                    for (int i = 0; i < receiveMails.length; i++) {
                        message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMails[i]));
                    }
                }
            }
        } else {
            if (email.getReceiveName() != null) {
                message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email.getReceiveMail(), email.getReceiveName(), "UTF-8"));
            } else {
                message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email.getReceiveMail(), "UTF-8"));
            }
        }
//        session.setPasswordAuthentication();
        //    To: 增加收件人（可选）
//        message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress("dd@receive.com", "USER_DD", "UTF-8"));

        //    Bcc: 密送（可选）
//        message.setRecipient(MimeMessage.RecipientType.BCC, new InternetAddress("ff@receive.com", "USER_FF", "UTF-8"));

        finish(session, message, props);

    }

//    public static void send(Email email,boolean all) throws IOException, MessagingException {
//        Properties props = PropertiesUtils.getProperties("mail.properties");
//        Session session = Session.getInstance(props);
//        MimeMessage message = new MimeMessage(session);
//        createMail(message, email, props);
//        String receiveMail = email.getReceiveMail();
//        if (receiveMail != null && !receiveMail.equals("")) {
//            String[] receiveMails = receiveMail.split(";");
//            if (receiveMails != null && receiveMails.length > 0) {
//                for (int i = 0; i < receiveMails.length; i++) {
//                    message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMails[i]));
//                }
//            }
//        }
//        finish(session,message,props);
//    }


    //创建邮件
    public static MimeMessage createMail(MimeMessage message, Email email, Image image, Properties props) throws UnsupportedEncodingException, MessagingException {
        // 1. 创建一封邮件
        // 用于连接邮件服务器的参数配置（发送邮件时才需要用到）
        String key = email.getKey();
        if (key == null || !key.equals(props.getProperty("mail.key"))) {
            throw new CommonException("key不存在");
        }
        if (email.getReceiveMail() == null || email.getReceiveMail().equals("")) {
            throw new CommonException("收件人不能为空");
        }
        if (email.getContent() == null || email.getContent().equals("")) {
            throw new CommonException("发送内容不能为空");
        }
//        session.setDebug(true);
        /*
         * 也可以根据已有的eml邮件文件创建 MimeMessage 对象
         * MimeMessage message = new MimeMessage(session, new FileInputStream("MyEmail.eml"));
         */

        // 2. From: 发件人
        //    其中 InternetAddress 的三个参数分别为: 邮箱, 显示的昵称(只用于显示, 没有特别的要求), 昵称的字符集编码
        //    真正要发送时, 邮箱必须是真实有效的邮箱。
        message.setFrom(new InternetAddress(props.getProperty("mail.stmp.user"), email.getSendName(), "UTF-8"));

        //抄送
        String copyReceiveMail = email.getCopyReceiveMail();
        if (copyReceiveMail != null && !copyReceiveMail.equals("")) {
            String[] copyReceiveMails = copyReceiveMail.split(";");
            if (copyReceiveMails != null && copyReceiveMails.length > 0) {
                for (int i = 0; i < copyReceiveMails.length; i++) {
                    message.addRecipient(MimeMessage.RecipientType.CC, new InternetAddress(copyReceiveMails[i]));
                }
            }
        }
        // 4. Subject: 邮件主题
        message.setSubject(email.getSubject(), "UTF-8");

        // 5. Content: 邮件正文（可以使用html标签）
        Multipart multipart = new MimeMultipart("related");
        BodyPart contentPart = new MimeBodyPart();
//        contentPart.setHeader("Content-Type", "text/html;charset=UTF-8");
//        contentPart.setHeader("Content-Transfer-Encoding", "base64");
        contentPart.setContent(email.getContent(), "text/html;charset=UTF-8");
        multipart.addBodyPart(contentPart);
        if (image != null && image.getPaths() != null) {
            for (int i = 0; i < image.getPaths().length; i++) {
                MimeBodyPart imgBodyPart = new MimeBodyPart();
                DataHandler dataHandler = null;
                try {
                    dataHandler = new DataHandler(new URLDataSource(new URL(image.getPaths()[i])));
//                    File file = new File("D:/picture/uploadFiles/uploadImgs/hotel/9a76e4df0644492f95dd213d37b5b961.png");
//                    dataHandler = new DataHandler(new FileDataSource(file));
                    imgBodyPart.setDataHandler(dataHandler);
//                        imgBodyPart.setContentID("abcd");
                    imgBodyPart.setHeader("Content-ID", "<" + (image.getCid() + (i + 1)) + ">");
                    multipart.addBodyPart(imgBodyPart);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        message.setContent(multipart);

        // 6. 设置显示的发件时间
        message.setSentDate(new Date());

        // 7. 保存前面的设置
        message.saveChanges();

        // 8. 将该邮件保存到本地
//        File file = new File("MyEmail.eml");
//        OutputStream out = new FileOutputStream("MyEmail.eml");
//        message.writeTo(out);
//        out.flush();
//        out.close();

        return null;
    }

    public static void finish(Session session, MimeMessage message, Properties props) throws MessagingException {
        // 4. 根据 Session 获取邮件传输对象
        SMTPTransport transport = (SMTPTransport) session.getTransport();
        transport.connect(props.getProperty("mail.stmp.user"), props.getProperty("mail.stmp.password"));
        // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(message, message.getAllRecipients());
        // 7. 关闭连接
        transport.close();
    }
}

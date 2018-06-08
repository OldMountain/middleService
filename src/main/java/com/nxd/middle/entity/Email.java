package com.nxd.middle.entity;

import com.nxd.middle.util.Tools;

import java.util.List;

/**
 * Email
 *
 * @author luhangqi
 * @date 2017/12/7
 */
public class Email {
    String sendName;
    String subject;
    String content;
    String receiveMail;
    String receiveName;
    String key;
    String copyReceiveMail;

    public Email() {
        this.sendName = "523";
        this.subject = "验证码";
    }

    public Email(String receiveMail) {
        this.sendName = "523";
        this.subject = "验证码";
        this.content = "你的验证码是:" + Tools.getRandomNum();
        this.receiveMail = receiveMail;
    }

    public Email(String content, String receiveMail) {
        this.sendName = "523";
        this.subject = "验证码";
        this.content = content;
        this.receiveMail = receiveMail;
    }

    public Email(String subject, String content, String receiveMail) {
        this.sendName = "523";
        this.subject = subject;
        this.content = content;
        this.receiveMail = receiveMail;
    }

    public Email(String sendName, String subject, String content, String receiveMail) {
        this.sendName = sendName;
        this.subject = subject;
        this.content = content;
        this.receiveMail = receiveMail;
    }

    public Email(String sendName, String subject, String content, String receiveMail, String copyReceiveMail) {
        this.sendName = sendName;
        this.subject = subject;
        this.content = content;
        this.receiveMail = receiveMail;
        this.copyReceiveMail = copyReceiveMail;
    }

    public Email(String sendName, String subject, String content, String receiveMail, String receiveName, String copyReceiveMail) {
        this.sendName = sendName;
        this.subject = subject;
        this.content = content;
        this.receiveMail = receiveMail;
        this.receiveName = receiveName;
        this.copyReceiveMail = copyReceiveMail;
    }

    public String getSendName() {
        return sendName;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public String getReceiveMail() {
        return receiveMail;
    }

    public String getCopyReceiveMail() {
        return copyReceiveMail;
    }

    public String getKey() {
        return key;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setReceiveMail(String receiveMail) {
        this.receiveMail = receiveMail;
    }

    public void setCopyReceiveMail(String copyReceiveMail) {
        this.copyReceiveMail = copyReceiveMail;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

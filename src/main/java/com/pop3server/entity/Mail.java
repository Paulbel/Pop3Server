package com.pop3server.entity;

import java.io.Serializable;
import java.util.Date;

public class Mail implements Serializable {
    private static final long serialVersionUID = -6820149946694827402L;

    private MailState state;
    private int id;
    private String senderLogin;
    private String receiverLogin;
    private String content;
    private String subject;
    private Date date;

    public MailState getState() {
        return state;
    }

    public void setState(MailState state) {
        this.state = state;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSenderLogin() {
        return senderLogin;
    }

    public void setSenderLogin(String senderLogin) {
        this.senderLogin = senderLogin;
    }

    public String getReceiverLogin() {
        return receiverLogin;
    }

    public void setReceiverLogin(String receiverLogin) {
        this.receiverLogin = receiverLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mail)) return false;

        Mail mail = (Mail) o;

        if (id != mail.id) return false;
        if (senderLogin != null ? !senderLogin.equals(mail.senderLogin) : mail.senderLogin != null) return false;
        if (receiverLogin != null ? !receiverLogin.equals(mail.receiverLogin) : mail.receiverLogin != null)
            return false;
        return content != null ? content.equals(mail.content) : mail.content == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (senderLogin != null ? senderLogin.hashCode() : 0);
        result = 31 * result + (receiverLogin != null ? receiverLogin.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "state=" + state +
                ", id=" + id +
                ", senderLogin='" + senderLogin + '\'' +
                ", receiverLogin='" + receiverLogin + '\'' +
                ", content='" + content + '\'' +
                ", subject='" + subject + '\'' +
                ", date=" + date +
                '}';
    }
}

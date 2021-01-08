package com.dattranuit.uitapp;

import java.io.Serializable;

public class Deadline implements Serializable {
    private String Subject;
    private String Context;
    private String timeDeadline;
    private String timeRemain;
    private String submitStatus;
    private String Url;

    public Deadline(String subject, String context, String timeDeadline, String timeRemain, String submitStatus, String url) {
        Subject = subject;
        Context = context;
        this.timeDeadline = timeDeadline;
        this.timeRemain = timeRemain;
        this.submitStatus = submitStatus;
        Url = url;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getContext() {
        return Context;
    }

    public void setContext(String context) {
        Context = context;
    }

    public String getTimeDeadline() {
        return timeDeadline;
    }

    public void setTimeDeadline(String timeDeadline) {
        this.timeDeadline = timeDeadline;
    }

    public String getTimeRemain() {
        return timeRemain;
    }

    public void setTimeRemain(String timeRemain) {
        this.timeRemain = timeRemain;
    }

    public String getSubmitStatus() {
        return submitStatus;
    }

    public void setSubmitStatus(String submitStatus) {
        this.submitStatus = submitStatus;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}

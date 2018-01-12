package com.szy.myapplication.Entity;

/**
 * Created by Administrator on 2018/1/12 0012.
 * 消息实体类
 */

public class MessageEntity {
    private String title;
    private String content;
    private String time;
    private String url;

    public MessageEntity(String title, String content, String time) {
        this.title = title;
        this.content = content;
        this.time = time;
    }

    public MessageEntity(String title, String content, String time, String url) {
        this.title = title;
        this.content = content;
        this.time = time;
        this.url = url;
    }

    public MessageEntity() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

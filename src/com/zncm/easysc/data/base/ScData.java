package com.zncm.easysc.data.base;

import com.j256.ormlite.field.DatabaseField;
import com.zncm.easysc.data.BaseData;

import java.io.Serializable;

public class ScData extends BaseData implements Serializable {

    private static final long serialVersionUID = 8838725426885988959L;
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String title;
    @DatabaseField
    private String type;
    @DatabaseField
    private String author;
    @DatabaseField
    private String content;
    @DatabaseField
    private String comment;
    @DatabaseField
    private int status = 0;//收藏,0未,1收藏


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "ScData{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", comment='" + comment + '\'' +
                ", status=" + status +
                '}';
    }
}

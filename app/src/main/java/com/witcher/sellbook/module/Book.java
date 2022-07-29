package com.witcher.sellbook.module;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Book {

    @Id(autoincrement = true)
    private long id;

    /**
     * 书名
     */
    private String name;

    /**
     * 价格 单位分
     */
    private long price;

    /**
     * 封面
     */
    private String cover;

    /**
     * 作者
     */
    private String author;

    /**
     * 出版社
     */
    private String press;

    /**
     * 发布日
     */
    private String publishData;

    /**
     * 标签
     */
    private String labels;

    /**
     * 详情
     */
    private String details;

    @Generated(hash = 158025040)
    public Book(long id, String name, long price, String cover, String author,
            String press, String publishData, String labels, String details) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.cover = cover;
        this.author = author;
        this.press = press;
        this.publishData = publishData;
        this.labels = labels;
        this.details = details;
    }

    @Generated(hash = 1839243756)
    public Book() {
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getPublishData() {
        return publishData;
    }

    public void setPublishData(String publishData) {
        this.publishData = publishData;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }
}

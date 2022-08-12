package com.witcher.sellbook.module;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 收藏关系实体类
 */
@Entity
public class Collection {

    /**
     * 收藏关系id 主键 自增
     */
    @Id(autoincrement = true)
    private Long id;
    /**
     * 用户id
     */
    private long uid;
    /**
     * 书id
     */
    private long bookId;

    @Generated(hash = 1238595165)
    public Collection(Long id, long uid, long bookId) {
        this.id = id;
        this.uid = uid;
        this.bookId = bookId;
    }

    @Generated(hash = 1149123052)
    public Collection() {
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

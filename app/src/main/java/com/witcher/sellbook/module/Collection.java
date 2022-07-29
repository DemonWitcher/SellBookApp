package com.witcher.sellbook.module;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Collection {

    private long uid;
    private long bookId;

    @Generated(hash = 537561584)
    public Collection(long uid, long bookId) {
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
}

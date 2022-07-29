package com.witcher.sellbook.event;

public class CollectionEvent {

    private long bookId;
    private boolean isCollection;

    public CollectionEvent(long bookId, boolean isCollection) {
        this.bookId = bookId;
        this.isCollection = isCollection;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public boolean isCollection() {
        return isCollection;
    }

    public void setCollection(boolean collection) {
        isCollection = collection;
    }
}

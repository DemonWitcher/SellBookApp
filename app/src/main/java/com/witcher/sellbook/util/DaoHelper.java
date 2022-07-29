package com.witcher.sellbook.util;

import android.database.sqlite.SQLiteDatabase;

import com.witcher.sellbook.BookApp;
import com.witcher.sellbook.module.Book;
import com.witcher.sellbook.module.BookDao;
import com.witcher.sellbook.module.Collection;
import com.witcher.sellbook.module.CollectionDao;
import com.witcher.sellbook.module.DaoMaster;
import com.witcher.sellbook.module.DaoSession;
import com.witcher.sellbook.module.Order;
import com.witcher.sellbook.module.OrderDao;
import com.witcher.sellbook.module.User;
import com.witcher.sellbook.module.UserDao;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询所有书籍
 * 查询收藏的所有书籍
 * 查询某个用户by id
 * 查询收藏关系 by uid
 * 查询所有订单 by uid
 * <p>
 * 插入书籍list
 * 插入一条用户
 * 插入一个收藏关系
 * 插入一个订单
 * <p>
 * 删除一个收藏关系 by uid+bookId
 */
public class DaoHelper {

    private volatile static DaoHelper daoHelper;

    public static DaoHelper getInstance() {
        if (daoHelper == null) {
            synchronized (DaoHelper.class) {
                if (daoHelper == null) {
                    daoHelper = new DaoHelper();
                }
            }
        }
        return daoHelper;
    }

    private DaoHelper() {
        init();
    }

    private BookDao bookDao;
    private CollectionDao collectionDao;
    private UserDao userDao;
    private OrderDao orderDao;

    private void init() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(BookApp.getApp(), "sellBook.db");
        SQLiteDatabase db = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        bookDao = daoSession.getBookDao();
        collectionDao = daoSession.getCollectionDao();
        userDao = daoSession.getUserDao();
        orderDao = daoSession.getOrderDao();
    }

    public List<Book> getAllBook() {
        return bookDao.loadAll();
    }

    public List<Book> getAllCollectionBook() {
        List<Book> list = new ArrayList<>();
        List<Collection> collections = UserHelper.getCollections();
        if (collections != null) {
            for (Collection collection : collections) {
                if (collection == null) {
                    continue;
                }
                Book book = bookDao.queryBuilder().where(BookDao.Properties.Id.eq(collection.getBookId())).unique();
                list.add(book);
            }
        }
        return list;
    }

    public User getUser(long id) {
        return userDao.load(id);
    }

    public User getUserByPhone(String phone) {
        return userDao.queryBuilder().where(UserDao.Properties.PhoneNumber.eq(phone)).unique();
    }

    public List<Collection> getCollectionByUid(long uid) {
        return collectionDao.queryBuilder().where(CollectionDao.Properties.Uid.eq(uid)).list();
    }

    public List<Order> getOrderByUid(long uid) {
        return orderDao.queryBuilder().where(OrderDao.Properties.Uid.eq(uid)).list();
    }

    public void addBook(List<Book> books) {
        bookDao.insertInTx(books);
    }

    public void addUser(User user) {
        userDao.insert(user);
    }

    public void addCollection(Collection collection) {
        collectionDao.insert(collection);
    }

    public void addOrder(Order order) {
        orderDao.insert(order);
    }

    public void deleteOrder(Order order) {
        orderDao.delete(order);
    }

    public void deleteCollection(Collection collection) {
        collectionDao.delete(collection);
    }

}

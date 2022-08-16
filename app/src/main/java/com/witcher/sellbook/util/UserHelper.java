package com.witcher.sellbook.util;

import com.witcher.sellbook.event.LoginEvent;
import com.witcher.sellbook.event.LogoutEvent;
import com.witcher.sellbook.module.Collection;
import com.witcher.sellbook.module.User;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserHelper {

    private static User user;
    private static List<Collection> collections;

    public static boolean isLogin() {
        return user != null;
    }

    public static void resumeLoginState() {
        long uid = SPUtil.getLoginUid();
        if (uid == -1) {
            return;
        }
        user = DaoHelper.getInstance().getUser(uid);
        collections = DaoHelper.getInstance().getCollectionByUid(user.getId());
    }

    public static void register(String phone, String password) {
        User user = new User();
        user.setPhoneNumber(phone);
        user.setPassword(password);
        DaoHelper.getInstance().addUser(user);
        //用数据库里的  因为id要数据库去生成
        login(DaoHelper.getInstance().getUserByPhone(phone));
    }

    public static void login(User user) {
        UserHelper.user = user;
        SPUtil.saveLoginState(user.getId());
        collections = DaoHelper.getInstance().getCollectionByUid(user.getId());
        EventBus.getDefault().post(new LoginEvent());
    }

    public static void loginOut() {
        user = null;
        collections = null;
        SPUtil.saveLoginState(-1);
        EventBus.getDefault().post(new LogoutEvent());
    }

    public static boolean isCollection(long bookId) {
        if (collections == null) {
            return false;
        }
        for (Collection collection : collections) {
            if (collection != null && collection.getBookId() == bookId) {
                return true;
            }
        }
        return false;
    }

    public static void addCollection(long bookId) {
        if (collections == null) {
            collections = new ArrayList<>();
        }
        Collection collection = new Collection();
        collection.setBookId(bookId);
        collection.setUid(user.getId());
        collections.add(collection);
        DaoHelper.getInstance().addCollection(collection);
    }

    public static void removeCollection(long bookId) {
        Collection collection = null;
        if (collections != null) {
            Iterator<Collection> iterator = collections.iterator();
            while (iterator.hasNext()) {
                collection = iterator.next();
                if (collection.getBookId() == bookId) {
                    iterator.remove();
                    break;
                }
            }
        }
        if (collection != null) {
            DaoHelper.getInstance().deleteCollection(collection);
        }
    }

    public static List<Collection> getCollections() {
        return collections;
    }

    public static User getUser() {
        return user;
    }
}

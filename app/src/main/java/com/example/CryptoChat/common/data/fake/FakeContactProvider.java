package com.example.CryptoChat.common.data.fake;

import com.example.CryptoChat.common.data.exceptions.DuplicatedException;

import com.example.CryptoChat.common.data.exceptions.ObjectNotExistException;
import com.example.CryptoChat.common.data.models.User;
import com.example.CryptoChat.common.data.provider.ContactProvider;

import java.util.ArrayList;

public class FakeContactProvider extends ContactProvider {

    private static FakeContactProvider instance;
    private ArrayList<User> users;

    public static FakeContactProvider getInstance() {
        if (instance == null) {
            synchronized (FakeContactProvider.class) {
                if (instance == null) {
                    instance = new FakeContactProvider();
                }
            }
        }
        return instance;
    }


    public FakeContactProvider() {
        super();
        users = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            users.add(new User(Integer.valueOf(i).toString(), "Contact - " + i, "", true));
        }
    }


    @Override
    public ArrayList<User> getUsers() {
        return this.users;
    }

    public void setUser(User user, int position) {
        users.set(position,user);
    }

    public void setUser(User user) {
        int i=0;
        for (User u : users) {
            if(u.getId().equals(user.getId())) {
                users.set(i, user);
            } else {
                i+=1;
            }
        }
    }

    public User getUser(int position) {
        return users.get(position);
    }

    public User getUser(String id)  throws ObjectNotExistException {
        for (User u : users) {
            if(u.getId().equals(id)) {
                return u;
            }
        }
        throw new ObjectNotExistException("User object does not exist");
    }


    public void sortUsers() {
        this.users.sort((user, t1) ->
                user.getAlias().compareTo(t1.getAlias())
        );
    }

    public void addUser(String id, String name) throws DuplicatedException {
        for (User u : getUsers()) {
            if (u.getId().equals(id)) {
                throw new DuplicatedException("User already exists!");
            }
        }
        getUsers().add(new User(id, name, "", true));
        sortUsers();
    }

    public void addUser(User user) throws DuplicatedException {
        for (User u : getUsers()) {
            if (u.getId().equals(user.getId())) {
                throw new DuplicatedException("User already exists!");
            }
        }
        getUsers().add(user);
        sortUsers();

    }

    public void sortByAlias(){
        this.users.sort((user, t1) -> user.getAlias().compareTo(t1.getAlias()));
    }

}

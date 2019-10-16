package com.example.CryptoChat.common.data.provider;

import com.example.CryptoChat.common.data.exceptions.DuplicatedException;
import com.example.CryptoChat.common.data.exceptions.ObjectNotExistException;
import com.example.CryptoChat.common.data.models.User;

import java.util.ArrayList;


/*
 * TODO: Implement singleton pattern with getInstance() method
 * */
public abstract class ContactProvider {

    private ArrayList<User> users;



    public ContactProvider() {
        users = new ArrayList<User>();
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void deleteUser(String uid) {
        getUsers().removeIf(u -> u.getId().equals(uid));
    }

    public void deleteUser(int idx) {
        getUsers().remove(idx);
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

    public void sortUsers() {
        this.users.sort((user, t1) ->
            user.getAlias().compareTo(t1.getAlias())
        );
    }

    public User getUser(String id) throws ObjectNotExistException {
        for (User u : getUsers()) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        throw new ObjectNotExistException("User object does not exist");
    }


    public User getUser(int idx) {
        return getUsers().get(idx);
    }

    public void setUser(int idx, User user) {
        getUsers().set(idx, user);
    }

    public int getCount() {

        return getUsers().size();
    }

    public void sortByAlias(){
        this.users.sort((user, t1) -> user.getAlias().compareTo(t1.getAlias()));
    }
}

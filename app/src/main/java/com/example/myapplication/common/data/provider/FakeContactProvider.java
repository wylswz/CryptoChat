package com.example.myapplication.common.data.provider;

import com.example.myapplication.common.data.exceptions.DuplicatedException;
import com.example.myapplication.common.data.exceptions.ObjectNotExistException;
import com.example.myapplication.common.data.models.User;

import java.util.ArrayList;

public class FakeContactProvider extends ContactProvider {

    private ArrayList<User> users;

    public FakeContactProvider(){
        super();
        users = new ArrayList<>();
        for (int i=0;i<100;i++) {
            users.add(new User(Integer.valueOf(i).toString(),"Contact - " +i,"", true));
        }

    }

    @Override
    public ArrayList<User> getUsers(){
        return this.users;
    }
}

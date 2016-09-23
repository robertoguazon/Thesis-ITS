package com.westlyf.agent;

import com.westlyf.domain.lesson.Level;
import com.westlyf.user.User;
import sample.model.Users;

/**
 * Created by robertoguazon on 05/09/2016.
 */
public class Agent {

    private Level level;
    private User user;
    private static Users loggedUser;

    public Agent(Level level, User user) {
        this.level = level;
        this.user = user;
    }

    public static Users getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(Users loggedUser) {
        Agent.loggedUser = loggedUser;
    }

    public static void removeLoggedUser(){
        setLoggedUser(null);
    }
}

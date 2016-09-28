package com.bpbatam.enterprise.model;

/**
 * Created by User on 20/07/2016.
 */
public class GitHubUser {
     String login;
     String name;

    @Override
    public String toString() {
        return(login);
    }

    public String toName() {
        return(name);
    }
}

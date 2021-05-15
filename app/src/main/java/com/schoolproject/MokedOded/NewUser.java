package com.schoolproject.MokedOded;

class NewUser {
    private static NewUser instance = null;
    public String username;
    public String password;
    public String isAdmin;

    private NewUser(){
        username = "";
        password = "";
    }

    public static NewUser getInstance(){
        if(instance==null){
            instance = new NewUser();
        }
        return instance;
    }
}

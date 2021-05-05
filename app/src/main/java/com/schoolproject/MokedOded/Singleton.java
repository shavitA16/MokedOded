package com.schoolproject.MokedOded;

class Singleton {
    private static Singleton instance = null;
    public int location;

    private Singleton(){
        location = 0;
    }

    public static Singleton getInstance(){
        if(instance==null){
            instance = new Singleton();
        }
        return instance;
    }
}

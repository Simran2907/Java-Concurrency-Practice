package com.interviewPrep;

public class SingletonWithDoubleCheck {

    private volatile SingletonWithDoubleCheck singletonWithDoubleCheck = null;
    private SingletonWithDoubleCheck(){}

    public SingletonWithDoubleCheck getInstance(){
        if(singletonWithDoubleCheck == null){
                synchronized (SingletonWithDoubleCheck.class){
                    if(singletonWithDoubleCheck == null)
                        singletonWithDoubleCheck = new SingletonWithDoubleCheck();
                }
        }
        return singletonWithDoubleCheck;
    }
}

package com.cloudlearn.xiaofeizhe.观察者模式;

import java.util.Observable;
import java.util.Observer;

public class Dingyuezhe implements Observer {
    private Observable ob;
    private String name;

    public Dingyuezhe(String name,Observable ob) {
        this.ob = ob;
        this.name=name;
        ob.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        Fabuzhe fabuzhe= (Fabuzhe) o;
        String s=fabuzhe.getInfo();
        System.out.println(name+"得到作业信息:"+s);
    }
}

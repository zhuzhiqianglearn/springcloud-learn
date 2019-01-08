package com.cloudlearn.xiaofeizhe.观察者模式;

public class Testss {
    public static void main(String[] args) {
        Fabuzhe fabuzhe=new Fabuzhe();
        Dingyuezhe dingyuezhe=new Dingyuezhe("张三",fabuzhe);
        Dingyuezhe dingyuezhe2=new Dingyuezhe("李四",fabuzhe);
        fabuzhe.setHomework("数学第五页第八题");
        fabuzhe.setHomework("语文第一页背诵课文");

    }
}

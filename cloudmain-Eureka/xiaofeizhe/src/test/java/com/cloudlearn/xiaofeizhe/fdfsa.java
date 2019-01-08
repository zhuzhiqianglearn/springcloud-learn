package com.cloudlearn.xiaofeizhe;

import java.text.DecimalFormat;

public class fdfsa {
    public static void main(String[] args) {
        System.out.println(getDoubleTwo("229 999.999999"));
        System.out.println(getDoubleTwo("22888888.88888"));
    }
    public static double getDoubleTwo(String money){
        DecimalFormat df = new DecimalFormat("#0.00");
        String format = df.format(Double.parseDouble(money) / 10000);
        return Double.parseDouble(format);
    }
}

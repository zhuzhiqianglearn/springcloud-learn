package com.cloudlearn.feign;

import java.util.Arrays;

public class aaaa {
    public static void batcherSort(int[] array) {
        int length = array.length ;
        boolean flag = true ;
        while(true) {
            flag = true ;
            for(int i=1;i<length-1;i+=2) {
                if(array[i] > array[i+1]) {
                    swap(array, i, i+1) ;
                    flag = false ;
                }
            }
            for(int i=0;i<length-1;i+=2) {
                if(array[i] > array[i+1]) {
                    swap(array, i, i+1) ;
                    flag = false ;
                }
            }
            if(flag) break ;
            printArr(array) ;
        }
    }
    /**
     * 按从小到大的顺序交换数组
     * @param a 传入的数组
     * @param b 传入的要交换的数b
     * @param c	传入的要交换的数c
     */
    public static void swap(int[] a, int b, int c) {
        int temp = 0 ;
        if(b < c) {
            if(a[b] > a[c]) {
                temp = a[b] ;
                a[b] = a[c] ;
                a[c] = temp ;
            }
        }
    }

    /**
     * 打印数组
     * @param array
     */
    public static void printArr(int[] array) {
        for(int c : array) {
            System.out.print(c + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] number333={11,95,45,15,78,84,51,24,12} ;
        Arrays.sort(number333);
        for (int anInt : number333) {
            System.out.print(anInt+" ");
        }
        System.out.println("!111111111111111111");
        int[] number={1,2,3,4,5,6,7,8,9,10,11};
        int[] ints=new int[number.length];
        int y=0;
        for (int x=0;x<number.length/2;x++){
            ints[y]=number[x];
            ints[y+1]=number[number.length-1-x];
            y+=2;
        }
        if(number.length%2==1) ints[number.length-1]=number[number.length/2];
        for (int anInt : ints) {
            System.out.println(anInt);
        }
    }


}

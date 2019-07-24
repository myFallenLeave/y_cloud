package com.yhw.sort.notlinear;

/**
 * 时间复杂度 最坏 O(n^2) 最好 O(n^2) 平均O(n^2)
 * 找到最小的值，将最小(最大)的值放大前面
 */
public class SelectSort {


    public static void sort(int[] arr,int length){
        int temp,sentry;
        for(int i = 0; i < length; i++){
            temp = arr[i];
            sentry = 0;
            for(int j = i; j < length; j++){
                //temp 赋值为最小值
                if(temp > arr[j]){
                    temp = arr[j];
                    sentry = j;
                }
            }
            if(sentry != 0){
               arr[sentry] = arr[i];
               arr[i] = temp;
            }
        }
    }

    public static void main(String[] args){
        //3 5 1 4 7 2
        //1
        int[] arr = {10,8,45,78,63,78,447,43,46,787,11,23,4,8,74,123};
        SelectSort.sort(arr,arr.length);
        for(int number : arr){
            System.out.println(number);
        }

    }
}

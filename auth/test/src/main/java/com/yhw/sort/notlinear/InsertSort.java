package com.yhw.sort.notlinear;

/**
 * 时间复杂度 最坏 O(n^2) 最好 O(n) 平均O(n^2)
 * 对比冒泡排序，数据交换次数少
 */
public class InsertSort {

    public static void sort(int[] arr,int length){
        int temp,j;
        for(int i = 1; i < length; i++){

            j = i - 1;
            temp = arr[i];
            //下标从后往前移动
            for (; j >= 0 ; j--){
                //如果 当前值 小于 下标指向 之前的值
                //往后移动一步
                if(temp < arr[j]){
                    arr[j+1] = arr[j];
                }else {
                    break;
                }
            }


            //如果有更换位置，填补移动的数据空间
            //如果没有更换位置，则数据保持不变(相当于，重复赋值了一次)
            arr[j+1] = temp;


        }
    }

    public static void main(String[] args){
        int[] arr = {10,8,45,78,63,78,447,43,46,787,11,23,4,8,74,123};
        InsertSort.sort(arr,arr.length);
        for(int number : arr){
            System.out.println(number);
        }
    }
}

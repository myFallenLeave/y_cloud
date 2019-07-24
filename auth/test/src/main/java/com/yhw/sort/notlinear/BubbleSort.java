package com.yhw.sort.notlinear;

/**
 * 适用于小规模数据(两个相邻比较，满足条件，交换位置，继续和相邻的比较，直到遍历结束)
 * 时间复杂度 最坏 O(n^2) 最好 O(n) 平均O(n^2)
 */
public class BubbleSort {

    public static void sort(int[] arr,int length){
        int temp = 0;
        boolean flag = false;//提前退出标识
        for(int i = 0; i < length; i++){
            flag = false;
            for(int j = 0; j < length -i -1; j++){
                //相邻比较 交换位置
                if(arr[j] > arr[j+1]){
                    temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                    flag = true;
                }
            }
            if(!flag){
                break;
            }
        }
    }

    public static void main(String[] args){
        int[] arr = {10,8,45,78,63,78,447,43,46,787,11,23,4,8,74,123};
        BubbleSort.sort(arr,arr.length);
        for(int number : arr){
            System.out.println(number);
        }
    }
}

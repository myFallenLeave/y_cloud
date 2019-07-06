package com.yhw.sort.linear;

import java.util.stream.Stream;

/**
 * 桶排序
 */
public class BucketSort {

    //桶排序思想
    //计算区间

    /**
     *
     * @param arr 数组
     * @param maxLength 数组长度
     * @param avgBucketNumber 平均每个桶的数量
     * @return
     */
    public int[] bucketSort(int[] arr,int maxLength,int avgBucketNumber){
        int min = arr[0];
        int max = arr[0];
        for(int i = 1;i<maxLength;i++){
            if(arr[i]>max){
                max = arr[i];
            }
            if(arr[i]<min){
                min = arr[i];
            }
        }

        int bucketNum = getBucketNum(maxLength,avgBucketNumber);

        //根据桶数计算区间 最大数加最小数 / 桶数下标
        for(int i = bucketNum; i < 0;i--){
            int right =(max + min) / i;
            if(right > min){
                int left = i == bucketNum ? min : ((max + min) / (i+1));
                if(i == bucketNum)
                for(int j = 0 ;j<maxLength;j++){
                    if(arr[i] < right && arr[i] >= left){

                    }
                }
            }
        }



        return null;
    }

    //桶内快排

    //计算最大位数
    public int getMaxDigitalCount(int maxNumber){
        int maxDigitalCount = 0;
        while (maxNumber > 0){
            maxNumber /= 10;
            maxDigitalCount++;
        }
        return maxDigitalCount;
    }

    /**
     * 计算桶数
     * @param arrLength 数组长度
     * @param avgNumber 每个桶平均装的数量
     * @return
     */
    public int getBucketNum(int arrLength,int avgNumber){
        //如果数组少量少于100 则只分配1个桶
        if(arrLength > 100){
            return 1;
        }
        int result = arrLength / avgNumber;
        return arrLength % avgNumber == 0 ? result : ++result;
    }

    public static void main(String[] args){
        int[] arr = {10,10,65,78,468,412,456,7864,1,2,57,74,5,78,63,8880
                ,12654,14563,12367,126,56,32,4785,21,2,8,23,9,1,4762,145};

        BucketSort sort = new BucketSort();
//        sort.bucketSort(arr,arr.length,10);
//        Stream.of(arr).forEach(System.out::println);
//        System.out.println(arr.length);
//        System.out.println(sort.getBucketNum(arr.length,2));

    }
}

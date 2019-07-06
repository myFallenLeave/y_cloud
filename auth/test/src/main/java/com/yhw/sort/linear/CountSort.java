package com.yhw.sort.linear;

/**
 * 计数排序
 * 适用场景(数据区间不大)
 */
public class CountSort {

    public static void sort(int[] arr,int length){
        //1.获取数组中 最大的值
        int maxNuber = arr[0];
        for (int i = 1; i < length; ++i){
            if(maxNuber < arr[i]){
                maxNuber = arr[i];
            }
        }

        //2.开辟一个大小为最大值的数组(下标从0开始 所以要加1)
        int[] maxNuberArr = new int[maxNuber+1];

        //3.数组赋初始值0
        for (int i=0; i <= maxNuber ;++i){
            maxNuberArr[i] = 0;
        }

        //4.统计每个数 出现的次数(通过次数相加 就可以得出下标位置)
        for (int i = 0; i < length; ++i){
            maxNuberArr[arr[i]]++;
        }


        //5.计算数的下标
        // 例如数组 【0，2，0，3，3，2，5，3】
        //每个数出现次数 [2,0,2,3,0,1]
        //得出下标位置 [2,2,4,7,7,8]
        for(int i = 1; i <= maxNuber ; ++i){
            maxNuberArr[i] = maxNuberArr[i-1] + maxNuberArr[i];
        }

        //6.声明临时数组
        int[] r = new int[length];

        //7.计算排序
        //从最后面开始取值
        for(int i = length -1 ; i >= 0 ; --i){
            //maxArr[arr[i]] 拿到当前值下标  例如此处为3  三存储的为7
            int index = maxNuberArr[arr[i]] -1; //计算得到下标值为6
            r[index] = arr[i];
            maxNuberArr[arr[i]]--; //使用了一次  就得去掉已使用的下标
        }

        //第二次进来排序的数为5  获取得出他的技术值为 8
        //8-1 = 7 下标为7
        //下标为7 处 赋值
        //减以使用的下标(当一个数出现多次)  7
        //依次类推


        //8.将数据拷贝到目标数组
        for(int i = 0; i < length; ++i){
            arr[i] = r[i];
        }

        //输出排序
        for(int obj : arr){
            System.out.println(obj);
        }
    }

    public static void main(String[] args){
        int[] arr = {0,2,0,3,3,2,5,3};
//        int[] arr = {12,55,78,44,12,30,44,0,41,10,14,47,63,10,20,36,47,10,47,20,15,12,30,12};
        CountSort.sort(arr,arr.length);
    }
}

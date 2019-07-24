package com.yhw.sort.notlinear;

/**
 * 快速排序(已中间点做区间  和左右两边比较，左边放大(或小)，右边反之)
 * 时间复杂度 O(nlog^2n)
 */
public class QuickSort {

    //1.取得中间的位置的数  a
    //2.从右边开始比较，找到第一个小于 a 的值，记录下标位置 i
    //3.从左边开始比较，找到第一个大于 a 的值，记录下标位置 j
    //4. i和j下标位置的数 交换位置
    //5. 将左坐标向前 进一位
    //6. 将右坐标向前 进一位


    static void quicksort(int[] arr, int left, int right){
        //当左右边界相等 或 左比右大 结束
        if(left < right){
            int sentry = (right+left)/2;//9
            //记录边界起始位置
            int i=right,j=left;

            //从右往左移动,找到第一个比当前哨兵值小的数
            /*while (sentry < i){
                if(arr[sentry] > arr[i]){
                    break;
                }
                i--;
            }*/
            while (arr[sentry] <= arr[i] && sentry < i){
                i--;
            }

            //从左到右边
            /*while (sentry > j){
                if(arr[sentry] < arr[j]){
                    break;
                }
                j++;
            }*/
            while (arr[sentry] >= arr[j] && sentry > j){
                j++;
            }

            //交换位置(当左边大于右边)
            if(arr[i] < arr[j]){
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }

            //左边递归
            quicksort(arr,left+1,right);
            //右边递归
            quicksort(arr,left,right-1);

        }
    }


    public static void main(String[] args){
//        int[] arr = {10,8,45,78,63,78,447,43,46,787,11,23,4,8,74,123};
        int[] arr = {10,8,7,6,4,77,12,1,5,3,2};
        long start = System.currentTimeMillis();
        QuickSort.quicksort(arr,0,arr.length-1);
        System.out.println("排序耗时："+ (System.currentTimeMillis() - start));
        for(Object obj : arr){
            System.out.println(obj);
        }

    }
}

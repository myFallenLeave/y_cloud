package com.yhw.sort.notlinear;

/**
 * 归并排序
 * 时间复杂度 O(nlog^2n)
 * 空间复杂度 O(n)
 */
public class Merger {

    //1.分治,将数组切分为2个，直到不能切分为止
    public void sort(int[] arr,int left,int right){
        // 0 1(true)  2 2(false)
        if(left < right){
            //取中间值进行分割
            int mid = (left+right) >> 1;

            sort(arr,left,mid);//左归并(先递归左边 先排完)递归完毕 左边数据就排序完毕了
            sort(arr,mid+1,right);//右归并(在排右边)


            mergerSort(arr,left,mid,right);
        }
    }


    //2.将数组合并
    public void  mergerSort(int[] arr,int left,int mid,int right){
        //申请一个临时数组
        int[] temp = new int[right - left + 1];

        //i左边第一个下标  j右边第一个下标
        int i = left,j = mid+1,k = 0;
        //当左边已排序完，或者  右边排序完，退出循环
        while (i <= mid && j <= right){
            if(arr[i] < arr[j]){
                temp[k++] = arr[i++];
            }else {
                temp[k++] = arr[j++];
            }
        }

        //当左边没有排序完(最后一次合并,之前的数据都排序好了，可以直接合并，保证数据有序)
        while (i <= mid){
            temp[k++] = arr[i++];
        }

        //右边没有排序完
        while (j <= right){
            temp[k++] = arr[j++];
        }


        //还原数组(遍历中间数组,中间数组已有序)
        for(int num = 0; num < temp.length ; num++){
            arr[left++] = temp[num];
        }
    }

    public static void main(String[] args){
        int[] arr = {10,8,45,78,63,78,447,43,46,787,11,23,4,8,74,123};
        Merger merger = new Merger();
        merger.sort(arr,0,arr.length-1);
        /*for(int obj : arr){
            System.out.println(obj);
        }*/

    }
}

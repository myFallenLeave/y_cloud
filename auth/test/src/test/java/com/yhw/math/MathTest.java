package com.yhw.math;

/**
 * 均摊法
 * Created by YHW on 2019/7/6.
 */
public class MathTest {

    //当队列小于8 所有机器监听所有队列（根据执行预估时间进行休眠，确保不会出现线程创建过多导致OOM）


    //通过Redis获取最新需要队列
    //通过最大单台核心线程数，和队列长度，计算需要的机器数


    public static void main(String[] args){
        int appCount = 6;
        MathTest mathTest = new MathTest();
        AvgNumber avgNumber = mathTest.getAvgNumber(88,appCount);
        System.out.println("平均值：" + avgNumber.getAvgNum() + "  余数:" + avgNumber.getRemainder());
        boolean isLast = false;
        for (int i = 0; i < appCount; i++){
            if(i == (appCount-1)){
                isLast = true;
            }
            System.out.println("["+mathTest.getBeginIndex(i,avgNumber) + ":" + mathTest.getEndIndex(i,avgNumber,isLast) + "]");
        }
    }

    /**
     * 通过队列长度，机器总数，计算分摊到每一天机器的平均值
     * @param queueLength 队列长度
     * @param appCount 机器总数
     * @return
     */
    public AvgNumber getAvgNumber(int queueLength,int appCount){
        return new AvgNumber(queueLength / appCount,queueLength % appCount);
    }

    /**
     * 通过机器下标获取 当前机器所需要监听的队列起始下标
     * @param appIndex
     * @param avgNumber
     * @return
     */
    public int getBeginIndex(int appIndex,AvgNumber avgNumber){
        if(appIndex == 0){
            return 0;
        }
        //余数为零时
        if(avgNumber.getRemainder() == 0){
            return appIndex * avgNumber.getAvgNum();
        }
        //当前下标小于或等于余数
        if(appIndex <= avgNumber.getRemainder()){
            return  (appIndex * avgNumber.getAvgNum()) + appIndex;
        }
        //否则当前下标乘平均数 加 余数
        return (appIndex * avgNumber.getAvgNum()) + avgNumber.getRemainder();
    }

    /**
     * 通过机器下标获取 当前机器所需要监听的队列结束下标
     * @param appIndex
     * @param avgNumber
     * @return
     */
    public int getEndIndex(int appIndex,AvgNumber avgNumber,boolean isLast){
        if(avgNumber.getRemainder() == 0){
            return ((appIndex+1) * avgNumber.getAvgNum()) -1;
        }
        if(appIndex < avgNumber.getRemainder()){
            return ((appIndex+1) * avgNumber.getAvgNum()) + appIndex;
        }
        return ((appIndex+1) * avgNumber.getAvgNum()) + avgNumber.getRemainder() -1;
    }

    static class AvgNumber{
        /**
         * 平均数
         */
        private int avgNum;
        /**
         * 余数
         */
        private int remainder;

        public AvgNumber(int avgNum,int remainder){
            this.avgNum = avgNum;
            this.remainder = remainder;
        }

        public AvgNumber(){}

        public int getAvgNum() {
            return avgNum;
        }

        public void setAvgNum(int avgNum) {
            this.avgNum = avgNum;
        }

        public int getRemainder() {
            return remainder;
        }

        public void setRemainder(int remainder) {
            this.remainder = remainder;
        }
    }
}

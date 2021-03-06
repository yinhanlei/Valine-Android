package com.zcy.valine;

public class fengguMain {

    static int[] h0 = {3723, 3716, 3715, 3714, 3714, 3714, 3717, 3716, 3715, 3712, 3712, 3712, 3711, 3716, 3714, 3717,
            3717, 3717, 3717, 3721, 3721, 3720, 3720, 3719, 3719, 3719, 3719, 3718, 3719, 3718, 3718, 3721, 3721, 3719,
            3718, 3718, 3717, 3716, 3718, 3718, 3718, 3718, 3718, 3720, 3720, 3717, 3717, 3714, 3715, 3714, 3716, 3715,
            3714, 3714, 3714, 3714, 3713, 3716, 3715, 3714, 3715, 3715, 3713, 3713, 3712, 3711, 3708, 3708, 3708, 3705,
            3706, 3706, 3704, 3703, 3699, 3697, 3699, 3704, 3705, 3708, 3707, 3708, 3708, 3712, 3711, 3709, 3708, 3708,
            3708, 3705, 3703};

    static int[] l0 = {3715, 3713, 3710, 3705, 3710, 3707, 3711, 3713, 3712, 3708, 3708, 3708, 3707, 3711, 3711, 3712, 3714, 3715, 3714,
            3715, 3719, 3716, 3716, 3717, 3715, 3716, 3716, 3716, 3716, 3716, 3715, 3717, 3718, 3717, 3716, 3716, 3715, 3715, 3715, 3716, 3716,
            3716, 3716, 3718, 3715, 3715, 3711, 3711, 3712, 3712, 3711, 3710, 3711, 3712, 3713, 3711, 3711, 3713, 3711, 3711, 3710, 3712, 3710,
            3710, 3710, 3706, 3703, 3706, 3701, 3699, 3702, 3701, 3701, 3697, 3694, 3692, 3696, 3698, 3703, 3704, 3704, 3704, 3706, 3706, 3707,
            3706, 3705, 3706, 3704, 3701, 3703};

    static int[] a0 = {3719, 3715, 3713, 3710, 3712, 3711, 3714, 3715, 3714, 3710, 3710, 3710, 3709, 3714, 3713, 3715, 3716, 3716, 3716, 3718,
            3720, 3718, 3718, 3718, 3717, 3718, 3718, 3717, 3718, 3717, 3717, 3719, 3720, 3718, 3717, 3717, 3716, 3716, 3717, 3717, 3717, 3717,
            3717, 3719, 3718, 3716, 3714, 3713, 3714, 3713, 3714, 3713, 3713, 3713, 3714, 3713, 3712, 3715, 3713, 3713, 3713, 3714, 3712, 3712,
            3711, 3709, 3706, 3707, 3705, 3702, 3704, 3704, 3703, 3700, 3697, 3695, 3698, 3701, 3704, 3706, 3706, 3706, 3707, 3709, 3709, 3708,
            3707, 3707, 3706, 3703, 3703};

    public static void main(String args[]) {
        System.out.println("start");

        maxProfit(h0);
        System.out.println("——————————————");
        wave(h0);
    }

    /**
     * https://bbs.csdn.net/topics/390007869?_=1515314884
     *
     * @param wave
     */
    public static void wave(int[] wave) {
        //		byte[] wave = new byte[] { 4, 5, 1, 1, 4, 3, 3, 3, 3, 5, 5, 6, 4, 4, 3, 2 };
        int direction = wave[0] > 0 ? -1 : 1;
        for (int i = 0; i < wave.length - 1; i++) {
            if ((wave[i + 1] - wave[i]) * direction > 0) {
                direction *= -1;
                //				if (direction == 1) {
                //					System.out.println("(" + i + "," + wave[i] + ")" + "峰");
                //				} else {
                //					System.out.println("(" + i + "," + wave[i] + ")" + "谷");
                //				}
                System.out.println(wave[i]);
            }
        }
    }

    /**
     * https://www.it610.com/article/1282496005136203776.htm
     *
     * @param prices
     * @return
     */
    public static int maxProfit(int[] prices) {
        int i = 0;
        int valley = prices[0];
        int peak = prices[0];
        int maxprofit = 0;
        while (i < prices.length - 1) {
            while (i < prices.length - 1 && prices[i] >= prices[i + 1])
                i++;
            valley = prices[i];
            while (i < prices.length - 1 && prices[i] <= prices[i + 1])
                i++;
            peak = prices[i];
            maxprofit += peak - valley;
        }
        System.out.println(maxprofit);
        return maxprofit;
    }

}

package com.infrastructure.util;

import android.annotation.SuppressLint;
import android.graphics.PointF;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 类名: MathUtil.java
 * <p>
 * 功能:数学处理类
 *
 * @author Mr.Z
 * @version 1.0
 * @dateTime 2015-2-9 上午11:20:20
 */
@SuppressLint("DefaultLocale")
public class MathUtils {

    /**
     * 由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精 确的浮点数运算，包括加减乘除和四舍五入。
     */

    // 默认除法运算精度
    private static final int DEF_DIV_SCALE = 10;


    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */

    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */

    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */

    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */

    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */

    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The   scale   must   be   a   positive   integer   or   zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */

    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The   scale   must   be   a   positive   integer   or   zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 四舍五入.
     *
     * @param number  原数
     * @param decimal 保留几位小数
     * @return 四舍五入后的值
     */
    public static BigDecimal round2(double number, int decimal) {
        return new BigDecimal(number).setScale(decimal, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 防止页面出现科学计数法
     *
     * @param totalDigit      表示总共要留多少位数
     * @param fractionalDigit 表示小数位数，不知道总共留多少位，可以给大一些（比如此处为50） 一般情况下，总位数不会超过50，除非客户有这个需要
     *                        小数按照客户要求来作
     */
    public static String padDoubleLeft(Double d, int totalDigit, int fractionalDigit) {
        String str = "";
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMinimumFractionDigits(fractionalDigit);
        decimalFormat.setMaximumFractionDigits(fractionalDigit);
        decimalFormat.setGroupingUsed(false);
        decimalFormat.setMaximumIntegerDigits(totalDigit - fractionalDigit - 1);
        decimalFormat.setMinimumIntegerDigits(totalDigit - fractionalDigit - 1);
        str = decimalFormat.format(d);
        /**
         * 去掉前面的0（比如000123213，最后输出123213）
         */
        while (str.startsWith("0")) {
            str = str.substring(1);
        }
        return str;
    }

    public static String generateTime(long time) {
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        // int hours = totalSeconds / 3600;
        // return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        return String.format("%02d:%02d", minutes, seconds);
    }


    /**
     * 描述：字节数组转换成16进制串.
     *
     * @param b      the b
     * @param length the length
     * @return the string
     */
    public static String byte2HexStr(byte[] b, int length) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < length; ++n) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
            hs = hs + ",";
        }
        return hs.toUpperCase();
    }

    /**
     * 二进制转为十六进制.
     *
     * @param binary the binary
     * @return char hex
     */
    public static char binaryToHex(int binary) {
        char ch = ' ';
        switch (binary) {
            case 0:
                ch = '0';
                break;
            case 1:
                ch = '1';
                break;
            case 2:
                ch = '2';
                break;
            case 3:
                ch = '3';
                break;
            case 4:
                ch = '4';
                break;
            case 5:
                ch = '5';
                break;
            case 6:
                ch = '6';
                break;
            case 7:
                ch = '7';
                break;
            case 8:
                ch = '8';
                break;
            case 9:
                ch = '9';
                break;
            case 10:
                ch = 'a';
                break;
            case 11:
                ch = 'b';
                break;
            case 12:
                ch = 'c';
                break;
            case 13:
                ch = 'd';
                break;
            case 14:
                ch = 'e';
                break;
            case 15:
                ch = 'f';
                break;
            default:
                ch = ' ';
        }
        return ch;
    }

    /**
     * 一维数组转为二维数组
     *
     * @param m      the m
     * @param width  the width
     * @param height the height
     * @return the int[][]
     */
    public static int[][] arrayToMatrix(int[] m, int width, int height) {
        int[][] result = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int p = j * height + i;
                result[i][j] = m[p];
            }
        }
        return result;
    }

    /**
     * 二维数组转为一维数组
     *
     * @param m the m
     * @return the double[]
     */
    public static double[] matrixToArray(double[][] m) {
        int p = m.length * m[0].length;
        double[] result = new double[p];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                int q = j * m.length + i;
                result[q] = m[i][j];
            }
        }
        return result;
    }

    /**
     * 描述：int数组转换为double数组.
     *
     * @param input the input
     * @return the double[]
     */
    public static double[] intToDoubleArray(int[] input) {
        int length = input.length;
        double[] output = new double[length];
        for (int i = 0; i < length; i++) {
            output[i] = Double.valueOf(String.valueOf(input[i]));
        }
        return output;
    }

    /**
     * 描述：int二维数组转换为double二维数组.
     *
     * @param input the input
     * @return the double[][]
     */
    public static double[][] intToDoubleMatrix(int[][] input) {
        int height = input.length;
        int width = input[0].length;
        double[][] output = new double[height][width];
        for (int i = 0; i < height; i++) {
            // 列
            for (int j = 0; j < width; j++) {
                // 行
                output[i][j] = Double.valueOf(String.valueOf(input[i][j]));
            }
        }
        return output;
    }

    /**
     * 计算数组的平均值.
     *
     * @param pixels 数组
     * @return int 平均值
     */
    public static int average(int[] pixels) {
        float m = 0;
        for (int i = 0; i < pixels.length; ++i) {
            m += pixels[i];
        }
        m = m / pixels.length;
        return (int) m;
    }

    /**
     * 计算数组的平均值.
     *
     * @param pixels 数组
     * @return int 平均值
     */
    public static int average(double[] pixels) {
        float m = 0;
        for (int i = 0; i < pixels.length; ++i) {
            m += pixels[i];
        }
        m = m / pixels.length;
        return (int) m;
    }

    /**
     * 描述：点在线段上. 点A（x，y）,B(x1,y1),C(x2,y2) 点A在线段BC上吗?
     */
    public static boolean pointAtELine(double x, double y, double x1, double y1, double x2, double y2) {
        double result = (x - x1) * (y2 - y1) - (y - y1) * (x2 - x1);
        if (result == 0) {
            return x >= Math.min(x1, x2) && x <= Math.max(x1, x2) && y >= Math.min(y1, y2) && y <= Math.max(y1, y2);
        } else {
            return false;
        }
    }

    /**
     * 描述：两条直线相交. 点A（x1，y1）,B(x2,y2),C(x3,y3),D(x4,y4) 直线AB与直线CD相交吗?
     */
    public static boolean LineAtLine(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
        double k1 = (y2 - y1) / (x2 - x1);
        double k2 = (y4 - y3) / (x4 - x3);
        return k1 != k2;
    }

    /**
     * 描述：线段与线段相交. 点A（x1，y1）,B(x2,y2),C(x3,y3),D(x4,y4) 线段AB与线段CD相交吗?
     */
    public static boolean eLineAtELine(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
        double k1 = (y2 - y1) / (x2 - x1);
        double k2 = (y4 - y3) / (x4 - x3);
        if (k1 == k2) {
            // System.out.println("平行线");
            return false;
        } else {
            double x = ((x1 * y2 - y1 * x2) * (x3 - x4) - (x3 * y4 - y3 * x4) * (x1 - x2)) / ((y2 - y1) * (x3 - x4) - (y4 - y3) * (x1 - x2));
            double y = (x1 * y2 - y1 * x2 - x * (y2 - y1)) / (x1 - x2);
            // System.out.println("直线的交点("+x+","+y+")");
            // System.out.println("交点（"+x+","+y+"）在线段上");
            // System.out.println("交点（"+x+","+y+"）不在线段上");
            return x >= Math.min(x1, x2) && x <= Math.max(x1, x2) && y >= Math.min(y1, y2) && y <= Math.max(y1, y2) && x >= Math.min(x3, x4) && x <= Math.max(x3, x4) && y >= Math.min(y3, y4) && y <= Math.max(y3, y4);
        }
    }

    /**
     * 描述：线段直线相交. 点A（x1，y1）,B(x2,y2),C(x3,y3),D(x4,y4) 线段AB与直线CD相交吗?
     */
    public static boolean eLineAtLine(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
        double k1 = (y2 - y1) / (x2 - x1);
        double k2 = (y4 - y3) / (x4 - x3);
        if (k1 == k2) {
            // System.out.println("平行线");
            return false;
        } else {
            double x = ((x1 * y2 - y1 * x2) * (x3 - x4) - (x3 * y4 - y3 * x4) * (x1 - x2)) / ((y2 - y1) * (x3 - x4) - (y4 - y3) * (x1 - x2));
            double y = (x1 * y2 - y1 * x2 - x * (y2 - y1)) / (x1 - x2);
            // System.out.println("交点("+x+","+y+")");
            // System.out.println("交点（"+x+","+y+"）在线段上");
            // System.out.println("交点（"+x+","+y+"）不在线段上");
            return x >= Math.min(x1, x2) && x <= Math.max(x1, x2) && y >= Math.min(y1, y2) && y <= Math.max(y1, y2);
        }
    }

    /**
     * 描述：点在矩形内. 矩形的边都是与坐标系平行或垂直的。 只要判断该点的横坐标和纵坐标是否夹在矩形的左右边和上下边之间。
     * 点A（x，y）,B(x1,y1),C(x2,y2) 点A在以直线BC为对角线的矩形中吗?
     */
    public static boolean pointAtRect(double x, double y, double x1, double y1, double x2, double y2) {
        // System.out.println("点（"+x+","+y+"）在矩形内上");
        // System.out.println("点（"+x+","+y+"）不在矩形内上");
        return x >= Math.min(x1, x2) && x <= Math.max(x1, x2) && y >= Math.min(y1, y2) && y <= Math.max(y1, y2);
    }

    /**
     * 描述：矩形在矩形内. 只要对角线的两点都在另一个矩形中就可以了. 点A(x1,y1),B(x2,y2)，C(x1,y1),D(x2,y2)
     * 以直线AB为对角线的矩形在以直线BC为对角线的矩形中吗?
     */
    public static boolean rectAtRect(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
        // System.out.println("矩形在矩形内");
        // System.out.println("矩形不在矩形内");
        return x1 >= Math.min(x3, x4) && x1 <= Math.max(x3, x4) && y1 >= Math.min(y3, y4) && y1 <= Math.max(y3, y4) && x2 >= Math.min(x3, x4) && x2 <= Math.max(x3, x4) && y2 >= Math.min(y3, y4) && y2 <= Math.max(y3, y4);
    }

    /**
     * 描述：圆心在矩形内 . 圆心在矩形中且圆的半径小于等于圆心到矩形四边的距离的最小值。 圆心(x,y) 半径r
     * 矩形对角点A（x1，y1），B(x2，y2)
     */
    public static boolean circleAtRect(double x, double y, double r, double x1, double y1, double x2, double y2) {
        // 圆心在矩形内
        if (x >= Math.min(x1, x2) && x <= Math.max(x1, x2) && y >= Math.min(y1, y2) && y <= Math.max(y1, y2)) {
            // 圆心到4条边的距离
            double l1 = Math.abs(x - x1);
            double l2 = Math.abs(y - y2);
            double l3 = Math.abs(x - x2);
            double l4 = Math.abs(y - y2);
            // System.out.println("圆在矩形内");
            // System.out.println("圆不在矩形内");
            return r <= l1 && r <= l2 && r <= l3 && r <= l4;

        } else {
            // System.out.println("圆不在矩形内");
            return false;
        }
    }

    /**
     * 描述：获取两点间的距离.
     */
    public static double getDistance(PointF p1, PointF p2) {
        return getDistance(p1.x, p1.y, p2.x, p2.y);
    }

    /**
     * 描述：获取两点间的距离.
     */
    public static double getDistance(double x1, double y1, double x2, double y2) {
        double x = x1 - x2;
        double y = y1 - y2;
        return Math.sqrt(x * x + y * y);
    }

    /**
     * 矩形碰撞检测 参数为x,y,width,height
     *
     * @param x1 第一个矩形的x
     * @param y1 第一个矩形的y
     * @param w1 第一个矩形的w
     * @param h1 第一个矩形的h
     * @param x2 第二个矩形的x
     * @param y2 第二个矩形的y
     * @param w2 第二个矩形的w
     * @param h2 第二个矩形的h
     * @return 是否碰撞
     */
    public static boolean isRectCollision(float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2) {
        if (x2 > x1 && x2 > x1 + w1) {
            return false;
        } else if (x2 < x1 && x2 < x1 - w2) {
            return false;
        } else if (y2 > y1 && y2 > y1 + h1) {
            return false;
        } else {
            return !(y2 < y1 && y2 < y1 - h2);
        }
    }

    /**
     * 描述：点在直线上. 点A（x，y）,B(x1,y1),C(x2,y2) 点A在直线BC上吗?
     */
    public boolean pointAtSLine(double x, double y, double x1, double y1, double x2, double y2) {
        double result = (x - x1) * (y2 - y1) - (y - y1) * (x2 - x1);
        return result == 0;
    }

}

package com.codyy.rrt.base.util;


import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * 功能描述： 处理数字的Util、计算百分比<br>
 */
public class NumUtil {
    /**
     * 转换为BigDecimal
     *  
     * @param o
     * @return BigDecimal
     * @author fantasy 
     * @date 2013-8-27
     */
    public static BigDecimal toBig(Object o) {
        if (o == null || o.toString().equals("") || o.toString().equals("NaN")) {
            return new BigDecimal(0);
        }
        return new BigDecimal(o.toString());
    }
    
    /**
     * 计算百分比
     *  
     * @param divisor
     * @param dividend
     * @return String
     * @author fantasy 
     * @date 2013-8-27
     */
    public static String getPercent(Object divisor, Object dividend){
        if(divisor == null || dividend == null){
            return "";
        }
        NumberFormat percent = NumberFormat.getPercentInstance();   
        //建立百分比格式化引用   
        percent.setMaximumFractionDigits(2);
        BigDecimal a = toBig(divisor);
        BigDecimal b = toBig(dividend);
        if(a.equals(toBig(0)) || b.equals(toBig(0)) || a.equals(toBig(0.0)) || b.equals(toBig(0.0))){
       	 return "0.00%";
        }
        BigDecimal c = a.divide(b, 4, BigDecimal.ROUND_DOWN);
        return percent.format(c);
    }
    
    /**
     * 计算比例
     *  
     * @param divisor
     * @param dividend
     * @return String
     * @author fantasy 
     * @date 2013-10-9
     */
    public static String divideNumber(Object divisor, Object dividend){
    	if(divisor == null || dividend == null){
            return "";
        }
    	 BigDecimal a = toBig(divisor);
         BigDecimal b = toBig(dividend);
         if(a.equals(toBig(0)) || b.equals(toBig(0))){
        	 return "0";
         }
         BigDecimal c = a.divide(b, 2, BigDecimal.ROUND_DOWN);
         return c.toString();
    }
    
    /**
     * 去两个数的平均值，四舍五入
     *  
     * @param divisor
     * @param dividend
     * @return int
     * @author fantasy 
     * @date 2013-11-6
     */
    public static int averageNumber(Object divisor, Object dividend){
    	if(divisor == null || dividend == null){
            return 0;
        }
    	BigDecimal a = toBig(divisor);
        BigDecimal b = toBig(dividend);
        if(a.equals(toBig(0)) || b.equals(toBig(0))){
       	 	return 0;
        }
        BigDecimal c = a.divide(b, 0, BigDecimal.ROUND_HALF_UP);
        return c.intValue();
    }
    
    /**
	 * 检查id是否合法可用
	 * @return 是否合法
	 */
    public static boolean isIdValid(Long id) {
    	return id != null && id.longValue() != 0L;
    }
    
    /**
     * 检查Integer值大于0
     * @param arg
     * @return
     */
    public static boolean isLtZero(Integer arg) {
    	return arg != null && arg.intValue() > 0;
    }
    
    /**
	 * 精确到小数点后n位
	 * @param in
	 * @return
	 */
	public static float keepNDecimalPlace(float in, int n) {
		BigDecimal b  = new BigDecimal(in);
		in = b.setScale(n, BigDecimal.ROUND_HALF_UP).floatValue();  
		return in;
	}
	
	private static String[] numChars = {"零","一", "二", "三", "四", "五", "六", "七", "八", "九"};
	private static String[] num1Strs = {"十","百","千"};
	private static String[] num2Strs = {"万","亿"};
	//阿拉伯正整数数字转为简中 
	public static String numberToSimpleChinese(int num) {
		StringBuilder sb = new StringBuilder();
		int temp = num;
		if (num >= 100000000) {//判断是否达到亿
			sb.append(makePiece(temp/ 100000000));
			sb.append(num2Strs[1]);
			temp = num % 100000000;//获取亿以下部分数字
			if (temp < 10000000) sb.append(numChars[0]);
		}
		
		if (temp >= 10000) {//判断是否达到万
			sb.append(makePiece(temp/ 10000));
			sb.append(num2Strs[0]);
			temp = temp % 10000;//获取万以下部分数字
			if (temp < 1000) sb.append(numChars[0]);
		}
		if (temp != 0) {
			sb.append(makePiece(temp));
		}
		
		return sb.toString();
	}
	
	private static String makePiece(int num) {
		String numStr = num + "";
		char[] nums = numStr.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i=0; i< nums.length; i++) {
			int index = nums[i] - 48;//ascii码0是48
			
			if (index != 0) {
				if (i > 0 && nums[i-1] - 48 == 0) {//上一个为零先追加0
					sb.append(numChars[0]);
				}
				sb.append(numChars[index]);
				if (i < nums.length - 1) {
					sb.append(num1Strs[nums.length - 2 - i]);
				}
			}
		}
		return sb.toString();
	}
}
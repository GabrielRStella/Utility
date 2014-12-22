package com.ralitski.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MathHelper {
	
	public static Random random = new Random();
	
	public static float round(float num, float accuracy)
	{
		num *= accuracy;
		num = Math.round(num);
		num /= accuracy;
		return num;
	}
	
	public static float floor(float num, float accuracy)
	{
		num *= accuracy;
		num = (float) Math.floor(num);
		num /= accuracy;
		return num;
	}
	
	public static float ceil(float num, float accuracy)
	{
		num *= accuracy;
		num = (float) Math.ceil(num);
		num /= accuracy;
		return num;
	}
	
	public static float floor(float num)
	{
		return (float) Math.floor(num);
	}
	
	public static float ceil(float num)
	{
		return (float) Math.ceil(num);
	}

	public static int sum(int... i)
	{
		if(i.length == 0) return 0;
		int total = 0;
		for(int n = 0; n < i.length; n++) total += i[n];
		return total;
	}

	public static long sum(long... i)
	{
		if(i.length == 0) return 0;
		long total = 0;
		for(int n = 0; n < i.length; n++) total += i[n];
		return total;
	}

	public static float sum(float... f)
	{
		if(f.length == 0) return 0F;
		float total = 0;
		for(int n = 0; n < f.length; n++) total += f[n];
		return total;
	}
	
	public static int product(int... i)
	{
		if(i.length == 0) return 0;
		if(i.length == 1) return i[0];
		if(i.length == 2) return i[0] * i[1];
		int total = 1;
		for(int n = 0; n < i.length; n++) total *= i[n];
		return total;
	}
	
	public static float oppositeReciprocal(float f)
	{
		return -1F/f;
	}
	
	public static int randomInt(int min, int max)
	{
		int i = random.nextInt(max - min);
		return min + i;
	}
	
	public static float randomFloat(float max)
	{
		float f = random.nextFloat();
		f *= max;
		return f;
	}
	
	public static float randomSignedFloat(float max)
	{
		float f = random.nextFloat();
		f *= max;
		if(random.nextBoolean()) f *= -1F;
		return f;
	}
	
	public static double randomDouble(double max)
	{
		double d = random.nextDouble();
		d *= max;
		return d;
	}
	
	public static double randomSignedDouble(double max)
	{
		double d = random.nextDouble();
		d *= max;
		if(random.nextBoolean()) d *= -1D;
		return d;
	}
	
	//TODO faster method for this, too
	public static boolean isPrime(int i)
	{
		if(i == 1) return false;
		if(i == 2) return true;
		double d = (double)i;
		for(int n = 2; n < Math.sqrt(i) + 1; n++)
		{
			double d1 = d / (double)n;
			if(d1 % 1D == 0D)
			{
				return false;
			}
		}
		return true;
	}
	
	//TODO: better (faster) methods
	public static int divisors(long i)
	{
		return divisorNums(i).length;
	}
	
	public static long[] divisorNums(long i)
	{
		List<Long> numList = new ArrayList<Long>();
		for(long n = 1; n < i / 2 + 1; n++)
		{
			double temp = (double)i / (double)n;
			if(temp % 1D == 0)
			{
				if(!numList.contains(n)) numList.add(n);
				if(!numList.contains(i / n)) numList.add(i / n);
			}
		}
		long[] numList2 = new long[numList.size()];
		for(int n2 = 0; n2 < numList.size(); n2++) numList2[n2] = numList.get(n2);
		return numList2;
	}
	
	public static int[] divisorNums(int i)
	{
		List<Integer> numList = new ArrayList<Integer>();
		for(int n = 1; n < i / 2 + 1; n++)
		{
			double temp = (double)i / (double)n;
			if(temp % 1D == 0)
			{
				if(!numList.contains(n)) numList.add(n);
				if(!numList.contains(i / n)) numList.add(i / n);
			}
		}
		int[] numList2 = new int[numList.size()];
		for(int n2 = 0; n2 < numList.size(); n2++) numList2[n2] = numList.get(n2);
		return numList2;
	}
	
	//for gradienting and such
	public static float between(float min, float max, float ratio)
	{
		float dif = max - min;
		dif *= ratio;
		return min + dif;
	}
	
	public static float avg(float...f)
	{
		return sum(f) / f.length;
	}
}

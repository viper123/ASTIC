using System;

namespace ro.info.asticlib.math
{

	public class Math
	{
		public static int getApxlog(int @base, int x)
		{
			int power = 0;
			while (System.Math.Pow(@base, power) < x)
			{
				power++;
			}
			return power - 1;
		}

		public static double computeCosine(double[] a, double[] b)
		{
			if (a.Length != b.Length)
			{
				return 0;
			}
			double topSum = 0, leftBSum = 0, rightBSum = 0;
			for (int i = 0;i < a.Length;i++)
			{
				topSum += (a[i] * b[i]);
				leftBSum += System.Math.Pow(a[i],2);
				rightBSum += System.Math.Pow(b[i], 2);
			}
			return topSum / (System.Math.Sqrt(leftBSum) * System.Math.Sqrt(rightBSum));
		}
	}

}
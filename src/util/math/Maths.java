package util.math;

import java.util.Vector;

import util.Stream;

public class Maths {
	
	public static Stream<Integer> primes = new Stream<Integer>() {
		int counter = 1;
		
		protected Integer next() {
			if (currentSize() == 0)
				return 2;
			
			countloop:
			while (true){
				counter+=2;
				for (int i : getData())
					if (counter % i == 0)
						continue countloop;
				break countloop;
			}
			
			return counter;
		}
	};
	
	public static int max(int... ints){
		int max = ints[0];
		for (int i = 1; i < ints.length; i++)
			if (ints[i] > max)
				max = ints[i]; 
		return max;
	}
	
	public static int LCM(int... ints){
		if (ints.length == 0)
			return 0;
		int prime, i = 0;
		int result = 1;
		while ((prime = primes.get(i)) <= max(ints)){
			boolean factor = false;
			for (int j = 0; j < ints.length; j++){
				if (ints[j] % prime == 0){
					ints[j] /= prime;
					factor = true;
				}
			}
			if (factor)
				result *= prime;
			else
				i++;
		}
		
		return result;
	}
	
	public static int clamp(int x, int min, int max){
		return x < min ? min : x > max ? max : x;
	}

	public static double sum(double[] x) {
		double sum = 0.0D;
		for (double d : x)
			sum += d;
		return sum;
	}

	public static double sum(Vector<Double> x) {
		double sum = 0.0D;
		for (double d : x)
			sum += d;
		return sum;
	}

}

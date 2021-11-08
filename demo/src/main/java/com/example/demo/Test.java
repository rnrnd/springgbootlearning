package com.example.demo;

public class Test {

	private static long start = 1;
	private static long end = 3;
	public static long compute(long number){
		if (number > end){
			System.out.println(number);
			return 1;
		} else {
			return number * compute(number++);
		}
	}
	public static int factorial(int n){
		if (n == 0){
			return 1;
		} else {
			return factorial(n-1) * n;
		}
	}
	public static void main(String[] args) {
		System.out.println(factorial(3));
	}
}

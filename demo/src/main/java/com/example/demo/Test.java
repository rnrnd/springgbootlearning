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
		//System.out.println(factorial(3));
		//new Test().foo(new StringBuffer(),0);
		int i = 3 | 1;
		int j = 3 & 1;
		int k = 3 ^ 1;
		System.out.println(i);
		System.out.println(j);
		System.out.println(k);
	}
	public void foo(StringBuffer buffer, int len){
		String[] arr = new String[]{"0", "5", "0", "4", "0", "3", "0", "2", "0", "1"};
		new Thread(() -> {
			if(len < 10) {
				if (arr[len] == "0") {
					buffer.append("A");
				} else if (arr[len].equals("0")) {
					buffer.append("B");
				} else {
					buffer.append(arr[len]);
				}
				foo(buffer, len + 1);
			} else {
				System.out.println(buffer.toString());
			}
		}).start();

	}
}

package com.idreamsky.promoter.test;

public class TestSplit1 {
	public static void main(String[] args) {
		String str="127.0.0.1<br>127.0.0.1";
		String[] s = str.split("<br>");
		System.out.println(s[0] +"  "+s[1]);
	}

}

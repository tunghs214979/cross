package com.it.loganalyze.test;

import java.util.LinkedHashMap;
import java.util.Map;

import com.it.loganalyze.util.Util;


public class Test {
	public static void main(String[] args) {
		Map <String, String> map = new LinkedHashMap<>();
		map.put("abc", "def");
		System.out.println(Util.prettyPrintMap(map));
	}
}

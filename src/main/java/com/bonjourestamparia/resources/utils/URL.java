package com.bonjourestamparia.resources.utils;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class URL {
	public static List<Integer> decodeIntList(String s){
		return Arrays.asList(s.split(",")).stream().mapToInt(num -> Integer.parseInt(num)).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}
	
	public static String decodeParam(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		}catch (Exception e) {
			return "";
		}
	}
}

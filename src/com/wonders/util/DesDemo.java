package com.wonders.util;


public class DesDemo {

	public static void main(String[] args) throws Exception {
		DES des = new DES("miyaoceshi");//括号内为加密的秘钥，密钥线下给定
		
		String orginstr = "{\"id\":32}";//原始json数据
		System.out.println("orginstr加密后为:" + des.encrypt(orginstr));//调用加密方法，便可将原始json数据加密
		
		
		String idcardEn2 ="3639ea0bb3146f5a94f4a24eca19935b";
		System.out.println("解密后为:" + des.decrypt(idcardEn2));//调用解密方法，便可将加过密的json数据解密
	}
}

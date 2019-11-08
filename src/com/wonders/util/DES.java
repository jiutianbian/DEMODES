package com.wonders.util;

/**
 * DES加密解密工具类
 */

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;

public class DES {
	private static String strDefaultKey = "ceshi";

	private Cipher encryptCipher = null;

	private Cipher decryptCipher = null;

	public static String byteArr2HexStr(byte[] arrB) throws Exception {

		int iLen = arrB.length;

		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];

			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}

			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	public static byte[] hexStr2ByteArr(String strIn) throws Exception {
		byte[] arrB = strIn.getBytes("utf-8");
		int iLen = arrB.length;

		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	/**
	 * 默认构造方法
	 * 
	 * @throws Exception
	 */
	public DES() throws Exception {
		this(strDefaultKey);
	}

	public DES(String strKey) throws Exception {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		Key key = getKey(strKey.getBytes("utf-8"));

		encryptCipher = Cipher.getInstance("DES");
		encryptCipher.init(Cipher.ENCRYPT_MODE, key);

		decryptCipher = Cipher.getInstance("DES");
		decryptCipher.init(Cipher.DECRYPT_MODE, key);
	}

	public byte[] encrypt(byte[] arrB) throws Exception {
		return encryptCipher.doFinal(arrB);
	}

	/**
	 * 加密
	 * 
	 * @param strIn
	 * @return
	 * @throws Exception
	 */
	public String encrypt(String strIn) throws Exception {
		return byteArr2HexStr(encrypt(strIn.getBytes("utf-8")));
	}

	public byte[] decrypt(byte[] arrB) throws Exception {
		return decryptCipher.doFinal(arrB);
	}

	/**
	 * 解密
	 * 
	 * @param strIn
	 * @return
	 * @throws Exception
	 */
	public String decrypt(String strIn) throws Exception {
		try {
			return new String(decrypt(hexStr2ByteArr(strIn)), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	private Key getKey(byte[] arrBTmp) throws Exception {

		byte[] arrB = new byte[8];

		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}

		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

		return key;
	}

	@SuppressWarnings("unused")
	private void enTest(String strOriginal) {
		try {
			System.out.println("Plain   String: " + strOriginal);

			String strEncrypt = encrypt(strOriginal);
			System.out.println("Encrypted String: " + strEncrypt);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private void deTest(String strOriginal) {
		try {
			System.out.println("Encrypted String: " + strOriginal);
			System.out.println("Encrypted String length =  "
					+ strOriginal.length());
			String strPlain = decrypt(strOriginal);
			System.out.println("Plain  String: " + strPlain);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		String key = "37788f5c6412b89da6b2ed8f382e2c1a";
		DES des = new DES(key);
		String idcard = "310107199602287226";
		System.out.println("idcard加密后为:" + des.encrypt(idcard));
		
		String idcardEn = "564447be9ca572bb6b82c0b92f6e909a9e030de417cfd30b";
		System.out.println("idcard解密后为:" + des.decrypt(idcardEn));
	}

}


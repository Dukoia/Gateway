package com.dukoia.microservice.gateway.util;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * @author:JefferyChang
 * @Date:2019/5/16 19:43
 * @Desp:
 */
public class SecurityUtil {
    private static Logger log = LoggerFactory.getLogger(SecurityUtil.class);

    private static final String SHA_ALG = "SHA-1";

    private static final String MD2 = "MD2";
    private static final String MD5 = "MD5";
    private static final String SHA_256 = "SHA-256";
    private static final String SHA_384 = "SHA-384";
    private static final String SHA_512 = "SHA-512";

    public static boolean checkSignature(String signature, String token, String timestamp, String nonce) {
        String tmpStr = sign(token, timestamp, nonce);
        log.info("[-checkSignature-]SecurityUtil：target signature={}", tmpStr);
        return tmpStr != null && tmpStr.equals(signature.toUpperCase());
    }

    public static String sign(String token, String timestamp, String nonce) {
        String[] arr = new String[]{token, timestamp, nonce};
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (String anArr : arr) {
            content.append(anArr);
        }
        MessageDigest md;
        String signature = null;
        try {
            md = MessageDigest.getInstance(SHA_ALG);
            byte[] digest = md.digest(content.toString().getBytes());
            signature = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            log.error("=============================错误信息：{}",e);
        }
        return signature;
    }

    private static String byteToStr(byte[] byteArray) {
        StringBuilder strDigest = new StringBuilder();
        for (byte aByteArray : byteArray) {
            strDigest.append(byteToHexStr(aByteArray));
        }
        return strDigest.toString();
    }

    private static String byteToHexStr(byte mByte) {
        char[] digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = digit[mByte & 0X0F];
        return new String(tempArr);
    }

    public static boolean checkMd5sign(String requestSign, String token, String timestamp, String nonce) {
        //1.get the param witch is necessary
        String[] arr = new String[]{token, timestamp, nonce};
        //2.make the params into right order
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (String anArr : arr) {
            content.append(anArr);
        }
        String targetSign = null;
        //3.digest the message
        try {
            MessageDigest md = MessageDigest.getInstance(MD5);
            byte[] digest = md.digest(content.toString().getBytes());
            targetSign = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            log.error("=============================错误信息：{}",e);
        }
        //4.return compare result
        return StringUtils.isNotBlank(targetSign) && targetSign.equals(requestSign.toUpperCase());
    }


    public static String decrypt(String content, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if (content == null || content.length() < 1) {
            return null;
        }
        byte[] byteRresult = new byte[content.length() / 2];
        for (int i = 0; i < content.length() / 2; i++) {
            int high = Integer.parseInt(content.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(content.substring(i * 2 + 1, i * 2 + 2), 16);
            byteRresult[i] = (byte) (high * 16 + low);
        }
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(key.getBytes());
        kgen.init(128, random);
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] result = cipher.doFinal(byteRresult);
        return new String(result);
    }

    public static String encrypt(String content, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if (StringUtils.isEmpty(content) || StringUtils.isEmpty(key)) {
            return content;
        }
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(key.getBytes());
        kgen.init(128, random);
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        byte[] byteContent = content.getBytes("utf-8");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] byteRresult = cipher.doFinal(byteContent);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteRresult.length; i++) {
            String hex = Integer.toHexString(byteRresult[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    public static String wrapCardNo(String cardNo) {
        return cardNo.substring(0, 3) + "****" + cardNo.substring(cardNo.length() - 3, cardNo.length());
    }

    public static Boolean checkOverTime(String timestamp, String hours) {
        long lt = new Long(timestamp);
        Date requestTime = new Date(lt);
        Date nowTime = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowTime);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - Integer.parseInt(hours));
        Date compareTime = calendar.getTime();
        if (requestTime.before(compareTime)) {
            return false;
        } else {
            return true;
        }
    }

}
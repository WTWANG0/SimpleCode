package com.fixbug.compress;

/**
 * 文件压缩和解压缩的工具类 'A'  "11000101" <=> byte
 */
public class FormatUtil {
    /**
     * 把字节数据data转成二进制编码串  11000101 1100010  1100010 110001 11000
     * @param data
     * @return
     */
    public static String byteToBinaryString(byte data){
        StringBuilder sbuilder = new StringBuilder();
        for(int i=0; i < 8; ++i){
            byte bit = (byte)(data >>> i & 1);
            sbuilder.append(bit); //
        }
        sbuilder.reverse();
        return sbuilder.toString();
    }

    /**
     * 把8个位的字符串编码转成字节 "00010100"
     * @param code
     * @return
     */
    public static byte binaryStringToByte(String code){
        byte data = 0;
        for(int i=0; i < code.length(); ++i){
            if(code.charAt(i) == '1'){
                data += Math.pow(2, code.length()-i-1);
            }
        }
        return data;
    }

    public static void main(String[] args) {
        System.out.println(byteToBinaryString((byte)20));
        System.out.println(binaryStringToByte("00010100"));
    }
}

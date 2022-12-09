package T09_.compress;

/***
 * 文件压缩和解压工具类
 *  'A'  "11000101" <=> byte
 * */
public class FormatUtil {


    /**
     * 将字符串数据转换成二进制编码串
     */
    public static String binaryStringToString(byte data) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            byte bit = (byte) (data >>> i & 1);
            sb.append(bit);
        }
        sb.reverse();
        return sb.toString();
    }


    /**
     * 把8个位字符串编码成字节
     * "00010100"
     */
    public static byte binaryStringToByte(String code) {
        byte date = 0;
        for (int i = 0; i < code.length(); i++) {
            if (code.charAt(i) == '1') {
                date += Math.pow(2, code.length() - i - 1);//右节点
            }
        }
        return date;
    }


    /**
     * test
     */
    public static void main(String[] args) {

    }
}

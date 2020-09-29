package com.xin.common.utils.code;

/**
 * 生成token工具类
 *
 * @author creator mafh 2019/12/5 11:19
 * @author updater
 * @version 1.0.0
 */
public class TokenUtils {

    private static final String[] CHARACTERS = {
            "0","A","X","M","R","S","6","G","b","N",
            "a","1","L","Q","D","5","F","7","H","Z",
            "V","d","2","C","4","E","K","T","8","I",
            "W","Y","P","3","B","U","c","O","J","9"
    };

    /**
     * 根据code生成token
     *
     * @param code 编号
     * @return token
     */
    public static String generateToken(String code) {
        // 拼接结果
        StringBuilder sb = new StringBuilder();
        // 生成随机数确定行数
        int rand = (int) Math.floor(Math.random() * 4) * 10;
        for (int i = 0; i < code.length(); i++) {
            int index = Integer.parseInt(code.substring(i, i + 1));
            sb.append(CHARACTERS[index + rand]);
        }
        return sb.toString();
    }
}

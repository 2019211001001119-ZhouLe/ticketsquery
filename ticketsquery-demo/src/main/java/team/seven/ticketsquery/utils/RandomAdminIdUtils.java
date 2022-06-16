package team.seven.ticketsquery.utils;

import java.io.Serializable;
import java.util.Random;

/**
 * User: 谢礼治
 * Date: 2022/6/13
 * Time: 12:44
 * Description: No Description
 */

public class RandomAdminIdUtils implements Serializable {

    /**
     *  随机获取英文+数字（用户名）
     * @param engCode 小写英文的数量
     * @param numCode 数字的数量
     * @return
     */
    public static String verifyUserName(int engCode,int numCode){
        //声明一个StringBuffer存储随机数
        StringBuffer sb = new StringBuffer();
        char[] englishCodeArray = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
                'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        char[] numCodeArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        Random random = new Random();
        //获取英文
        for (int i = 0; i <engCode; i++){
            char num = englishCodeArray[random.nextInt(englishCodeArray.length)];
            sb.append(num);
        }
        //获取数字
        for (int i = 0; i <numCode; i++){
            char num = numCodeArray[random.nextInt(numCodeArray.length)];
            sb.append(num);
        }
        return sb.toString();
    }

}

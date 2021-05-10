package xiaozhi.com.test;

import java.util.ArrayList;

/**
 * 类描述
 *
 * @author zhangzy
 * @date 2021/3/30-21:37
 * @since v1.0
 */
public class Demo1 {

    /**
     * 1M
     */
    byte[] array = new byte[1024 * 1024];

    public static void main(String[] args) {
        ArrayList<Demo1> list = new ArrayList<>();
        int count = 1;
        try {
            while (true) {
                list.add(new Demo1());
                count = count + 1;
            }
        }catch (Error e){
            e.printStackTrace();
        }


    }
}

package com.xiaozhi.test;

import lombok.Data;

/**
 * @author zhangzy
 * @date 2020/11/3-15:25
 * @since v1.0
 */
@Data
public class testDao {
    private double[] resolution;

    public static void main(String[] args) {
//        ArrayList<EntityTest> entityTests = new ArrayList<>();
//        entityTests.add(new EntityTest("111", 10.0));
//        entityTests.add(new EntityTest("222", 20.5));
//        entityTests.add(new EntityTest("333", 30.5));
//        entityTests.add(new EntityTest("555", 40.5));
//        double v = entityTests.stream().limit(2).mapToDouble(EntityTest::getNum).average().orElse(0.0);
//        System.out.println(v);
        Double[][] data = {
                {0.0, 0.1, 0.2, 0.3, 0.4},
                {1.0, 1.1, 1.2, 1.3, 1.4},
                {2.0, 2.1, 2.2, 2.3, 2.4},
                {3.0, 3.1, 3.2, 3.3, 3.4},
                {4.0, 4.1, 4.2, 4.3, 4.4},
        };
        Double[][] doubles = new Double[3][3];
        for (int i = 0; i < data.length; i+=2) {
            for (int j = 0; j < data[i].length; j+=2) {
                doubles[i/2][j/2] = data[i][j];
            }
        }
        System.out.println(doubles);
    }
}

package com.xiaozhi.test;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author zhangzy
 * @date 2020/9/11-12:52
 * @since v1.0
 */
public class TestOpt {
    public static void main(String[] args) {
        ArrayList<GridInfo> gridInfos = new ArrayList<>();
        GridInfo gridInfo1 = new GridInfo();
        gridInfo1.setMinLon(28);
        gridInfo1.setMaxLon(33);
        gridInfo1.setMinLat(105);
        gridInfo1.setMaxLat(111);
        gridInfo1.setResolution(1);
        gridInfo1.setGrids(new double[][]{{1.0, 2.0},{2.0, 3.0}});

        GridInfo gridInfo2 = new GridInfo();
        gridInfo2.setMinLon(28);
        gridInfo2.setMaxLon(33);
        gridInfo2.setMinLat(105);
        gridInfo2.setMaxLat(111);
        gridInfo2.setResolution(1);
        gridInfo2.setGrids(new double[][]{{2.0, 4.0},{4.0, 6.0}});

        gridInfos.add(gridInfo1);
        gridInfos.add(gridInfo2);

        GridInfo gridInfo = gridInfos.get(0);
        double[][] grids = gridInfo.getGrids();
        for (int i = 0; i < grids.length; i++) {
            for (int j = 0; j < grids[i].length; j++) {
                double sum = 0.0;
                for (GridInfo g : gridInfos) {
                    sum += g.getGrids()[i][j];
                }
                double avg = sum/gridInfos.size();
                grids[i][j] = avg;
            }
        }
        HashMap<String, String> hashMap = new HashMap<>(2);
        hashMap.put("1", "lpo");
        hashMap.put("2", "shi");
        hashMap.put("3", "lko");
        String str = JSON.toJSONString(hashMap);
        System.out.println("ddd");
        System.out.println(str);
    }
}

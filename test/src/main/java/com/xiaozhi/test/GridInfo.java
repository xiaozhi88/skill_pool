package com.xiaozhi.test;

import lombok.Data;

/**
 * txt版本的格点数据
 *
 * @author LeoRmAo
 * @date 20201020 10:21:11
 * @since xavi
 */
@Data
public class GridInfo {

    private double minLon;

    private double maxLon;

    private double minLat;

    private double maxLat;

    private double resolution;

    private double[][] grids;
}

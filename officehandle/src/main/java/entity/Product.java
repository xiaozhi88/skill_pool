package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 产品实体类
 *
 * @author zhangzy
 * @date 2020/8/20-9:12
 * @since v1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    /**
     * id
     */
    private int id;

    /**
     * 标题
     */
    private String title;

    /**
     * 产品类别
     */
    private String type;

    /**
     * 状态
     */
    private String status;

    /**
     * 部门
     */
    private String section;

    /**
     * 制作者
     */
    private String framer;

    /**
     * word文件路径
     */
    private String wordPath;

    /**
     * pdf文件路径
     */
    private String pdfPath;

    /**
     * 附件
     */
    private String accessory;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}

package com.xiaozhi.office.utils;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * word模板导出(docx文件)
 *
 * @author zhangzy
 * @date 2020/8/16-22:02
 * @since v1.0
 */
@Log4j2
public class ExportWordOfDocx {
    /**
     * 实现图片文件的拷贝
     *
     * @param srcPathStr  源文件
     * @param desPath     接收路径
     * @param newFileName 新文件名
     * @return 返回 java.lang.String 描述此返回参数
     * @author zhangzy
     * @date 2020/8/17
     * @since v1.0
     */
    private static String copyFile(String srcPathStr, String desPath, String newFileName) {
        newFileName = "image" + newFileName + ".jpeg";
        desPath = desPath + File.separator + newFileName;
        try {
            //2.创建输入输出流对象
            FileInputStream fis = new FileInputStream(srcPathStr);
            FileOutputStream fos = new FileOutputStream(desPath);

            //创建搬运工具
            byte[] datas = new byte[1024 * 8];
            //创建长度
            int len = 0;
            //循环读取数据
            while ((len = fis.read(datas)) != -1) {
                fos.write(datas, 0, len);
            }
            //3.释放资源
            fis.close();
            fos.close();
            return newFileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 清空媒体文件
     *
     * @param media 文件
     * @author zhangzy
     * @date 2020/8/17
     * @since v1.0
     */
    private static void clearMediaFile(File media) {
        System.out.println("meida:" + media.isDirectory());
        if (media.isDirectory()) {
            File[] fileList = media.listFiles();
            if (fileList != null) {
                for (File file : fileList) {
                    if (file.exists()) {
                        file.delete();
                    }
                }
            }
        }
    }

    /**
     * 模板替换
     *
     * @param templateDir  模板路径
     * @param templateFile 模板文件
     * @param distFile     接收文件
     * @param data         数据
     * @author zhangzy
     * @date 2020/8/17
     * @since v1.0
     */
    private static void templateReplace(String templateDir, String templateFile, String distFile, Map<String, Object> data) throws Exception {
        Configuration config = new Configuration();
        config.setDirectoryForTemplateLoading(new File(templateDir));
        config.setObjectWrapper(new DefaultObjectWrapper());
        Template template = config.getTemplate(templateFile, "UTF-8");

        FileOutputStream fos = new FileOutputStream(distFile);
        Writer out = new OutputStreamWriter(fos, "UTF-8");
        template.process(data, out);
        out.flush();
        out.close();
    }

    /**
     * 导出Docx
     *
     * @param outFile         导出的目录
     * @param template        模板文件根目录
     * @param documentFtl     document模板文件
     * @param documentRelsFtl documentRels模板文件
     * @param data            document数据
     * @param images          documentRles数据
     * @author zhangzy
     * @date 2020/8/17
     * @since v1.0
     */
    public static void exportDocx(File outFile, File template, File documentFtl, File documentRelsFtl, Map<String, Object> data, List<Map<String, String>> images) throws Exception {
        // 获取document.xml文件路径
        File document = new File(template.getAbsolutePath() + File.separator + "word", "document.xml");
        log.info("获取document.xml：" + document.getAbsolutePath());

        // 获取document.xml.rels文件路径
        File documentRels = new File(template.getAbsolutePath() + File.separator + "word" + File.separator + "_rels", "document.xml.rels");
        log.info("获取document.xml.rels：" + documentRels.getAbsolutePath());

        templateReplace(documentFtl.getParent(), documentFtl.getName(), document.getAbsolutePath(), data);
        // 获取media目录
        File mediaDir = new File(template.getAbsolutePath() + File.separator + "word" + File.separator + "media" + File.separator);
        if (mediaDir.exists() && Objects.requireNonNull(mediaDir.listFiles()).length > 0 && !CollectionUtils.isEmpty(images)) {
            // 清空media 模板
            clearMediaFile(mediaDir);
            Map<String, Object> rels = new HashMap<>();
            for (Map<String, String> image : images) {
                String newFileName = copyFile(image.get("image"), mediaDir.getAbsolutePath(), image.get("index"));
                if (newFileName == null) {
                    rels.put(image.get("index"), "image.jpeg");
                } else {
                    rels.put(image.get("index"), newFileName);
                }
            }
            templateReplace(documentRelsFtl.getParent(), documentRelsFtl.getName(), documentRels.getAbsolutePath(), rels);
        }
        // 压缩模板文件生成docx
        ZipUtils.zip(template.getAbsolutePath(), outFile.getAbsolutePath());
    }

}

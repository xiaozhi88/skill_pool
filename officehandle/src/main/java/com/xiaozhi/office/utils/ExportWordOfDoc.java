package com.xiaozhi.office.utils;

import com.xiaozhi.office.vo.MonitorMsgVo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.log4j.Log4j2;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * word模板导出 (doc)
 *
 * @author zhangzy
 * @date 2020/8/15-17:04
 * @since v1.0
 */
@Log4j2
public class ExportWordOfDoc {
    /**
     * 生成word文件(.doc)
     *
     * @param templatePath ftl模板路径
     * @param templateFileName ftl模板名称
     * @param outPath word输出路径
     * @param outFileName word名称
     * @param dataMap 数据集合
     * @author zhangzy
     * @date 2020/8/15
     * @since v1.0
     */
    public static void createWord(String templatePath, String templateFileName, String outPath, String outFileName, Map<String,Object> dataMap) {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
        configuration.setDefaultEncoding("UTF-8");
        File outFile = new File(outPath + outFileName);
        try(Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"))) {
            configuration.setDirectoryForTemplateLoading(new File(templatePath));
            Template t = configuration.getTemplate(templateFileName);
            t.process(dataMap, out);
        } catch (TemplateException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 图片转base64字符串
     *
     * @param imgFile 图片文件
     * @return 返回 java.lang.String base64字符串
     * @author zhangzy
     * @date 2020/8/15
     * @since v1.0
     */
    public static String getImageStr(String imgFile) {
        byte[] data = null;
        try {
            InputStream in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }


    public static void main(String[] args) throws IOException {
        String templatePath = "D:\\word_pdf_test\\模板";
        String templateFileName = "气象监测.ftl";
        String outpath = "D:\\word_pdf_test\\模板\\word\\";
        String outFileName = "气象监测.doc";
        Map<String,Object> dataMap = new HashMap<>();
        //高温日数
        /*ArrayList<HighTemDaysVo> highTemDaysVos = new ArrayList<>();
        ArrayList<HighTempDateVo> highTemDateVos = new ArrayList<>();
        highTemDaysVos.add(new HighTemDaysVo("北碚",10,20,30,40,50));
        highTemDaysVos.add(new HighTemDaysVo("重庆",100,200,300,400,500));
        highTemDaysVos.add(new HighTemDaysVo("城口", null, null, null, null,null));
        highTemDateVos.add(new HighTempDateVo("重庆", "-", "*", "-", "Ⅰ", "-", "*", "*", "*", "*", "*", "*", "*", "*", "*", "*", "*", "*", "*", "*", "*", "*", "*", "*", "*", "*", "*", "*", "*", "*", null, null));
        dataMap.put("year", "2020");
        dataMap.put("month", "08");
        dataMap.put("areaDayList", highTemDaysVos);
        dataMap.put("areaDateList", highTemDateVos);*/
        //气象监测
        dataMap.put("data", new MonitorMsgVo());
        createWord(templatePath, templateFileName, outpath, outFileName, dataMap);
    }
}
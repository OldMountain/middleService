/**
 * @Title: PropertiesUtils.java
 * @Package com.havebugs.common.util.file
 * Copyright: Copyright (c) 2015
 * Company:HAVEBUGS
 * @author Havebugs-John
 * @date 2015-3-1 下午3:21:15
 * @version V1.0
 */
package com.nxd.middle.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @ClassName: PropertiesUtils
 * @Description: * 对属性文件操作的工具类 获取，新增，修改 注意： 以下方法读取属性文件会缓存问题,在修改属性文件时，不起作用， 　InputStream in =
 *               PropertiesUtils.class.getResourceAsStream("/config.properties"); 　解决办法： 　String savePath =
 *               PropertiesUtils.class.getResource("/config.properties").getPath();
 * @author Havebugs-John
 * @date 2015-3-1 下午3:30:32
 *
 */
public class PropertiesUtils {
    // 日志管理
    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);

    /**
     * getProperties(这里用一句话描述这个方法的作用)
     *
     * @Title: getProperties
     * @param @return 设定文件
     * @return Properties 返回类型
     * @throws
     */
    public static Properties getProperties() {
        Properties prop = new Properties();
        String savePath = PropertiesUtils.class.getResource("/config.properties").getPath();
        // 以下方法读取属性文件会缓存问题
        // InputStream in = PropertiesUtils.class
        // .getResourceAsStream("/config.properties");
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(savePath));
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(savePath), "utf-8"));
            prop.load(br);
        } catch (Exception e) {
            logger.error("[配置文件加载异常]Properties configuration load file faild:", e);
        }
        return prop;
    }

    public static Properties getProperties(String fileName) {
        Properties prop = new Properties();
        String savePath = PropertiesUtils.class.getResource("/" + fileName).getPath();
        // 以下方法读取属性文件会缓存问题
        // InputStream in = PropertiesUtils.class
        // .getResourceAsStream("/config.properties");
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(savePath));
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(savePath), "utf-8"));
            prop.load(br);
        } catch (Exception e) {
            logger.error("[配置文件加载异常]Properties configuration load file faild:", e);
        }
        return prop;
    }

    /**
     * findPropertiesKey(获取属性文件的数据 根据key获取值)
     *
     * @Title: findPropertiesKey
     * @param @param key
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     */
    public static String findPropertiesKey(String key) {
        try {
            Properties prop = getProperties();
            return prop.getProperty(key);
        } catch (Exception e) {
            logger.error("[配置文件内容读取异常]Properties Failed to read the configuration file:", e);
        }
        return null;
    }
    public static String findPropertiesKey(String key,String fileName) {
        try {
            Properties prop = getProperties(fileName);
            return prop.getProperty(key);
        } catch (Exception e) {
            logger.error("[配置文件内容读取异常]Properties Failed to read the configuration file:", e);
        }
        return null;
    }

    /**
     * modifyProperties(写入properties信息)
     *
     * @Title: 写入properties信息
     * @param @param key
     * @param @param value 设定文件
     * @return void 返回类型
     * @throws
     */
    public static void modifyProperties(String key, String value) {
        try {
            // 从输入流中读取属性列表（键和元素对）
            Properties prop = getProperties();
            prop.setProperty(key, value);
            String path = PropertiesUtils.class.getResource("/config.properties").getPath();
            FileOutputStream outputFile = new FileOutputStream(path);
            prop.store(outputFile, "modify");
            outputFile.flush();
            outputFile.close();
        } catch (Exception e) {
            logger.error("[配置文件内容修改异常]To modify the configuration file to fail:", e);
        }
    }

    public static void main(String[] args) {
        Properties prop = new Properties();
        InputStream in = PropertiesUtils.class.getResourceAsStream("/config.properties");
        try {
            prop.load(in);
            Iterator<Entry<Object, Object>> itr = prop.entrySet().iterator();
            while (itr.hasNext()) {
                Entry<Object, Object> e = (Entry<Object, Object>) itr.next();
                System.err.println((e.getKey().toString() + "" + e.getValue().toString()));
            }
        } catch (Exception e) {

        }
    }

    public static String getPropertiesKey(String key) {
        ResourceBundle rb = ResourceBundle.getBundle("config.properties");
        return rb.getString(key);
    }
}

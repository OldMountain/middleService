package com.nxd.middle.entity;

/**
 * Image
 *
 * @author luhangqi
 * @date 2018/1/24
 */
public class Image {
    private String[] paths;
    private String cid;
    private String imgPath;


    public String[] getPaths() {
        return paths;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setPaths(String[] paths) {
        this.paths = paths;
    }

    public void setImgPath(String imgPath) {
        paths = imgPath != null && !"".equals(imgPath) ? imgPath.split(","):null;
        this.imgPath = imgPath;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}

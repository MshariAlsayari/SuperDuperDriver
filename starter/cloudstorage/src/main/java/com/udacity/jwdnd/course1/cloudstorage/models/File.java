package com.udacity.jwdnd.course1.cloudstorage.models;

public class File {

    private Integer fileId;
    private String filename;
    private String contentType;
    private String fileSize;
    private Integer userid;
    private byte[] filedata;

    public File() {
    }

    public File(Integer fileId, String filename, String contentType, String fileSize, Integer userid, byte[] filedata) {
        this.fileId = fileId;
        this.filename = filename;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.userid = userid;
        this.filedata = filedata;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public byte[] getFiledata() {
        return filedata;
    }

    public void setFiledata(byte[] filedata) {
        this.filedata = filedata;
    }
}


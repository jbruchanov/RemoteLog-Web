package com.scurab.gwt.rlw.shared.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "LogItemBlobs")
public class LogItemBlob implements Serializable {

    private static final long serialVersionUID = 897695906066127214L;

    @Id
    @Column(name = "LogItemID", nullable = false)
    private int mId;

    @Column(name = "Data", nullable = false)
    private byte[] mData;

    @Column(name = "FileName", nullable = false)
    private String mFileName;

    public LogItemBlob() {
    }

    public LogItemBlob(int id, byte[] data, String fileName){
        mId = id;
        mData = data;
        setFileName(fileName);
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public byte[] getData() {
        return mData;
    }

    public void setData(byte[] data) {
        mData = data;
    }

    public String getFileName() {
        return mFileName;
    }

    public void setFileName(String fileName) {
        mFileName = fileName;
    }
}

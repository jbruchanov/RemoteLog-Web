package com.scurab.gwt.rlw.shared.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.ForeignKey;

import com.google.gson.annotations.SerializedName;

@Entity(name = "LogItemBlobs")
public class LogItemBlob implements Serializable {

    private static final long serialVersionUID = 897695906066127214L;
    
    @Id    
    @Column(name = "LogItemID", nullable = false)    
    private int mId;
        
    @Column(name = "Data")    
    private byte[] mData;
    
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
}

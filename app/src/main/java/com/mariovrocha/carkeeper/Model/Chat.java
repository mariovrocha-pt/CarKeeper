package com.mariovrocha.carkeeper.Model;

public class Chat {

    private String fromUid;
    private String toUid;

    public void setFromUid(String fromUid) {
        this.fromUid = fromUid;
    }

    public void setToUid(String toUid) {
        this.toUid = toUid;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    private String pesan;

    public String getFromUid() {
        return fromUid;
    }

    public String getToUid() {
        return toUid;
    }

    public String getPesan() {
        return pesan;
    }

    public String getFotoPesanURL() {
        return FotoPesanURL;
    }

    private String FotoPesanURL;

    public Chat(){}

    public Chat(String fromUid,String toUid,String pesan,String url){
        this.fromUid = fromUid;
        this.toUid = toUid;
        this.pesan = pesan;
        this.FotoPesanURL = url;
    }

}

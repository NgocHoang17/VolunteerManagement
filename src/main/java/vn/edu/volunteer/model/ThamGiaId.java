package vn.edu.volunteer.model;


import java.io.Serializable;

public class ThamGiaId implements Serializable {
    private String mssv;
    private String maHD;

    // Getters and Setters
    public String getMssv() { return mssv; }
    public void setMssv(String mssv) { this.mssv = mssv; }
    public String getMaHD() { return maHD; }
    public void setMaHD(String maHD) { this.maHD = maHD; }
}

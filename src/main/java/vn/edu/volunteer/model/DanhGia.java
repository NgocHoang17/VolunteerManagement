package vn.edu.volunteer.model;

import javax.persistence.*;

@Entity
@Table(name = "DANHGIA")
@IdClass(ThamGiaId.class)
public class DanhGia {
    @Id
    @Column(name = "MSSV")
    private String mssv;

    @Id
    @Column(name = "MaHD")
    private String maHD;

    @Column(name = "Diem")
    private int diem;

    @Column(name = "NhanXet", columnDefinition = "TEXT")
    private String nhanXet;

    @ManyToOne
    @JoinColumn(name = "MSSV", insertable = false, updatable = false)
    private SinhVien sinhVien;

    @ManyToOne
    @JoinColumn(name = "MaHD", insertable = false, updatable = false)
    private HoatDong hoatDong;

    public String getMssv() { return mssv; }
    public void setMssv(String mssv) { this.mssv = mssv; }
    public String getMaHD() { return maHD; }
    public void setMaHD(String maHD) { this.maHD = maHD; }
    public int getDiem() { return diem; }
    public void setDiem(int diem) { this.diem = diem; }
    public String getNhanXet() { return nhanXet; }
    public void setNhanXet(String nhanXet) { this.nhanXet = nhanXet; }
    public SinhVien getSinhVien() { return sinhVien; }
    public void setSinhVien(SinhVien sinhVien) { this.sinhVien = sinhVien; }
    public HoatDong getHoatDong() { return hoatDong; }
    public void setHoatDong(HoatDong hoatDong) { this.hoatDong = hoatDong; }
}
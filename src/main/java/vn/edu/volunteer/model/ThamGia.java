package vn.edu.volunteer.model;

import javax.persistence.*;

@Entity
@Table(name = "THAMGIA")
@IdClass(ThamGiaId.class)
public class ThamGia {
    @Id
    @Column(name = "MSSV")
    private String mssv;

    @Id
    @Column(name = "MaHD")
    private String maHD;

    @Column(name = "ThoiGianThamGia")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date thoiGianThamGia;

    @Column(name = "SoGioThamGia")
    private int soGioThamGia;

    @Column(name = "XepLoai")
    private String xepLoai;

    @Column(name = "ChungNhan")
    private String chungNhan;

    @Column(name = "TrangThai")
    private String trangThai;

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
    public java.util.Date getThoiGianThamGia() { return thoiGianThamGia; }
    public void setThoiGianThamGia(java.util.Date thoiGianThamGia) { this.thoiGianThamGia = thoiGianThamGia; }
    public int getSoGioThamGia() { return soGioThamGia; }
    public void setSoGioThamGia(int soGioThamGia) { this.soGioThamGia = soGioThamGia; }
    public String getXepLoai() { return xepLoai; }
    public void setXepLoai(String xepLoai) { this.xepLoai = xepLoai; }
    public String getChungNhan() { return chungNhan; }
    public void setChungNhan(String chungNhan) { this.chungNhan = chungNhan; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public SinhVien getSinhVien() { return sinhVien; }
    public void setSinhVien(SinhVien sinhVien) { this.sinhVien = sinhVien; }
    public HoatDong getHoatDong() { return hoatDong; }
    public void setHoatDong(HoatDong hoatDong) { this.hoatDong = hoatDong; }
}
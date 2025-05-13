package vn.edu.volunteer.model;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "SINHVIEN")
public class SinhVien {
    @Id
    @Column(name = "MSSV")
    private String mssv;

    @Column(name = "HoTen")
    private String hoTen;

    @Column(name = "Email")
    private String email;

    @Column(name = "SoDT")
    private String soDT;

    @Column(name = "Lop")
    private String lop;

    @Column(name = "Khoa")
    private String khoa;

    @Column(name = "Truong")
    private String truong;

    @OneToMany(mappedBy = "sinhVien")
    private List<ThamGia> thamGias;

    // Getters and Setters
    public String getMssv() { return mssv; }
    public void setMssv(String mssv) { this.mssv = mssv; }
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSoDT() { return soDT; }
    public void setSoDT(String soDT) { this.soDT = soDT; }
    public String getLop() { return lop; }
    public void setLop(String lop) { this.lop = lop; }
    public String getKhoa() { return khoa; }
    public void setKhoa(String khoa) { this.khoa = khoa; }
    public String getTruong() { return truong; }
    public void setTruong(String truong) { this.truong = truong; }
    public List<ThamGia> getThamGias() { return thamGias; }
    public void setThamGias(List<ThamGia> thamGias) { this.thamGias = thamGias; }
}
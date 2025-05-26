package vn.edu.volunteer.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "TRUONG")
public class Truong {
    @Id
    @Column(name = "MaTruong")
    private String maTruong;

    @Column(name = "TenTruong")
    private String tenTruong;

    @Column(name = "DiaChi", columnDefinition = "TEXT")
    private String diaChi;

    @Column(name = "KhuVuc")
    private String khuVuc;

    @OneToMany(mappedBy = "truong")
    private List<SinhVien> sinhViens;

    public String getMaTruong() { return maTruong; }
    public void setMaTruong(String maTruong) { this.maTruong = maTruong; }
    public String getTenTruong() { return tenTruong; }
    public void setTenTruong(String tenTruong) { this.tenTruong = tenTruong; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    public String getKhuVuc() { return khuVuc; }
    public void setKhuVuc(String khuVuc) { this.khuVuc = khuVuc; }
    public List<SinhVien> getSinhViens() { return sinhViens; }
    public void setSinhViens(List<SinhVien> sinhViens) { this.sinhViens = sinhViens; }
}
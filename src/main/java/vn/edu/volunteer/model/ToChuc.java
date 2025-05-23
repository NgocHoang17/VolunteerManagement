package vn.edu.volunteer.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "TOCHUC")
public class ToChuc {
    @Id
    @Column(name = "MaToChuc")
    private String maToChuc;

    @Column(name = "TenToChuc")
    private String tenToChuc;

    @Column(name = "Email")
    private String email;

    @Column(name = "SoDT")
    private String soDT;

    @Column(name = "DiaChi")
    private String diaChi;

    @OneToMany(mappedBy = "toChuc")
    private List<HoatDong> hoatDongs;

    public String getMaToChuc() { return maToChuc; }
    public void setMaToChuc(String maToChuc) { this.maToChuc = maToChuc; }
    public String getTenToChuc() { return tenToChuc; }
    public void setTenToChuc(String tenToChuc) { this.tenToChuc = tenToChuc; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSoDT() { return soDT; }
    public void setSoDT(String soDT) { this.soDT = soDT; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    public List<HoatDong> getHoatDongs() { return hoatDongs; }
    public void setHoatDongs(List<HoatDong> hoatDongs) { this.hoatDongs = hoatDongs; }
}
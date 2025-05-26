package vn.edu.volunteer.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "HOATDONG")
public class HoatDong {
    @Id
    @Column(name = "MaHD")
    private String maHD;

    @Column(name = "TenHD")
    private String tenHD;

    @Column(name = "DiaDiem", columnDefinition = "TEXT")
    private String diaDiem;

    @Column(name = "ThoiGianBatDau")
    @Temporal(TemporalType.TIMESTAMP)
    private Date thoiGianBatDau;

    @Column(name = "ThoiGianKetThuc")
    @Temporal(TemporalType.TIMESTAMP)
    private Date thoiGianKetThuc;

    @ManyToOne
    @JoinColumn(name = "MaToChuc")
    private ToChuc toChuc;

    @OneToMany(mappedBy = "hoatDong")
    private List<ThamGia> thamGias;

    public String getMaHD() { return maHD; }
    public void setMaHD(String maHD) { this.maHD = maHD; }
    public String getTenHD() { return tenHD; }
    public void setTenHD(String tenHD) { this.tenHD = tenHD; }
    public String getDiaDiem() { return diaDiem; }
    public void setDiaDiem(String diaDiem) { this.diaDiem = diaDiem; }
    public Date getThoiGianBatDau() { return thoiGianBatDau; }
    public void setThoiGianBatDau(Date thoiGianBatDau) { this.thoiGianBatDau = thoiGianBatDau; }
    public Date getThoiGianKetThuc() { return thoiGianKetThuc; }
    public void setThoiGianKetThuc(Date thoiGianKetThuc) { this.thoiGianKetThuc = thoiGianKetThuc; }
    public ToChuc getToChuc() { return toChuc; }
    public void setToChuc(ToChuc toChuc) { this.toChuc = toChuc; }
    public List<ThamGia> getThamGias() { return thamGias; }
    public void setThamGias(List<ThamGia> thamGias) { this.thamGias = thamGias; }
}
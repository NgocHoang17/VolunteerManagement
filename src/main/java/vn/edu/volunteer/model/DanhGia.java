package vn.edu.volunteer.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "danh_gia")
public class DanhGia {
    @EmbeddedId
    private DanhGiaId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("maSinhVien")
    @JoinColumn(name = "ma_sinh_vien")
    private SinhVien sinhVien;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("maHoatDong")
    @JoinColumn(name = "ma_hoat_dong")
    private HoatDong hoatDong;

    @Column(name = "diem", nullable = false)
    private Integer diem;

    @Column(name = "nhan_xet")
    @Lob
    private String nhanXet;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public void setMaSinhVien(String maSinhVien) {
        if (id == null) {
            id = new DanhGiaId();
        }
        id.setMaSinhVien(maSinhVien);
    }

    public void setMaHoatDong(String maHoatDong) {
        if (id == null) {
            id = new DanhGiaId();
        }
        id.setMaHoatDong(maHoatDong);
    }

    public String getMaSinhVien() {
        return id != null ? id.getMaSinhVien() : null;
    }

    public String getMaHoatDong() {
        return id != null ? id.getMaHoatDong() : null;
    }
}
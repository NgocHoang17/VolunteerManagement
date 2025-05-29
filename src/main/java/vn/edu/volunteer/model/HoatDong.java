package vn.edu.volunteer.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "hoat_dong")
public class HoatDong {
    @Id
    @Column(name = "ma_hoat_dong")
    private String maHoatDong;

    @Column(name = "ten_hoat_dong", nullable = false)
    private String tenHoatDong;

    @Column(name = "mo_ta")
    @Lob
    private String moTa;

    @Column(name = "yeu_cau")
    @Lob
    private String yeuCau;

    @Column(name = "thoi_gian_bat_dau", nullable = false)
    private LocalDateTime thoiGianBatDau;

    @Column(name = "thoi_gian_ket_thuc", nullable = false)
    private LocalDateTime thoiGianKetThuc;

    @Column(name = "dia_diem", nullable = false)
    private String diaDiem;

    @Column(name = "khu_vuc")
    private String khuVuc;

    @Column(name = "linh_vuc", nullable = false)
    private String linhVuc;

    @Column(name = "so_luong_tinh_nguyen_vien", nullable = false)
    private Integer soLuongTinhNguyenVien;

    @Column(name = "so_luong_toi_da", nullable = false)
    private Integer soLuongToiDa;

    @Column(name = "so_luong_da_dang_ky")
    private Integer soLuongDaDangKy = 0;

    @Column(name = "loi_ich")
    private String loiIch;

    @Column(name = "hinh_anh_url")
    private String hinhAnhUrl;

    @Column(name = "trang_thai", nullable = false)
    @Enumerated(EnumType.STRING)
    private TrangThaiHoatDong trangThai = TrangThaiHoatDong.CHUA_DIEN_RA;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_to_chuc", nullable = false)
    private ToChuc toChuc;

    @OneToMany(mappedBy = "hoatDong", cascade = CascadeType.ALL)
    private List<ThamGia> thamGias = new ArrayList<>();

    @OneToMany(mappedBy = "hoatDong", cascade = CascadeType.ALL)
    private List<DanhGia> danhGias = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public enum TrangThaiHoatDong {
        CHUA_DIEN_RA,
        DANG_DIEN_RA,
        DA_HOAN_THANH,
        DA_HUY
    }
}
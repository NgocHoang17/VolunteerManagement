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
@Table(name = "to_chuc")
public class ToChuc {
    @Id
    @Column(name = "ma_to_chuc")
    private String maToChuc;

    @Column(name = "ten_to_chuc", nullable = false)
    private String tenToChuc;

    @Column(name = "mo_ta")
    @Lob
    private String moTa;

    @Column(name = "muc_tieu")
    @Lob
    private String mucTieu;

    @Column(name = "linh_vuc", nullable = false)
    private String linhVuc;

    @Column(name = "dia_chi")
    private String diaChi;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "so_dien_thoai")
    private String soDienThoai;

    @Column(name = "website")
    private String website;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "nguoi_dai_dien", nullable = false)
    private String nguoiDaiDien;

    @Column(name = "chuc_vu_nguoi_dai_dien")
    private String chucVuNguoiDaiDien;

    @Column(name = "trang_thai_xac_thuc", nullable = false)
    @Enumerated(EnumType.STRING)
    private TrangThaiXacThuc trangThaiXacThuc = TrangThaiXacThuc.CHUA_XAC_THUC;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "toChuc", cascade = CascadeType.ALL)
    private List<HoatDong> hoatDongs = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public enum TrangThaiXacThuc {
        CHUA_XAC_THUC,
        DA_XAC_THUC,
        BI_TU_CHOI
    }
}
package vn.edu.volunteer.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "chung_nhan")
public class ChungNhan {
    @Id
    @Column(name = "ma_chung_nhan")
    private String maChungNhan;

    @Column(name = "ten_tep")
    private String tenTep;

    @Column(name = "noi_dung_tep")
    @Lob
    private byte[] noiDungTep;

    @Column(name = "ngay_cap")
    private LocalDateTime ngayCap;

    @Column(name = "trang_thai")
    private String trangThai = "PENDING";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_sinh_vien", nullable = false)
    private SinhVien sinhVien;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_hoat_dong", nullable = false)
    private HoatDong hoatDong;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "nguoi_duyet_id")
    private User nguoiDuyet;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ngay_duyet")
    private Date ngayDuyet;
    
    @Column(name = "ly_do_tu_choi")
    private String lyDoTuChoi;

    // Getter methods
    public String getMaChungNhan() {
        return maChungNhan;
    }

    public User getNguoiDuyet() {
        return nguoiDuyet;
    }

    public Date getNgayDuyet() {
        return ngayDuyet;
    }

    public String getLyDoTuChoi() {
        return lyDoTuChoi;
    }

    // Setter methods
    public void setMaChungNhan(String maChungNhan) {
        this.maChungNhan = maChungNhan;
    }

    public void setNguoiDuyet(User nguoiDuyet) {
        this.nguoiDuyet = nguoiDuyet;
    }

    public void setNgayDuyet(Date ngayDuyet) {
        this.ngayDuyet = ngayDuyet;
    }

    public void setLyDoTuChoi(String lyDoTuChoi) {
        this.lyDoTuChoi = lyDoTuChoi;
    }
} 
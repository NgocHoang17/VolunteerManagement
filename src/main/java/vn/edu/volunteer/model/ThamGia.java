package vn.edu.volunteer.model;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity đại diện cho việc sinh viên tham gia hoạt động tình nguyện
 * Sử dụng khóa chính kép gồm mã sinh viên và mã hoạt động
 */
@Entity
@Table(name = "tham_gia")
@IdClass(ThamGiaId.class)
public class ThamGia {
    // mã sinh viên và mã hoạt động - khóa chính kép
    @Id
    @Column(name = "ma_sinh_vien")
    private String maSinhVien;

    @Id
    @Column(name = "ma_hoat_dong")
    private String maHoatDong;


     // Trạng thái tham gia: PENDING (Chờ duyệt), APPROVED (Đã duyệt), CANCELED (Đã hủy)
    @Column(name = "trang_thai")
    private String trangThai;

    // xếp loại kq tham gia hđ
    @Column(name = "xep_loai")
    private String xepLoai;

    // thời điểm sv đki tham gia
    @Column(name = "ngay_dang_ky")
    private LocalDateTime ngayDangKy;

    // thời điểm đc duyệt tham gia
    @Column(name = "ngay_duyet")
    private LocalDateTime ngayDuyet;

    // ghi chú về việc tham gia
    @Column(name = "ghi_chu")
    private String ghiChu;

    // số giờ tham gia thực tế
    @Column(name = "so_gio_tham_gia")
    private Integer soGioThamGia = 0;

    // quan hệ với bảng sinh viên
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_sinh_vien", referencedColumnName = "ma_sinh_vien", insertable = false, updatable = false)
    private SinhVien sinhVien;

    // qhe với bảng hoạt động
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_hoat_dong", referencedColumnName = "ma_hoat_dong", insertable = false, updatable = false)
    private HoatDong hoatDong;

    // tư đọng set ngày đki khi tạo mới
    @PrePersist
    protected void onCreate() {
        ngayDangKy = LocalDateTime.now();
    }

    // get - set
    public String getMaSinhVien() {
        return maSinhVien;
    }

    public void setMaSinhVien(String maSinhVien) {
        this.maSinhVien = maSinhVien;
    }

    public String getMaHoatDong() {
        return maHoatDong;
    }

    public void setMaHoatDong(String maHoatDong) {
        this.maHoatDong = maHoatDong;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getXepLoai() {
        return xepLoai;
    }

    public void setXepLoai(String xepLoai) {
        this.xepLoai = xepLoai;
    }

    public LocalDateTime getNgayDangKy() {
        return ngayDangKy;
    }

    public void setNgayDangKy(LocalDateTime ngayDangKy) {
        this.ngayDangKy = ngayDangKy;
    }

    public LocalDateTime getNgayDuyet() {
        return ngayDuyet;
    }

    public void setNgayDuyet(LocalDateTime ngayDuyet) {
        this.ngayDuyet = ngayDuyet;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Integer getSoGioThamGia() {
        return soGioThamGia;
    }

    public void setSoGioThamGia(Integer soGioThamGia) {
        this.soGioThamGia = soGioThamGia;
    }

    public SinhVien getSinhVien() {
        return sinhVien;
    }

    public void setSinhVien(SinhVien sinhVien) {
        this.sinhVien = sinhVien;
    }

    public HoatDong getHoatDong() {
        return hoatDong;
    }

    public void setHoatDong(HoatDong hoatDong) {
        this.hoatDong = hoatDong;
    }
}
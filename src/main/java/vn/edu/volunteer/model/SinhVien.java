package vn.edu.volunteer.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name = "sinh_vien")
public class SinhVien {
    @Id
    @NotBlank(message = "Mã sinh viên không được để trống")
    @Pattern(regexp = "^[A-Z0-9]{8,10}$", message = "Mã sinh viên phải có 8-10 ký tự chữ hoa và số")
    @Column(name = "ma_sinh_vien")
    private String maSinhVien;

    @NotBlank(message = "Họ tên không được để trống")
    @Size(min = 2, max = 100, message = "Họ tên phải từ 2-100 ký tự")
    @Column(name = "ho_ten", nullable = false)
    private String hoTen;

    @NotBlank(message = "Trường không được để trống")
    @Column(name = "truong", nullable = false)
    private String truong;

    @Size(max = 100, message = "Khoa không được quá 100 ký tự")
    @Column(name = "khoa")
    private String khoa;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Pattern(regexp = "^[0-9]{10,11}$", message = "Số điện thoại phải có 10-11 chữ số")
    @Column(name = "so_dien_thoai")
    private String soDienThoai;

    @Column(name = "dia_chi")
    private String diaChi;

    @Column(name = "nganh")
    private String nganh;

    @Column(name = "lop")
    private String lop;

    @Column(name = "nam_hoc")
    private Integer namHoc;

    @Column(name = "gioi_thieu")
    @Lob
    private String gioiThieu;

    @Column(name = "ky_nang")
    private String kyNang;

    @Column(name = "kinh_nghiem")
    @Lob
    private String kinhNghiem;

    @Column(name = "so_thich")
    private String soThich;

    @Min(value = 0, message = "Tổng giờ tình nguyện không được âm")
    @Column(name = "tong_gio_tinh_nguyen")
    private Integer tongGioTinhNguyen = 0;

    @Column(name = "so_diem_tich_luy")
    private Integer soDiemTichLuy = 0;

    @Column(name = "so_gio_tham_gia")
    private Integer soGioThamGia = 0;

    @OneToMany(mappedBy = "sinhVien", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<ChungNhan> chungNhans = new ArrayList<>();

    @OneToMany(mappedBy = "sinhVien", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<ThamGia> thamGias = new ArrayList<>();

    @OneToMany(mappedBy = "sinhVien", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<DanhGia> danhGias = new ArrayList<>();

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
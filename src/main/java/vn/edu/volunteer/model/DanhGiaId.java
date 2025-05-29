package vn.edu.volunteer.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class DanhGiaId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "ma_sinh_vien")
    private String maSinhVien;

    @Column(name = "ma_hoat_dong")
    private String maHoatDong;

    public DanhGiaId() {
    }

    public DanhGiaId(String maSinhVien, String maHoatDong) {
        this.maSinhVien = maSinhVien;
        this.maHoatDong = maHoatDong;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DanhGiaId that = (DanhGiaId) o;

        if (!maSinhVien.equals(that.maSinhVien)) return false;
        return maHoatDong.equals(that.maHoatDong);
    }

    @Override
    public int hashCode() {
        int result = maSinhVien.hashCode();
        result = 31 * result + maHoatDong.hashCode();
        return result;
    }
} 
package vn.edu.volunteer.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class ThamGiaId implements Serializable {
    private String maSinhVien;
    private String maHoatDong;

    public ThamGiaId() {
    }

    public ThamGiaId(String maSinhVien, String maHoatDong) {
        this.maSinhVien = maSinhVien;
        this.maHoatDong = maHoatDong;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ThamGiaId that = (ThamGiaId) o;

        if (!maSinhVien.equals(that.maSinhVien)) return false;
        return maHoatDong.equals(that.maHoatDong);
    }

    @Override
    public int hashCode() {
        int result = maSinhVien.hashCode();
        result = 31 * result + maHoatDong.hashCode();
        return result;
    }

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
}

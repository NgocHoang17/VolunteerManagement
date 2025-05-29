-- Xóa các bảng theo thứ tự phụ thuộc
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS danh_gia;
DROP TABLE IF EXISTS tham_gia;
DROP TABLE IF EXISTS hoat_dong;
DROP TABLE IF EXISTS sinh_vien;
DROP TABLE IF EXISTS truong;
DROP TABLE IF EXISTS to_chuc;
DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS audit_log;
DROP TABLE IF EXISTS users;
SET FOREIGN_KEY_CHECKS = 1;

-- Tạo bảng users
CREATE TABLE users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    phone VARCHAR(10),
    role VARCHAR(20),
    enabled BOOLEAN DEFAULT true,
    blocked BOOLEAN DEFAULT false,
    avatar_url VARCHAR(255),
    last_login TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_users_username (username),
    UNIQUE KEY uk_users_email (email)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- Tạo bảng authorities
CREATE TABLE authorities (
    user_id BIGINT NOT NULL,
    authority VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_id, authority),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Thêm tài khoản admin mặc định (password: admin123)
INSERT INTO users (username, password, email, full_name, role, enabled)
VALUES ('admin', '$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW', 'admin@localhost', 'Administrator', 'ROLE_ADMIN', true);

INSERT INTO authorities (user_id, authority) VALUES (1, 'ROLE_ADMIN');

-- Tạo bảng truong
CREATE TABLE truong (
    ma_truong VARCHAR(20) NOT NULL,
    ten_truong VARCHAR(100) NOT NULL,
    dia_chi TEXT,
    khu_vuc VARCHAR(50),
    PRIMARY KEY (ma_truong)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tạo bảng to_chuc
CREATE TABLE to_chuc (
    ma_to_chuc VARCHAR(20) NOT NULL,
    ten_to_chuc VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    so_dt VARCHAR(15),
    dia_chi TEXT,
    PRIMARY KEY (ma_to_chuc)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tạo bảng sinh_vien
CREATE TABLE sinh_vien (
    ma_sinh_vien VARCHAR(20) NOT NULL,
    ho_ten VARCHAR(100) NOT NULL,
    truong VARCHAR(100) NOT NULL,
    khoa VARCHAR(100),
    email VARCHAR(100) NOT NULL,
    so_dien_thoai VARCHAR(20),
    dia_chi VARCHAR(200),
    nganh VARCHAR(100),
    lop VARCHAR(50),
    nam_hoc INTEGER,
    gioi_thieu TEXT,
    ky_nang VARCHAR(500),
    kinh_nghiem TEXT,
    so_thich VARCHAR(500),
    tong_gio_tinh_nguyen INTEGER DEFAULT 0,
    so_diem_tich_luy INTEGER DEFAULT 0,
    so_gio_tham_gia INTEGER DEFAULT 0,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL,
    PRIMARY KEY (ma_sinh_vien),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tạo bảng hoat_dong
CREATE TABLE hoat_dong (
    ma_hoat_dong VARCHAR(20) NOT NULL,
    ten_hoat_dong VARCHAR(200) NOT NULL,
    mo_ta TEXT,
    dia_diem VARCHAR(200),
    thoi_gian_bat_dau TIMESTAMP NOT NULL,
    thoi_gian_ket_thuc TIMESTAMP NOT NULL,
    so_luong_tinh_nguyen_vien INTEGER NOT NULL,
    so_luong_da_dang_ky INTEGER DEFAULT 0,
    trang_thai VARCHAR(20) DEFAULT 'OPEN',
    ma_to_chuc VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL,
    PRIMARY KEY (ma_hoat_dong),
    FOREIGN KEY (ma_to_chuc) REFERENCES to_chuc(ma_to_chuc) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tạo bảng tham_gia
CREATE TABLE tham_gia (
    ma_sinh_vien VARCHAR(20) NOT NULL,
    ma_hoat_dong VARCHAR(20) NOT NULL,
    trang_thai VARCHAR(20) DEFAULT 'PENDING',
    xep_loai VARCHAR(20),
    ngay_dang_ky TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ngay_duyet TIMESTAMP NULL,
    ghi_chu TEXT,
    so_gio_tham_gia INTEGER DEFAULT 0,
    PRIMARY KEY (ma_sinh_vien, ma_hoat_dong),
    FOREIGN KEY (ma_sinh_vien) REFERENCES sinh_vien(ma_sinh_vien) ON DELETE CASCADE,
    FOREIGN KEY (ma_hoat_dong) REFERENCES hoat_dong(ma_hoat_dong) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tạo bảng danh_gia
CREATE TABLE danh_gia (
    ma_sinh_vien VARCHAR(20) NOT NULL,
    ma_hoat_dong VARCHAR(20) NOT NULL,
    diem INTEGER NOT NULL,
    nhan_xet TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (ma_sinh_vien, ma_hoat_dong),
    FOREIGN KEY (ma_sinh_vien) REFERENCES sinh_vien(ma_sinh_vien) ON DELETE CASCADE,
    FOREIGN KEY (ma_hoat_dong) REFERENCES hoat_dong(ma_hoat_dong) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tạo bảng audit_log
CREATE TABLE audit_log (
    id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(50),
    action VARCHAR(100),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    details TEXT,
    PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4; 
document.addEventListener('DOMContentLoaded', function() {
    // Validate form sinh viên
    const sinhVienForm = document.getElementById('sinhVienForm');
    if (sinhVienForm) {
        sinhVienForm.addEventListener('submit', function(e) {
            const email = document.querySelector('input[name="email"]').value;
            const soDT = document.querySelector('input[name="soDT"]').value;
            if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
                e.preventDefault();
                alert('Email không hợp lệ!');
            }
            if (soDT && !/^[0-9]{10}$/.test(soDT)) {
                e.preventDefault();
                alert('Số điện thoại phải là 10 số!');
            }
        });
    }

    // Validate form hoạt động
    const hoatDongForm = document.getElementById('hoatDongForm');
    if (hoatDongForm) {
        hoatDongForm.addEventListener('submit', function(e) {
            const thoiGianBatDau = new Date(document.querySelector('input[name="thoiGianBatDau"]').value);
            const thoiGianKetThuc = new Date(document.querySelector('input[name="thoiGianKetThuc"]').value);
            if (thoiGianBatDau >= thoiGianKetThuc) {
                e.preventDefault();
                alert('Thời gian kết thúc phải sau thời gian bắt đầu!');
            }
        });
    }

    // Validate form tham gia
    const thamGiaForm = document.getElementById('thamGiaForm');
    if (thamGiaForm) {
        thamGiaForm.addEventListener('submit', function(e) {
            const soGio = document.querySelector('input[name="soGioThamGia"]').value;
            if (soGio <= 0) {
                e.preventDefault();
                alert('Số giờ tham gia phải lớn hơn 0!');
            }
        });
    }

    // Validate form đánh giá
    const danhGiaForm = document.getElementById('danhGiaForm');
    if (danhGiaForm) {
        danhGiaForm.addEventListener('submit', function(e) {
            const diem = document.querySelector('input[name="diem"]').value;
            if (diem < 1 || diem > 10) {
                e.preventDefault();
                alert('Điểm phải từ 1 đến 10!');
            }
        });
    }

    // Hiển thị toast
    const urlParams = new URLSearchParams(window.location.search);
    const successMessage = urlParams.get('successMessage');
    const errorMessage = urlParams.get('errorMessage');
    if (successMessage) {
        const toast = new bootstrap.Toast(document.getElementById('successToast'));
        document.querySelector('#successToast .toast-body').textContent = successMessage;
        toast.show();
    }
    if (errorMessage) {
        const toast = new bootstrap.Toast(document.getElementById('errorToast'));
        document.querySelector('#errorToast .toast-body').textContent = errorMessage;
        toast.show();
    }
});
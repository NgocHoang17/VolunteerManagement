package vn.edu.volunteer.service.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.volunteer.model.HoatDong;
import vn.edu.volunteer.model.ToChuc;
import vn.edu.volunteer.repository.HoatDongRepository;
import vn.edu.volunteer.service.HoatDongService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class HoatDongServiceImpl implements HoatDongService {

    @Autowired
    private HoatDongRepository hoatDongRepository;

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Page<HoatDong> findAll(String maHoatDong, String tenHoatDong, String toChuc, String trangThai, Pageable pageable) {
        return hoatDongRepository.findAll(maHoatDong, tenHoatDong, toChuc, trangThai, pageable);
    }

    @Override
    public Page<HoatDong> findAll(Pageable pageable) {
        return hoatDongRepository.findAll(pageable);
    }

    @Override
    public List<HoatDong> findAll() {
        return hoatDongRepository.findAll();
    }

    @Override
    public List<HoatDong> findAllWithPaging(int pageNumber, int pageSize) {
        return hoatDongRepository.findAllWithPaging(pageNumber, pageSize);
    }

    @Override
    public List<HoatDong> findByTrangThaiWithPaging(String trangThai, int pageNumber, int pageSize) {
        return hoatDongRepository.findByTrangThai(trangThai, PageRequest.of(pageNumber, pageSize)).getContent();
    }

    @Override
    public List<HoatDong> searchByKeywordWithPaging(String keyword, int pageNumber, int pageSize) {
        return hoatDongRepository.findByTenHoatDongContainingIgnoreCase(keyword, PageRequest.of(pageNumber, pageSize)).getContent();
    }

    @Override
    public List<HoatDong> search(String keyword, int pageNumber, int pageSize) {
        return hoatDongRepository.search(keyword, pageNumber, pageSize);
    }

    @Override
    public List<HoatDong> searchAdvanced(String maHoatDong, String tenHoatDong, String maToChuc, int pageNumber, int pageSize) {
        return hoatDongRepository.searchAdvanced(maHoatDong, tenHoatDong, maToChuc, pageNumber, pageSize);
    }

    @Override
    public long count() {
        return hoatDongRepository.count();
    }

    @Override
    public long countByToChuc(ToChuc toChuc) {
        return hoatDongRepository.countByToChuc(toChuc);
    }

    @Override
    public long countCertificates() {
        return hoatDongRepository.countCertificates();
    }

    @Override
    public List<HoatDong> findRecentActivities(int limit) {
        return getCurrentSession()
            .createQuery("from HoatDong h order by h.thoiGianBatDau desc", HoatDong.class)
            .setMaxResults(limit)
            .list();
    }

    @Override
    public List<HoatDong> findRecentActivitiesByToChuc(ToChuc toChuc, int limit) {
        return getCurrentSession()
            .createQuery("from HoatDong h where h.toChuc = :toChuc order by h.thoiGianBatDau desc", HoatDong.class)
            .setParameter("toChuc", toChuc)
            .setMaxResults(limit)
            .list();
    }

    @Override
    public HoatDong getMostPopularActivity() {
        return hoatDongRepository.getMostPopularActivity();
    }

    @Override
    public List<HoatDong> findAvailableActivities(int page, int size) {
        return hoatDongRepository.findByTrangThaiAndThoiGianBatDauAfterOrderByThoiGianBatDauAsc(
            "OPEN", 
            LocalDateTime.now(), 
            PageRequest.of(page, size)
        ).getContent();
    }

    @Override
    public List<HoatDong> findByToChuc(String maToChuc, int page, int size) {
        return getCurrentSession()
            .createQuery("from HoatDong h where h.toChuc.maToChuc = :maToChuc order by h.thoiGianBatDau desc", HoatDong.class)
            .setParameter("maToChuc", maToChuc)
            .setFirstResult(page * size)
            .setMaxResults(size)
            .list();
    }

    @Override
    public long countCertificatesByToChuc(ToChuc toChuc) {
        return hoatDongRepository.countCertificatesByToChuc(toChuc);
    }

    @Override
    public long countHoursByToChuc(ToChuc toChuc) {
        return hoatDongRepository.countHoursByToChuc(toChuc);
    }

    @Override
    public long countByTrangThai(String trangThai) {
        return hoatDongRepository.countByTrangThai(trangThai);
    }

    @Override
    public HoatDong findById(String maHoatDong) {
        return hoatDongRepository.findById(maHoatDong).orElse(null);
    }

    @Override
    public HoatDong findByMaHoatDong(String maHoatDong) {
        return hoatDongRepository.findById(maHoatDong).orElse(null);
    }

    @Override
    public HoatDong save(HoatDong hoatDong) {
        return hoatDongRepository.save(hoatDong);
    }

    @Override
    public void delete(String maHoatDong) {
        hoatDongRepository.deleteById(maHoatDong);
    }

    @Override
    public long countActiveByToChuc(ToChuc toChuc) {
        return getCurrentSession()
            .createQuery("select count(h) from HoatDong h where h.toChuc = :toChuc and h.trangThai = 'DANG_DIEN_RA'", Long.class)
            .setParameter("toChuc", toChuc)
            .uniqueResult();
    }

    @Override
    public List<HoatDong> findCertificatesByToChuc(ToChuc toChuc, int page, int size) {
        return getCurrentSession()
            .createQuery("from HoatDong h where h.toChuc = :toChuc and h.trangThai = 'DA_KET_THUC' order by h.thoiGianKetThuc desc", HoatDong.class)
            .setParameter("toChuc", toChuc)
            .setFirstResult(page * size)
            .setMaxResults(size)
            .list();
    }
} 
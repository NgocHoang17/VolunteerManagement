package vn.edu.volunteer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.edu.volunteer.model.ToChuc;
import java.util.List;
import java.util.Optional;

public interface ToChucRepository extends JpaRepository<ToChuc, String> {
    Page<ToChuc> findByTenToChucContainingIgnoreCase(String tenToChuc, Pageable pageable);
    Optional<ToChuc> findByEmail(String email);

    @Query("SELECT t FROM ToChuc t " +
           "WHERE (:maToChuc IS NULL OR t.maToChuc LIKE %:maToChuc%) " +
           "AND (:tenToChuc IS NULL OR t.tenToChuc LIKE %:tenToChuc%) " +
           "AND (:diaChi IS NULL OR t.diaChi LIKE %:diaChi%) " +
           "AND (:trangThai IS NULL OR t.trangThaiXacThuc = :trangThai)")
    Page<ToChuc> findAll(@Param("maToChuc") String maToChuc,
                        @Param("tenToChuc") String tenToChuc,
                        @Param("diaChi") String diaChi,
                        @Param("trangThai") String trangThai,
                        Pageable pageable);

    @Query("SELECT t FROM ToChuc t WHERE t.user.username = :userId")
    ToChuc findByUserId(@Param("userId") String userId);

    @Query(value = "SELECT * FROM to_chuc LIMIT :pageSize OFFSET :offset", nativeQuery = true)
    List<ToChuc> findAllWithPaging(@Param("offset") int offset, @Param("pageSize") int pageSize);

    @Query(value = "SELECT * FROM to_chuc WHERE LOWER(ten_to_chuc) LIKE LOWER(:keyword) OR ma_to_chuc LIKE :keyword LIMIT :pageSize OFFSET :offset", nativeQuery = true)
    List<ToChuc> search(@Param("keyword") String keyword, @Param("offset") int offset, @Param("pageSize") int pageSize);
}
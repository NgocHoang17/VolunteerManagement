package vn.edu.volunteer.service;

import vn.edu.volunteer.model.ToChuc;
import java.util.List;

public interface ToChucService {
    List<ToChuc> findAll();
    
    List<ToChuc> findAllWithPaging(int pageNumber, int pageSize);
    
    List<ToChuc> search(String keyword, int pageNumber, int pageSize);
    
    ToChuc findById(String maToChuc);
    
    ToChuc save(ToChuc toChuc);
    
    void deleteById(String maToChuc);
    
    long count();
}
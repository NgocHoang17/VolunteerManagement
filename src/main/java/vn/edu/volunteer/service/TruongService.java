package vn.edu.volunteer.service;

import vn.edu.volunteer.model.Truong;
import java.util.List;

public interface TruongService {
    List<Truong> findAll();
    
    List<Truong> findAllWithPaging(int pageNumber, int pageSize);
    
    List<Truong> search(String keyword, int pageNumber, int pageSize);
    
    Truong findById(String maTruong);
    
    Truong save(Truong truong);
    
    void deleteById(String maTruong);
    
    long count();
}
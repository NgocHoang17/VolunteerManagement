package vn.edu.volunteer.repository;

import vn.edu.volunteer.model.User;

public interface UserRepository {
    User findByUsername(String username);
    User findByEmail(String email);
    User save(User user);
    void deleteByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
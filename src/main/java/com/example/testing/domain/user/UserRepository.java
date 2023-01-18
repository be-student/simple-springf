package com.example.testing.domain.user;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    @Override
    List<User> findAll();
}

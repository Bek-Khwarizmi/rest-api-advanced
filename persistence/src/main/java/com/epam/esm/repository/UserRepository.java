package com.epam.esm.repository;

import com.epam.esm.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class UserRepository{


    @PersistenceContext
    EntityManager entityManager;


    public User save(User user){
        entityManager.persist(user);
        if (user.getId() != null)
            return user;
        return null;
    }

    public Optional<User> findById(Long id){
        User user = entityManager.find(User.class, id);
        if (user != null){
            return Optional.of(user);
        }
        return Optional.empty();
    }
}

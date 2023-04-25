package ru.l92169.test_task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.l92169.test_task.entity.User;

import javax.transaction.Transactional;
import java.util.List;

@Repository()
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE UPPER(u.name)=UPPER(:name)")
    List<User> findByName(String name);

    @Query("SELECT DISTINCT u.name, u.count FROM User u WHERE u.fromFile = :bool")
    List<List<String>> findAllFromFile(@Param("bool") boolean bool);

    @Query("SELECT u FROM User u WHERE u.age = (SELECT MAX(u2.age) FROM User u2 WHERE u2.fromFile = :bool) and u.fromFile = :bool")
    List<User> findUsersWithMaxAge(@Param("bool") boolean bool);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.count = u.count + 1 WHERE UPPER(u.name) = UPPER(:name)")
    void addOne(@Param("name") String name);

    @Query("SELECT COUNT(u) FROM User u WHERE UPPER(u.name)=UPPER(:name)")
    Integer getCount(@Param("name") String name);
}

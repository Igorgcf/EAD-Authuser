package com.ead.authuser.repositories;

import com.ead.authuser.models.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface UserCourseRepository extends JpaRepository<UserCourse, UUID> {

    boolean existsByUserIdAndCourseId(UUID userId, UUID id);

    @Query(value = "SELECT * FROM tb_user_course WHERE user_id = :userId", nativeQuery = true)
    List<UserCourse> findAllUserCourseIntoUser(@Param("userId") UUID userId);
}

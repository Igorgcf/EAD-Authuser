package com.ead.authuser.services.impl;

import com.ead.authuser.clients.UserClient;
import com.ead.authuser.dto.CourseDTO;
import com.ead.authuser.dto.UserCourseDTO;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.User;
import com.ead.authuser.models.UserCourse;
import com.ead.authuser.repositories.UserCourseRepository;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.services.UserCourseService;
import com.ead.authuser.services.exceptions.BadRequestException;
import com.ead.authuser.services.exceptions.ResourceNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
public class UserCourseServiceImpl implements UserCourseService {

    @Autowired
    private UserCourseRepository UserCourseRepository;

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserClient client;

    @Transactional
    @Override
    public UserCourseDTO saveSubscriptionUserInCourse(UUID userId, CourseDTO dto) {

        Optional<User> obj = repository.findById(userId);
        User entity = obj.orElseThrow(() -> new ResourceNotFoundException("User Id not found: " + userId));

        if(entity.getUserStatus().equals(UserStatus.BLOCKED)){
            throw new BadRequestException("User is blocked");
        }

        boolean existsByUserIdAndCourseId = UserCourseRepository.existsByUserIdAndCourseId(userId, dto.getId());
        if(existsByUserIdAndCourseId){
            throw new BadRequestException("User already subscribed to this course");
        }

        dto = client.findById(dto.getId());

        UserCourse userCourse = entity.convertToUserCourse(dto.getId());

        UserCourseRepository.save(userCourse);

        log.debug("UserCourse Id saved {}, UserCourse courseId saved {}, UserCourse user saved {}" , userCourse.getId(),
                (userCourse.getCourseId() != null ? userCourse.getCourseId() : "No courseId associated"),
                (userCourse.getUser() != null ? userCourse.getUser() : "No User associated"));

        log.info("UserCourse Id saved {}, UserCourse courseId saved {}, UserCourse userId saved {}" , userCourse.getId(),
                (userCourse.getCourseId() != null ? userCourse.getCourseId() : "No courseId associated"),
                (userCourse.getUser().getId() != null ? userCourse.getUser().getId() : "No userId associated"));

        return new UserCourseDTO(userCourse.getId(), userCourse.getCourseId(), userCourse.getUser());
    }

    @Transactional
    @Override
    public Page<UserCourseDTO> findAllPaged(Pageable pageable) {

        Page<UserCourse> page = UserCourseRepository.findAll(pageable);
        return page.map(UserCourseDTO::new);
    }
}

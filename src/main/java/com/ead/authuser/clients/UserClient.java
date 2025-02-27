package com.ead.authuser.clients;

import com.ead.authuser.dto.CourseDTO;
import com.ead.authuser.dto.ResponsePageDTO;
import com.ead.authuser.services.UtilsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Log4j2
@Component
public class UserClient {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UtilsService service;

    public Page<CourseDTO> findAllCoursesByUser(UUID userId, Pageable pageable) {

        String url = service.createUrl(userId, pageable);

        log.debug("Request URL: {} ", url);
        log.info("Request URL: {} ", url);

        try {
            ParameterizedTypeReference<ResponsePageDTO<CourseDTO>> responseType = new ParameterizedTypeReference<ResponsePageDTO<CourseDTO>>() {
            };
            ResponseEntity<ResponsePageDTO<CourseDTO>> result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            if (result.getBody() != null) {
                log.debug("Response Number of Elements: {} ", result.getBody().getContent().size());
                return result.getBody();
            }

        } catch (HttpStatusCodeException e) {
            log.error("Error request /courses {} ", e);
        }
        log.info("Ending request /courses userId {} ", userId);

        return Page.empty();
        }

        public CourseDTO findById(UUID courseId){

            String url = service.createUrl(courseId);

            log.debug("Request URL: {} ", url);
            log.info("Request URL: {} ", url);

            try {
                ResponseEntity<CourseDTO> result = restTemplate.exchange(url, HttpMethod.GET, null, CourseDTO.class);
                if(result.getBody()!= null) {
                    log.debug("Response Element: {} ", result.getBody());
                    return result.getBody();
                }
            } catch (HttpStatusCodeException e) {
                log.error("Error request /courses {} ", e);
            }
            log.info("Ending request /courses courseId {} ", courseId);

            return null;
        }
    }




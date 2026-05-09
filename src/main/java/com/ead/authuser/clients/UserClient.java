package com.ead.authuser.clients;

import com.ead.authuser.dto.CourseDTO;
import com.ead.authuser.dto.ResponsePageDTO;
import com.ead.authuser.dto.UserDTO;
import com.ead.authuser.models.User;
import com.ead.authuser.services.UtilsService;
import com.ead.authuser.services.exceptions.BadRequestException;
import com.ead.authuser.services.exceptions.ResourceNotFoundException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Log4j2
@Component
public class UserClient {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UtilsService service;

    @CircuitBreaker(name = "circuitbreakerInstance")
    public Page<CourseDTO> findAllCoursesByUser(UUID userId, Pageable pageable, String token) {

        String url = service.createUrl(userId, pageable);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> requestEntity = new HttpEntity<String>("parameters", headers);

        log.debug("Request URL: {} ", url);
        log.info("Request URL: {} ", url);

        try {
            ParameterizedTypeReference<ResponsePageDTO<CourseDTO>> responseType = new ParameterizedTypeReference<ResponsePageDTO<CourseDTO>>() {
            };
            ResponseEntity<ResponsePageDTO<CourseDTO>> result = restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType);
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
            } catch (HttpClientErrorException.NotFound e) {
                throw new ResourceNotFoundException("Course Id not found: " + courseId);
            }

            return null;
        }

}





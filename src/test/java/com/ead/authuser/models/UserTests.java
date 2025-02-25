package com.ead.authuser.models;

import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

public class UserTests {

    @Test
    public void userShouldHaveCorrectStructure(){

        User entity = new User();

        UUID id = UUID.randomUUID();

        entity.setId(id);
        entity.setUsername("TomásTechnology");
        entity.setEmail("Tomás@gmail.com");
        entity.setPassword("1234567");
        entity.setFullName("Tomás Gonçalves");
        entity.setUserType(UserType.STUDENT);
        entity.setUserStatus(UserStatus.ACTIVE);
        entity.setPhoneNumber("+55 11 99070-9070");
        entity.setCpf("123-456-789-4");
        entity.setImageUrl("www.image.com");
        entity.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        entity.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));


        Assertions.assertNotNull(entity);
        Assertions.assertEquals(entity.getId(), id);
        Assertions.assertEquals(entity.getUsername(), "TomásTechnology");
        Assertions.assertEquals(entity.getFullName(), "Tomás Gonçalves");
        Assertions.assertEquals(entity.getEmail(), "Tomás@gmail.com");
        Assertions.assertEquals(entity.getPassword(), "1234567");
        Assertions.assertEquals(entity.getPhoneNumber(), "+55 11 99070-9070");
        Assertions.assertEquals(entity.getCpf(), "123-456-789-4");
        Assertions.assertEquals(entity.getImageUrl(), "www.image.com");
        Assertions.assertEquals(entity.getUserType(), UserType.STUDENT);
        Assertions.assertEquals(entity.getUserStatus(), UserStatus.ACTIVE);


    }
}

package com.ead.authuser.specification;

import com.ead.authuser.models.User;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationTemplate {

    @And({
            @Spec(path = "userType", spec = Equal.class),
            @Spec(path = "userStatus", spec = Equal.class),
            @Spec(path = "email", spec = Like.class),
            @Spec(path = "username", spec = Equal.class),
            @Spec(path = "fullName", spec = Equal.class),
            @Spec(path = "phoneNumber", spec = Equal.class),
            @Spec(path = "cpf", spec = Equal.class)})
    public interface UserSpec extends Specification<User> {
    }
}

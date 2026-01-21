package com.ead.authuser.services;

import com.ead.authuser.dto.RoleDTO;
import com.ead.authuser.enums.RoleType;

public interface RoleService {

   RoleDTO findByName(RoleType name);
}

package com.suman.project1.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.suman.project1.model.UserModel;
@Repository
public interface Userrepo extends JpaRepository<UserModel, Integer>
{
	UserModel findByUsername(String user);
	boolean existsUserModelByUsername(String user);
	boolean existsUserModelByPhone(String phone);
	boolean existsUserModelByEmail(String email);
}

package com.suman.project1.controller;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.suman.project1.model.UserModel;
import com.suman.project1.service.UserService;
@CrossOrigin(origins="*")
@RestController
public class UserController 
{
	@Autowired
	private UserService userservice;
	@GetMapping("/dashboard")
	public Object allUserDetails()
	{
		return userservice.allUserDetails();
	}
	@PostMapping("/dashboard/{username}")
	public Object getDataById(@PathVariable("username")String username)
	{
		return userservice.getUserByUsername(username);
	}
	@PostMapping("/adduser")
	public Object adduser(@RequestBody UserModel model) throws Exception
	{
		return userservice.saveUser(model);//return new user object with id
	}
	@PutMapping("/update")
	public Object update(@RequestBody UserModel model)
	{
		return userservice.update(model);
	}
	@DeleteMapping("/delete/{id}")
	public Object delete(@PathVariable("id")int id)
	{
		return userservice.delete(id);
	}
	@GetMapping("/signout")
	public String logout(HttpSession session)
	{
		return userservice.logout(session);
	}
}
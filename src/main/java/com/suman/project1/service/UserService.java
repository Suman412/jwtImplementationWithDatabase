package com.suman.project1.service;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.suman.project1.common.CustomSuccessResponse;
//import com.suman.project1.model.FetchUserModel;
import com.suman.project1.model.UserModel;
import com.suman.project1.repository.Userrepo;
@Service
public class UserService implements UserServiceInterface
{
	//PasswordEncoder encoder = new BCryptPasswordEncoder();
	@Autowired
	private PasswordEncoder bcryptEncoder;
	@Autowired
	private Userrepo userrepo;
	//@Autowired
	//private FetchUserModel fetchUserModel;
	/*public Object validate(UserModel model)
	{
		if(userrepo.existsUserModelByUsername(model.getUsername()))
		{
			System.out.println(model.getPassword());
			UserModel data=userrepo.findByUsername(model.getUsername());
			System.out.println(data.getPassword());
			if(encoder.matches(model.getPassword(), data.getPassword()))//data.getPassword().equals(encoder.encode(model.getPassword())))
				return new CustomSuccessResponse(HttpStatus.ACCEPTED.value(),"login confirmed", data);
			else
				return new CustomSuccessResponse(HttpStatus.NOT_FOUND.value(),"Wrong Password", new UserModel());
		}
		return new CustomSuccessResponse(HttpStatus.NOT_FOUND.value(),"UserName does not exist", new UserModel());
	}*/
	public Object allUserDetails() 
	{
		return new CustomSuccessResponse(HttpStatus.OK.value(),"you got all Data", userrepo.findAll());
	}
	public Object getUserByUsername(String username) 
	{
		if(userrepo.existsUserModelByUsername(username)==true)
			return new CustomSuccessResponse(HttpStatus.ACCEPTED.value(),"username exist", userrepo.findByUsername(username));
		else
			return new CustomSuccessResponse(HttpStatus.NOT_FOUND.value(),"username not exist", new UserModel());
	}
	public Object saveUser(UserModel model) throws Exception//adduser and cannot manupulate by id
	{
		if(userrepo.existsUserModelByPhone(model.getPhone())||model.getId()!=null)
		{
			model.setPhone(null);
			return new CustomSuccessResponse(HttpStatus.CONFLICT.value(),"duplicate phone number", model);
		}
		else if(userrepo.existsUserModelByUsername(model.getUsername())||model.getId()!=null)
		{
			model.setUsername(null);
			return new CustomSuccessResponse(HttpStatus.CONFLICT.value(),"duplicate UserName", model);
		}
		else if(userrepo.existsUserModelByEmail(model.getEmail())||model.getId()!=null)
		{
			model.setEmail(null);
			return new CustomSuccessResponse(HttpStatus.CONFLICT.value(),"duplicate Email", model);
		}
		else
		{
			System.out.println(model.getPassword());
			model.setPassword(bcryptEncoder.encode(model.getPassword()));
			model.setModifyBy(model.getCreatedBy());
			model.setCreatedDate(java.time.LocalDate.now());
			model.setModifyDate(java.time.LocalDate.now());
			return new CustomSuccessResponse(HttpStatus.ACCEPTED.value(),"data added",userrepo.save(model));
		}
	}
	public Object update(UserModel model)//update the database details
	{
		UserModel um=userrepo.findById(model.getId()).orElse(new UserModel());
		boolean username=um.getUsername().equals(model.getUsername());
		boolean email=um.getEmail().equals(model.getEmail());
		boolean phone=um.getPhone().equals(model.getPhone());
		boolean repoPhone=userrepo.existsUserModelByPhone(model.getPhone());
		boolean repoEmail=userrepo.existsUserModelByEmail(model.getEmail());
		boolean repoUsername=userrepo.existsUserModelByUsername(model.getUsername());
		if(model.getId()!=null&&model.getId()>0&&model.getPassword()!=null)
		{
			if(username&&email&&phone)
			{
				um.setName(model.getName());
				um.setPassword(bcryptEncoder.encode(model.getPassword()));
				um.setModifyBy(model.getModifyBy());
				um.setModifyDate(java.time.LocalDate.now());
				return new CustomSuccessResponse(HttpStatus.ACCEPTED.value(),"data saved",userrepo.save(um));
			}
			else if(username&&email&&(phone==false))
			{
				if(repoPhone==true)
				{
					model.setPhone(null);
					return new CustomSuccessResponse(HttpStatus.CONFLICT.value(),"duplicate phone number in database",model);
				}
				else
				{
					um.setPhone(model.getPhone());
					um.setName(model.getName());
					um.setPassword(bcryptEncoder.encode(model.getPassword()));
					um.setModifyBy(model.getModifyBy());
					um.setModifyDate(java.time.LocalDate.now());
					return new CustomSuccessResponse(HttpStatus.ACCEPTED.value(),"data saved",userrepo.save(um));
				}
			}
			else if(username&&(email==false)&&phone)
			{
				if(repoEmail==true)
				{
					model.setEmail(null);
					return new CustomSuccessResponse(HttpStatus.CONFLICT.value(),"duplicate email in database",model);
				}
				else
				{
					um.setEmail(model.getEmail());
					um.setName(model.getName());
					um.setPassword(bcryptEncoder.encode(model.getPassword()));
					um.setModifyBy(model.getModifyBy());
					um.setModifyDate(java.time.LocalDate.now());
					return new CustomSuccessResponse(HttpStatus.ACCEPTED.value(),"data saved",userrepo.save(um));
				}
			
			}
			else if(username&&(email==false)&&(phone==false))
			{
				if(repoEmail==true)
				{
					model.setEmail(null);
					return new CustomSuccessResponse(HttpStatus.CONFLICT.value(),"duplicate email in database",model);
				}
				else if(repoPhone==true)
				{
					model.setPhone(null);
					return new CustomSuccessResponse(HttpStatus.CONFLICT.value(),"duplicate phone number in database",model);
				}
				else 
				{
					um.setPhone(model.getPhone());
					um.setEmail(model.getEmail());
					um.setName(model.getName());
					um.setPassword(bcryptEncoder.encode(model.getPassword()));
					um.setModifyBy(model.getModifyBy());
					um.setModifyDate(java.time.LocalDate.now());
					return new CustomSuccessResponse(HttpStatus.ACCEPTED.value(),"data saved",userrepo.save(um));
				}
			}
			else if((username==false)&&email&&phone)
			{
				if(repoUsername==true)
				{
					model.setUsername(null);
					return new CustomSuccessResponse(HttpStatus.CONFLICT.value(),"duplicate userName in database",model);
				}
				else
				{
					um.setUsername(model.getUsername());
					um.setName(model.getName());
					um.setPassword(bcryptEncoder.encode(model.getPassword()));	
					um.setModifyBy(model.getModifyBy());
					um.setModifyDate(java.time.LocalDate.now());
					return new CustomSuccessResponse(HttpStatus.ACCEPTED.value(),"data saved",userrepo.save(um));
				}
			}
			else if((username==false)&&email&&(phone==false))
			{
				if(repoUsername==true)
				{
					model.setUsername(null);
					return new CustomSuccessResponse(HttpStatus.CONFLICT.value(),"duplicate UserName in database",model);
				}
				else if(repoPhone==true)
				{
					return new CustomSuccessResponse(HttpStatus.CONFLICT.value(),"duplicate phone number in database",model);
				}
				else 
				{
					um.setPhone(model.getPhone());
					um.setUsername(model.getUsername());
					um.setName(model.getName());
					um.setPassword(bcryptEncoder.encode(model.getPassword()));
					um.setModifyBy(model.getModifyBy());
					um.setModifyDate(java.time.LocalDate.now());
					return new CustomSuccessResponse(HttpStatus.ACCEPTED.value(),"data saved",userrepo.save(um));
				}
			}
			else if((username==false)&&(email==false)&&phone)
			{
				if(repoUsername==true)
				{
					model.setUsername(null);
					return new CustomSuccessResponse(HttpStatus.CONFLICT.value(),"duplicate UserName in database",model);
				}
				else if(repoEmail==true)
				{
					model.setEmail(null);
					return new CustomSuccessResponse(HttpStatus.CONFLICT.value(),"duplicate email in database",model);
				}
				else 
				{
					um.setUsername(model.getUsername());
					um.setName(model.getName());
					um.setEmail(model.getEmail());
					um.setPassword(bcryptEncoder.encode(model.getPassword()));
					um.setModifyBy(model.getModifyBy());
					um.setModifyDate(java.time.LocalDate.now());
					return new CustomSuccessResponse(HttpStatus.ACCEPTED.value(),"data saved",userrepo.save(um));
				}
			}
			else if((username==false&&repoUsername==false)&&(email==false&&repoEmail==false)&&(phone==false&&repoPhone==false))
			{
				if(repoUsername==true)
				{
					model.setUsername(null);
					return new CustomSuccessResponse(HttpStatus.CONFLICT.value(),"duplicate UserName in database",model);
				}
				else if(repoEmail==true)
				{
					model.setEmail(null);
					return new CustomSuccessResponse(HttpStatus.CONFLICT.value(),"duplicate Email in database",model);
				}
				else if(repoPhone==true)
				{
					model.setPhone(null);
					return new CustomSuccessResponse(HttpStatus.CONFLICT.value(),"duplicate phone number in database",model);
				}
				else
				{
					um.setPhone(model.getPhone());
					um.setUsername(model.getUsername());
					um.setName(model.getName());
					um.setEmail(model.getEmail());
					um.setPassword(bcryptEncoder.encode(model.getPassword()));
					um.setModifyBy(model.getModifyBy());
					um.setModifyDate(java.time.LocalDate.now());
					return new CustomSuccessResponse(HttpStatus.ACCEPTED.value(),"data saved",userrepo.save(um));
				}
			}
			else
				return new CustomSuccessResponse(HttpStatus.CONFLICT.value(),"You cannot go to this state",new UserModel());
		}
		else
			return new CustomSuccessResponse(HttpStatus.NOT_FOUND.value(),"Id does not exist or password value is null",new UserModel());
	}
	public Object delete(int id) {
		if(userrepo.existsById(id)==true)
		{
			UserModel data=userrepo.findById(id).orElse(new UserModel());
			userrepo.deleteById(id);
			return new  CustomSuccessResponse(HttpStatus.OK.value(),"delete successful",data);
		}
		return new CustomSuccessResponse(HttpStatus.NOT_FOUND.value(),"id not found", new UserModel());
	}
	public String logout(HttpSession session) 
	{
		session.invalidate();
		return "logout successfully";
	}
}
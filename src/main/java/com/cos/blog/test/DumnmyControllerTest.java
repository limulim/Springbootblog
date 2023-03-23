package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController
public class DumnmyControllerTest {

	@Autowired
	private UserRepository userRepository;
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
	try {
		userRepository.deleteById(id);
	}catch(Exception e){
		return "삭제에 실패하였습니다. 해당id는 없습니다.";
	}
	
		
		return "삭제되었습니다.id"+id;
	}
	
	
	
	@Transactional
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
		System.out.println("id :" + id);
		System.out.println("Password :" + requestUser.getPassword());
		System.out.println("email :" + requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하셨습니다.");
		});		
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		//@transactional을 사용하면 save함수를 호출 안해도됨 업데이트가된다.
		//userRepository.save(user);
		
		return user;
	}
	
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size=2, sort="id", direction = Sort.Direction.DESC)Pageable pageable){
	Page<User> paginUser =	userRepository.findAll(pageable);
	List<User> users = paginUser.getContent();
	return users;
	}
	
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
	User user =	userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
	@Override
	public IllegalArgumentException get() {
		return new IllegalArgumentException("해당 유저는 없습니다. id: "+id);
	}
	});
	return user;
	}
	
	@PostMapping("/dummy/join")
	public String join(User user) {
		System.out.println("id:"+user.getId());
		System.out.println("username :" + user.getUsername());
		System.out.println("password :" + user.getPassword());
		System.out.println("email :" + user.getEmail());
		System.out.println("role:" + user.getRole());
		System.out.println("createDate:" + user.getCreateDate());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";
	}
	
}

package com.anant.rest.services.restfulwebservices.user;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {
	
	private static List<User> users = new ArrayList<>();
	private static int usersCount=0;
	static {
		users.add(new User(++usersCount,"Anant",LocalDate.now().minusYears(22)));
		users.add(new User(++usersCount,"Mukul",LocalDate.now().minusYears(23)));
		users.add(new User(++usersCount,"Rajeev",LocalDate.now().minusYears(23)));
	}
	
	public List<User> findAll(){
		return users;
	}
	public User save(User user) {
		user.setId(++usersCount);
		users.add(user);
		return user;
	}
	public User findUser(int id){
		Predicate<? super User> predicate = user -> user.getId().equals(id);
		return users.stream().filter(predicate).findFirst().orElse(null);
	}
	
	public void deleteById(int id){
		Predicate<? super User> predicate = user -> user.getId().equals(id);
		users.removeIf(predicate);
	}

}

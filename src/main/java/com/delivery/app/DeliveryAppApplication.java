package com.delivery.app;

import com.delivery.app.models.Person;
import com.delivery.app.models.Role;
import com.delivery.app.models.User;
import com.delivery.app.repositories.PersonRepo;
import com.delivery.app.repositories.RoleRepo;
import com.delivery.app.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.UUID;
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class DeliveryAppApplication {
	@Autowired
	private RoleRepo roleRep;
	@Autowired
	private UserRepo userRepository;
	@Autowired
	private PersonRepo personRepo;
	@Autowired
	private PasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(DeliveryAppApplication.class, args);
	}
//	@PostConstruct
	public void init() {
		Role admin = new Role();
		Role client = new Role();
		Role delivery = new Role();
		admin.setName("ADMIN");
		admin.setDescription("admin");
		client.setName("CLIENT");
		client.setDescription("client");
		delivery.setName("DELIVERY");
		delivery.setDescription("delivery");
		admin = roleRep.save(admin);
		client = roleRep.save(client);
		delivery = roleRep.save(delivery);

		User superAdmin = User.builder()
				.username("yusuf0x")
				.email("yusuf@gmail.com")
				.passwordd(encoder.encode("yusuf0x"))
				.notificationToken(UUID.randomUUID().toString())
				.role(admin)
				.build();
		Person person = Person.builder()
				.firstName("yusuf")
				.lastName("mansouri")
				.phone("20203393993")
				.state(true)
				.image("")
				.created(new Date())
				.build();
		personRepo.save(person);
		superAdmin.setPerson(person);
		userRepository.save(superAdmin);
	}
}

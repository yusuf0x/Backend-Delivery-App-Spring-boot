package com.delivery.app.services;

import com.delivery.app.exceptions.UserNotFoundException;
import com.delivery.app.models.Address;
import com.delivery.app.models.Person;
import com.delivery.app.models.Role;
import com.delivery.app.models.User;
import com.delivery.app.payload.request.ChangePasswordRequest;
import com.delivery.app.payload.request.RegisterDeliveryRequest;
import com.delivery.app.payload.request.UserProfileEditRequest;
import com.delivery.app.repositories.AddressRepo;
import com.delivery.app.repositories.RoleRepo;
import com.delivery.app.repositories.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepo usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final PersonService personService;
    private final FileService fileService;
    private final AddressRepo addressRepo;
    private final RoleRepo roleRepo;

    public UserService(UserRepo usersRepository, PasswordEncoder passwordEncoder, PersonService personService, FileService fileService, AddressRepo addressRepo, RoleRepo roleRepo) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.personService = personService;
        this.fileService = fileService;
        this.addressRepo = addressRepo;
        this.roleRepo = roleRepo;
    }
    public User findByEmail(String email){
        return  usersRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User Not Found with email = "+email)
        );
    }
    public boolean existsByUsername(String username){
        return  usersRepository.existsByUsername(username);
    }
    public User findByUsername(String username){
        return  usersRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("User Not Found with username = "+username)
        );
    }
    public boolean existsByEmail(String email){
        return  usersRepository.existsByEmail(email);
    }
    public void save(User user){
        usersRepository.save(user);
    }
    public List<User> findAll(){
        return usersRepository.findAll();
    }
    public User findById(Long  id){
        return  usersRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User Not Found with id = "+id)
        );
    }

    public void editUserProfile(Long userId, UserProfileEditRequest editRequest) {
        User user = findById(userId);
        user.getPerson().setFirstName(editRequest.getFirstname());
        user.getPerson().setLastName(editRequest.getLastname());
        user.getPerson().setPhone(editRequest.getPhone());
        save(user);
    }
    public void changeProfileImage(Long userId, MultipartFile file) throws IOException {
        User user = findById(userId);
        String fileName = fileService.uploadImage("users/",file);
        user.getPerson().setImage(fileName);
        save(user);
    }
    public void changePassword(Long userId,ChangePasswordRequest changePasswordRequest) throws Exception {
        User user = findById(userId);
        String currentPassword = user.getPasswordd();
//        if (bcrypt.matches(changePasswordRequest.getCurrentPassword(), currentPassword)) {
//            String newPassword = bcrypt.hash(changePasswordRequest.getNewPassword(), salt);
//            user.setPasswordd(newPassword);
//            save(user);
//        } else {
//                throw new Exception("Passwords do not match");
//        }

    }
    public List<Address> getUserAddresses(Long userId){
        User user = findById(userId);
        return addressRepo.findByUser(user);
    }
    public  Address getOneAddress(Long userId){
        User user = findById(userId);
        return addressRepo.findFirstByUserOrderByIdDesc(user);
    }
    public  void deleteAddress(Long userId,Long addressId){
        User user = findById(userId);
        addressRepo.deleteByIdAndUser(addressId,user);
    }
    public void addAddress(Address request, Long userId) {
        User user = findById(userId);
        Address address = new Address();
        address.setStreet(request.getStreet());
        address.setReference(request.getReference());
        address.setLatitude(request.getLatitude());
        address.setLongitude(request.getLongitude());
        address.setUser(user);
        addressRepo.save(address);
    }

    public  void updateNotificationToken(Long userId,String token){
        User user = findById(userId);
        user.setNotificationToken(token);
        save(user);
    }
    public List<String> getAdminNotificationTokens(){
//        List<User> adminUsers = userRepository.findByRoleId(1);
//        List<String> tokens = new ArrayList<>();
//        for (User adminUser : adminUsers) {
//            tokens.add(adminUser.getNotificationToken());
//        }
//        return tokens;
            return null;
    }
    public void updateUserRoleToClient(Long userId) {
        User user = findById(userId);
//        user.setRole();
        save(user);
    }

    public void registerClient(RegisterDeliveryRequest request, MultipartFile file) throws IOException {
        String fileName = fileService.uploadImage("users/",file);
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .passwordd(passwordEncoder.encode(request.getPassword()))
                .notificationToken(UUID.randomUUID().toString())
                .role(roleRepo.findByName("CLIENT"))
                .build();
        Person person = Person.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .phone(request.getPhone())
                .state(true)
                .image(fileName)
                .created(new Date())
                .build();
        personService.savePerson(person);
        user.setPerson(person);
        save(user);
    }
    public void registerDelivery(RegisterDeliveryRequest request, MultipartFile file) throws IOException {
        String fileName = fileService.uploadImage("users/",file);

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .passwordd(passwordEncoder.encode(request.getPassword()))
                .notificationToken(UUID.randomUUID().toString())
                .role(roleRepo.findByName("DELIVERY"))
                .build();
        Person person = Person.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .phone(request.getPhone())
                .state(true)
                .image(fileName)
                .created(new Date())
                .build();
        personService.savePerson(person);
        user.setPerson(person);
        save(user);
    }

    public List<User> getAllDeliveries(){
        Role role = roleRepo.findByName("DELIVERY");
        List<User> users = usersRepository.findByRole(role);
        return users;
    }
    public List<User> getAllClients(){
        Role role = roleRepo.findByName("CLIENT");
        List<User> users = usersRepository.findByRole(role);
        return users;
    }

}

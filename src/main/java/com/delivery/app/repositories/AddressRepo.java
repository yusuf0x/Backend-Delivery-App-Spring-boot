package com.delivery.app.repositories;

import com.delivery.app.models.Address;
import com.delivery.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepo extends JpaRepository<Address,Long> {
    List<Address> findByUser(User user);
    void deleteByIdAndUser(Long addressId,User user);
    Address findFirstByUserOrderByIdDesc(User user);
}

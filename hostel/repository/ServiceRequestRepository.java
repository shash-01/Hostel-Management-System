package com.example.hostel.repository;

import com.example.hostel.model.ServiceRequest;
import com.example.hostel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {
    List<ServiceRequest> findByStudent(User student);
    List<ServiceRequest> findByCleaner(User cleaner);
}

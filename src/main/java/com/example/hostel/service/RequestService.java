package com.example.hostel.service;

import com.example.hostel.model.*;
import com.example.hostel.repository.ServiceRequestRepository;
import com.example.hostel.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestService {

    private final ServiceRequestRepository reqRepo;
    private final UserRepository userRepo;

    public RequestService(ServiceRequestRepository reqRepo, UserRepository userRepo) {
        this.reqRepo = reqRepo;
        this.userRepo = userRepo;
    }

    public ServiceRequest createRequest(ServiceRequest r) {
        return reqRepo.save(r);
    }

    public List<ServiceRequest> findByStudent(User student) {
        return reqRepo.findByStudent(student);
    }

    public List<ServiceRequest> findAll() {
        return reqRepo.findAll();
    }

    public Optional<ServiceRequest> findById(Long id) {
        return reqRepo.findById(id);
    }

    public ServiceRequest assignCleaner(Long requestId, Long cleanerId) {
        ServiceRequest r = reqRepo.findById(requestId).orElseThrow();
        User cleaner = userRepo.findById(cleanerId).orElseThrow();
        r.setCleaner(cleaner);
        r.setStatus(RequestStatus.ASSIGNED);
        return reqRepo.save(r);
    }

    public ServiceRequest updateStatus(Long requestId, RequestStatus status) {
        ServiceRequest r = reqRepo.findById(requestId).orElseThrow();
        r.setStatus(status);
        return reqRepo.save(r);
    }
}

package com.sims.ppob.service;

import com.sims.ppob.entity.Banners;
import com.sims.ppob.entity.Services;
import com.sims.ppob.entity.Users;
import com.sims.ppob.model.ServiceResponse;
import com.sims.ppob.repository.ServiceRepository;
import com.sims.ppob.utility.Model;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;

    private final Model model;

    @Autowired
    public ServiceServiceImpl(ServiceRepository serviceRepository, Model model) {
        this.serviceRepository = serviceRepository;
        this.model = model;
    }

    @Override
    @Transactional
    public List<ServiceResponse> getAll(Users user) {
        List<Services> services = serviceRepository.getAll();

        return services.stream()
                .map(model::toServiceResponse)
                .collect(Collectors.toList());
    }
}

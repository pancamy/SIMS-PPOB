package com.sims.ppob.service;

import com.sims.ppob.entity.Banners;
import com.sims.ppob.entity.Services;
import com.sims.ppob.entity.Users;
import com.sims.ppob.model.ServiceResponse;
import com.sims.ppob.model.ServiceSaveRequest;
import com.sims.ppob.repository.ServiceRepository;
import com.sims.ppob.utility.Model;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ServiceServiceImpl implements ServiceService {

    private final LocalDateTime newDate = LocalDateTime.now();

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

    @Override
    @Transactional
    public ServiceResponse save(Users user, ServiceSaveRequest request, BindingResult bindingResult) {
        ValidationService.validate(bindingResult);

        Services service = new Services();
        service.setId(UUID.randomUUID().toString());
        service.setServiceCode(request.getServiceCode().toUpperCase());
        service.setServiceName(request.getServiceName());
        service.setServiceTariff(request.getServiceTariff());
        service.setServiceIcon("/dummy.jpeg");
        service.setCreatedAt(newDate);
        service.setUpdatedAt(newDate);

        serviceRepository.save(service);

        return model.toServiceResponse(service);
    }
}

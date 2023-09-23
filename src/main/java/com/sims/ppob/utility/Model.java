package com.sims.ppob.utility;

import com.sims.ppob.constant.UrlConstant;
import com.sims.ppob.entity.*;
import com.sims.ppob.model.*;
import org.springframework.stereotype.Component;

@Component
public class Model {

    public UserResponse toUserResponse(Users users) {
        return UserResponse.builder()
                .email(users.getEmail())
                .firstName(users.getFirstName())
                .lastName(users.getLastName())
                .profileImage(UrlConstant.BASE_URL + users.getProfile())
                .build();
    }

    public UserLoginResponse toUserLoginResponse(String token) {
        return UserLoginResponse.builder()
                .token(token)
                .build();
    }

    public BannerResponse toBannerResponse(Banners banner) {
        return BannerResponse.builder()
                .bannerName(banner.getBannerName())
                .bannerImage(banner.getBannerImage())
                .description(banner.getDescription())
                .build();
    }

    public ServiceResponse toServiceResponse(Services service) {
        return ServiceResponse.builder()
                .serviceCode(service.getServiceCode())
                .serviceName(service.getServiceName())
                .serviceIcon(service.getServiceIcon())
                .serviceTariff(service.getServiceTariff())
                .build();
    }

    public BalanceResponse toBalanceResponse(Balances balance) {
        return BalanceResponse.builder()
                .balance(balance.getBalance())
                .build();
    }

    public TransactionHistoryResponse toTransactionHistoryResponse(TransactionHistories transactionHistory) {
        return TransactionHistoryResponse.builder()
//                .id(transactionHistory.getId())
                .invoiceNumber(transactionHistory.getInvoiceNumber())
                .transactionType(transactionHistory.getTransactionType())
                .description(transactionHistory.getDescription())
                .totalAmount(transactionHistory.getTotalAmount())
                .createdOn(transactionHistory.getCreatedAt())
//                .updatedAt(transactionHistory.getUpdatedAt())
                .build();
    }
}

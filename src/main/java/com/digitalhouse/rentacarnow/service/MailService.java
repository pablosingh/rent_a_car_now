package com.digitalhouse.rentacarnow.service;

import com.digitalhouse.rentacarnow.entity.User;

public interface MailService {
    void sendWelcome(User user);

    void sendPasswordReset(User user, String resetLink);
}

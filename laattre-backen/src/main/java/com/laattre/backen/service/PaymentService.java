package com.laattre.backen.service;

import com.laattre.backen.persistence.model.Payment;
import com.laattre.backen.persistence.model.UserPayment;

public interface PaymentService {
	Payment setByUserPayment(UserPayment userPayment, Payment payment);
}

package com.epam.esm.service;

import java.util.List;

public interface StressTestService {

    List<String> createTag(Long number);

    List<String> createGiftCertificate(Long number);

    List<String> createUser(Long number);

    List<String> createOrder(Long number);
}

package com.simple.crud.api.base.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
@RequiredArgsConstructor
public class BaseService {
    public void writeConsole(String text) {
        System.out.println("Write console method has been called. Your Text: " + text);
    }

    public String readFromApp() {
        return "This message created from SIMPLE CRUD API. Message created at: " + LocalDate.now().toString() + " . Time getting from LocalDate.now() .";
    }
}

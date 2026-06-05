package com.simple.crud.api.base.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BaseService {
    public void writeConsole(String text) {
        System.out.println("Write console method has been called. Your Text: " + text);
        return;
    }
}

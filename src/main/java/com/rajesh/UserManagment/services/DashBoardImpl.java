package com.rajesh.UserManagment.services;

import com.rajesh.UserManagment.Dtos.QuoteApiResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DashBoardImpl implements  DashService{

    private  String quoteApiUrl="https://dummyjson.com/quotes/random";
    @Override
    public QuoteApiResponseDTO getQuote() {
        RestTemplate restTemplate =new RestTemplate();
        QuoteApiResponseDTO body =
                  restTemplate.getForEntity(quoteApiUrl, QuoteApiResponseDTO.class)
                .getBody();
        return body;
    }
}

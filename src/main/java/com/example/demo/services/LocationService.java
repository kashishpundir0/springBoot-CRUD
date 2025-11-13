package com.example.demo.services;

import com.example.demo.entities.Employee;
import com.example.demo.repositorise.EmployeeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@RequiredArgsConstructor
@Service
public class LocationService {
    @Value("${map.key}")
    private String key;
    private final EmployeeRepository repo;
     RestTemplate restTemplate=new RestTemplate();

    public String getCity(String lat,String Lon) throws URISyntaxException, JsonProcessingException {
        URI uri=new URI(String.format("https://geocode.maps.co/reverse?lat=%s&lon=%s&api_key=%s",lat,Lon,key));
        log.info("uri :{}",uri);
        ResponseEntity<String> response=restTemplate.exchange(uri,HttpMethod.GET,null,String.class);
        ObjectMapper obj=new ObjectMapper();
        JsonNode node=obj.readTree(response.getBody());
        log.info("Json Object: {}",response.getBody());
        return node.get("address").get("city").asText();
    }
    public Employee setCity( String token, String lat,String Lon) throws URISyntaxException, JsonProcessingException {
        String city = getCity(lat, Lon);
        Employee exist = repo.findByToken(token).orElseThrow(()->new RuntimeException("Session expired"));
        exist.setCity(city);
        return repo.save(exist);
    }
}

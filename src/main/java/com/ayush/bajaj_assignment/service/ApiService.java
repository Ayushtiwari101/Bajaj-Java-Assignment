package com.ayush.bajaj_assignment.service;

import com.ayush.bajaj_assignment.model.RequestBody;
import com.ayush.bajaj_assignment.model.WebhookResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ApiService {

    public void executeFlow() {

        RestTemplate restTemplate = new RestTemplate();

        String url = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

        // Prepare request body
        RequestBody request = new RequestBody();
        request.setName("Ayush Sunil Tiwari");
        request.setRegNo("ADT23SOCB0293");
        request.setEmail("ayush.s.tiwari11@gmail.com");

        // Call API
        WebhookResponse response =
                restTemplate.postForObject(url, request, WebhookResponse.class);

        if (response == null) {
            System.out.println("Failed to get response from API");
            return;
        }

        System.out.println("Webhook URL: " + response.getWebhook());
        System.out.println("Access Token: " + response.getAccessToken());

        // Solve SQL
        String finalQuery = solveSqlProblem();


        sendResult(response.getWebhook(), response.getAccessToken(), finalQuery);
    }


    private String solveSqlProblem() {
        return "SELECT p.AMOUNT AS SALARY, CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME) AS NAME, TIMESTAMPDIFF(YEAR, e.DOB, CURDATE()) AS AGE, d.DEPARTMENT_NAME FROM PAYMENTS p JOIN EMPLOYEE e ON p.EMP_ID = e.EMP_ID JOIN DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID WHERE DAY(p.PAYMENT_TIME) != 1 ORDER BY p.AMOUNT DESC LIMIT 1;";
    }

    private void sendResult(String webhookUrl, String token, String finalQuery) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = new HashMap<>();
        body.put("finalQuery", finalQuery);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    webhookUrl,
                    entity,
                    String.class
            );

            System.out.println("Submission Response: " + response.getBody());

        } catch (Exception e) {
            System.out.println("Error while sending result: " + e.getMessage());
        }
    }
}
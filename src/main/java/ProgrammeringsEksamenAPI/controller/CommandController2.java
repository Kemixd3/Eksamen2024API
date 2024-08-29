package ProgrammeringsEksamenAPI.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/api")
public class CommandController2 {

    private final String websocketServerUrl = "http://localhost:8082/send-command"; // Adjust URL if needed

    // Inject your RestTemplate bean
    private final RestTemplate restTemplate;

    public CommandController2(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/send-command")
    public ResponseEntity<String> sendCommand(@RequestParam String userId, @RequestBody String command) {
        try {
            // Construct URL for the WebSocket server endpoint
            String url = String.format("%s?userId=%s&command=%s", websocketServerUrl, userId, command);

            // Send the command via HTTP POST
            restTemplate.postForObject(url, null, String.class);

            return ResponseEntity.ok("Command sent successfully");

        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body("Error sending command: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
        }
    }
}


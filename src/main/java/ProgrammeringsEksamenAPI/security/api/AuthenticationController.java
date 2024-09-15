package ProgrammeringsEksamenAPI.security.api;

import ProgrammeringsEksamenAPI.security.dto.LoginRequest;
import ProgrammeringsEksamenAPI.security.dto.LoginResponse;
import ProgrammeringsEksamenAPI.security.entity.UserWithRoles;
import ProgrammeringsEksamenAPI.security.service.UserDetailsServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.PostConstruct;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import util.PcTimeReceiverSocket;
import ProgrammeringsEksamenAPI.security.entity.Role;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.joining;

@RestController
@RequestMapping("/api/auth/")
public class AuthenticationController {

  @Value("${app.token-issuer}")
  private String tokenIssuer;

  @Value("${app.token-expiration}")
  private long tokenExpiration;

  //@Value("${websocket.server.url}")
  //private String webSocketServerUrl; // Externalize WebSocket URL to configuration

  //private WebSocketClient webSocketClient;
  private AuthenticationManager authenticationManager;
  private JwtEncoder encoder;

  public AuthenticationController(AuthenticationConfiguration authenticationConfiguration, JwtEncoder encoder) throws Exception {
    this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
    this.encoder = encoder;
  }

/*  @PostConstruct
  public void init() {
    try {
      URI uri = new URI(webSocketServerUrl); // Use externalized WebSocket URL
      webSocketClient = new WebSocketClient(uri) {
        @Override
        public void onOpen(ServerHandshake handshakedata) {
          System.out.println("WebSocket connection opened");
        }

        @Override
        public void onMessage(String message) {
          System.out.println("Received message: " + message);
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
          System.out.println("WebSocket connection closed");
          // Optionally implement reconnection logic
        }

        @Override
        public void onError(Exception ex) {
          System.err.println("WebSocket error: " + ex.getMessage());
        }
      };
      webSocketClient.connectBlocking(); // This blocks until the connection is established
    } catch (InterruptedException | URISyntaxException e) {
      e.printStackTrace();
      throw new RuntimeException("Failed to initialize WebSocket client", e);
    }
  }*/

  @PostMapping("login")
  @Operation(summary = "Login", description = "Use this to login and get a token")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
    try {
      UsernamePasswordAuthenticationToken uat = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
      Authentication authentication = authenticationManager.authenticate(uat);

      UserWithRoles user = (UserWithRoles) authentication.getPrincipal();
      Instant now = Instant.now();
      String scope = authentication.getAuthorities().stream()
              .map(GrantedAuthority::getAuthority)
              .collect(joining(" "));

      JwtClaimsSet claims = JwtClaimsSet.builder()
              .issuer(tokenIssuer)
              .issuedAt(now)
              .expiresAt(now.plusSeconds(tokenExpiration))
              .subject(user.getUsername())
              .claim("roles", scope)
              .build();
      JwsHeader jwsHeader = JwsHeader.with(() -> "HS256").build();
      String token = encoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
      List<String> roles = user.getRoles().stream().map(Role::getRoleName).toList();

      // Start receiving data by connecting to the WebSocket server
      new Thread(() -> {
        try {
          //PcTimeReceiverSocket.startClient(token); // Pass the JWT token to authenticate the WebSocket connection
        } catch (Exception e) {
          e.printStackTrace();
        }
      }).start();

      return ResponseEntity.ok()
              .body(new LoginResponse(user.getUsername(), token, roles));

    } catch (BadCredentialsException | AuthenticationServiceException e) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, UserDetailsServiceImp.WRONG_USERNAME_OR_PASSWORD);
    }
  }

/*

  @PostMapping("send-usercommand")
  @Operation(summary = "Send Command", description = "Send a command to the connected WinForms client.")
  public ResponseEntity<String> sendCommand(@RequestBody Map<String, String> requestBody, Authentication authentication) {
    String command = requestBody.get("command");
    if (command == null || command.isEmpty()) {
      return ResponseEntity.badRequest().body("Command cannot be empty");
    }

    try {
      sendCommandToWebSocketServer(command);
      return ResponseEntity.ok("Command sent successfully");
    } catch (Exception e) {
      e.printStackTrace(); // Log the exception for debugging
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("Failed to send command: " + e.getMessage());
    }
  }
*/

/*  private void sendCommandToWebSocketServer(String command) throws IOException {
    if (webSocketClient != null && webSocketClient.isOpen()) {
      webSocketClient.send(command);
    } else {
      throw new IOException("WebSocket connection is not open");
    }
  }*/
}

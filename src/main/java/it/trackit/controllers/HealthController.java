package it.trackit.controllers;

import it.trackit.dtos.HealthResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.time.Instant;

@RestController
@RequestMapping("/api")
public class HealthController {

  private final long startTime = System.currentTimeMillis();

  @GetMapping("/health")
  public HealthResponse health() throws Exception {

    HealthResponse response = new HealthResponse();
    response.setStatus("UP");
    response.setApplication("my-spring-app");
    response.setVersion("1.0.0");
    response.setTimestamp(Instant.now().toString());
    response.setHostname(InetAddress.getLocalHost().getHostName());
    response.setUptimeSeconds(
      (System.currentTimeMillis() - startTime) / 1000
    );

    return response;
  }
}

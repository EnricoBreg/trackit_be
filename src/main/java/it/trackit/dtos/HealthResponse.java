package it.trackit.dtos;

import lombok.Data;

@Data
public class HealthResponse {
  private String status;
  private String application;
  private String version;
  private String timestamp;
  private String hostname;
  private long uptimeSeconds;
}

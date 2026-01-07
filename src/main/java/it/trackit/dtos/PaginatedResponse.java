package it.trackit.dtos;

import lombok.Data;

import java.util.List;

@Data
public class PaginatedResponse<T> {
  private List<T> results;
  private int currentPage;
  private int totalPages;
  private long totalItems;
  private int pageSize;
  private boolean hasNext;
  private boolean hasPrevious;
}

package it.trackit.commons.utils;

import it.trackit.dtos.PaginatedResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public class DomainUtils {

  public static <T, DTO> PaginatedResponse<DTO> buildPaginatedResponse(Page<T> page, List<DTO> pageContent) {
    PaginatedResponse<DTO> response = new PaginatedResponse<>();
    response.setResults(pageContent);
    response.setCurrentPage(page.getNumber());
    response.setTotalPages(page.getTotalPages());
    response.setTotalItems(page.getTotalElements());
    response.setPageSize(page.getSize());
    response.setHasNext(page.hasNext());
    response.setHasPrevious(page.hasPrevious());
    return response;
  }
}

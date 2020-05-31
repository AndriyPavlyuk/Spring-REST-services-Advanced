package it.discovery.model.pagination;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationCriteria {

    private int page;

    private int size;
}

package com.example.bhcbbackend.api.rest.parameters;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

@ParameterObject
@Data
public class PagingRequestParameter implements Serializable
{
    @Min(0)
    @Parameter(description = "Page to retrieve", required = true, example = "0")
    private int page = 0;

    @Min(1)
    @Parameter(description = "Size of page", required = true, example = "10")
    private int size = 10;

    public Pageable asPageable()
    {
        return PageRequest.of(this.getPage(), this.getSize(), Sort.unsorted());
    }
}

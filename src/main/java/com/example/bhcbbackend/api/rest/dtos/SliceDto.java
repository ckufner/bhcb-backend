package com.example.bhcbbackend.api.rest.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

@Data
public abstract class SliceDto<I extends Serializable> implements Serializable
{
    @Schema(description = "number of slice")
    int number;

    @Schema(description = "size of slice")
    int size;

    @Schema(description = "number of elements in slice")
    int numberOfElements;

    @Schema(description = "flag indicating whether slice has content")
    boolean content;

    @Schema(description = "flag indicating whether slice is first slice")
    boolean first;

    @Schema(description = "flag indicating whether slice is last slice")
    boolean last;

    @Schema(description = "flag indicating whether slice has next slice")
    boolean next;

    @Schema(description = "flag indicating whether slice has previous slice")
    boolean previous;

    @Schema(description = "items")
    private ArrayList<I> items = new ArrayList<>();
}

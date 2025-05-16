package com.example.bhcbbackend.api.rest.mapper;

import com.example.bhcbbackend.api.rest.dtos.SliceDto;
import lombok.NonNull;
import org.springframework.data.domain.Slice;

public interface SliceDtoUtil
{
    static void map(
            @NonNull final Slice<?> sliceModel,
            @NonNull final SliceDto<?> sliceDto
    )
    {
        sliceDto.setNumber(sliceModel.getNumber());
        sliceDto.setSize(sliceModel.getSize());
        sliceDto.setNumberOfElements(sliceModel.getNumberOfElements());
        sliceDto.setContent(sliceModel.hasContent());

        sliceDto.setFirst(sliceModel.isFirst());
        sliceDto.setLast(sliceModel.isLast());
        sliceDto.setNext(sliceModel.hasNext());
        sliceDto.setPrevious(sliceModel.hasPrevious());
    }
}

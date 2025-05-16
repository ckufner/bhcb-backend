package com.example.bhcbbackend.services.seeding;

import com.opencsv.bean.CsvBindAndSplitByPosition;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

import java.util.List;

@Data
public class CsvLine
{
    @CsvBindByPosition(position = 0)
    private String email;

    @CsvBindByPosition(position = 1)
    private String name;

    @CsvBindByPosition(position = 2)
    private String team;

    @CsvBindByPosition(position = 3)
    private String description;

    @CsvBindAndSplitByPosition(position = 4, splitOn = ",", elementType = String.class)
    private List<String> skills;

    @CsvBindAndSplitByPosition(position = 5, splitOn = ",", elementType = String.class)
    private List<String> social;
}

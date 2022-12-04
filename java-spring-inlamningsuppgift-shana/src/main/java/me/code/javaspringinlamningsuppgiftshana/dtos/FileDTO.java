package me.code.javaspringinlamningsuppgiftshana.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FileDTO {

    private int id;
    private String name;
    private String type;
    private Long size;
    private String user;
}

package me.code.javaspringinlamningsuppgiftshana.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/** This class used for sending response to the user,
 * including get request for all-files, upload for new file ...
 *
 */
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

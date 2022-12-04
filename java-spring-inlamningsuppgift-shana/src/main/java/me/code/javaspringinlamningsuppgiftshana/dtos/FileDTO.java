package me.code.javaspringinlamningsuppgiftshana.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/** This class is used for sending response to the user
 *  including get request for all-files, upload for new file ...,
 *  instead of sending File object,
 *  we could use FileDTO to protect the information of the object.
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

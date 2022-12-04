package me.code.javaspringinlamningsuppgiftshana.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * File class connects to the database
 * it includes all columns we need for the file object
 */

@Getter
@Setter
@Entity(name = "files")
@AllArgsConstructor
@NoArgsConstructor
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer fileId;
    private String name;
    private String type;
    private Long size;
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public File(String name, String type, Long size, byte[] data, User user){
        this.name = name;
        this.type = type;
        this.size = size;
        this.data = data;
        this.user = user;


    }

}

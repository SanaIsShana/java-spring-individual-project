package me.code.javaspringinlamningsuppgiftshana.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


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

    @Lob
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public File(String name, String type, byte[] data, User user){
        this.name = name;
        this.type = type;
        this.data = data;
        this.user = user;

    }

}

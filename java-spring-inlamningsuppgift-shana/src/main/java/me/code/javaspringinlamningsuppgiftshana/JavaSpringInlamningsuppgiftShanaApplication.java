package me.code.javaspringinlamningsuppgiftshana;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaSpringInlamningsuppgiftShanaApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaSpringInlamningsuppgiftShanaApplication.class, args);
    }

    /* I used MultipartFile to upload the file
     *  MultipartFile object contains the binary content of the file
     *  and some metadata, such as the name of the file, extension, size, and some other information.
     *  in this program we store the file in a database, but using a database to store files is not always the best.
     *  Especially when the files are big. There are other solutions to store these large files, for example, store
     *  the data on the hard drive. Also, a "CDN (Content Delivery Network)" * if the files are not required to store
     *  in a database.
     *  By using CDN we could duplicate files in several servers
     *  all around the world to let the user download
     *  them from the nearest server which is also separated from the database, and we
     *  can don't need any resources from the backend server,
     *  it can cause trouble and add a heavy weight to the backend,
     *  if we download the files through the backend.
     *  However, we have learned to use and save documents in the database, and MultipartFile object
     *  was the only suitable way I found online to store files in a spring-boot application.
     *  The maximum size of a single file is set in the application.properties file which is 1MB,
     *  if the files are larger than the limit, the program will show an error message.
     *  It is easy to use MultipartFile to upload files.
     *  It also has built-in methods to manage files.
     *
     * */


    /* CDN solution is referenced from internet resource in
    Upload files with Spring Boot and MultipartFile by Sergio Lema */
}

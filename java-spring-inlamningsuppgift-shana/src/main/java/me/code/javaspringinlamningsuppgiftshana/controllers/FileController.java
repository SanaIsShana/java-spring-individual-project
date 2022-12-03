package me.code.javaspringinlamningsuppgiftshana.controllers;

import me.code.javaspringinlamningsuppgiftshana.data.File;
import me.code.javaspringinlamningsuppgiftshana.data.User;
import me.code.javaspringinlamningsuppgiftshana.dtos.FileDTO;
import me.code.javaspringinlamningsuppgiftshana.services.FileService;
import me.code.javaspringinlamningsuppgiftshana.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;
    private final UserService userService;

    @Autowired
    public FileController(FileService fileService, UserService userService){
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/upload")
    public ResponseEntity<FileDTO> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestHeader String userId)
            throws IOException{
        var user = userService.getById(userId).get();
        var fileDB = fileService.uploadFile(file, user);

        var dto = new FileDTO(
                fileDB.getFileId(),
                fileDB.getName(),
                user.getUsername()
        );
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public File getFile(
            @PathVariable String id
    ){
        return fileService.getFileById(id);
    }

    @GetMapping("/all-files")
    public Collection<File> getAllFiles(){
        return fileService.getAllFiles();
    }


}

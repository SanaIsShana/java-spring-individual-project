package me.code.javaspringinlamningsuppgiftshana.controllers;

import me.code.javaspringinlamningsuppgiftshana.data.File;
import me.code.javaspringinlamningsuppgiftshana.dtos.FileDTO;
import me.code.javaspringinlamningsuppgiftshana.exceptions.FileAlreadyExistsException;
import me.code.javaspringinlamningsuppgiftshana.exceptions.FileNotFoundException;
import me.code.javaspringinlamningsuppgiftshana.services.FileService;
import me.code.javaspringinlamningsuppgiftshana.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public FileDTO uploadFile(
            @RequestParam("file") MultipartFile file,
            Authentication authentication)
            throws IOException, FileAlreadyExistsException
    {
        var user = userService.getByUsername(authentication.getName()).get();
        var fileDB = fileService.uploadFile(file, user);

        return new FileDTO(
                fileDB.getFileId(),
                fileDB.getName(),
                user.getUsername()
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable int id, Authentication authentication) throws FileNotFoundException {
        Optional<File> fileOptional = fileService.getFileById(id, authentication);

        if (fileOptional.isEmpty()) {
            return ResponseEntity.notFound()
                    .build();
        }

        File file = fileOptional.get();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .contentType(MediaType.valueOf(file.getType()))
                .body(file.getData());
    }

    @GetMapping("/all-files")
    public List<FileDTO> getAllFiles(
            Authentication authentication
    ){
            return fileService.getAllFiles(authentication)
                    .stream()
                    .map(file -> {
                        return new FileDTO(
                                file.getFileId(),
                                file.getName(),
                                file.getUser().getUsername());
                    }).collect(Collectors.toList());

    }

    @DeleteMapping("/{id}")
    public FileDTO deleteFile(@PathVariable int id, Authentication authentication)
    throws FileNotFoundException{
        return fileService.deleteFile(id, authentication);
    }

}

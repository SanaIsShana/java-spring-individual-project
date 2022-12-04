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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * FileController class includes all request handling methods for files
 */
@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;
    private final UserService userService;

    @Autowired
    public FileController(FileService fileService, UserService userService){
        this.fileService = fileService;
        this.userService = userService;
    }

    /* Upload a file if the file doesn't exist in the database
     * and returns FileDTO as response which has fileId, name, size, content-type and username.
     */
    @PostMapping("/upload")
    public ResponseEntity<FileDTO> uploadFile(
            @RequestParam("file") MultipartFile file,
            Authentication authentication)
            throws IOException, FileAlreadyExistsException
    {
        var user = userService.getByUsername(authentication.getName()).get();
        var fileDB = fileService.uploadFile(file, user);

        var dto = new FileDTO(
                fileDB.getFileId(),
                fileDB.getName(),
                fileDB.getType(),
                fileDB.getSize(),
                user.getUsername());

        return ResponseEntity.ok(dto);
    }

    /* Endpoint to download a file, it also checks if the file owner (user) is same as the
     * authenticated user, if not throws a FileNotFoundException.
     */
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

    /* Request method to get all files,
     * it only gets the files that are owned by the authenticated user.
     */
    @GetMapping("/all-files")
    public ResponseEntity<List<FileDTO>>getAllFiles(
            Authentication authentication
    ){

        return ResponseEntity.ok(fileService.getAllFiles(authentication)
                    .stream()
                    .map(file -> new FileDTO(
                            file.getFileId(),
                            file.getName(),
                            file.getType(),
                            file.getSize(),
                            file.getUser().getUsername())).collect(Collectors.toList()));

    }

    /* Request method to delete a file based on the id,
    * if a file is not found throws a FileNotFoundException,
    * also only the owner has access to delete the file */

    @DeleteMapping("/{id}")
    public FileDTO deleteFile(@PathVariable int id, Authentication authentication)
    throws FileNotFoundException{
        return fileService.deleteFile(id, authentication);
    }

}

package me.code.javaspringinlamningsuppgiftshana.services;

import me.code.javaspringinlamningsuppgiftshana.data.File;
import me.code.javaspringinlamningsuppgiftshana.data.User;
import me.code.javaspringinlamningsuppgiftshana.dtos.FileDTO;
import me.code.javaspringinlamningsuppgiftshana.exceptions.FileAlreadyExistsException;
import me.code.javaspringinlamningsuppgiftshana.exceptions.FileNotFoundException;
import me.code.javaspringinlamningsuppgiftshana.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * FileService includes the business functionalities for file object,
 * and we use FileService to upload, get, download and delete the files.
 */
@Service
public class FileService {
    private final FileRepository fileRepository;

    @Autowired
    public FileService(FileRepository fileRepository){
        this.fileRepository = fileRepository;
    }

    public File uploadFile(MultipartFile file, User user) throws FileAlreadyExistsException, IOException {

        String fileName = file.getOriginalFilename();
        var existing = fileRepository.findByName(fileName);

        /* Check if both the file is stored in the same user's account,
         *  throws an exception if the user already has the file.
         */

        if (existing.isPresent()){
            var fileOwner = existing.get().getUser().getUsername();
            if (Objects.equals(fileOwner, user.getUsername())){
            throw new FileAlreadyExistsException();}
        }

        File fileDB = new File(
                fileName,
                file.getContentType(),
                file.getSize(),
                file.getBytes(),
                user);

        return fileRepository.save(fileDB);
    }

    public Optional<File> getFileById(int id, Authentication authentication) throws FileNotFoundException{
        Optional<File> fileOptional = fileRepository.findById(id);

        if (fileOptional.isEmpty()){
            throw new FileNotFoundException();
        }

        /* Check if the authenticated user's name is same as the file's owner,
         * so that the user only have access to his/her files.
         */
        var username = fileOptional.get().getUser().getUsername();
        if (!Objects.equals(username, authentication.getName())){
            throw new FileNotFoundException();
        }
        return fileOptional;
    }

    public List<File> getAllFiles(Authentication authentication){

        /* Also uses authentication user to restrict the files. */
        return fileRepository.findAll()
                .stream()
                .filter(file -> Objects.equals(file.getUser().getUsername(), authentication.getName()))
                .collect(Collectors.toList());
    }

    public FileDTO deleteFile(int id, Authentication authentication) throws FileNotFoundException{
        Optional<File> fileOptional = fileRepository.findById(id);

        /*
         * Here also used authentication user to restrict to delete the file
         * that belong the user.
         */
        if (fileOptional.isEmpty()){
            throw new FileNotFoundException();
        }
        var file = fileOptional.get();

        if (!Objects.equals(authentication.getName(), file.getUser().getUsername())){
            throw new FileNotFoundException();
        }

        fileRepository.delete(file);

        /* Returns a FileDTO object when the file is deleted. */
        return new FileDTO(
                file.getFileId(),
                file.getName(),
                file.getType(),
                file.getSize(),
                file.getUser().getUsername()
        );
    }
}

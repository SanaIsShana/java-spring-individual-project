package me.code.javaspringinlamningsuppgiftshana.services;

import me.code.javaspringinlamningsuppgiftshana.data.File;
import me.code.javaspringinlamningsuppgiftshana.data.User;
import me.code.javaspringinlamningsuppgiftshana.dtos.FileDTO;
import me.code.javaspringinlamningsuppgiftshana.exceptions.FileAlreadyExistsException;
import me.code.javaspringinlamningsuppgiftshana.exceptions.FileNotFoundException;
import me.code.javaspringinlamningsuppgiftshana.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
        if (existing.isPresent()){
            throw new FileAlreadyExistsException();
        }
        File fileDB = new File(
                fileName,
                file.getContentType(),
                file.getBytes(),
                user);
        return fileRepository.save(fileDB);
    }

    public Optional<File> getFileById(int id) throws FileNotFoundException{
        Optional<File> fileOptional = fileRepository.findById(id);

        if (fileOptional.isEmpty()){
            throw new FileNotFoundException();
        }
        return fileOptional;
    }

    public List<File> getAllFiles(){
        return fileRepository.findAll();
    }

    public FileDTO deleteFile(int id) throws FileNotFoundException{
        Optional<File> fileOptional = fileRepository.findById(id);

        if (fileOptional.isEmpty()){
            throw new FileNotFoundException();
        }
        var file = fileOptional.get();
        fileRepository.delete(file);

        var dto = new FileDTO(
                file.getFileId(),
                file.getName(),
                file.getUser().getUsername()
        );

        return dto;
    }
}

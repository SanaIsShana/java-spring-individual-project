package me.code.javaspringinlamningsuppgiftshana.services;

import lombok.extern.slf4j.Slf4j;
import me.code.javaspringinlamningsuppgiftshana.data.File;
import me.code.javaspringinlamningsuppgiftshana.data.User;
import me.code.javaspringinlamningsuppgiftshana.dtos.FileDTO;
import me.code.javaspringinlamningsuppgiftshana.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

@Service
@Slf4j
public class FileService {
    private final FileRepository fileRepository;

    @Autowired
    public FileService(FileRepository fileRepository){
        this.fileRepository = fileRepository;
    }

    public File uploadFile(MultipartFile file, User user) throws IOException {
        String fileName = file.getOriginalFilename();
        var existing = fileRepository.findByName(fileName);
        if (existing.isPresent()){
            log.info("Failed to upload file since name '" + fileName + "' already exists!");
        }
        File fileDB = new File(
                fileName,
                file.getContentType(),
                file.getBytes(),
                user);
        log.info("Successfully uploaded file!");
        return fileRepository.save(fileDB);
    }

    public File getFileById(String id){
        Optional<File> fileOptional = fileRepository.findById(id);
        return fileOptional.orElse(null);
    }

    public Collection<File> getAllFiles(){
        return fileRepository.findAll();
    }
}

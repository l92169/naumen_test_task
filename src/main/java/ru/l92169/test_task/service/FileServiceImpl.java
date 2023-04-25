package ru.l92169.test_task.service;

import org.springframework.stereotype.Service;
import ru.l92169.test_task.entity.User;
import ru.l92169.test_task.repository.FileRepository;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;

    FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public List<User> readFile() {
        return fileRepository.fileReader();
    }
}
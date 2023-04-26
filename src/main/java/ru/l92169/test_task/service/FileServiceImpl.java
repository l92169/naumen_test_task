package ru.l92169.test_task.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.l92169.test_task.entity.User;
import ru.l92169.test_task.repository.FileRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;

    public List<User> readFile() {
        return fileRepository.fileReader();
    }
}
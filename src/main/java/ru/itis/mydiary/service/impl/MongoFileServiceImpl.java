package ru.itis.mydiary.service.impl;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import ru.itis.mydiary.repositories.MongoFileRepository;
import ru.itis.mydiary.service.FileService;

import java.io.*;
import java.util.UUID;

public class MongoFileServiceImpl implements FileService {
    private static MongoFileRepository mongoImageRepository;

//    private UUID defaultImageId = "kavasaki.jpg";
    private UUID defaultImageId = UUID.fromString("76fb957b-5a27-48dd-8613-f40711ce5f55");

    public MongoFileServiceImpl(MongoFileRepository mongoImageRepository) {
        this.mongoImageRepository = mongoImageRepository;
    }

    @Override
    public UUID uploadFile(Part part) throws IOException {
        return mongoImageRepository.save(part.getInputStream());
    }

    @Override
    public void downloadFile(UUID id, HttpServletResponse response) throws FileNotFoundException {
        response.setContentType("image/jpeg");
        try (OutputStream outputStream = response.getOutputStream()) {
            try {
                byte[] data = mongoImageRepository.get(id);
                outputStream.write(data);
            } catch (FileNotFoundException e) {
                byte[] data = mongoImageRepository.get(defaultImageId);
                outputStream.write(data);
            }
        } catch (IOException e) {
            throw new FileNotFoundException("File not found error [FileServiceMongoImpl.downloadFile()]");
        }
    }

    @Override
    public void downloadFile(String id, HttpServletResponse response) throws FileNotFoundException {
        try {
            downloadFile(UUID.fromString(id), response);
        } catch (Exception e) {
            downloadFile(defaultImageId, response);
        }
    }
}

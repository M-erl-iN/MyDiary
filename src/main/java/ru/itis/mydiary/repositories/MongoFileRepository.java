package ru.itis.mydiary.repositories;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public interface MongoFileRepository {
    UUID save(InputStream inputStream) throws IOException;
    byte[] get(UUID id);
}

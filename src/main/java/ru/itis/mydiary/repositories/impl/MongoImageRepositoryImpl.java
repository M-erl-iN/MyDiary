package ru.itis.mydiary.repositories.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.Binary;
import ru.itis.mydiary.exceptions.FileNotFoundException;
import ru.itis.mydiary.repositories.MongoFileRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class MongoImageRepositoryImpl implements MongoFileRepository {
    private MongoCollection collection;
    private long maxSize = 1024 * 1024 * 20; //20mb

    public MongoImageRepositoryImpl(MongoDatabase imageDB, String title) {
        collection = imageDB.getCollection(title);
    }

    @Override
    public UUID save(InputStream inputStream) throws IOException {
        UUID id = UUID.randomUUID();
        byte[] bytes = inputStream.readAllBytes();
        if (bytes.length > maxSize) {throw new IOException("Слишком большой файл");}
        Binary data = new Binary(bytes);
        Document doc = new Document("file", data).append("id", id.toString());
        collection.insertOne(doc);
        return id;
    }

    @Override
    public byte[] get(UUID id) {
        Document selector = new Document("id", id.toString());
        Document result = (Document) collection.find(selector).first();
        if (result != null) {return result.get("file", Binary.class).getData();}
        throw new FileNotFoundException();
    }
}

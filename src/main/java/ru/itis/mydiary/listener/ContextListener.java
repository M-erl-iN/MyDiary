package ru.itis.mydiary.listener;

import ch.qos.logback.core.encoder.JsonEscapeUtil;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.itis.mydiary.config.DataSourceConfiguration;
import ru.itis.mydiary.mapper.*;
import ru.itis.mydiary.repositories.*;
import ru.itis.mydiary.repositories.impl.*;
import ru.itis.mydiary.service.FileService;
import ru.itis.mydiary.service.NoteService;
import ru.itis.mydiary.service.impl.MongoFileServiceImpl;
import ru.itis.mydiary.service.impl.NoteServiceImpl;
import ru.itis.mydiary.service.impl.NotebookServiceImpl;
import ru.itis.mydiary.service.impl.UserServiceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("application.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        DataSourceConfiguration configuration =
                new DataSourceConfiguration(properties);

        MongoClient mongoClient = configuration.mongoClient();
        MongoDatabase mongoDatabase = mongoClient.getDatabase("images");
        MongoFileRepository mongoFileRepository = new MongoImageRepositoryImpl(mongoDatabase, "images");
        FileService fileService = new MongoFileServiceImpl(mongoFileRepository);

        UserRowMapper userRowMapper = new UserRowMapper();
        NotebookRowMapper notebookRowMapper = new NotebookRowMapper();
        RoleRowMapper roleRowMapper = new RoleRowMapper();
        NoteRowMapper noteRowMapper = new NoteRowMapper();
        InviteRowMapper inviteRowMapper = new InviteRowMapper();

        UserRepository userRepository =
                new UserRepositoryImpl(configuration.hikariDataSource(), userRowMapper, notebookRowMapper);
        UserServiceImpl userService = new UserServiceImpl(userRepository, userRowMapper);

        NotebookRepository notebookRepository =
                new NotebookRepositoryImpl(configuration.hikariDataSource(), notebookRowMapper, userRowMapper, noteRowMapper);
        NotebookServiceImpl notebookService = new NotebookServiceImpl(notebookRepository, notebookRowMapper);

        RoleInteractionRowMapper roleInteractionRowMapper = new RoleInteractionRowMapper();
        RoleInteractionRepository roleInteractionRepository =
                new RoleInteractionRepositoryImpl(
                        configuration.hikariDataSource(),
                        roleRowMapper,
                        roleInteractionRowMapper,
                        userRepository,
                        notebookRepository
                        );

        NoteRepository noteRepository = new NoteRepositoryImpl(configuration.hikariDataSource(), noteRowMapper);
        NoteService noteService = new NoteServiceImpl(noteRepository, noteRowMapper);

        InviteRepository inviteRepository = new InviteRepositoryImpl(configuration.hikariDataSource(), inviteRowMapper);

        ServletContext servletContext = sce.getServletContext();

        servletContext.setAttribute("userRowMapper", userRowMapper);
        servletContext.setAttribute("userRepository", userRepository);
        servletContext.setAttribute("userService", userService);

        servletContext.setAttribute("notebookRowMapper", notebookRowMapper);
        servletContext.setAttribute("notebookRepository", notebookRepository);
        servletContext.setAttribute("notebookService", notebookService);

        servletContext.setAttribute("roleRowMapper", roleRowMapper);
        servletContext.setAttribute("roleInteractionRowMapper", roleInteractionRowMapper);
        servletContext.setAttribute("roleInteractionRepository", roleInteractionRepository);

        servletContext.setAttribute("noteRowMapper", noteRowMapper);
        servletContext.setAttribute("noteRepository", noteRepository);
        servletContext.setAttribute("noteService", noteService);

        servletContext.setAttribute("inviteRowMapper", inviteRowMapper);
        servletContext.setAttribute("inviteRepository", inviteRepository);

        servletContext.setAttribute("mongoClient", mongoClient);
        servletContext.setAttribute("mongoDatabase", mongoDatabase);
        servletContext.setAttribute("mongoFileRepository", mongoFileRepository);
        servletContext.setAttribute("fileService", fileService);

        servletContext.setAttribute("properties", properties);
    }
}

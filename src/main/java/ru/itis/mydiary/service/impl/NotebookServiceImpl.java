package ru.itis.mydiary.service.impl;

import lombok.AllArgsConstructor;
import ru.itis.mydiary.dto.CreateNotebookDto;
import ru.itis.mydiary.dto.CreateNotebookResponse;
import ru.itis.mydiary.entity.Notebook;
import ru.itis.mydiary.mapper.NotebookRowMapper;
import ru.itis.mydiary.repositories.NotebookRepository;
import ru.itis.mydiary.service.NotebookService;

import java.util.Optional;

@AllArgsConstructor
public class NotebookServiceImpl implements NotebookService {
    private final NotebookRepository notebookRepository;
    private final NotebookRowMapper notebookRowMapper;

    @Override
    public CreateNotebookResponse createNotebook(CreateNotebookDto notebook, Long creatorId) {
        if (notebook.getTitle() == null) {
            return response(1, "Empty data", null);}

        Notebook temp = notebookRowMapper.toEntity(notebook, creatorId);
        Optional<Notebook> notebookOptional = notebookRepository.save(temp);
        if (notebookOptional.isEmpty()) {
            return response(99, "Database process error", null);}

        return response(0, "OK", notebookOptional.get());

    }

    private CreateNotebookResponse response(int status, String statusDesc, Notebook notebook) {
        return CreateNotebookResponse.builder()
                .status(status)
                .statusDesc(statusDesc)
                .notebook(notebook)
                .build();
    }
}

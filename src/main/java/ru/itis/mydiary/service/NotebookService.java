package ru.itis.mydiary.service;

import ru.itis.mydiary.dto.CreateNotebookDto;
import ru.itis.mydiary.dto.CreateNotebookResponse;

public interface NotebookService {
    CreateNotebookResponse createNotebook(CreateNotebookDto notebook, Long creatorId);
}

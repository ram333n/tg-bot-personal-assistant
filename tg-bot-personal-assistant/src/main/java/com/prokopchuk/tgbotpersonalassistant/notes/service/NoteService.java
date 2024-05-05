package com.prokopchuk.tgbotpersonalassistant.notes.service;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.notes.NoteDto;
import org.springframework.data.domain.Page;

public interface NoteService {

  Long createNote(NoteDto note);

  void updateNote(NoteDto note);

  void deleteNoteById(Long id);

  Page<NoteDto> getNotesByChatId(Long chatId, int page, int limit);

  Page<NoteDto> getNotesFirstPageByChatId(Long chatId, int limit);

  boolean isLastPage(Long chatId, int page, int limit);

}

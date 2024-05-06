package com.prokopchuk.tgbotpersonalassistant.notes.service.impl;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.notes.NoteDto;
import com.prokopchuk.tgbotpersonalassistant.notes.domain.Note;
import com.prokopchuk.tgbotpersonalassistant.notes.mapper.NoteMapper;
import com.prokopchuk.tgbotpersonalassistant.notes.repository.NoteRepository;
import com.prokopchuk.tgbotpersonalassistant.notes.service.NoteService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultNoteService implements NoteService {

  private static final String DATE_MODIFIED = "dateModified";
  private final NoteRepository noteRepository;
  private final NoteMapper noteMapper;

  @Override
  public Long createNote(NoteDto note) {
    Note entity = noteMapper.toEntity(note);

    return noteRepository.save(entity).getId();
  }

  @Override
  public void updateNote(NoteDto note) {
    Note entity = noteRepository.findById(note.getId())
        .orElseThrow(() -> new NoSuchElementException(String.format("Note with id: %d doesn't exist", note.getId())));

    noteMapper.map(note, entity);
    noteRepository.save(entity);
  }

  @Override
  public void deleteNoteById(Long id) {
    noteRepository.deleteById(id);
  }

  @Override
  public Page<NoteDto> getNotesByChatId(Long chatId, int page, int limit) {
    Pageable pageable = createPageable(page, limit);

    return noteRepository.findAll(pageable)
        .map(noteMapper::toDto);
  }

  private Pageable createPageable(int page, int limit) {
    return PageRequest.of(page, limit, Sort.by(DATE_MODIFIED).descending());
  }

  @Override
  public Page<NoteDto> getNotesFirstPageByChatId(Long chatId, int limit) {
    return getNotesByChatId(chatId, 0, limit);
  }

  @Override
  public boolean isLastPage(Long chatId, int page, int limit) {
    return getNotesByChatId(chatId, page, limit)
        .isLast();
  }

  @Override
  public Optional<NoteDto> getNoteById(Long noteId) {
    return noteRepository.findById(noteId)
        .map(noteMapper::toDto);
  }

}

package com.prokopchuk.tgbotpersonalassistant.notes.repository;

import com.prokopchuk.tgbotpersonalassistant.notes.domain.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

  Page<Note> findAllByChatId(Long chatId, Pageable pageable);

}

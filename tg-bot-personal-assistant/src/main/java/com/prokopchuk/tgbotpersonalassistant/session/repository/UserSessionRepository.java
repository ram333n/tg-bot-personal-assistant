package com.prokopchuk.tgbotpersonalassistant.session.repository;

import com.prokopchuk.tgbotpersonalassistant.session.domain.UserSession;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {

  Optional<UserSession> findByChatId(Long chatId);

}

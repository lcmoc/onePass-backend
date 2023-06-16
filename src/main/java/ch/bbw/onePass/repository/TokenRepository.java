package ch.bbw.onePass.repository;

import java.util.List;
import java.util.Optional;

import ch.bbw.onePass.token.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<Token, Integer> {
  Optional<Token> findByToken(String token);

  List<Token> findAllValidTokenByUser(int toIntExact);
}

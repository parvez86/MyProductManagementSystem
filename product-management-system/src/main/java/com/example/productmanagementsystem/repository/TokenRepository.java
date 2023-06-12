package com.example.productmanagementsystem.repository;

import com.example.productmanagementsystem.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
//    @Query(value = """
//                  SELECT * \s
//                  FROM Token t\s
//                  where t.user_id = :id and (t.expired = false or t.revoked = false)
//            """, nativeQuery = true)
    @Query(value = """
          select t from Token t inner join User u\s
          on t.user_id = u.id\s
          where u.id = :id and (t.expired = false or t.revoked = false)\s
    """)
    List<Token> findAllValidTokenByUser(@Param("id") Integer id);
//    List<Token> findByRevokedOrExpired(Boolean expired, Boolean invoked);

//    List<Token> findAllByUserIdAndExpiredOrRevoked(Integer id, Boolean expired,Boolean revoked);
    Optional<Token> findByToken(String token);
}

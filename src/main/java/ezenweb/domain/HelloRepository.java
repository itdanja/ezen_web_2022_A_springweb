package ezenweb.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface HelloRepository extends JpaRepository<HelloEntity , Long> {
                                                                                            // 엔티티명 , 엔티티pk자료형
}


// Repository  <----------->  Dao


package ezenweb.domain.test;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HelloRepository extends JpaRepository<HelloEntity , Long> {
                                                                                            // 엔티티명 , 엔티티pk자료형
}


// Repository  <----------->  Dao


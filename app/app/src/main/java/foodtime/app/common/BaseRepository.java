package foodtime.app.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;


@NoRepositoryBean
public interface BaseRepository<T, K> extends JpaRepository<T, K> {
    List<T> findAllByDeletedIsFalse();
}

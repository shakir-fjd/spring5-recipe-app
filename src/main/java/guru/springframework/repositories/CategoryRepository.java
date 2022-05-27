package guru.springframework.repositories;

import guru.springframework.domain.*;
import org.springframework.data.repository.*;

import java.util.*;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    Optional<Category> findByDescription(String description);
}

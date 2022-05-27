package guru.springframework.repositories;

import guru.springframework.domain.*;
import org.springframework.data.repository.*;

import java.util.*;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {
    Optional<UnitOfMeasure> findByDescription(String description);
}

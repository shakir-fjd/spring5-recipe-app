package guru.springframework.repositories;

import guru.springframework.domain.*;
import org.springframework.data.repository.*;

public interface IngredientsRepository extends CrudRepository<Ingredient, Long> {
}

package guru.springframework.service;

import guru.springframework.commands.*;
import guru.springframework.domain.*;

import java.util.*;

public interface RecipeService {
    Set<Recipe> getRecipeList();

    Recipe getRecipeById(Long id);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    RecipeCommand findRecipeCommandById(Long id);

    Recipe saveRecipe(Recipe recipe);

    void deleteById(Long id);
}

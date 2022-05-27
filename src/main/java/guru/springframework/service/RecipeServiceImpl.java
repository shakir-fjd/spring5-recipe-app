package guru.springframework.service;

import guru.springframework.domain.*;
import guru.springframework.repositories.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> getRecipeList() {
        Set<Recipe> setOfRecipes = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(setOfRecipes::add);
        return setOfRecipes;
    }
}

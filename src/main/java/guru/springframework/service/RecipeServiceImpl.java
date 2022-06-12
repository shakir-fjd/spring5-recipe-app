package guru.springframework.service;

import guru.springframework.domain.*;
import guru.springframework.repositories.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;

import java.util.*;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> getRecipeList() {
        log.debug("IN THE RECIPE SERVICE IMPLEMENTATION:::::::::::::::");
        Set<Recipe> setOfRecipes = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(setOfRecipes::add);
        return setOfRecipes;
    }
}

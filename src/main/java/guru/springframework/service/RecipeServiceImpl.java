package guru.springframework.service;

import guru.springframework.commands.*;
import guru.springframework.converters.*;
import guru.springframework.domain.*;
import guru.springframework.exceptions.*;
import guru.springframework.repositories.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;

import javax.transaction.*;
import java.util.*;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipeList() {
        log.debug("IN THE RECIPE SERVICE IMPLEMENTATION:::::::::::::::");
        Set<Recipe> setOfRecipes = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(setOfRecipes::add);
        return setOfRecipes;
    }

    public Recipe getRecipeById(Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);
        if (!recipeOptional.isPresent()) {
//            throw new RuntimeException("Recipe Not Found!");
            throw new NotFoundException("Recipe not Found for ID: " + id.toString());
        }
        return recipeOptional.get();
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);
        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("SAVED RECIPE ::::: " + savedRecipe.getId());
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    public RecipeCommand findRecipeCommandById(Long id) {
        return recipeToRecipeCommand.convert(getRecipeById(id));
    }

    @Override
    @Transactional
    public Recipe saveRecipe(Recipe recipe) {
        Recipe returnedRecipe = recipeRepository.save(recipe);
        return returnedRecipe;
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }
}

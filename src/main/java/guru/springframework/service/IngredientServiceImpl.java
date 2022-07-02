package guru.springframework.service;

import guru.springframework.commands.*;
import guru.springframework.converters.*;
import guru.springframework.domain.*;
import guru.springframework.repositories.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;

import javax.transaction.*;
import java.util.*;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (!recipeOptional.isPresent()) {
            log.debug("RECIPE OBJECT IS NOT AVAILABLE :::::");
        }
        Recipe returnedRecipe = recipeOptional.get();
        Optional<IngredientCommand> ingredientCommandOptional = returnedRecipe.getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient))
                .findFirst();
        if (!ingredientCommandOptional.isPresent()) {
            log.debug("INGREDIENTCOMMAND OBJECT IS NOT AVAILABLE :::::");
        }
        return ingredientCommandOptional.get();
    }

    @Override
    @Transactional
    public IngredientCommand saveOrUpdateIngredient(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());
        if (!recipeOptional.isPresent()) {
            log.debug("RECIPE OBJECT IS NOT AVAILABLE :::::");
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();
            Optional<Ingredient> ingredientOptional = recipe.getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();
            if (ingredientOptional.isPresent()) {
                Ingredient ingredient = ingredientOptional.get();
                ingredient.setDescription(ingredientCommand.getDescription());
                ingredient.setAmount(ingredientCommand.getAmount());
                ingredient.setUnitOfMeasure(unitOfMeasureRepository.findById(ingredientCommand.getUnitOfMeasureCommand().getId())
                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUNT ::::")));
            } else {
                recipe.addIngridents(ingredientCommandToIngredient.convert(ingredientCommand));
            }
            Recipe savedRecipe = recipeRepository.save(recipe);
            Optional<Ingredient> returnedIngredient = savedRecipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();
            if (!returnedIngredient.isPresent()) {
                returnedIngredient = savedRecipe.getIngredients().stream()
                        .filter(ingredient -> ingredient.getDescription().equalsIgnoreCase(ingredientCommand.getDescription()))
                        .filter(ingredient -> ingredient.getAmount().equals(ingredientCommand.getAmount()))
                        .filter(ingredient -> ingredient.getUnitOfMeasure().getId().equals(ingredientCommand.getUnitOfMeasureCommand().getId()))
                        .findFirst();
            }
            return ingredientToIngredientCommand.convert(returnedIngredient.get());
        }
    }

    @Override
    @Transactional
    public void deleteIngredient(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (recipeOptional.isPresent()) {
            Recipe returnedRecipe = recipeOptional.get();
            Optional<Ingredient> optionalIngredient = returnedRecipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientId))
                    .findFirst();

            Ingredient ingredientToDelete = optionalIngredient.get();
            ingredientToDelete.setRecipe(null);
            returnedRecipe.getIngredients().remove(ingredientToDelete);
            recipeRepository.save(returnedRecipe);
        } else {
            log.debug("INGREDIENT NOT FOUND :::::::");
        }

    }
}



















package guru.springframework.service;

import guru.springframework.commands.*;

public interface IngredientService {

    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long IngredientId);

    IngredientCommand saveOrUpdateIngredient(IngredientCommand ingredientCommand);

    void deleteIngredient(Long recipeId, Long IngredientId);

}

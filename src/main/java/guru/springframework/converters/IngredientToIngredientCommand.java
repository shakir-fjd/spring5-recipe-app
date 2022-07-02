package guru.springframework.converters;

import guru.springframework.commands.*;
import guru.springframework.domain.*;
import lombok.*;
import org.springframework.core.convert.converter.*;
import org.springframework.lang.*;
import org.springframework.stereotype.*;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    private final UnitOfMeasureToUnitOfMeasureCommand uomToUomCommand;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uomToUomCommand) {
        this.uomToUomCommand = uomToUomCommand;
    }


    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient ingredient) {

        if (ingredient == null) {
            return null;
        }

        final IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(ingredient.getId());
        if (ingredient.getRecipe() != null) {
            ingredientCommand.setRecipeId(ingredient.getRecipe().getId());
        }
        ingredientCommand.setAmount(ingredient.getAmount());
        ingredientCommand.setDescription(ingredient.getDescription());
        ingredientCommand.setUnitOfMeasureCommand(uomToUomCommand.convert(ingredient.getUnitOfMeasure()));
        return ingredientCommand;
    }
}

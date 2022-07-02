package guru.springframework.converters;

import guru.springframework.commands.*;
import guru.springframework.domain.*;
import lombok.*;
import org.springframework.core.convert.converter.*;
import org.springframework.lang.*;
import org.springframework.stereotype.*;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final CategoryToCategoryCommand categoryCommand;
    private final IngredientToIngredientCommand ingredientCommand;
    private final NotesToNotesCommand notesCommand;

    public RecipeToRecipeCommand(CategoryToCategoryCommand categoryCommand, IngredientToIngredientCommand ingredientCommand, NotesToNotesCommand notesCommand) {
        this.categoryCommand = categoryCommand;
        this.ingredientCommand = ingredientCommand;
        this.notesCommand = notesCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {

        if (source == null) {
            return null;
        }

        final RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(source.getId());
        recipeCommand.setUrl(source.getUrl());
        recipeCommand.setSource(source.getSource());
        recipeCommand.setDescription(source.getDescription());
        recipeCommand.setCookTime(source.getCookTime());
        recipeCommand.setDirections(source.getDirections());
        recipeCommand.setPrepTime(source.getPrepTime());
        recipeCommand.setDifficulty(source.getDifficulty());
        recipeCommand.setServings(source.getServings());
        recipeCommand.setNotes(notesCommand.convert(source.getNotes()));

        if (source.getCategories() != null && source.getCategories().size() > 0) {
            source.getCategories()
                    .forEach(category -> recipeCommand.getCategories().add(categoryCommand.convert(category)));
        }

        if (source.getIngredients() != null && source.getIngredients().size() > 0) {
            source.getIngredients()
                    .forEach(ingredient -> recipeCommand.getIngredients().add(ingredientCommand.convert(ingredient)));
        }
        return recipeCommand;
    }
}

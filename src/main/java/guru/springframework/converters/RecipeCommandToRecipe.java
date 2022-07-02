package guru.springframework.converters;

import guru.springframework.commands.*;
import guru.springframework.domain.*;
import lombok.*;
import org.springframework.core.convert.converter.*;
import org.springframework.lang.*;
import org.springframework.stereotype.*;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {


    private final CategoryCommandToCategory categoryCommand;
    private final IngredientCommandToIngredient ingredientCommand;
    private final NotesCommandToNotes notesCommand;

    public RecipeCommandToRecipe(CategoryCommandToCategory categoryCommand, IngredientCommandToIngredient ingredientCommand, NotesCommandToNotes notesCommand) {
        this.categoryCommand = categoryCommand;
        this.ingredientCommand = ingredientCommand;
        this.notesCommand = notesCommand;
    }


    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {

        if (source == null) {
            return null;
        }

        final Recipe recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setUrl(source.getUrl());
        recipe.setSource(source.getSource());
        recipe.setDescription(source.getDescription());
        recipe.setCookTime(source.getCookTime());
        recipe.setDirections(source.getDirections());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setServings(source.getServings());
        recipe.setNotes(notesCommand.convert(source.getNotes()));

        if (source.getCategories() != null && source.getCategories().size() > 0) {
            source.getCategories()
                    .forEach(category -> recipe.getCategories().add(categoryCommand.convert(category)));
        }

        if (source.getIngredients() != null && source.getIngredients().size() > 0) {
            source.getIngredients()
                    .forEach(ingredient -> recipe.getIngredients().add(ingredientCommand.convert(ingredient)));
        }


        return recipe;
    }
}

package guru.springframework.controller;

import guru.springframework.commands.*;
import guru.springframework.domain.*;
import guru.springframework.service.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/recipe/{id}/show")
    public String showRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.getRecipeById(new Long(id)));
        return "recipe/show";
    }

    @RequestMapping("/recipe/new")
    public String newRecipeCommand(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeForm";
    }


    @PostMapping
    @RequestMapping("/recipe")
    public String saveOrUpdateRecipeCommand(@ModelAttribute RecipeCommand command) {
        RecipeCommand recipeCommand = recipeService.saveRecipeCommand(command);
        return "redirect:/recipe/" + recipeCommand.getId() + "/show";
    }

    @RequestMapping("/recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findRecipeCommandById(new Long(id)));
        return "recipe/recipeForm";
    }

    @RequestMapping("/recipe/newRecipe")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new Recipe());
        return "recipe/recipeForm";
    }

    @PostMapping
    @RequestMapping("/recipeModel")
    public String saveOrUpdateRecipe(@ModelAttribute Recipe recipe) {
        Recipe returnedRecipe = recipeService.saveRecipe(recipe);
        return "redirect:/recipe/" + returnedRecipe.getId() + "/show";
    }

    @RequestMapping("/recipe/{id}/delete")
    public String deleteRecipeById(@PathVariable String id) {
        log.debug("DELETING ID:::::" + id);
        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }
}

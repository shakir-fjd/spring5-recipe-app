package guru.springframework.controller;

import guru.springframework.service.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class indexController {

    private final RecipeService recipeService;

    public indexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping(value = {"", "/", "/index"})
    public String indexController(Model model) {
        model.addAttribute("setOfRecipes", recipeService.getRecipeList());
        return "index";
    }
}

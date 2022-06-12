package guru.springframework.controller;

import guru.springframework.service.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping(value = {"", "/", "/index"})
    public String indexController(Model model) {
        log.debug("IN THE INDEX CONTROLLER:::::::::::::::");
        model.addAttribute("setOfRecipes", recipeService.getRecipeList());
        return "index";
    }
}

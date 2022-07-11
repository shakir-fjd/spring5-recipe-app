package guru.springframework.controller;

import guru.springframework.commands.*;
import guru.springframework.domain.*;
import guru.springframework.exceptions.*;
import guru.springframework.service.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import javax.validation.*;

@Slf4j
@Controller
public class RecipeController {

    private final String RECIPE_RECIPEFORM_URL = "recipe/recipeForm";
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
        return RECIPE_RECIPEFORM_URL;
    }


    @PostMapping
    @RequestMapping("/recipe")
    public String saveOrUpdateRecipeCommand(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });
            return RECIPE_RECIPEFORM_URL;
        }

        RecipeCommand recipeCommand = recipeService.saveRecipeCommand(command);
        return "redirect:/recipe/" + recipeCommand.getId() + "/show";
    }

    @RequestMapping("/recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findRecipeCommandById(new Long(id)));
        return RECIPE_RECIPEFORM_URL;
    }

    @RequestMapping("/recipe/newRecipe")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new Recipe());
        return RECIPE_RECIPEFORM_URL;
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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception) {
        log.debug("Handling Not Found Exception ::::::::");
        log.debug(exception.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }
}

package guru.springframework.controller;

import guru.springframework.commands.*;
import guru.springframework.domain.*;
import guru.springframework.service.*;
import org.junit.*;
import org.mockito.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecipeControllerTest {

    @Mock
    RecipeService service;

    RecipeController controller;

    MockMvc mock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new RecipeController(service);
        mock = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void showRecipe() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        when(service.getRecipeById(anyLong())).thenReturn(recipe);
        mock.perform(get("/recipe/1/show"))
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"));
//                .andExpect(model().attribute("recipe", recipe));
    }

    @Test
    public void testGetNewRecipeForm() throws Exception {
        mock.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeForm"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testPostNewRecipeForm() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(2L);
        when(service.saveRecipeCommand(any())).thenReturn(recipeCommand);
        mock.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/show"));
    }

    @Test
    public void testGetUpdateView() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);

        when(service.findRecipeCommandById(anyLong())).thenReturn(command);

        mock.perform(get("/recipe/2/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeForm"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testDeleteItem() throws Exception {
        mock.perform(get("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }
}
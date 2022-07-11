package guru.springframework.service;

import guru.springframework.commands.*;
import guru.springframework.converters.*;
import guru.springframework.domain.*;
import guru.springframework.exceptions.*;
import guru.springframework.repositories.*;
import org.junit.*;
import org.mockito.*;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    public void getRecipeList() {
        Recipe recipe1 = new Recipe();
        Recipe recipe2 = new Recipe();
        recipe2.setId(4L);
        Set set = new HashSet();
        set.add(recipe1);
        set.add(recipe2);

        when(recipeRepository.findAll()).thenReturn(set);
//        when(recipeService.getRecipeList()).thenReturn(set);
        Set<Recipe> recipes = recipeService.getRecipeList();
        assertEquals(recipes.size(), 2);
    }

    @Test
    public void getRecipeById() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        Recipe recipeReturned = recipeService.getRecipeById(1L);
        assertNotNull(recipeReturned);
        verify(recipeRepository).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void deleteByDI() {
        Long idToDelete = 1L;
        recipeService.deleteById(idToDelete);
        verify(recipeRepository).deleteById(anyLong());
    }

    @Test(expected = NotFoundException.class)
    public void getRecipeByIdTestNotFound() {
        Optional<Recipe> recipeOptional = Optional.empty();
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        RecipeCommand recipeCommand = recipeService.findRecipeCommandById(anyLong());
    }
}
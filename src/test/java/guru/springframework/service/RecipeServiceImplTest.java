package guru.springframework.service;

import guru.springframework.domain.*;
import guru.springframework.repositories.*;
import org.junit.*;
import org.mockito.*;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    RecipeService recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository);
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
        assertEquals(recipes.size(), 2 );

    }
}
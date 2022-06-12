package guru.springframework.controller;

import guru.springframework.domain.*;
import guru.springframework.service.*;
import org.junit.*;
import org.mockito.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.*;
import org.springframework.ui.*;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IndexControllerTest {


    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

//    @Autowired
//    MockMvc mock;

    IndexController indexController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        indexController = new IndexController(recipeService);
    }

    @Test
    public void mockMvc() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));

//        mock.perform(MockMvcRequestBuilders.get("/"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("index"));
    }

    @Test
    public void indexController() {

        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(recipe);
        recipes.add(new Recipe());

        //when
        when(recipeService.getRecipeList()).thenReturn(recipes);
        ArgumentCaptor<Set<Recipe>> acRecipeSet = ArgumentCaptor.forClass(Set.class);
        assertEquals("index", indexController.indexController(model));
        verify(recipeService, atMost(3)).getRecipeList();
//        verify(model, times(1)).addAttribute(eq("setOfRecipes"), anySet());
        verify(model, times(1)).addAttribute(eq("setOfRecipes"), acRecipeSet.capture());
        Set<Recipe> setOfRecipeSet = acRecipeSet.getValue();
        assertEquals(2, setOfRecipeSet.size());
    }
}
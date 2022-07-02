package guru.springframework.service;

import guru.springframework.domain.*;
import guru.springframework.repositories.*;
import org.junit.*;
import org.mockito.*;
import org.springframework.mock.web.*;

import java.io.*;
import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ImageServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    ImageService imageService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        imageService = new ImageServiceImpl(recipeRepository);
    }

    @Test
    public void saveImageFile() throws IOException {

        // Given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("imagefile",
                "testing.txt", "text/plain", "Spring Upload File".getBytes());
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        // When
        imageService.saveImageFile(1L, mockMultipartFile);

        // Then
        verify(recipeRepository).save(argumentCaptor.capture());
        Recipe savedRecipe = argumentCaptor.getValue();
        Assert.assertEquals(mockMultipartFile.getBytes().length, savedRecipe.getImage().length);
    }
}
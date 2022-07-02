package guru.springframework.service;

import guru.springframework.domain.*;
import guru.springframework.repositories.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.web.multipart.*;

import javax.transaction.*;
import java.io.*;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void saveImageFile(Long recipeId, MultipartFile file) {
        log.debug("IN IMAGE SERVICE, RECEIVED FILE :::::::: ");

        try {
            Recipe recipe = recipeRepository.findById(recipeId).get();
            Byte[] byteObject = new Byte[file.getBytes().length];

            int i = 0;
            for (Byte eachByte : file.getBytes()) {
                byteObject[i++] = eachByte;
            }
            recipe.setImage(byteObject);
            recipeRepository.save(recipe);
            log.debug("IMAGE PERSISTED INTO DATABASE :::: ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

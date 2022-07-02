package guru.springframework.service;

import org.springframework.web.multipart.*;

public interface ImageService {

    void saveImageFile(Long recipeId, MultipartFile file);

}

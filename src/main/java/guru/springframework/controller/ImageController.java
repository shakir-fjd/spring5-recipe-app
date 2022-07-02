package guru.springframework.controller;

import guru.springframework.commands.*;
import guru.springframework.service.*;
import org.apache.tomcat.util.http.fileupload.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import javax.servlet.http.*;
import java.io.*;

@Controller
public class ImageController {

    private final RecipeService recipeService;
    private final ImageService imageService;

    public ImageController(RecipeService recipeService, ImageService imageService) {
        this.recipeService = recipeService;
        this.imageService = imageService;
    }

    @GetMapping("recipe/{recipeId}/image")
    public String showUploadForm(@PathVariable String recipeId, Model model) {
        RecipeCommand recipeCommand = recipeService.findRecipeCommandById(Long.valueOf(recipeId));
        model.addAttribute("recipe", recipeCommand);
        return "recipe/imageUploadForm";
    }

    @PostMapping("recipe/{recipeId}/image")
    public String uploadImage(@PathVariable String recipeId, @RequestParam("imagefile") MultipartFile file) {
        imageService.saveImageFile(Long.valueOf(recipeId), file);
        return "redirect:/recipe/" + recipeId + "/show";
    }

    @GetMapping("/recipe/{recipeId}/recipeImage")
    public void renderImageFromDb(@PathVariable String recipeId, HttpServletResponse response) throws IOException {

        RecipeCommand recipeCommand = recipeService.findRecipeCommandById(Long.valueOf(recipeId));
        byte[] imageByteArr = new byte[recipeCommand.getImage().length];
        int i = 0;
        for (byte eachByte : recipeCommand.getImage()) {
            imageByteArr[i++] = eachByte;
        }
        response.setContentType("image/jpeg");
        InputStream is = new ByteArrayInputStream(imageByteArr);
        IOUtils.copy(is, response.getOutputStream());
    }
}

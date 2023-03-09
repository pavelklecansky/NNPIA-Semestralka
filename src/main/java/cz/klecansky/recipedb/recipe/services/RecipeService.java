package cz.klecansky.recipedb.recipe.services;

import cz.klecansky.recipedb.recipe.endpoints.request.CreateRecipe;
import cz.klecansky.recipedb.recipe.io.RecipeEntity;
import cz.klecansky.recipedb.recipe.io.RecipeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class RecipeService {

    @NonNull RecipeRepository recipeRepository;

    public List<RecipeEntity> findAll() {
        return recipeRepository.findAll();
    }

    public Optional<RecipeEntity> findById(UUID id) {
        return recipeRepository.findById(id);
    }

    public RecipeEntity save(CreateRecipe recipe) {
        RecipeEntity recipeEntity = createRecipeToRecipeEntity(recipe);
        return recipeRepository.save(recipeEntity);
    }

    private RecipeEntity createRecipeToRecipeEntity(CreateRecipe recipe) {
        RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setName(recipe.getName());
        recipeEntity.setDescription(recipe.getDescription());
        recipeEntity.setDirections(recipe.getDescription());
        recipeEntity.setIngredients(recipe.getIngredients());
        recipeEntity.setPrepTimeInMinutes(recipe.getPrepTimeInMinutes());
        recipeEntity.setCookTimeInMinutes(recipe.getCookTimeInMinutes());
        recipeEntity.setServings(recipe.getServings());
        recipeEntity.setImageBase64(recipe.getImageBase64());
        return recipeEntity;
    }

    public void deleteById(UUID id) {
        recipeRepository.deleteById(id);
    }

    public Optional<RecipeEntity> update(UUID id, RecipeEntity recipe) {
        return recipeRepository.findById(id)
                .map(oldRecipe -> {
                    oldRecipe.setName(recipe.getName());
                    oldRecipe.setDescription(recipe.getDescription());
                    oldRecipe.setIngredients(recipe.getIngredients());
                    oldRecipe.setDirections(recipe.getDirections());
                    oldRecipe.setServings(recipe.getServings());
                    oldRecipe.setCookTimeInMinutes(recipe.getCookTimeInMinutes());
                    oldRecipe.setPrepTimeInMinutes(recipe.getPrepTimeInMinutes());
                    oldRecipe.setImageBase64(recipe.getImageBase64());
                    return recipeRepository.save(oldRecipe);
                });
    }
}

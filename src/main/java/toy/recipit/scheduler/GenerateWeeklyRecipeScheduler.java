package toy.recipit.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import toy.recipit.common.Constants;
import toy.recipit.service.RecipeService;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class GenerateWeeklyRecipeScheduler {
    private final RecipeService recipeService;

    @Scheduled(cron = "0 0 2 * * *", zone = Constants.TimeZone.SEOUL)
    public void generateWeeklyRecipe() {
        try {
            recipeService.generateWeeklyRecipes();
        } catch (Exception e) {
            log.error("Error generateWeeklyRecipes", e);
        }
    }
}

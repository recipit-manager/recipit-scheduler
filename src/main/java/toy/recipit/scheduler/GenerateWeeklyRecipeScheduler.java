package toy.recipit.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import toy.recipit.service.RecipeService;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class GenerateWeeklyRecipeScheduler {
    private final RecipeService RecipeService;

    @Scheduled(cron = "0 0 0 * * *")
    public void generateWeeklyRecipe() {
        RecipeService.generateWeeklyRecipes();
    }
}

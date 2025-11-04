package toy.recipit.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import toy.recipit.Dispatch.NoticeDispatch;
import toy.recipit.Dispatch.dto.NoticeItem;
import toy.recipit.common.Constants;
import toy.recipit.service.RecipeService;

import java.util.List;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class RecipeScheduler {
    private final RecipeService recipeService;
    private final NoticeDispatch noticeDispatch;

    @Scheduled(cron = "0 0 2 * * *", zone = Constants.TimeZone.SEOUL)
    public void generateWeeklyRecipe() {
        try {
            recipeService.generateWeeklyRecipes();
        } catch (Exception e) {
            log.error("Error generateWeeklyRecipes", e);
        }
    }

    @Scheduled(cron = "0 0 3 * * *", zone = Constants.TimeZone.SEOUL)
    public void deleteWeeklyRecipe() {
        try {
            recipeService.deleteWeeklyRecipes();
        } catch (Exception e) {
            log.error("Error deleteWeeklyRecipe", e);
        }
    }

    @Scheduled(cron = "0 15 3 * * *", zone = Constants.TimeZone.SEOUL)
    public void deleteViewRecipe() {
        try {
            recipeService.deleteViewRecipes();
        } catch (Exception e) {
            log.error("Error deleteUnLikeRecipe", e);
        }
    }

    @Scheduled(cron = "0 30 3 * * *", zone = Constants.TimeZone.SEOUL)
    public void deleteUnLikeRecipe() {
        try {
            recipeService.deleteUnlikeRecipes();
        } catch (Exception e) {
            log.error("Error deleteUnLikeRecipe", e);
        }
    }

    @Scheduled(cron = "0 45 3 * * *", zone = Constants.TimeZone.SEOUL)
    public void deleteDraftRecipe() {
        try {
            List<NoticeItem> notices = recipeService.deleteDraftRecipes();

            if (!notices.isEmpty()) {
                noticeDispatch.sendNotices(notices);
            }
        } catch (Exception e) {
            log.error("Error deleteDraftRecipe", e);
        }
    }
}

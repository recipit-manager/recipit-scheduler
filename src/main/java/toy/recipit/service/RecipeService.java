package toy.recipit.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.recipit.common.Constants;
import toy.recipit.mapper.WeeklyRecipeMapper;
import toy.recipit.mapper.vo.RecipeLikeVo;
import toy.recipit.mapper.vo.WeeklyRecipeVo;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeService {

    private final WeeklyRecipeMapper weeklyRecipeMapper;

    @Transactional
    public void generateWeeklyRecipes() {
        LocalDate today = LocalDate.now();
        DayOfWeek todayDay = today.getDayOfWeek();
        LocalDateTime startDateTime = today.minusWeeks(1).with(todayDay).atStartOfDay();
        LocalDateTime endDateTime = today.minusDays(1).atTime(LocalTime.MAX);

        List<WeeklyRecipeVo> weeklyRecipeList = new ArrayList<>();

        List<RecipeLikeVo> topLikedList =
                weeklyRecipeMapper.getTopLikedRecipes(startDateTime, endDateTime, Constants.Recipe.RELEASE);

        if (!topLikedList.isEmpty()) {
            weeklyRecipeList = buildWeeklyRecipes(topLikedList);
        }

        if (weeklyRecipeList.size() < Constants.WeeklyRecipe.RECOMMEND_COUNT) {
            int neededRecipeCount = Constants.WeeklyRecipe.RECOMMEND_COUNT - weeklyRecipeList.size();

            List<String> excludeRecipeNos = weeklyRecipeList.stream()
                    .map(WeeklyRecipeVo::getRecipeNo)
                    .toList();

            List<WeeklyRecipeVo> supplements =
                    buildCategorySupplements(excludeRecipeNos, neededRecipeCount);

            weeklyRecipeList.addAll(supplements);
        }

        weeklyRecipeMapper.insertWeeklyRecipes(today, weeklyRecipeList);
    }

    private List<WeeklyRecipeVo> buildWeeklyRecipes(List<RecipeLikeVo> topLikedList) {
        List<WeeklyRecipeVo> weeklyRecipes = new ArrayList<>();

        for (int i = 0; i < topLikedList.size(); i++) {
            RecipeLikeVo vo = topLikedList.get(i);
            weeklyRecipes.add(new WeeklyRecipeVo(
                    vo.getRecipeNo(),
                    vo.getCategoryCode(),
                    Constants.RecommendType.RECOMMEND,
                    i
            ));
        }

        return weeklyRecipes;
    }

    private List<WeeklyRecipeVo> buildCategorySupplements(List<String> excludeRecipeNos, int neededRecipeCount) {
        List<WeeklyRecipeVo> supplementRecipes = new ArrayList<>();

        List<String> lowFrequencyCategories = weeklyRecipeMapper.getLowFrequencyCategories(Constants.GroupCode.RECIPE_CATEGORY);

        for (String categoryCode : lowFrequencyCategories) {
            if (supplementRecipes.size() >= neededRecipeCount) break;

            weeklyRecipeMapper.getRandomRecipeByCategory(categoryCode, excludeRecipeNos, Constants.Recipe.RELEASE)
                    .ifPresent(supplementRecipes::add);
        }

        return getWeeklyRecipeVos(neededRecipeCount, supplementRecipes);
    }

    private List<WeeklyRecipeVo> getWeeklyRecipeVos(int neededRecipeCount, List<WeeklyRecipeVo> supplements) {
        List<WeeklyRecipeVo> weeklyRecipeVoList = new ArrayList<>();

        for (int i = 0; i < supplements.size(); i++) {
            WeeklyRecipeVo vo = supplements.get(i);
            WeeklyRecipeVo updated = new WeeklyRecipeVo(
                    vo.getRecipeNo(),
                    vo.getCategoryCode(),
                    Constants.RecommendType.SUPPLEMENT,
                    Constants.WeeklyRecipe.RECOMMEND_COUNT - neededRecipeCount + i
            );
            weeklyRecipeVoList.add(updated);
        }

        return weeklyRecipeVoList;
    }
}

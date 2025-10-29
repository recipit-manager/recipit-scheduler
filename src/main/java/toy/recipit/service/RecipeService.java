package toy.recipit.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.recipit.common.Constants;
import toy.recipit.mapper.WeeklyRecipeMapper;
import toy.recipit.mapper.vo.WeeklyRecipeInfoVo;
import toy.recipit.mapper.vo.InsertWeeklyRecipeVo;

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

        List<InsertWeeklyRecipeVo> weeklyRecipeList = new ArrayList<>();

        List<WeeklyRecipeInfoVo> topLikedList =
                weeklyRecipeMapper.getTopLikedRecipes(startDateTime, endDateTime, Constants.Recipe.RELEASE);

        if (!topLikedList.isEmpty()) {
            weeklyRecipeList = buildWeeklyRecipes(topLikedList);
        }

        if (weeklyRecipeList.size() < Constants.WeeklyRecipe.RECOMMEND_COUNT) {
            int neededRecipeCount = Constants.WeeklyRecipe.RECOMMEND_COUNT - weeklyRecipeList.size();

            List<String> excludeRecipeNos = weeklyRecipeList.stream()
                    .map(InsertWeeklyRecipeVo::getRecipeNo)
                    .toList();

            List<InsertWeeklyRecipeVo> supplements =
                    buildCategorySupplements(excludeRecipeNos, neededRecipeCount);

            weeklyRecipeList.addAll(supplements);
        }

        weeklyRecipeMapper.insertWeeklyRecipes(today, weeklyRecipeList);
    }

    @Transactional
    public void deleteWeeklyRecipes() {
        log.info("WeeklyRecipe Deleted rows: {}", weeklyRecipeMapper.deleteWeeklyRecipes());
    }

    @Transactional
    public void deleteViewRecipes() {
        log.info("View Recipe Deleted rows: {}", weeklyRecipeMapper.deleteViewRecipes());
    }

    @Transactional
    public void deleteUnlikeRecipes() {
        log.info("Unlike Recipe Deleted rows: {}", weeklyRecipeMapper.deleteUnlikeRecipes());
    }

    private List<InsertWeeklyRecipeVo> buildWeeklyRecipes(List<WeeklyRecipeInfoVo> topLikedList) {
        List<InsertWeeklyRecipeVo> weeklyRecipes = new ArrayList<>();

        for (int i = 0; i < topLikedList.size(); i++) {
            WeeklyRecipeInfoVo vo = topLikedList.get(i);
            weeklyRecipes.add(new InsertWeeklyRecipeVo(
                    vo.getRecipeNo(),
                    vo.getCategoryCode(),
                    Constants.RecommendType.RECOMMEND,
                    i
            ));
        }

        return weeklyRecipes;
    }

    private List<InsertWeeklyRecipeVo> buildCategorySupplements(List<String> excludeRecipeNos, int neededRecipeCount) {
        List<WeeklyRecipeInfoVo> supplementRecipes = new ArrayList<>();

        List<String> lowFrequencyCategories = weeklyRecipeMapper.getLowFrequencyCategories(Constants.GroupCode.RECIPE_CATEGORY);

        for (String categoryCode : lowFrequencyCategories) {
            if (supplementRecipes.size() >= neededRecipeCount) {
                break;
            }

            weeklyRecipeMapper.getRandomRecipeByCategory(categoryCode, excludeRecipeNos, Constants.Recipe.RELEASE)
                    .ifPresent(supplementRecipes::add);
        }

        return getWeeklyRecipeVos(neededRecipeCount, supplementRecipes);
    }

    private List<InsertWeeklyRecipeVo> getWeeklyRecipeVos(int neededRecipeCount, List<WeeklyRecipeInfoVo> supplements) {
        List<InsertWeeklyRecipeVo> weeklyRecipeVoList = new ArrayList<>();

        for (int i = 0; i < supplements.size(); i++) {
            WeeklyRecipeInfoVo weeklyRecipeInfoVo = supplements.get(i);

            InsertWeeklyRecipeVo insertWeeklyRecipeVo = new InsertWeeklyRecipeVo(
                    weeklyRecipeInfoVo.getRecipeNo(),
                    weeklyRecipeInfoVo.getCategoryCode(),
                    Constants.RecommendType.SUPPLEMENT,
                    Constants.WeeklyRecipe.RECOMMEND_COUNT - neededRecipeCount + i
            );

            weeklyRecipeVoList.add(insertWeeklyRecipeVo);
        }

        return weeklyRecipeVoList;
    }
}

package toy.recipit.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import toy.recipit.mapper.vo.RecipeLikeVo;
import toy.recipit.mapper.vo.WeeklyRecipeVo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Mapper
public interface WeeklyRecipeMapper {
    List<RecipeLikeVo> getTopLikedRecipes(@Param("startDateTime") LocalDateTime startDateTime,
                                          @Param("endDateTime") LocalDateTime endDateTime,
                                          @Param("statusCode") String statusCode);

    void insertWeeklyRecipes(@Param("recommendDate") LocalDate recommendDate,
                             @Param("weeklyRecommendationVoList") List<WeeklyRecipeVo> weeklyRecommendationVoList);

    List<String> getLowFrequencyCategories(@Param("categoryGroupCode") String categoryGroupCode);

    Optional<WeeklyRecipeVo> getRandomRecipeByCategory(@Param("categoryCode") String categoryCode,
                                                       @Param("excludeRecipeNos") List<String> excludeRecipeNos,
                                                       @Param("statusCode") String statusCode);
}

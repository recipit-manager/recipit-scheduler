package toy.recipit.mapper.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RecipeVo {
    private final String recipeNo;
    private final String userNo;
    private final String title;
    private final String description;
    private final String categoryCode;
    private final int cookingTime;
    private final int servingSize;
    private final String difficultyCode;
    private final String statusCode;
}

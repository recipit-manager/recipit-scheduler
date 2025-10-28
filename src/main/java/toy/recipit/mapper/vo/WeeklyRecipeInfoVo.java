package toy.recipit.mapper.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WeeklyRecipeInfoVo {
    private final String recipeNo;
    private final String categoryCode;
}
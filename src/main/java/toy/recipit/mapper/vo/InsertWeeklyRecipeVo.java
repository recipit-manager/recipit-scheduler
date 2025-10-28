package toy.recipit.mapper.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class InsertWeeklyRecipeVo {
    private final String recipeNo;
    private final String categoryCode;
    private final String recommendedTypeCode;
    private final int sortSequence;
}

package toy.recipit.mapper.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RecipeLikeVo {
    private final String recipeNo;
    private final String categoryCode;
}
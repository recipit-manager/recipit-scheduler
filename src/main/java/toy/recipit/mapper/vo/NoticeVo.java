package toy.recipit.mapper.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NoticeVo {
    private final String noticeNo;
    private final String userNo;
    private final String recipeNo;
    private final String categoryCode;
    private final String contents;
    private final String readYn;
    private final String createUser;
}
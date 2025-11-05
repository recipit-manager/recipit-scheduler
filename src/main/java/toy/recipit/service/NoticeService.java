package toy.recipit.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.recipit.Dispatch.NoticeDispatch;
import toy.recipit.Dispatch.dto.NoticeItem;
import toy.recipit.common.Constants;
import toy.recipit.mapper.NoticeMapper;
import toy.recipit.mapper.RecipeMapper;
import toy.recipit.mapper.vo.NoticeVo;
import toy.recipit.mapper.vo.RecipeVo;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeMapper noticeMapper;
    private final RecipeMapper recipeMapper;
    private final NoticeDispatch noticeDispatch;

    @Transactional
    public void deleteOlderNotices() {
        log.info("Older Notice Deleted rows: {}", noticeMapper.deleteOlderNotices());
    }

    @Transactional
    public void sendDraftRecipeExpireNotice() {
        List<RecipeVo> draftRecipes = recipeMapper.getRecipesScheduledExpire(Constants.Recipe.DRAFT);

        if (draftRecipes.isEmpty()) {
            log.info("No draft recipes to delete Scheduled");
            return;
        }

        List<NoticeVo> noticeList = draftRecipes.stream()
                .map(recipe -> new NoticeVo(
                        null,
                        recipe.getUserNo(),
                        recipe.getRecipeNo(),
                        Constants.NoticeType.DRAFT,
                        recipe.getTitle() + " 레시피의 저장 만료기한이 일주일 남았습니다.",
                        Constants.Yn.NO,
                        recipe.getUserNo()
                ))
                .toList();

        insertNotice(noticeList);
    }

    public void insertNotice(List<NoticeVo> noticeList) {
        recipeMapper.insertNotices(noticeList);

        noticeDispatch.sendNotices(noticeList.stream()
                .map(noticeVo -> new NoticeItem(noticeVo.getNoticeNo(), noticeVo.getUserNo()))
                .toList()
        );
    }
}
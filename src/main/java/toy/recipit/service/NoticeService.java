package toy.recipit.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.recipit.feign.NoticeDispatch;
import toy.recipit.feign.dto.NoticeItem;
import toy.recipit.mapper.NoticeMapper;
import toy.recipit.mapper.RecipeMapper;
import toy.recipit.mapper.vo.NoticeVo;

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

    public void insertNotice(List<NoticeVo> noticeList) {
        recipeMapper.insertNotices(noticeList);

        noticeDispatch.sendNotices(noticeList.stream()
                .map(noticeVo -> new NoticeItem(noticeVo.getNoticeNo(), noticeVo.getUserNo()))
                .toList()
        );
    }
}
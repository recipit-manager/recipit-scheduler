package toy.recipit.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.recipit.mapper.NoticeMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeMapper noticeMapper;

    @Transactional
    public void deleteOlderNotices() {
        log.info("Older Notice Deleted rows: {}", noticeMapper.deleteOlderNotices());
    }
}
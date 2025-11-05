package toy.recipit.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import toy.recipit.common.Constants;
import toy.recipit.service.NoticeService;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class NoticeScheduler {
    private final NoticeService noticeService;

    @Scheduled(cron = "0 50 3 * * *", zone = Constants.TimeZone.SEOUL)
    public void deleteOlderNotices() {
        try {
            noticeService.deleteOlderNotices();
        } catch (Exception e) {
            log.error("Error delete OlderNotice", e);
        }
    }
}

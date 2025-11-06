package toy.recipit.dispatch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import toy.recipit.dispatch.client.NoticeClient;
import toy.recipit.dispatch.dto.NoticeItem;
import toy.recipit.dispatch.dto.NoticeRequest;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class NoticeDispatch {
    private final NoticeClient noticeClient;

    @Value("${internal.auth-key}")
    private String internalAuthKey;

    public void sendNotices(List<NoticeItem> items) {
        try {
            noticeClient.sendNotices(new NoticeRequest(items), internalAuthKey);
            log.info("Success send message, Size : {}", items.size());
        } catch (Exception e) {
            log.error("Failed send message", e);
        }
    }
}

package toy.recipit.Dispatch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import toy.recipit.Dispatch.dto.NoticeItem;
import toy.recipit.Dispatch.dto.NoticeRequest;

import java.util.List;

@Slf4j
@Component
public class NoticeDispatch {
    @Value("${recipit.noticeApi.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendNotices(List<NoticeItem> items) {
        NoticeRequest noticeRequest = new NoticeRequest(items);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<NoticeRequest> requestEntity = new HttpEntity<>(noticeRequest, headers);

        try {
            restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    requestEntity,
                    Void.class
            );

            log.info("Success send message, Size : {}", items.size());
        } catch (Exception e) {
            log.error("Failed send message", e);
        }
    }
}

package toy.recipit.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import toy.recipit.feign.dto.NoticeRequest;

@FeignClient(name = "noticeClient", url = "${recipit.noticeApi.url}")
public interface NoticeClient {

    @PostMapping("/dispatch")
    void sendNotices(@RequestBody NoticeRequest request,
                     @RequestHeader("AuthenticationKey") String authKey);
}

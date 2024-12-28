package kr.kainos.book.main;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Collections;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Tag(name = "A - Monitoring", description = "모니터링")
public class MainController {

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public Map<String, String> home() {
    return Collections.singletonMap("status", "UP");
  }

  @RequestMapping(value = "/status/health", method = RequestMethod.GET)
  public Map<String, String> healthCheck() {
    return Collections.singletonMap("status", "UP");
  }
}

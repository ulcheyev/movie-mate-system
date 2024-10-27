package cz.cvut.userservice.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Slf4j(topic = "APP_USER_CONTROLLER")
@RequiredArgsConstructor
@Tag(name = "AppUserController", description = "Controller for managing user data.")
public class AppUserController {
}

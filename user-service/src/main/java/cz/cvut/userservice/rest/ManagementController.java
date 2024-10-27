package cz.cvut.userservice.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management")
@Slf4j(topic = "MANAGEMENT_CONTROLLER")
@RequiredArgsConstructor
@Tag(name = "ManagementController", description = "Controller for managing administrative tasks such as role assignment, lock user etc.")
public class ManagementController {
}

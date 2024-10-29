package cz.cvut.userservice.dto;

import cz.cvut.userservice.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;

public record SetNewRolesRequest(
        @Schema(description = "Roles to add", example = "[\"USER\", \"ADMIN\"]")
        Role[] roles
) {}

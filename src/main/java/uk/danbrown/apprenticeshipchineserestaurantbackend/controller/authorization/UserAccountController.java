package uk.danbrown.apprenticeshipchineserestaurantbackend.controller.authorization;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.danbrown.apprenticeshipchineserestaurantbackend.controller.model.JwtResource;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Authorization.UserAccount;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Authorization.UserLoginRequest;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityAlreadyExistsWithIdException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.EntityNotFoundException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.InvalidPasswordException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.service.authorization.UserAccountService;

@RestController
@RequestMapping("/user-authorization")
public class UserAccountController {

    private final UserAccountService userAccountService;

    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @PostMapping("/create-account")
    public ResponseEntity<JwtResource> createAccount(@RequestBody UserAccount userAccount) throws EntityAlreadyExistsWithIdException, FailureInsertingEntityException {
        return ResponseEntity.status(201).body(new JwtResource(userAccountService.createAccount(userAccount)));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResource> login(@RequestBody UserLoginRequest loginRequest) throws InvalidPasswordException, EntityNotFoundException {
        return ResponseEntity.ok(new JwtResource(userAccountService.login(loginRequest)));
    }
}

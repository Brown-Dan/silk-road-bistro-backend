package uk.danbrown.apprenticeshipchineserestaurantbackend.controller.authorization;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.danbrown.apprenticeshipchineserestaurantbackend.service.authorization.UserAccountService;

@ExtendWith(MockitoExtension.class)
class UserAccountControllerTest {

    @Mock
    private UserAccountService userAccountService;

    private UserAccountController userAccountController;

    @BeforeEach
    void setUp() {
        userAccountController = new UserAccountController(userAccountService);
    }

    @Test
    void createAccount() {

    }

    @Test
    void login() {
    }
}
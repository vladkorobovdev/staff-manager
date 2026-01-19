package com.github.vladkorobovdev.staff_manager.service;

import com.github.vladkorobovdev.staff_manager.entity.RefreshToken;
import com.github.vladkorobovdev.staff_manager.repository.RefreshTokenRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RefreshTokenServiceTest {
    @Mock
    private RefreshTokenRepository refreshTokenRepository;
    @InjectMocks
    private RefreshTokenService refreshTokenService;

    @Test
    @DisplayName("Verify Expiration: Token Expired -> Delete & Throw")
    void verifyExpiration_ShouldThrow_WhenExpired() {
        RefreshToken expiredToken = new RefreshToken();
        expiredToken.setToken("old-token");
        expiredToken.setExpiryDate(Instant.now().minusSeconds(100));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            refreshTokenService.verifyExpiration(expiredToken);
        });

        assertEquals("Refresh token was expired. Please sign in again.", exception.getMessage());

        verify(refreshTokenRepository, times(1)).delete(expiredToken);
    }

    @Test
    @DisplayName("Verify Expiration: Token Valid -> Return Token")
    void verifyExpiration_ShouldReturnToken_WhenValid() {
        RefreshToken validToken = new RefreshToken();
        validToken.setExpiryDate(Instant.now().plusSeconds(100));

        RefreshToken result = refreshTokenService.verifyExpiration(validToken);

        assertEquals(validToken, result);
        verify(refreshTokenRepository, never()).delete(any());
    }
}

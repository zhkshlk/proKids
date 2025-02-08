package com.example.prokids.Services;

import com.example.prokids.dto.*;

public interface AuthenticationService {
    LoginResponse registration(UserCreate userCreate);
    LoginResponse login(LoginRequest request);
    RefreshResponse refresh(RefreshRequest refreshRequest);
    void logout(LogoutRequest logoutRequest);
}

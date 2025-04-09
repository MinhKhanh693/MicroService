package mk.auth.service.services;

import mk.auth.service.dtos.requests.SigningRequestDto;
import mk.auth.service.dtos.response.TblUserEntityResponseDto;
import mk.auth.service.dtos.response.TokenResponseDto;

public interface IAuthenticationService {

    TokenResponseDto getAccessToken(SigningRequestDto signingRequestDto);

    TokenResponseDto refreshToken(String refreshToken);

    TblUserEntityResponseDto validateToken(String token, String uri);
}

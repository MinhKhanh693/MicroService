package mk.auth.service.services;

import mk.auth.service.repositories.TblUserEntityRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public record UserServiceDetail(TblUserEntityRepository userRepository) {

    public UserDetailsService userServiceDetail() {
        return userRepository::findTblUserByUsername;
    }
}

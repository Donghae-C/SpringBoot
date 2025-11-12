package web.mvc.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.mvc.domain.User;
import web.mvc.exception.BasicException;
import web.mvc.exception.ErrorCode;
import web.mvc.repository.UserRepository;

import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Override
    public User loginCheck(User user) {
        User dbUser = userRepository.findById(user.getUserId()).orElseThrow(()->new BasicException(ErrorCode.NOTFOUND_ID));
        if(!dbUser.getPwd().equals(user.getPwd())){
            throw new BasicException(ErrorCode.WRONG_PASS);
        }
        return dbUser;
    }
}

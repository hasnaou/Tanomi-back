package net.hsn.taskschedulerandeventmanagementtoolapi.services.serviceImpl;


import net.hsn.taskschedulerandeventmanagementtoolapi.repositories.UserRepository;
import net.hsn.taskschedulerandeventmanagementtoolapi.services.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;


    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }
}

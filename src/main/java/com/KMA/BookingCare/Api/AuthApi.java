package com.KMA.BookingCare.Api;

import com.KMA.BookingCare.Dto.JwtReponse;
import com.KMA.BookingCare.Dto.LoginDto;
import com.KMA.BookingCare.Dto.SignupRequest;
import com.KMA.BookingCare.Dto.User;
import com.KMA.BookingCare.Entity.RoleEntity;
import com.KMA.BookingCare.Entity.UserEntity;
import com.KMA.BookingCare.Repository.RoleRepository;
import com.KMA.BookingCare.Repository.UserRepository;
import com.KMA.BookingCare.Service.UserService;
import com.KMA.BookingCare.ServiceImpl.UserDetailsImpl;
import com.KMA.BookingCare.common.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@RestController
@RequestMapping(value = "/api")
public class AuthApi {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserService userServiceImpl;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private  JwtUtils jwtUtils;

    @PostMapping(value = "/signin")
    public ResponseEntity<?> singin(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtReponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles));
    }

//    @PostMapping(value = "/signup")
//    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest){
//        if(userRepository.existsByUsername(signupRequest.getUsername())){
//            return ResponseEntity.badRequest().body("Error: username is already taken!");
//        }
//        UserEntity user = new UserEntity();
//        user.setUsername(signupRequest.getUsername());
//        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
//
//        Set<String> strRoles = signupRequest.getRoles();
//        Set<RoleEntity> roles = new HashSet<>();
//
//        if(strRoles == null){
//            List<RoleEntity> rolelst = roleRepository.findByName("ROLE_USER");
//            roles.addAll(roles);
//        }else {
//            strRoles.forEach( role ->{
//                List<RoleEntity> rolelst = roleRepository.findByName(role);
//                roles.addAll(roles);
//            });
//        }
//        user.setRoles(roles);
//        userRepository.save(user);
//        return ResponseEntity.ok(HttpStatus.OK);
//    }

    @PostMapping(value = "/signup")
    public ResponseEntity<?> signup(@RequestBody User user){
        if(userRepository.existsByUsername(user.getUsername())){
            return ResponseEntity.badRequest().body("Error: username is already taken!");
        }
        userServiceImpl.add(user, "user");
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

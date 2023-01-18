package com.example.testing;

import com.example.testing.domain.user.User;
import com.example.testing.domain.user.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
})
@RequestMapping("/demo")
@RequiredArgsConstructor
public class MainController {

    private final UserRepository userRepository;

    @Operation(summary = "헬프")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found User", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
    })
    @PostMapping("/add")
    public User addNewUser(@RequestParam String name, @RequestParam String email) {
        User newUser = new User(null, name, email);
        userRepository.save(newUser);
        return newUser;
    }

    @Operation(summary = "겟")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Found User", content = {
//                    @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
//    })
    @ApiResponse(responseCode = "200", description = "Found User", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = User.class)))})
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userRepository.findAll();

    }

    @GetMapping("/")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "example", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Dto.class))}),
    })
    public Dto index() {
        return new Dto("404", "OK", "Hello World");
    }
}

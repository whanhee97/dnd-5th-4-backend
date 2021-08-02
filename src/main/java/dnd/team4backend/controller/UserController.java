package dnd.team4backend.controller;

import com.google.gson.JsonObject;
import dnd.team4backend.domain.User;
import dnd.team4backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "user")
    public String signUp(@RequestBody UserForm userForm) {
        User user = new User();
        user.addBasicInfo("fjsklaj-asjfdkl-fjjfi-123", userForm.getName(), userForm.getGender(), userForm.getAge(), userForm.getConstitution());
        try {
            userService.join(user);

            JsonObject obj = new JsonObject();

            obj.addProperty("success", true);
            obj.addProperty("msg", "회원 등록 완료");

            JsonObject data = new JsonObject();

            data.addProperty("name", userForm.getName());
            data.addProperty("gender", userForm.getGender().toString());
            data.addProperty("age", userForm.getAge());
            data.addProperty("constitution", userForm.getConstitution().toString());
            obj.add("data", data);

            return obj.toString();

        } catch (IllegalStateException e) {
            JsonObject obj = new JsonObject();

            obj.addProperty("success", false);
            obj.addProperty("msg", e.getMessage());

            return obj.toString();
        }

    }
}

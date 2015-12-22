
    package com.theironyard;
    import com.fasterxml.jackson.core.JsonProcessingException;
    import com.theironyard.Entities.Post;
    import com.theironyard.Entities.User;
    import com.theironyard.Entities.Wedding;
    import com.theironyard.Services.PostRepository;
    import com.theironyard.Services.UserRepository;
    import com.theironyard.Services.WeddingRepository;
    import com.theironyard.Utilities.PasswordHash;
    import org.junit.Before;
    import org.junit.Test;
    import org.junit.runner.RunWith;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.test.context.web.WebAppConfiguration;
    import org.springframework.boot.test.SpringApplicationConfiguration;
    import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
    import org.springframework.test.web.servlet.MockMvc;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
    import org.springframework.test.web.servlet.setup.MockMvcBuilders;
    import org.springframework.web.context.WebApplicationContext;

    import static org.junit.Assert.assertTrue;

    @RunWith(SpringJUnit4ClassRunner.class)
    @SpringApplicationConfiguration(classes = WeDayApplication.class)
    @WebAppConfiguration
    public class WeDayApplicationTests {

        @Autowired
        WeddingRepository weddings;

        @Autowired
        UserRepository users;

        @Autowired
        PostRepository posts;

        @Autowired
        WebApplicationContext wap;

        MockMvc mockMvc;

        @Before
        public void before() {
            //weddings.deleteAll();
            users.deleteAll();
            posts.deleteAll();

            mockMvc = MockMvcBuilders.webAppContextSetup(wap).build();
        }

        @Test
        public void testLogin() throws Exception {

            users.deleteAll();

            String password = "password";

            User user = new User();
            user.email = "nathan@gmail.com";
            user.username = "Nathan";
            user.password = PasswordHash.createHash(password);
            user.phone = "123-4567";
            users.save(user);

            mockMvc.perform(
                    MockMvcRequestBuilders.post("/login")
                            .param("password", password)
                            .param("email", user.email)
            );

            assertTrue(users.count() == 1 && PasswordHash.validatePassword(password, user.password));
        }

        @Test
        public void createUser() throws Exception {
            users.deleteAll();

            String password = "password";

            User user = new User();
            user.email = "nathan@gmail.com";
            user.username = "Nathan";
            user.password = PasswordHash.createHash(password);
            user.phone = "123-4567";
            users.save(user);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(user);

            mockMvc.perform(
                    MockMvcRequestBuilders.post("/create-user")
                            .content(json)
                            .contentType("application/json")
            );

            assertTrue(users.count() == 1);
        }
        @Test
        public void createWedding() throws Exception {
            weddings.deleteAll();
            users.deleteAll();

            String password = "password";


            User user = new User();
            user.email = "nathan@gmail.com";
            user.username = "Nathan";
            user.password = PasswordHash.createHash(password);
            user.phone = "123-4567";
            users.save(user);

            Wedding wedding = new Wedding();
            wedding.location = "Charleston";
            wedding.date = "date";
            wedding.weddingName = "Nathan's Wedding";

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(wedding);

            mockMvc.perform(
                    MockMvcRequestBuilders.post("/create-wedding")
                            .content(json)
                            .contentType("application/json")
                            .sessionAttr("email",user.email)

            );

            assertTrue(weddings.count() ==1);


        }

    }

//        @Test
//        public void testNotification() throws Exception {
//            User user = new User();
//            user.email = "nathan@gmail.com";
//            user.username = "Nathan Martin";
//            user.zip = "12345";
//            user.password = "password";
//            user.address = "123 Fake St";
//            user.phone = "+18435180835";
//            users.save(user);
//
//            User user1 = new User();
//            user1.email = "charleslane@gmail.com";
//            user1.username = "Charles Lane";
//            user1.zip = "12345";
//            user1.password = "password";
//            user1.address = "123 Fake St";
//            user1.phone = "+18436479951";
//            users.save(user1);
//
//            String body = "Charles. if you're reading this our notifications work. ";
//
//            mockMvc.perform(
//                    MockMvcRequestBuilders.post("/send-notification")
//                            .param("body",body)
//            );
//
//
//        }






















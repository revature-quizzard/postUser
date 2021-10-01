package com.revature.post_user;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.revature.post_user.dtos.UserDTO;
import com.revature.post_user.models.User;
import software.amazon.awssdk.http.HttpStatusCode;

public class PostUserHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final Gson mapper = new GsonBuilder().setPrettyPrinting().create();
    private final UserRepository userRepository;

    public PostUserHandler() {
        userRepository = new UserRepository();
    }

    public PostUserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * This function will take an id and username from that is obtained when Cognito registers. Then, it
     * will create an empty User model from a template. We do this to store additional fields that Cognito
     * does not handle.
     *
     * @param requestEvent contains id and username from Cognito
     * @return
     * @author Robert Ni
     */
    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent requestEvent, Context context) {
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();

        LambdaLogger logger = context.getLogger();
        logger.log("RECEIVED EVENT: " + requestEvent);

        UserDTO userDTO = mapper.fromJson(requestEvent.getBody(), UserDTO.class);

        if (userDTO == null || !userDTO.isValid()) {
            responseEvent.setStatusCode(HttpStatusCode.BAD_REQUEST);
            responseEvent.setBody("The provided request body is invalid");
            return responseEvent;
        }

        User user = new User(userDTO);
        responseEvent.setBody(mapper.toJson(userRepository.saveUser(user)));
        return responseEvent;
    }
}

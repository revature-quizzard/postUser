package com.revature.post_user;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PostUserHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final Gson mapper = new GsonBuilder().setPrettyPrinting().create();
    private final UserRepository userRepository = UserRepository.getInstance();

    /**
     * This function is used to persist user data to DynamoDB
     *
     * @param requestEvent will contain information in body
     * @return
     */
    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent requestEvent, Context context) {
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();

        LambdaLogger logger = context.getLogger();
        logger.log("RECEIVED EVENT: " + requestEvent);

        // this is the information sent from the client
        UserDTO userDTO = mapper.fromJson(requestEvent.getBody(), UserDTO.class);

        // mapping the info from request to user
        User user = new User(userDTO);

        // store user in database
        responseEvent.setBody(mapper.toJson(userRepository.saveUser(user)));

        return responseEvent;
    }
}

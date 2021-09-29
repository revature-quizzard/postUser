package com.revature.post_user;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.revature.post_user.stubs.TestLogger;
import org.junit.jupiter.api.*;
import software.amazon.awssdk.http.HttpStatusCode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PostUserHandlerTest {

    static TestLogger testLogger;
    static final Gson mapper = new GsonBuilder().setPrettyPrinting().create();

    PostUserHandler sut;
    Context mockContext;
    UserRepository mockUserRepository;

    @BeforeAll
    public static void beforeAllTests() {
        testLogger = new TestLogger();
    }

    @AfterAll
    public static void afterAllTests() {
        testLogger.close();
    }

    @BeforeEach
    public void beforeEachTest() {
        mockUserRepository = mock(UserRepository.class);
        sut = new PostUserHandler(mockUserRepository);
        mockContext = mock(Context.class);
        when(mockContext.getLogger()).thenReturn(testLogger);
    }

    @AfterEach
    public void afterEachTest() {
        sut = null;
        reset(mockContext, mockUserRepository);
    }

    @Test
    public void given_validRequestBody_persistUserToDynamoDB() {
        UserDTO validRequest = UserDTO.builder()
                .id("valid")
                .username("valid")
                .build();

        User expectedUser = new User(validRequest);

        APIGatewayProxyRequestEvent mockRequestEvent = new APIGatewayProxyRequestEvent();
        mockRequestEvent.withPath("/users");
        mockRequestEvent.withHttpMethod("POST");
        mockRequestEvent.withHeaders(null);
        mockRequestEvent.withBody(mapper.toJson(validRequest));
        mockRequestEvent.withQueryStringParameters(null);

        when(mockUserRepository.saveUser(any())).thenReturn(expectedUser);

        APIGatewayProxyResponseEvent expectedResponse = new APIGatewayProxyResponseEvent();
        expectedResponse.setBody(mapper.toJson(expectedUser));

        APIGatewayProxyResponseEvent actualResponse = sut.handleRequest(mockRequestEvent, mockContext);

        verify(mockUserRepository, times(1)).saveUser(any());
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void given_emptyRequestBody_returnsBadRequestStatusCode() {
        APIGatewayProxyRequestEvent mockRequestEvent = new APIGatewayProxyRequestEvent();
        mockRequestEvent.withPath("/users");
        mockRequestEvent.withHttpMethod("POST");
        mockRequestEvent.withHeaders(null);
        mockRequestEvent.withBody(null);
        mockRequestEvent.withQueryStringParameters(null);

        APIGatewayProxyResponseEvent expectedResponse = new APIGatewayProxyResponseEvent();
        expectedResponse.setStatusCode(HttpStatusCode.BAD_REQUEST);

        APIGatewayProxyResponseEvent actualResponse = sut.handleRequest(mockRequestEvent, mockContext);

        verify(mockUserRepository, times(0)).saveUser(any());
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void given_invalidRequestBody_returnsBadRequestStatusCode() {
        UserDTO invalidRequestBody = UserDTO.builder()
                .id("valid")
                .username(null)
                .build();

        APIGatewayProxyRequestEvent mockRequestEvent = new APIGatewayProxyRequestEvent();
        mockRequestEvent.withPath("/users");
        mockRequestEvent.withHttpMethod("POST");
        mockRequestEvent.withHeaders(null);
        mockRequestEvent.withBody(mapper.toJson(invalidRequestBody));
        mockRequestEvent.withQueryStringParameters(null);

        when(mockUserRepository.saveUser(any())).thenReturn(null);

        APIGatewayProxyResponseEvent expectedResponse = new APIGatewayProxyResponseEvent();
        expectedResponse.setStatusCode(HttpStatusCode.BAD_REQUEST);

        APIGatewayProxyResponseEvent actualResponse = sut.handleRequest(mockRequestEvent, mockContext);

        verify(mockUserRepository, times(0)).saveUser(any());
        assertEquals(expectedResponse, actualResponse);
    }
}
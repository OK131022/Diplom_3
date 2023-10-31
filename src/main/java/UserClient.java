import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;



public class UserClient {
    public static final String CREATE_URI="https://stellarburgers.nomoreparties.site/api/auth/register";
    public static final String USER_DATA_URI ="https://stellarburgers.nomoreparties.site/api/auth/user";

    public ValidatableResponse createUser(CreateUser createUserRequest){
        return given()
                .contentType(ContentType.JSON)
                .body(createUserRequest)
                .when()
                .post(CREATE_URI)
                .then();
    }

    public void deleteUser(String accessToken){
        given()
                .header("authorization",accessToken)
                .contentType(ContentType.JSON)
                .when()
                .delete(USER_DATA_URI);
    }
}
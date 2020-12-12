package Api;

import com.example.alpha_pharma.LoginModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Loginint {
    @POST("/login")
    Call<LoginModel> sendLogin(@Body LoginModel loginModel);
}

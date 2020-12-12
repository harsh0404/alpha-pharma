package Api;

import com.example.alpha_pharma.LoginModel;
import com.example.alpha_pharma.RegModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Registerinter {
    @POST("/Register")
    Call<RegModel> sendRegister(@Body RegModel regModel);


}

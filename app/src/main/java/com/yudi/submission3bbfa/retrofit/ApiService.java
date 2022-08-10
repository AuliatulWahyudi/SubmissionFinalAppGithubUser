package com.yudi.submission3bbfa.retrofit;

import com.yudi.submission3bbfa.model.DetailUserModel;
import com.yudi.submission3bbfa.model.FollowerUserModel;
import com.yudi.submission3bbfa.model.FollowingUserModel;
import com.yudi.submission3bbfa.model.ResponseUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

	@GET("search/users")
	@Headers("Authorization: token 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")
	Call<ResponseUser> getSearchUser(
			@Query("q") String username
	);

	@GET("users/{username}")
	@Headers("Authorization: token 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")
	Call<DetailUserModel> getDetailUser(
			@Path("username") String username
	);

	@GET("users/{username}/followers")
	@Headers("Authorization: token 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")
	Call<List<FollowerUserModel>> getFollowerUser(
			@Path("username") String username
	);

	@GET("users/{username}/following")
	@Headers("Authorization: token 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")
	Call<List<FollowingUserModel>> getFollowingUser(
			@Path("username") String username
	);
}

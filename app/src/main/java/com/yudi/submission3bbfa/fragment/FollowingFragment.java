package com.yudi.submission3bbfa.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yudi.submission3bbfa.DetailListActivity;
import com.yudi.submission3bbfa.R;
import com.yudi.submission3bbfa.adapter.FollowingAdapter;
import com.yudi.submission3bbfa.adapter.UserAdapter;
import com.yudi.submission3bbfa.model.FollowingUserModel;
import com.yudi.submission3bbfa.model.UserModel;
import com.yudi.submission3bbfa.retrofit.ApiClient;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowingFragment extends Fragment {
    RecyclerView rvFollowing;
    UserModel dataUser;

    public FollowingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false);
    }

    @Override
    public void onViewCreated(@NotNull final View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        //Mengambil data dari search user
        DetailListActivity detailUserActivity = (DetailListActivity) getActivity();
        Bundle bundle = detailUserActivity.getIntent().getBundleExtra(UserAdapter.DATA_EXTRA);
        dataUser = Parcels.unwrap(bundle.getParcelable(UserAdapter.DATA_USER));

        rvFollowing = view.findViewById(R.id.rv_following);
        rvFollowing.setLayoutManager(new LinearLayoutManager(view.getContext()));

        Call<List<FollowingUserModel>> request = ApiClient.getApiService().getFollowingUser(dataUser.getLogin());
        request.enqueue(new Callback<List<FollowingUserModel>>() {
            @Override
            public void onResponse(Call<List<FollowingUserModel>> call, Response<List<FollowingUserModel>> response) {
                ArrayList<FollowingUserModel> listFollowing = new ArrayList<>();
                if (response.isSuccessful()){
                    if (response.body() != null){
                        listFollowing.addAll(response.body());
                        Log.d("TAG RESULT", "onResponse: " +listFollowing.size());
                        rvFollowing.setAdapter(new FollowingAdapter(getContext(), listFollowing));
                    }
                }
                else {
                    Toast.makeText(getContext(), "Request Not Success", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<FollowingUserModel>> call, Throwable t) {
                Toast.makeText(getContext(), "Request Failure"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}

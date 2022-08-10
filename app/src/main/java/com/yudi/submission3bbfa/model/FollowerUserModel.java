package com.yudi.submission3bbfa.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class FollowerUserModel {

	@SerializedName("avatar_url")
	private String avatarUrl;

	@SerializedName("id")
	private int id;

	@SerializedName("login")
	private String login;

	public void setAvatarUrl(String avatarUrl){
		this.avatarUrl = avatarUrl;
	}

	public String getAvatarUrl(){
		return avatarUrl;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setLogin(String login){
		this.login = login;
	}

	public String getLogin(){
		return login;
	}
}
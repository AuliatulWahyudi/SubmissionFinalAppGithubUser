package com.yudi.submission3bbfa.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
@Entity(tableName = UserModel.TABLE_NAME)
public class UserModel {

	public static final String TABLE_NAME = "favorite";

	@SerializedName("id")
	@Expose
	@PrimaryKey(autoGenerate = true)
	private int id;

	@SerializedName("avatar_url")
	@Expose
	@ColumnInfo(name = "avatarUrl")
	private String avatarUrl;

	@SerializedName("login")
	@Expose
	@ColumnInfo(name = "login")
	private String login;

	@SerializedName("gravatar_id")
	private String gravatarId;

	@SerializedName("url")
	private String url;

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

	public void setGravatarId(String gravatarId){
		this.gravatarId = gravatarId;
	}

	public String getGravatarId(){
		return gravatarId;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}
}
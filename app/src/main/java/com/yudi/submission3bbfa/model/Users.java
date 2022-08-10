package com.yudi.submission3bbfa.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *  Created by Auliatul Wahyudi on 30/06/22
 */
public class Users implements Parcelable
{
	private String name;
	private String location;
	private String user_company;
	private String username;
	private String followers;
	private String following;
	private String repo;
	private String year;
	private int poster;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getUser_company() {
		return user_company;
	}

	public void setUser_company(String user_company) {
		this.user_company = user_company;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFollowers() {
		return followers;
	}

	public void setFollowers(String followers) {
		this.followers = followers;
	}

	public String getFollowing() {
		return following;
	}

	public void setFollowing(String following) {
		this.following = following;
	}

	public String getRepo() {
		return repo;
	}

	public void setRepo(String repo) {
		this.repo = repo;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public int getPoster() {
		return poster;
	}

	public void setPoster(int poster) {
		this.poster = poster;
	}

	public Users()
	{

	}

	protected Users(Parcel in)
	{
		name = in.readString();
		location = in.readString();
		user_company = in.readString();
		username = in.readString();
		followers = in.readString();
		following = in.readString();
		repo = in.readString();
		year = in.readString();
		poster = in.readInt();
	}

	public static final Creator<Users> CREATOR = new Creator<Users>() {
		@Override
		public Users createFromParcel(Parcel in) {
			return new Users(in);
		}

		@Override
		public Users[] newArray(int size) {
			return new Users[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeString(name);
		dest.writeString(location);
		dest.writeString(user_company);
		dest.writeString(username);
		dest.writeString(followers);
		dest.writeString(following);
		dest.writeString(repo);
		dest.writeString(year);
		dest.writeInt(poster);
	}
}

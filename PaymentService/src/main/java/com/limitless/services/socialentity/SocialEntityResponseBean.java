package com.limitless.services.socialentity;

public class SocialEntityResponseBean {

	private String totalViews;
	private String likes;
	private boolean like;
	private String shares;
	private String followers;
	private boolean follow;

	public boolean isLike() {
		return like;
	}

	public void setLike(boolean like) {
		this.like = like;
	}

	public boolean isFollow() {
		return follow;
	}

	public void setFollow(boolean follow) {
		this.follow = follow;
	}

	public String getTotalViews() {
		return totalViews;
	}

	public void setTotalViews(String totalViews) {
		this.totalViews = totalViews;
	}

	public String getLikes() {
		return likes;
	}

	public void setLikes(String likes) {
		this.likes = likes;
	}

	public String getShares() {
		return shares;
	}

	public void setShares(String shares) {
		this.shares = shares;
	}

	public String getFollowers() {
		return followers;
	}

	public void setFollowers(String followers) {
		this.followers = followers;
	}

}

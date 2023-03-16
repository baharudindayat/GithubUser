package com.baharudindayat.githubuser.apiservice

import com.google.gson.annotations.SerializedName

data class GithubResponse(

	@field:SerializedName("total_count")

	val totalCount: Int,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean,

	@field:SerializedName("items")
	val items: List<ItemsItem>
)

data class ItemsItem(

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,
)

data class DetailUser(

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("company")
	val company: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("location")
	val location: String,

	@field:SerializedName("following")
	val following: Int,

	@field:SerializedName("followers")
	val followers: Int

	)

data class Following(

	@field:SerializedName("Following")
	val following: List<FollowingItem>
)

data class FollowingItem(

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String


)

data class Followers(

	@field:SerializedName("Followers")
	val followers: List<FollowersItem>
)

data class FollowersItem(

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String

)

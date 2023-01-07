package spotify.automation;

import org.testng.Assert;

import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class SpotifyTest {
	
	String token = "Bearer BQCSjOrSg--RfxQVUGc7oRSvtjGK3yi7p_k3T6OTzODYraxMVzyVF6ZPXfqdbbRqZIYxTcVl6T2p7CpKgJuz1gNrOmBOjhU65DO8Q97mJEPpwS2ABRBQRbVlep8AQJluFIXnysdUED4vrRdWEasD3RJ0XmC0zCHrNdMyFKcTL7QtSK1yRzMdlP8kRytDaNZL8Ilwm8Rfqvd_lZulZ7dTEUP6LcmIZ5zmJAEjFl4IjDkj48ottzgTRO2hLN03QztlkV9ZfcXvF0o7NEhXlCoQXQ";
	String user_id ;	
	
	@Test(priority=1)
	public void getCurrentUsersProfile () {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/me");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
		
	    String user_name = respo.path("display_name");
		System.out.println("user name :- " + user_name);	
		
		String user_email = respo.path("email");
		System.out.println("user email id :- " + user_email);
		
		user_id = respo.path("id");
		System.out.println("user id :- " + user_id);
	
	}
	
	@Test(priority=2)
	public void getUsersProfile() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/users/"+ user_id);
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
		String user_email = respo.path("email");
		System.out.println("user email id :- " + user_email);	
	}
	
	@Test(priority=3)
	public void getCurrentUsersPlaylists() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/me/playlists");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	
	@Test(dependsOnMethods="getCurrentUsersProfile")
	public void createPlaylist() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.body("{\"name\":\"Adithya Playlist\",\"description\":\"New playlist description\",\"public\":false}")
				.when()
				.post("https://api.spotify.com/v1/users/"+ user_id+"/playlists");
		respo.prettyPrint();
		respo.then().statusCode(201);
		Assert.assertEquals(respo.statusCode(),201);
		String userPlaylist_id = respo.path("id");
		System.out.println("playlist_id :" +userPlaylist_id);
	}
	String userPlaylist_id = "0P1IaLPZtuvGX5oCfbBV6Z";
	@Test(priority=4)
	public void  addItemsToPlaylist() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.body("{\"uris\":[\"spotify:track:4iV5W9uYEdYUVa79Axb7Rh\",\"spotify:track:1301WleyT98MSxVHPZCA6M\",\"spotify:episode:512ojhOuo1ktJprKbVcKyQ\"]}")
				.when()
				.post("https://api.spotify.com/v1/playlists/"+ userPlaylist_id +"/tracks");
		respo.prettyPrint();
		respo.then().statusCode(201);
		Assert.assertEquals(respo.statusCode(),201);
	
	}
	
	@Test(priority=5)
	public void  updatePlaylistItems() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.body("{\"range_start\":1,\"insert_before\":3,\"range_length\":2}")
				.when()
				.put("https://api.spotify.com/v1/playlists/" + userPlaylist_id + "/tracks");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	
	@Test(priority=6)
	public void  changePlaylistDetails() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.body("{\"name\":\"Updated Adithya Playlist \",\"description\":\"Updated playlist description\",\"public\":false}")
				.when()
				.put("https://api.spotify.com/v1/playlists/" + userPlaylist_id);
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	
	@Test(priority=7)
	public void  removePlaylistItems() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.body("{\"tracks\":[{\"uri\":\"spotify:track:4iV5W9uYEdYUVa79Axb7Rh\"},{\"uri\":\"spotify:track:1301WleyT98MSxVHPZCA6M\"}]}")
				.when()
				.delete("https://api.spotify.com/v1/playlists/" + userPlaylist_id + "/tracks");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	
	@Test(priority=8)
	public void getPlaylistCoverImage() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/playlists/" + userPlaylist_id + "/images");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	
	@Test(priority=9)
	public void getPlaylistItems() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/playlists/" + userPlaylist_id + "/tracks");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	
	@Test(priority=10)
	public void getPlaylist() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/playlists/" + userPlaylist_id );
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	
	@Test(priority=11)
	public void getUsersPlaylists() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/users/"+user_id+"/playlists");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	
	@Test(priority=12)
	public void getTrack() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/tracks/11dFghVXANMlKmJXsNCbNl?market=ES");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	
	@Test(priority=13)
	public void getSeveralTracks() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/tracks?market=ES&ids=11dFghVXANMlKmJXsNCbNl%2C0P1IaLPZtuvGX5oCfbBV6Z");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	
	@Test(priority=14)
	public void getTracksAudioFeatures() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/audio-features/11dFghVXANMlKmJXsNCbNl");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	
	@Test(priority=15)
	public void getTracksAudioAnalysis() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/audio-analysis/11dFghVXANMlKmJXsNCbNl");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	@Test(priority=16)
	public void searchForItems_pathParam() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.pathParam("q", "avatar")
				.pathParam("type", "album")
				.when()
				.get("https://api.spotify.com/v1/search?q={q}&type={type}");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	
	@Test(priority=17)
	public void searchForItems_queryParam() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.queryParam("q", "avatar")
				.queryParam("type", "album")
				.when()
				.get("https://api.spotify.com/v1/search");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	@Test(priority=18)
	public void getSeveralShows() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/shows?ids=5CfCWKI5pZ28U0uOzXkDHe");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	
	@Test(priority=19)
	public void getShow() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/shows/38bS44xjbVVZ3No3ByF1dJ");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	@Test(priority=20)
	public void getShowEpisode() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/shows/38bS44xjbVVZ3No3ByF1dJ/episodes");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	@Test(priority=21)
	public void getSeveralAlbums() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/albums?ids=4aawyAB9vmqN3uQ7FjRGTy");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	@Test(priority=22)
	public void getAlbumTracks() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/albums/4aawyAB9vmqN3uQ7FjRGTy/tracks");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	@Test(priority=23)
	public void getArtistsAlbums() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/artists/0TnOYISbd1XYRBk9myaseg/albums");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	@Test(priority=24)
	public void getArtistsRelatedArtists() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/artists/0TnOYISbd1XYRBk9myaseg/related-artists");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	@Test(priority=25)
	public void getArtistsTopTracks() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/artists/0TnOYISbd1XYRBk9myaseg/top-tracks?market=ES");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	@Test(priority=26)
	public void getArtists() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/artists/0TnOYISbd1XYRBk9myaseg");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	@Test(priority=27)
	public void getSeveralArtists() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/artists?ids=2CIMQHirSU0MQqyYHq0eOx");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	@Test(priority=28)
	public void getSeveralAudiobooks() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/audiobooks?ids=7ouMYWpwJ422jRcDASZB7P%2C4VqPOruhp5EdPBeR92t6lQ%2C2takcwOaAZWiXQijPHIx7B");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	
	@Test(priority=29)
	public void getAvailableGenreSeeds() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/recommendations/available-genre-seeds");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	@Test(priority=30)
	public void getSeveralBrowseCategories() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/browse/categories?country=SE");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	@Test(priority=31)
	public void getSingleBrowseCategory() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/browse/categories/0JQ5DAqbMKFEOEBCABAxo9");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	@Test(priority=32)
	public void getCategorysPlaylists() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/browse/categories/0JQ5DAqbMKFEOEBCABAxo9/playlists");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	@Test(priority=33)
	public void getFeaturedPlaylists() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/browse/featured-playlists");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	@Test(priority=34)
	public void getNewReleases() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/browse/new-releases");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	@Test(priority=35)
	public void getRecommendations() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/recommendations?seed_artists=4NHQUGzhtTLFvgF5SZesLK&seed_genres=classical%2Ccountry&seed_tracks=0c6xIDDpzE81m2q797ordA");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	@Test(priority=36)
	public void getSeveralChapters() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/chapters?ids=7ouMYWpwJ422jRcDASZB7P%2C4VqPOruhp5EdPBeR92t6lQ%2C2takcwOaAZWiXQijPHIx7B");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	@Test(priority=37)
	public void getEpisode() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/episodes/512ojhOuo1ktJprKbVcKyQ");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	@Test(priority=38)
	public void getSeveralEpisodes() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/episodes?ids=7ouMYWpwJ422jRcDASZB7P&market=ES");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	@Test(priority=39)
	public void followPlaylists() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.body("{\"public\":false}")
				.when()
				.put("https://api.spotify.com/v1/playlists/0P1IaLPZtuvGX5oCfbBV6Z/followers");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	@Test(priority=40)	
	public void getFollowedArstist() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/me/following?type=artist&after=0I2XqVXqHScXjHhk6AYYRe");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	@Test(priority=41)	
	public void checkIfUserFollowsArtistsorUsers() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.when()
				.get("https://api.spotify.com/v1/me/following/contains?type=artist&ids=2CIMQHirSU0MQqyYHq0eOx");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	@Test(priority=42)
	public void unfollowPlaylist() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token)
				.when()
				.delete("https://api.spotify.com/v1/playlists/3cEYpjA9oz9GiPac4AsH4n/followers");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	String token2 = "Bearer BQCy1KmrYmPGGh3MiWl1_l3sjUyuTDZgNQsCERPnFrBAFf5nO-nyZijuqqSjEFdJZyI7sfn2SFcBxTn3jzbdDnO9tENT3Xna3hOarpeu2Z--A-1wFTbiH1zwtZcxuIpLW0Uwc60Jb1uJ17d8p4psR9S1KowvRA-sTCimVmrimMyMkMVL8SKFJxPXV9XhuakW_PMvuZi929YyF-hv9e_4oOT4_0TY0_is4LKWDIj-Ce2DfRlXQTyVf0sUc3A";
	@Test(priority=43)
	public void checkUsersSavedEpisodes() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token2)
				.when()
				.get("https://api.spotify.com/v1/me/episodes/contains?ids=77o6BIVlYM3msb4MMIL1jH");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	@Test(priority=44)
	public void checkUsersSavedAlbums() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token2)
				.when()
				.get("https://api.spotify.com/v1/me/albums/contains?ids=7ouMYWpwJ422jRcDASZB7P");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	@Test(priority=45)
	public void checkUsersSavedTracks() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token2)
				.when()
				.get("https://api.spotify.com/v1/me/tracks/contains?ids=7ouMYWpwJ422jRcDASZB7P");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	@Test(priority=46)
	public void checkUsersSavedAudiobooks() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token2)
				.when()
				.get("https://api.spotify.com/v1/me/audiobooks/contains?ids=7ouMYWpwJ422jRcDASZB7P");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	
	@Test(priority=47)
	public void getUsersSavedAlbums() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token2)
				.when()
				.get("https://api.spotify.com/v1/me/albums");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	@Test(priority=48)
	public void getUsersSavedEpisodes() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token2)
				.when()
				.get("https://api.spotify.com/v1/me/episodes");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	@Test(priority=49)
	public void getUsersSavedShows() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token2)
				.when()
				.get("https://api.spotify.com/v1/me/shows");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	@Test(priority=50)
	public void getUsersSavedTracks() {
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token2)
				.when()
				.get("https://api.spotify.com/v1/me/tracks");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
	@Test(priority=51)
	public void getAvailableMarkets(){
		Response respo = given()
				.header("Accept","application/json")
				.header("Content-Type","application/json")
				.header("Authorization",token2)
				.when()
				.get("https://api.spotify.com/v1/markets");
		respo.prettyPrint();
		respo.then().statusCode(200);
		Assert.assertEquals(respo.statusCode(),200);
	}
}














package com.bridelabz.rest.assured.testing;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RestAssured_ApiTesting {
    String token ="";
    String userId,playlistId;

    @BeforeTest
    public void setup() {
        token = "Bearer BQAYq9R9N2PGsubO0rJ6waaXexN8YmTTP-mzk4K-UmefQG5zzP0bIURdOTZCED0kgExfZdAHxtKJ7PRf-CNL5fBe8XsD_OlcOH6yrD7ep8VEWTstHIFV219Z2uRzNT9EgHGiPp8IeUSB2aDj4iMmqzsgLMH_vpgviQVtQPtaqOUYsZkfgsxJyoP6oSQjoYi3gUmigq5DZJo-YmMjcv68m_OtZ_fy9RgD8AJqvQi05AJlYJ5de3fMbaqdBcW9sm6zzua4XD6bi3hiyRf-M_Oyj7oI3ZfNSoPI_SswFUSt_SxiwmgCR8zZPpf1i3bHK9doRBpPjAdNizvYrw";
    }

    /***
     * GET CURRENT USER PROFILE  *
     * **/
    @Test(priority = 0)
    public void get_Current_User_Profile() {
        System.out.println("---------------Get Current User's Profile---------------");
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .get("https://api.spotify.com/v1/me");
        response.prettyPrint();
        userId = response.path("id");
        System.out.println("Current user Id : "+userId);
        response.then().assertThat().statusCode(200);
        Assert.assertEquals("31ss5unrjygeqtgzoir22gplqo5m",userId);
    }

    /***
     * GET USER PROFILE  *
     * **/
    @Test(priority = 1)
    public void get_User_Profile() {
        System.out.println("------------Get User's Profile--------------");
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/users/"+userId+"/");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }

    /***
     * CREATE PLAYLIST  *
     * **/
    @Test(priority = 2)
    public void playlist_creating(){
        System.out.println("-----------Create Playlist--------------");
        System.out.println("userid in create playlist : "+userId);
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .body("{\"name\":\"Create New Playlist\",\"description\":\"New Playlist Description\",\"public\":\"false\"}")
                .when()
                .post("https://api.spotify.com/v1/users/"+userId+"/playlists/");
        response.prettyPrint();
        playlistId = response.path("id");
        System.out.println("playlistId :"+playlistId);
        response.then().assertThat().statusCode(201);
        Assert.assertEquals(201,response.getStatusCode());
    }
    /***
     * GET PLAYLIST  *
     * **/
    @Test(priority = 3)
    public void playlist_get(){
        System.out.println("------------------Get Playlist---------------------------");
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/playlists/"+playlistId);
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }

    /***
     * GET USER PLAYLIST  *
     * **/
    @Test(priority = 4)
    public void get_UserPlayList(){
        System.out.println("-------------------Get User's Playlists--------------------------");
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/users/"+userId+"/playlists/");
        response.prettyPrint();
        String fourthPlayListId = response.path("items[0].owner.display_name");
        System.out.println("Fourth PlayList Owner Id :"+fourthPlayListId);
        Assert.assertEquals("Deepak",fourthPlayListId);
    }

    /***
     * UPDATE PLAYLIST DETAILS  *
     * **/
    @Test(priority = 5)
    public void playlist_UpdateDetails(){
        System.out.println("-----------Update Playlist Details--------------");
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .body("{\"name\":\"UpdateTest New Playlist\",\"description\":\"UpdateTest New Playlist Description\",\"public\":\"false\"}")
                .when()
                .put("https://api.spotify.com/v1/playlists/6Dq7N4KjFtaHxMCGZrGgsr/playlists/");
        response.prettyPrint();
    }

    /***
     * Add Items to Playlist  *
     * **/
    @Test(priority = 6)
    public void playlist_Item_Adding(){
        System.out.println("----------------Add Items to Playlist---------------------");
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .body("{\"uris\":[\"spotify:track:4iV5W9uYEdYUVa79Axb7Rh\",\"spotify:track:1301WleyT98MSxVHPZCA6M\",\"spotify:episode:512ojhOuo1ktJprKbVcKyQ\"]}")
                .when()
                .post("https://api.spotify.com/v1/playlists/6Dq7N4KjFtaHxMCGZrGgsr/tracks");
        response.prettyPrint();
        response.then().assertThat().statusCode(201);
        Assert.assertEquals(201,response.getStatusCode());
    }

    /***
     * Get Playlist Items  *
     * **/
    @Test(priority = 7)
    public void playList_Item_Get(){
        System.out.println("----------------------------Get Playlist Items----------------------------------");
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/playlists/6Dq7N4KjFtaHxMCGZrGgsr/tracks");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }

    /***
     * UPDATE PLAYLIST ITEM  *
     * **/
    @Test(priority = 8)
    public void playlist_Item_Update(){
        System.out.println("--------------------Update Playlist Items----------------------------");
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .body("{\"range_start\":1,\"insert_before\":3,\"range_length\":2}")
                .when()
                .put("https://api.spotify.com/v1/playlists/6Dq7N4KjFtaHxMCGZrGgsr/tracks");
        response.prettyPrint();
    }

    /***
     * DELETE PLAYLIST ITEM *
     * **/
    @Test(priority = 9)
    public void playlist_Item_Delete(){
        System.out.println("--------------------Update Playlist Items----------------------------");
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .when()
                .delete("https://api.spotify.com/v1/playlists/6Dq7N4KjFtaHxMCGZrGgsr/tracks");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());

    }

    /***
     * Get Current User’s PLAYLIST *
     * **/
    @Test(priority = 10)
    public void playlist_Get_Current_User(){
        System.out.println("--------------------Get Current User’s Playlists----------------------------");
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/me/playlists");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());

    }

    /***
     * GET PLAYLIST COVER IMAGE *
     * **/
    @Test(priority = 11)
    public void playlist_Get_Cover_Image(){
        System.out.println("--------------------Get Playlist Cover Image----------------------------");
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/playlists/6Dq7N4KjFtaHxMCGZrGgsr/images");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }

    /***
     *  SEARCH API ***/
    @Test(priority = 12)
    public void search_For_Item(){
        System.out.println("--------------------Search For ITEM----------------------------");
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/search?q=name:abacab&type=album,track,artist");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }

    /***
     * GET SEVERAL ALBUMS
     */
    @Test(priority = 13)
    public void get_Several_Album(){
        System.out.println("--------------------Get Several Albums----------------------------");
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/albums?ids=382ObEPsp2rxGrnsizN5TX");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }

    /***
     * GET ALBUMS Tracks
     */
    @Test(priority = 14)
    public void get_Album_Tracks(){
        System.out.println("--------------------Get Album Tracks----------------------------");
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/albums/382ObEPsp2rxGrnsizN5TX");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }

    /***
     * Get Several Artists
     */
    @Test(priority = 15)
    public void get_Several_Artists(){
        System.out.println("--------------------Get Several Artists----------------------------");
        Response response = given()
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/artists/0TnOYISbd1XYRBk9myaseg");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        Assert.assertEquals(200,response.getStatusCode());
    }
    
}

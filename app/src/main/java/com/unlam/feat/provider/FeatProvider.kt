package com.unlam.feat.provider

import com.unlam.feat.model.*
import com.unlam.feat.model.request.*
import retrofit2.Response
import retrofit2.http.*

interface FeatProvider {
    //<editor-fold desc="Events">
    @GET("/events/")
    suspend fun getEventsToday(): Response<List<Event>>

    @GET("/events/getAllEventSuggestedForUser/{uid}")
    suspend fun getEventsSuggestedForUser(@Path("uid") uid: String): Response<List<Event>>

    @GET("/events/getAllCreatedByUser/{uid}")
    suspend fun getEventsCreatedByUser(@Path("uid") uid: String): Response<List<Event>>

    @GET("/events/getAllByOrganizer/{organizer}")
    suspend fun getEventsByOrganizer(@Path("organizer") organizer: Int): Response<List<Event>>

    @GET("/events/getAllApplied/{uid}")
    suspend fun getEventsApplied(@Path("uid") uid: String): Response<List<Event>>

    @GET("/events/getAllConfirmed/{uid}")
    suspend fun getEventsConfirmed(@Path("uid") uid: String): Response<List<Event>>

    @GET("/events/getAllByUser/{uid}")
    suspend fun getEventsByUser(@Path("uid") uid: String): Response<List<Event>>

    @GET("/events/getEventById/{id}")
    suspend fun getEventById(@Path("id") id: Int): Response<Event>

    @Headers("Content-type: application/json")
    @POST("/events/create")
    suspend fun postEvent(@Body requestEvent: RequestEvent): Response<String>
    //</editor-fold>
    //<editor-fold desc="Availabilities">
    @GET("/availabilities/")
    suspend fun getAvailabilities(): Response<List<Availability>>

    @GET("/availabilities/{id}")
    suspend fun getAvailability(@Path("id") id: Int): Response<Availability>

    @POST("/availabilities/create")
    suspend fun createAvailability(@Body req: RequestAvailability): Response<String>

    @POST("/availabilities/update")
    suspend fun updateAvailability(
        @Path("id") id: Int,
        @Body req: RequestAvailability
    ): Response<String>
    //</editor-fold>
    //<editor-fold desc="Levels">
    @GET("/levels/")
    suspend fun getLevels(): Response<List<Level>>

    @GET("/levels/{id}")
    suspend fun getLevel(@Path("id") id: Int): Response<Level>

    @POST("/levels/create")
    suspend fun createLevel(@Body req: RequestLevel): Response<String>
    //</editor-fold>
    //<editor-fold desc="Periodicities">
    @GET("/periodicities/")
    suspend fun getPeriodicities(): Response<List<Periodicity>>

    @GET("/periodicities/{id}")
    suspend fun getPeriodicity(@Path("id") id: Int): Response<Periodicity>

    @GET("/periodicities/create")
    suspend fun createPeriodicity(@Body req: RequestPeriodicity): Response<String>
    //</editor-fold>
    //<editor-fold desc="Players">
    @GET("/players/")
    suspend fun getPlayers(): Response<List<Player>>

    @GET("/players/{id}")
    suspend fun getPlayer(@Path("id") id: Int): Response<Player>

    @GET("/players/getAllByPerson/{personId}")
    suspend fun getAllByPerson(@Path("personId") personId: Int): Response<List<Player>>

    @GET("/players/getAllPlayersSuggestedForEvent/{eventId}")
    suspend fun getAllPlayersSuggestedForEvent(@Path("eventId") eventId: Int): Response<List<Player>>

    @GET("/players/getAllConfirmedByEvent/{eventId}")
    suspend fun getAllPlayersConfirmedByEvent(@Path("eventId") eventId: Int): Response<List<Player>>

    @GET("/players/getAllAppliedByEvent/{eventId}")
    suspend fun getAllPlayersAppliedByEvent(@Path("eventId") eventId: Int): Response<List<Player>>

    @POST("/players/create")
    suspend fun createPlayer(@Body req: RequestPlayer): Response<String>
    //</editor-fold>
    //<editor-fold desc="Positions">
    @GET("/positions/")
    suspend fun getPositions(): Response<List<Position>>

    @GET("/positions/{id}")
    suspend fun getPosition(@Path("id") id: Int): Response<Position>

    @GET("/positions/create")
    suspend fun createPosition(@Body req: RequestPosition): Response<String>
    //</editor-fold>
    //<editor-fold desc="Sports">
    @GET("/sports/")
    suspend fun getSports(): Response<List<Sport>>

    @GET("/sports/{id}")
    suspend fun getSport(@Path("id") id: Int): Response<Sport>

    //</editor-fold>
    //<editor-fold desc="Users">
    @GET("/users/")
    suspend fun getUsers(): Response<List<User>>

    @GET("/users/{id}")
    suspend fun getUser(@Path("id") id: Int): Response<User>

    @Headers("Content-type: application/json")
    @POST("/users/create")
    suspend fun createUser(@Body requestEvent: RequestUser): Response<String>
    //</editor-fold>

    //<editor-fold desc="Persons">
    @GET("/persons/getPersonById/{id}")
    suspend fun getPerson(@Path("id") id: String): Response<Person>

    @Headers("Content-type: application/json")
    @POST("/persons/create")
    suspend fun createPerson(@Body requestEvent: RequestPerson): Response<String>

    @Headers("Content-type: application/json")
    @PUT("/persons/update/{id}")
    suspend fun updatePerson(@Body requestEvent: RequestPerson): Response<String>
    //</editor-fold>
    //<editor-fold desc="Sports">

    @GET("/sportsGeneric/")
    suspend fun getGenericsSports(): Response<List<SportGeneric>>

    //</editor-fold>
}
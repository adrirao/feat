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
    suspend fun getEventById(@Path("id") id: Int): Response<Event?>

    @Headers("Content-type: application/json")
    @POST("/events/create")
    suspend fun postEvent(@Body requestEvent: RequestEvent): Response<String>

    @GET("/events/getAllInvitationsForUser/{uid}")
    suspend fun getAllInvitationsForUser(@Path("uid") uid: String): Response<List<Event>>

    @Headers("Content-type: application/json")
    @PUT("/events/setConfirmed")
    suspend fun setConfirmed(@Body requestEventState: RequestEventState): Response<String>

    @Headers("Content-type: application/json")
    @PUT("/events/setCanceled")
    suspend fun setCanceled(@Body requestEventState: RequestEventState): Response<String>

    @GET("/events/getAllEventsOfTheWeek/{uid}")
    suspend fun getAllEventsOfTheWeek(@Path("uid") uid: String): Response<List<Event>>

    @GET("/events/getAllConfirmedOrAppliedByUser/{uid}")
    suspend fun getAllConfirmedOrAppliedByUser(@Path("uid") uid: String): Response<List<HomeEvent>>

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

    @GET("/levels/getAllBySportGeneric/{id}")
    suspend fun getAllLevelsBySportGeneric(@Path("id") id: Int): Response<List<Level>>

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

    @GET("/players/getAllByUser/{userUid}")
    suspend fun getPlayersByUser(@Path("userUid") userUid: String): Response<List<Player>>

    @GET("/players/getAllByPerson/{personId}")
    suspend fun getAllByPerson(@Path("personId") personId: Int): Response<List<Player>>

    @GET("/players/getAllPlayersSuggestedForEvent/{eventId}")
    suspend fun getAllPlayersSuggestedForEvent(@Path("eventId") eventId: Int): Response<List<Player>>

    @GET("/players/getAllConfirmedByEvent/{eventId}")
    suspend fun getAllPlayersConfirmedByEvent(@Path("eventId") eventId: Int): Response<List<Player>>

    @GET("/players/getAllAppliedByEvent/{eventId}")
    suspend fun getAllPlayersAppliedByEvent(@Path("eventId") eventId: Int): Response<List<PlayerApplyDetail>>

    @POST("/players/create")
    suspend fun createPlayer(@Body req: RequestPlayer): Response<String>

    @Headers("Content-type: application/json")
    @POST("/players/setDismissedFromList")
    suspend fun setKickApply (@Body requestEventApply: RequestEventApply): Response<String>

    //</editor-fold>
    //<editor-fold desc="Positions">
    @GET("/positions/")
    suspend fun getPositions(): Response<List<Position>>

    @GET("/positions/{id}")
    suspend fun getPosition(@Path("id") id: Int): Response<Position>

    @GET("/positions/getAllBySportGeneric/{id}")
    suspend fun getAllPositionsBySportGeneric(@Path("id") id: Int): Response<List<Position>>


    @GET("/positions/create")
    suspend fun createPosition(@Body req: RequestPosition): Response<String>
    //</editor-fold>

    //<editor-fold desc="Sports">
    @GET("/sports/")
    suspend fun getSports(): Response<List<Sport>>

    @GET("/sports/{id}")
    suspend fun getSport(@Path("id") id: Int): Response<Sport>
    //</editor-fold>

    //<editor-fold desc="SportsGenerics">
    @GET("/sportsGeneric/")
    suspend fun getGenericsSports(): Response<List<SportGeneric>>
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
    suspend fun createPerson(@Body requestPerson: RequestPerson): Response<String>

    @Headers("Content-type: application/json")
    @PUT("/persons/update")
    suspend fun updatePerson(@Body requestUpdatePerson: RequestUpdatePerson): Response<String>

    @Headers("Content-type: application/json")
    @PUT("/persons/update_personal_information")
    suspend fun updatePersonPersonalInformation(@Body requestUpdatePersonPersonalInformation: RequestUpdatePersonPersonalInformation): Response<String>

    @Headers("Content-type: application/json")
    @POST("/persons/createPersonTransaction")
    suspend fun createPersonTransaction(@Body requestPerson: RequestPersonTransaction): Response<String>
    //</editor-fold>

    //<editor-fold desc="Valuations">
    @GET("/valuations/")
    suspend fun getValuations(): Response<List<Valuation>>
    //</editor-fold>
    //<editor-fold desc="Sports">

    //<editor-fold desc="Addresses">
    @GET("/addresses/{id}")
    suspend fun getAddress(@Path("id") id: Int): Response<Address>

    @Headers("Content-type: application/json")
    @POST("/addresses/create")
    suspend fun addAddress(@Body requestAddress: RequestAddress): Response<String>
    //</editor-fold>
    //<editor-fold desc="EventApplies">
    @Headers("Content-type: application/json")
    @POST("/eventApplies/setAcceptedApply")
    suspend fun setAcceptedApply(@Body requestEventApply: RequestEventApply): Response<String>

    @Headers("Content-type: application/json")
    @POST("/eventApplies/setDeniedApply")
    suspend fun setDeniedApply (@Body requestEventApply: RequestEventApply): Response<String>

    //<editor-fold desc="Addresses">
    @GET("/addresses/{id}")
    suspend fun getAddressesByUser(@Path("id") id: String): Response<List<Address>>

    @Headers("Content-type: application/json")
    @PUT("/addresses/update")
    suspend fun updateAddress(@Body requestAddress: RequestAddress): Response<String>

    @Headers("Content-type: application/json")
    @POST("/addresses/create")
    suspend fun createAddress(@Body requestAddress: RequestAddress): Response<String>
    //</editor-fold>

    @Headers("Content-type: application/json")
    @POST("/eventApplies/create")
    suspend fun createInvitation(@Body requestCreateInvitation: RequestCreateInvitation): Response<String>

    @Headers("Content-type: application/json")
    @POST("/califications/qualifyPlayers")
    suspend fun qualifyPlayers(@Body requestQualifyPlayers : RequestQualifyPlayers): Response<String>

//    //</editor-fold>

}
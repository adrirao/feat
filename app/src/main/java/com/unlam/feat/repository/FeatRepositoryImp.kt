package com.unlam.feat.repository

import android.util.Log
import com.unlam.feat.util.Result
import com.unlam.feat.model.*
import com.unlam.feat.model.request.*
import com.unlam.feat.model.response.*
import com.unlam.feat.model.response.ResponseDataSport
import com.unlam.feat.model.response.ResponseDetailEvent
import com.unlam.feat.model.response.ResponseDetailProfile
import com.unlam.feat.provider.FeatProvider
import com.unlam.feat.util.print
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.Exception

@Singleton
class FeatRepositoryImp
@Inject
constructor(
    private val featProvider: FeatProvider
) : FeatRepository {
    //<editor-fold desc="Events">

    override fun getEventsToday(uId: String): Flow<Result<List<Event>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getEventsToday().body() ?: listOf()
            emit(Result.Success(data = response))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getEventsSuggestedForUser(uId: String): Flow<Result<List<Event>>> = flow {
        try {
            emit(Result.Loading())
            delay(600)
            val response = featProvider.getEventsSuggestedForUser(uId).body() ?: listOf()
            emit(Result.Success(data = response))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getEventsCreatedByUser(uId: String): Flow<Result<List<Event>>> = flow {
        try {
            emit(Result.Loading())
            delay(600)
            val response = featProvider.getEventsCreatedByUser(uId).body() ?: listOf()
            emit(Result.Success(data = response))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getEventsByOrganizer(organizer: Int): Flow<Result<List<Event>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getEventsByOrganizer(organizer).body() ?: listOf()
            emit(Result.Success(data = response))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getEventsApplied(uId: String): Flow<Result<List<Event>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getEventsApplied(uId).body() ?: listOf()
            emit(Result.Success(data = response))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getEventsConfirmed(uId: String): Flow<Result<List<Event>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getEventsConfirmed(uId).body() ?: listOf()
            emit(Result.Success(data = response))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getEventsByUser(uId: String): Flow<Result<List<Event>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getEventsByUser(uId)
            Log.e("rao", response.toString())
            emit(Result.Success(data = response.body() ?: listOf()))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getEventById(id: Int): Flow<Result<Event>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getEventById(id).body()
            if (response != null) emit(Result.Success(data = response)) else emit(
                Result.Error(
                    message = "Unknown Error"
                )
            )
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun postEvent(request: RequestEvent): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.postEvent(request).code()
            if (response in 200..299) emit(Result.Success(data = "Creado con exito")) else emit(
                Result.Error("Algo malo ocurrio.")
            )
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getAllInvitationsForUser(uId: String): Flow<Result<List<Event>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getAllInvitationsForUser(uId).body() ?: listOf()
            if (response != null) emit(Result.Success(data = response)) else emit(
                Result.Error(
                    message = "Unknown Error"
                )
            )
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }
    override fun setConfirmed(request:RequestEventState): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.setConfirmed(request).code()
            if (response in 200..299) emit(Result.Success(data = "Creado con exito")) else emit(
                Result.Error("Algo malo ocurrio.")
            )
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }
    override fun setCanceled(request:RequestEventState): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.setCanceled(request).code()
            if (response in 200..299) emit(Result.Success(data = "Creado con exito")) else emit(
                Result.Error("Algo malo ocurrio.")
            )
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getAllEventsOfTheWeek(uId: String): Flow<Result<List<Event>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getAllEventsOfTheWeek(uId).body() ?: listOf()
            if (response != null) emit(Result.Success(data = response)) else emit(
                Result.Error(
                    message = "Unknown Error"
                )
            )
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getAllConfirmedOrAppliedByUser(uId: String): Flow<Result<List<Event>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getAllConfirmedOrAppliedByUser(uId).body() ?: listOf()
            if (response != null) emit(Result.Success(data = response)) else emit(
                Result.Error(
                    message = "Unknown Error"
                )
            )
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }



    //</editor-fold desc="Events">
    //<editor-fold desc="Availabilities">

    override fun getAvailabilities(): Flow<Result<List<Availability>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getAvailabilities().body() ?: listOf()
            emit(Result.Success(data = response))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getAvailability(id: Int): Flow<Result<Availability>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getAvailability(id).body()
            if (response != null) emit(Result.Success(data = response)) else emit(
                Result.Error(
                    message = "Unknown Error"
                )
            )

        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun createAvailability(req: RequestAvailability): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.createAvailability(req).code()
            if (response in 200..299) emit(Result.Success(data = "Creado con exito")) else emit(
                Result.Error("Algo malo ocurrio.")
            )
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }
    //</editor-fold desc="Availabilities">
    //<editor-fold desc="Levels">

    override fun getLevels(): Flow<Result<List<Level>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getLevels().body() ?: listOf()
            emit(Result.Success(data = response))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getLevel(id: Int): Flow<Result<Level>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getLevel(id).body()
            if (response != null) emit(Result.Success(data = response)) else emit(
                Result.Error(
                    message = "Unknown Error"
                )
            )
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getAllLevelsBySportGeneric(id: Int): Flow<Result<List<Level>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getAllLevelsBySportGeneric(id).body()
            if (response != null) emit(Result.Success(data = response)) else emit(
                Result.Error(
                    message = "Unknown Error"
                )
            )
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun createLevel(req: RequestLevel): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.createLevel(req).code()
            if (response in 200..299) emit(Result.Success(data = "Creado con exito")) else emit(
                Result.Error("Algo malo ocurrio.")
            )
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }
    //</editor-fold desc="Levels">
    //<editor-fold desc="Periodicities">

    override fun getPeriodicities(): Flow<Result<List<Periodicity>>> = flow {
        try {
            emit(Result.Loading())
            val periodicity = featProvider.getPeriodicities().body() ?: emptyList()
            emit(Result.Success(data = periodicity))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getPeriodicity(id: Int): Flow<Result<Periodicity>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getPeriodicity(id).body()
            if (response != null) emit(Result.Success(data = response)) else emit(
                Result.Error(
                    message = "Unknown Error"
                )
            )
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun createPeriodicity(req: RequestPeriodicity): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.createPeriodicity(req).code()
            if (response in 200..299) emit(Result.Success(data = "Creado con exito")) else emit(
                Result.Error("Algo malo ocurrio.")
            )
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }
    //</editor-fold desc="Periodicities">
    //<editor-fold desc="Players">

    override fun getPlayers(): Flow<Result<List<Player>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getPlayers().body() ?: listOf()
            emit(Result.Success(data = response))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getPlayer(id: Int): Flow<Result<Player>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getPlayer(id).body()
            if (response != null) emit(Result.Success(data = response)) else emit(
                Result.Error(
                    message = "Unknown Error"
                )
            )
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getPlayersByUser(userUid: String): Flow<Result<List<Player>>> = flow{
        try {
            emit(Result.Loading())
            val response = featProvider.getPlayersByUser(userUid).body() ?: listOf()
            emit(Result.Success(data = response))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getAllByPerson(personId: Int): Flow<Result<List<Player>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getAllByPerson(personId).body() ?: listOf()
            emit(Result.Success(data = response))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getAllPlayersSuggestedForEvent(eventId: Int): Flow<Result<List<Player>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getAllPlayersSuggestedForEvent(eventId).body() ?: listOf()
            emit(Result.Success(data = response))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getAllPlayersConfirmedByEvent(eventId: Int): Flow<Result<List<Player>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getAllPlayersConfirmedByEvent(eventId).body() ?: listOf()
            emit(Result.Success(data = response))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getAllPlayersAppliedByEvent(eventId: Int): Flow<Result<List<Player>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getAllPlayersAppliedByEvent(eventId).body() ?: listOf()
            emit(Result.Success(data = response))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun createPlayer(req: RequestPlayer): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.createPlayer(req).code()
            if (response in 200..299) emit(Result.Success(data = "Creado con exito")) else emit(
                Result.Error("Algo malo ocurrio.")
            )
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    //</editor-fold desc="Players">
    //<editor-fold desc="Positions">

    override fun getPositions(): Flow<Result<List<Position>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getPositions().body() ?: listOf()
            emit(Result.Success(data = response))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getPosition(id: Int): Flow<Result<Position>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getPosition(id).body()
            if (response != null) emit(Result.Success(data = response)) else emit(
                Result.Error(
                    message = "Unknown Error"
                )
            )
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getAllPositionsBySportGeneric(id: Int): Flow<Result<List<Position>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getAllPositionsBySportGeneric(id).body()
            if (response != null) emit(Result.Success(data = response)) else emit(
                Result.Error(
                    message = "Unknown Error"
                )
            )
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun createPosition(req: RequestPosition): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.createPosition(req).code()
            if (response in 200..299) emit(Result.Success(data = "Creado con exito")) else emit(
                Result.Error("Algo malo ocurrio.")
            )
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }
    //</editor-fold desc="Positions">
    //<editor-fold desc="Sports">

    override fun getSports(): Flow<Result<List<Sport>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getSports().body() ?: listOf()
            emit(Result.Success(data = response))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getSport(id: Int): Flow<Result<Sport>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getSport(id).body()
            if (response != null) emit(Result.Success(data = response)) else emit(
                Result.Error(
                    message = "Unknown Error"
                )
            )
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getGenericsSports(): Flow<Result<List<SportGeneric>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getGenericsSports().body()
            if (response != null) emit(Result.Success(data = response)) else emit(
                Result.Error(
                    message = "Unknown Error"
                )
            )
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    //</editor-fold desc="Sports">
    //<editor-fold desc="Users">

    override fun getUsers(): Flow<Result<List<User>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getUsers().body() ?: listOf()
            emit(Result.Success(data = response))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getUser(id: Int): Flow<Result<User>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getUser(id).body()
            if (response != null) emit(Result.Success(data = response)) else emit(
                Result.Error(
                    message = "Unknown Error"
                )
            )
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }


    override fun createUser(req: RequestUser): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.createUser(req)
            if (response.code() in 200..299) emit(Result.Success(data = "Creado con exito")) else emit(
                Result.Error("Algo malo ocurrio.")
            )
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }



    //</editor-fold desc="Users">

    //<editor-fold desc="Persons">
    override fun getPerson(uId: String): Flow<Result<Person>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getPerson(uId).body()
            emit(Result.Success(data = response))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun createPerson(req: RequestPerson): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.createPerson(req).code()
            if (response in 200..299) emit(Result.Success(data = "Creado con exito")) else emit(
                Result.Error("Algo malo ocurrio.")
            )
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun updatePerson(req: RequestUpdatePerson): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.updatePerson(req).code()
            if (response in 200..299) emit(Result.Success(data = "Actualizado con exito")) else emit(
                Result.Error("Algo malo ocurrio.")
            )
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun updatePersonPersonalInformation(req: RequestUpdatePersonPersonalInformation): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.updatePersonPersonalInformation(req).code()
            if (response in 200..299) emit(Result.Success(data = "Actualizado con exito")) else emit(
                Result.Error("Algo malo ocurrio.")
            )
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }


    //</editor-fold desc="Persons">
    //<editor-fold desc="Valuations">
    override fun getValuations(): Flow<Result<List<Valuation>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getValuations().body()
            emit(Result.Success(data = response))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }
    //<editor-fold desc="Valuations">
    //<editor-fold desc="Addresses">
    override fun getAddress(personId:Int): Flow<Result<Address>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getAddress(personId).body()
            emit(Result.Success(data = response))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }


    override fun addAddress(req: RequestAddress): Flow<Result<String>> = flow {
        try {
            Log.d("DIRECCION", req.toString())
            emit(Result.Loading())
            val response = featProvider.addAddress(req).code()
            if (response in 200..299) emit(Result.Success(data = "Agregada con exito")) else emit(
                Result.Error("Algo malo ocurrio.")
            )
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }
    //<editor-fold desc="Addresses">
    //<editor-fold desc="EventApplies">
    override fun setAcceptedApply(req: RequestEventApply): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.setAcceptedApply(req).code()
            if (response in 200..299) emit(Result.Success(data = "Creado con exito")) else {
                emit(
                    Result.Error("Algo malo ocurrio.")
                )
                print(req,response)
            }
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }
    override fun setDeniedApply(req: RequestEventApply): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.setDeniedApply(req).code()
            if (response in 200..299) emit(Result.Success(data = "Creado con exito")) else emit(
                Result.Error("Algo malo ocurrio.")
            )
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun setKickApply(req: RequestEventApply): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.setKickApply(req) as retrofit2.Response
            if (response.code() in 200..299) {
                emit(Result.Success(data = "Creado con exito"))
            } else {
                emit(
                    Result.Error(response.toString())
                )
            }
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun createInvitation(req: RequestCreateInvitation): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.createInvitation(req).code()
            if (response in 200..299) emit(Result.Success(data = "Creado con exito")) else emit(
                Result.Error("Algo malo ocurrio.")
            )
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }
    //<editor-fold desc="EventApplies">

    //<editor-fold desc="Multiple EndPoints">

    override fun getDataDetailEvent(idEvent: Int,uId: String): Flow<Result<ResponseDetailEvent>> = flow {
        try {
            emit(Result.Loading())

            val responseEvent = featProvider.getEventById(idEvent).body()
            val playersConfirmed = featProvider.getAllPlayersConfirmedByEvent(idEvent).body()
            val playersApplied = featProvider.getAllPlayersAppliedByEvent(idEvent).body()
            val playersSuggested = featProvider.getAllPlayersSuggestedForEvent(idEvent).body() ?: emptyList()
            val responsePlayer = featProvider.getPlayersByUser(uId).body()

            if (responsePlayer != null && responseEvent != null && playersSuggested != null && playersConfirmed != null && playersApplied != null) {
                emit(
                    Result.Success(
                        data = ResponseDetailEvent(
                            event = responseEvent,
                            playersSuggested = playersSuggested,
                            playersApplied = playersApplied,
                            playersConfirmed = playersConfirmed,
                            players = responsePlayer
                        )
                    )
                )
            } else {
                emit(Result.Error(message = "Unknown Error"))
            }
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }


    override fun getDataSearchEvent(idEvent: Int, uId:String): Flow<Result<ResponseDataSearchEvent>> = flow {
        try {
            emit(Result.Loading())

            val responseEvent = featProvider.getEventById(idEvent).body()
            val playersConfirmed = featProvider.getAllPlayersConfirmedByEvent(idEvent).body()
            val players = featProvider.getPlayersByUser(uId).body()

            if (responseEvent != null  && playersConfirmed != null) {
                emit(
                    Result.Success(
                        data = ResponseDataSearchEvent(
                            event = responseEvent,
                            playersConfirmed = playersConfirmed,
                            playersUser = players ?: listOf()
                        )
                    )
                )
            } else {
                emit(Result.Error(message = "Unknown Error"))
            }
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }
    override fun getDataSportScreen(uId:String,sportGenericId:Int): Flow<Result<ResponseDataSport>> = flow {
        try {
            emit(Result.Loading())
            val responsePerson = featProvider.getPerson(uId).body()
            val responseLevels = featProvider.getAllLevelsBySportGeneric(sportGenericId).body()
            val responseValuations = featProvider.getValuations().body()
            val responsePositions = featProvider.getAllPositionsBySportGeneric(sportGenericId).body()


            if (responseLevels != null && responseValuations != null && responsePositions != null && responsePerson != null) {
                emit(
                    Result.Success(
                        data = ResponseDataSport(
                            person = responsePerson,
                            levelList = responseLevels,
                            positionList = responsePositions,
                            valuationList = responseValuations
                        )
                    )
                )
            } else {
                emit(Result.Error(message = "Unknown Error"))
            }
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }
//<editor-fold desc="Multiple EndPoints">



    //<editor-fold desc="Addresses">
    override fun getAddressesByUser(uId: String): Flow<Result<List<Address>>> = flow{
        try {
            emit(Result.Loading())
            val response = featProvider.getAddressesByUser(uId)
            Log.e("rao", response.toString())
            emit(Result.Success(data = response.body() ?: listOf()))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun updateAddress(req: RequestAddress): Flow<Result<String>> = flow{
        try {
            emit(Result.Loading())
            val response = featProvider.updateAddress(req).code()
            if (response in 200..299) emit(Result.Success(data = "Actualizado con exito")) else emit(
                Result.Error("Algo malo ocurrio.")
            )
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun createAddress(req: RequestAddress): Flow<Result<String>> = flow{
        try {
            emit(Result.Loading())
            val response = featProvider.createAddress(req).code()
            if (response in 200..299) emit(Result.Success(data = "Creado con exito")) else emit(
                Result.Error("Algo malo ocurrio.")
            )
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getDetailProfile(uId: String): Flow<Result<ResponseDetailProfile>> = flow{
        try {
            emit(Result.Loading())
            var person = featProvider.getPerson(uId).body()
            var players = featProvider.getPlayersByUser(uId).body() ?: emptyList()
            var addresses = featProvider.getAddressesByUser(uId).body() ?: emptyList()
            if (person != null && addresses != null && players != null) {
                emit(
                    Result.Success(
                        data = ResponseDetailProfile(
                            person = person,
                            addresses = addresses,
                            players = players
                        )
                    )
                )
            } else {
                emit(Result.Error(message = "Unknown Error"))
            }

        }catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    //</editor-fold desc="Addresses">


    override fun getDataAddEvent(uId: String): Flow<Result<ResponseDataAddEvent>> = flow {
        try {
            emit(Result.Loading())
            val responsePerson = featProvider.getPerson(uId).body()
            val responsePeriodicity = featProvider.getPeriodicities().body()
            val responseSportGeneric = featProvider.getGenericsSports().body()
            val responseSport = featProvider.getSports().body()


            if (responsePerson != null && responsePeriodicity != null && responseSportGeneric != null && responseSport != null) {
                emit(
                    Result.Success(
                        data = ResponseDataAddEvent(
                            person = responsePerson,
                            periodicityList = responsePeriodicity,
                            sportList = responseSport,
                            sportGenericList = responseSportGeneric

                        )
                    )
                )
            } else {
                emit(Result.Error(message = "Unknown Error"))
            }
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    override fun getDataHomeEvent(uId: String): Flow<Result<ResponseDataHomeEvent>> = flow {
        try {
            emit(Result.Loading())
            val responseEventOfTheWeek = featProvider.getAllEventsOfTheWeek(uId).body()
            val responseEventConfirmedOrApplied = featProvider.getAllConfirmedOrAppliedByUser(uId).body()


            if (responseEventOfTheWeek != null && responseEventConfirmedOrApplied != null ) {
                emit(
                    Result.Success(
                        data = ResponseDataHomeEvent(
                            eventOfTheWeek = responseEventOfTheWeek,
                            eventConfirmedOrApplied = responseEventConfirmedOrApplied,
                        )
                    )
                )
            } else {
                emit(Result.Error(message = "Unknown Error"))
            }
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unknown Error"))
        }
    }




//</editor-fold desc="Multiple EndPoints">
}


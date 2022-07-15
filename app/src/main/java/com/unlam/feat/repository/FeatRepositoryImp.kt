package com.unlam.feat.repository

import android.util.Log
import com.unlam.feat.model.*
import com.unlam.feat.model.request.*
import com.unlam.feat.model.response.*
import com.unlam.feat.model.response.ResponseDataSport
import com.unlam.feat.model.response.ResponseDetailEvent
import com.unlam.feat.model.response.ResponseDetailProfile
import com.unlam.feat.provider.FeatProvider
import com.unlam.feat.ui.view.info_player.InfoPlayerState
import com.unlam.feat.util.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
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
            val response = featProvider.getEventsToday()
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = uId, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun getEventsSuggestedForUser(uId: String): Flow<Result<List<Event>>> = flow {
        try {
            emit(Result.Loading())
            delay(600)
            val response = featProvider.getEventsSuggestedForUser(uId)
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = uId, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun getEventsCreatedByUser(uId: String): Flow<Result<List<Event>>> = flow {
        try {
            emit(Result.Loading())
            delay(600)
            val response = featProvider.getEventsCreatedByUser(uId)
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = uId, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun getEventsByOrganizer(organizer: Int): Flow<Result<List<Event>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getEventsByOrganizer(organizer)
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = organizer, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun getEventsApplied(uId: String): Flow<Result<List<Event>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getEventsApplied(uId)
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = uId, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun getEventsConfirmed(uId: String): Flow<Result<List<Event>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getEventsConfirmed(uId)
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = uId, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun getEventsByUser(uId: String): Flow<Result<List<Event>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getEventsByUser(uId)
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = uId, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun getEventById(idEvent: Int): Flow<Result<Event>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getEventById(idEvent)
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = idEvent, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun postEvent(request: RequestEvent): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.postEvent(request)
            if (response.code() in 200..299) {
                emit(Result.Success(data = Messages.SUCCESS_CREATED))
            } else {
                logging(request = request, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun getAllInvitationsForUser(uId: String): Flow<Result<List<Event>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getAllInvitationsForUser(uId)
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = uId, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun setConfirmed(request: RequestEventState): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.setConfirmed(request)
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = request, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun setCanceled(request: RequestEventState): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.setCanceled(request)
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = request, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun getAllEventsOfTheWeek(uId: String): Flow<Result<List<Event>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getAllEventsOfTheWeek(uId)
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = uId, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun getAllConfirmedOrAppliedByUser(uId: String): Flow<Result<List<HomeEvent>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getAllConfirmedOrAppliedByUser(uId)
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = uId, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun getfilterEventForUser(
        uId: String,
        req: RequestFilterEvent
    ): Flow<Result<List<Event>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getfilterEventForUser(req)
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = uId, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage)
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun setFinalized(req: RequestSetFinalized): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.setFinalized(req)
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = req, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage)
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }


    //</editor-fold desc="Events">
    //<editor-fold desc="Availabilities">

    override fun getAvailabilities(): Flow<Result<List<Availability>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getAvailabilities()
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = "", response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun getAvailability(id: Int): Flow<Result<Availability>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getAvailability(id)
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = id, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }

        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun createAvailability(req: RequestAvailability): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.createAvailability(req)
            if (response.code() in 200..299) {
                emit(Result.Success(data = Messages.SUCCESS_CREATED))
            } else {
                logging(request = req, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }
    //</editor-fold desc="Availabilities">
    //<editor-fold desc="Levels">

    override fun getLevels(): Flow<Result<List<Level>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getLevels()
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = "", response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun getLevel(id: Int): Flow<Result<Level>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getLevel(id)
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = id, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun getAllLevelsBySportGeneric(id: Int): Flow<Result<List<Level>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getAllLevelsBySportGeneric(id)
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = id, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun createLevel(req: RequestLevel): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.createLevel(req)
            if (response.code() in 200..299) {
                emit(Result.Success(data = Messages.SUCCESS_CREATED))
            } else {
                logging(request = req, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }
    //</editor-fold desc="Levels">
    //<editor-fold desc="Periodicities">

    override fun getPeriodicities(): Flow<Result<List<Periodicity>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getPeriodicities()
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = "", response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun getPeriodicity(id: Int): Flow<Result<Periodicity>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getPeriodicity(id)
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = id, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun createPeriodicity(req: RequestPeriodicity): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.createPeriodicity(req)
            if (response.code() in 200..299) {
                emit(Result.Success(data = Messages.SUCCESS_CREATED))
            } else {
                logging(request = req, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }
    //</editor-fold desc="Periodicities">
    //<editor-fold desc="Players">

    override fun getPlayers(): Flow<Result<List<Player>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getPlayers()
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = "", response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun getPlayer(id: Int): Flow<Result<Player>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getPlayer(id)
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = id, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun getPlayersByUser(userUid: String): Flow<Result<List<Player>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getPlayersByUser(userUid)
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = userUid, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun getAllByPerson(personId: Int): Flow<Result<List<Player>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getAllByPerson(personId)
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = personId, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun getAllPlayersSuggestedForEvent(
        eventId: Int,
        uId: String
    ): Flow<Result<ResponseDataSuggestedPlayers>> = flow {
        try {
            emit(Result.Loading())
            val listPlayersId: MutableList<Int> = mutableListOf()
            val response = featProvider.getAllPlayersSuggestedForEvent(eventId);
            val person = featProvider.getPerson(uId).body()

            if (response.code() in 200..299) {
                response.body()?.forEach { player ->
                    listPlayersId.add(player.id)
                }
                val requestPlayerId = RequestPlayerId(idPlayers = listPlayersId.toList())
                val responsePhotoUrl = featProvider.getPhotoUrlsByPlayers(requestPlayerId)

                if (responsePhotoUrl.code() in 200..299) {
                    emit(
                        Result.Success(
                            data = ResponseDataSuggestedPlayers(
                                players = response.body() ?: listOf(),
                                person = person,
                                playersPhotoUrl = responsePhotoUrl.body() ?: listOf(),
                            )
                        )
                    )
                } else if (responsePhotoUrl.code() in 400..499) {
                    emit(
                        Result.Success(
                            data = ResponseDataSuggestedPlayers(
                                players = response.body() ?: listOf(),
                                person = person,
                                playersPhotoUrl = listOf(),
                            )
                        )
                    )
                }
            } else {
                logging(request = eventId, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun getAllPlayersConfirmedByEvent(eventId: Int): Flow<Result<List<Player>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getAllPlayersConfirmedByEvent(eventId)
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = eventId, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun getAllPlayersAppliedByEvent(eventId: Int): Flow<Result<List<PlayerApplyDetail>>> =
        flow {
            try {
                emit(Result.Loading())
                val response = featProvider.getAllPlayersAppliedByEvent(eventId)
                if (response.code() in 200..299) {
                    emit(Result.Success(data = response.body()))
                } else {
                    logging(request = eventId, response = response)
                    emit(Result.Error(message = Messages.UNKNOW_ERROR))
                }
            } catch (e: Exception) {
                logging(e.localizedMessage!!.toString())
                emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
            }
        }

    override fun createPlayer(req: RequestPlayer): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.createPlayer(req)
            if (response.code() in 200..299) {
                emit(Result.Success(data = Messages.SUCCESS_CREATED))
            } else {
                logging(request = req, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun getPhotoUrlsByPlayers(personsId: RequestPlayerId): Flow<Result<List<ResponsePhotoUrl>>> =
        flow {
            try {
                emit(Result.Loading())
                val response = featProvider.getPhotoUrlsByPlayers(personsId)
                if (response.code() in 200..299) {
                    emit(Result.Success(data = response.body()))
                } else {
                    logging(request = personsId, response = response)
                    emit(Result.Error(message = Messages.UNKNOW_ERROR))
                }
            } catch (e: Exception) {
                logging(e.localizedMessage!!.toString())
                emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
            }
        }

    override fun filterPlayersForEvent(requestFilterPlayers: RequestFilterPlayers): Flow<Result<ResponseFilterPlayers>> =
        flow {
            try {
                emit(Result.Loading())
                val response = featProvider.filterPlayersForEvent(requestFilterPlayers)
                if (response.code() in 200..299) {
                    emit(
                        Result.Success(
                            data = ResponseFilterPlayers(
                                players = response.body()!!
                            )
                        )
                    )
                } else {
                    emit(Result.Error(message = "Unknown Error"))
                }
            } catch (e: Exception) {
                logging(e.localizedMessage)
                emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
            }
        }
    //</editor-fold desc="Players">
    //<editor-fold desc="Positions">

    override fun getPositions(): Flow<Result<List<Position>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getPositions()
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = "", response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun getPosition(id: Int): Flow<Result<Position>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getPosition(id)
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = id, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun getAllPositionsBySportGeneric(id: Int): Flow<Result<List<Position>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getAllPositionsBySportGeneric(id)
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = id, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun createPosition(req: RequestPosition): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.createPosition(req)
            if (response.code() in 200..299) {
                emit(Result.Success(data = Messages.SUCCESS_CREATED))
            } else {
                logging(request = req, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }
    //</editor-fold desc="Positions">
    //<editor-fold desc="Sports">

    override fun getSports(): Flow<Result<List<Sport>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getSports()
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = "", response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun getSport(id: Int): Flow<Result<Sport>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getSport(id)
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = id, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun getGenericsSports(): Flow<Result<List<SportGeneric>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getGenericsSports()
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = "", response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    //</editor-fold desc="Sports">
    //<editor-fold desc="Users">

    override fun getUsers(): Flow<Result<List<User>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getUsers()
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = "", response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun getUser(id: Int): Flow<Result<User>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getUser(id)
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = id, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }


    override fun createUser(req: RequestUser): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.createUser(req)
            if (response.code() in 200..299) {
                emit(Result.Success(data = Messages.SUCCESS_CREATED))
            } else {
                logging(request = req, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }


    //</editor-fold desc="Users">

    //<editor-fold desc="Persons">
    override fun getPerson(uId: String): Flow<Result<Person>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getPerson(uId)
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = uId, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun createPerson(req: RequestPerson): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.createPerson(req)
            if (response.code() in 200..299) {
                emit(Result.Success(data = Messages.SUCCESS_CREATED))
            } else {
                logging(request = req, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun updatePerson(req: RequestUpdatePerson): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.updatePerson(req)
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = req, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun updatePersonPersonalInformation(req: RequestUpdatePersonPersonalInformation): Flow<Result<String>> =
        flow {
            try {
                emit(Result.Loading())
                val response = featProvider.updatePersonPersonalInformation(req)
                if (response.code() in 200..299) {
                    emit(Result.Success(data = response.body()))
                } else {
                    logging(request = req, response = response)
                    emit(Result.Error(message = Messages.UNKNOW_ERROR))
                }
            } catch (e: Exception) {
                logging(e.localizedMessage!!.toString())
                emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
            }
        }

    override fun createPersonTransaction(req: RequestPersonTransaction): Flow<Result<String>> =
        flow {
            try {
                emit(Result.Loading())
                val response = featProvider.createPersonTransaction(req)
                if (response.code() in 200..299) {
                    emit(Result.Success(data = Messages.SUCCESS_CREATED))
                } else {
                    logging(request = req, response = response)
                    emit(Result.Error(message = Messages.UNKNOW_ERROR))
                }
            } catch (e: Exception) {
                logging(e.localizedMessage!!.toString())
                emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
            }
        }

    override fun updateUriImage(req: RequestUriImage): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.updateUriImage(req)
            if (response.code() in 200..299) {
                logging(request = req, response = response)

                emit(Result.Success(data = response.body()))
            } else {
                logging(request = req, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }


    //</editor-fold desc="Persons">
    //<editor-fold desc="Valuations">
    override fun getValuations(): Flow<Result<List<Valuation>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getValuations()
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = "", response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    //<editor-fold desc="Valuations">
    //<editor-fold desc="Addresses">
    override fun getAddress(personId: Int): Flow<Result<Address>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getAddress(personId)
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = personId, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }


    override fun addAddress(req: RequestAddress): Flow<Result<String>> = flow {
        try {
            Log.d("DIRECCION", req.toString())
            emit(Result.Loading())
            val response = featProvider.addAddress(req)
            if (response.code() in 200..299) {
                emit(Result.Success(data = Messages.SUCCESS_CREATED))
            } else {
                logging(request = req, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    //<editor-fold desc="Addresses">
    //<editor-fold desc="EventApplies">
    override fun setAcceptedApply(req: RequestEventApply): Flow<Result<ResponseCapacity>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.setAcceptedApply(req)
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = req, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun setDeniedApply(req: RequestEventApply): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.setDeniedApply(req)
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = req, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun setKickApply(req: RequestEventApply): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.setKickApply(req)
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body()))
            } else {
                logging(request = req, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun createInvitation(req: RequestCreateInvitation): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.createInvitation(req)
            if (response.code() in 200..299) {
                emit(Result.Success(data = Messages.SUCCESS_CREATED))
            } else {
                logging(request = req, response = response)
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }
    //<editor-fold desc="EventApplies">

    //<editor-fold desc="Multiple EndPoints">

    override fun getDataDetailEvent(idEvent: Int, uId: String): Flow<Result<ResponseDetailEvent>> =
        flow {
            try {
                emit(Result.Loading())

                val listPlayersId: MutableList<Int> = mutableListOf()
                val responseEvent = featProvider.getEventById(idEvent)
                val responsePlayersConfirmed = featProvider.getAllPlayersConfirmedByEvent(idEvent)
                val responsePlayersApplied = featProvider.getAllPlayersAppliedByEvent(idEvent)
                val responsePlayersSuggested = featProvider.getAllPlayersSuggestedForEvent(idEvent)
                val responsePlayer = featProvider.getPlayersByUser(uId)



                if (responsePlayer.code() in 200..299 && responseEvent.code() in 200..299 && responsePlayersConfirmed.code() in 200..299 && responsePlayersApplied.code() in 200..299 && responsePlayersSuggested.code() in 200..299) {
                    responsePlayersSuggested.body()?.forEach { player ->
                        listPlayersId.add(player.id)
                    }
                    responsePlayersApplied.body()?.forEach { player ->
                        if (!listPlayersId.contains(player.id)) {
                            listPlayersId.add(player.id)
                        }
                    }
                    responsePlayersConfirmed.body()?.forEach { player ->
                        if (!listPlayersId.contains(player.id)) {
                            listPlayersId.add(player.id)
                        }
                    }
                    if (listPlayersId.isNotEmpty()) {
                        val requestPlayerId = RequestPlayerId(idPlayers = listPlayersId.toList())
                        val responsePhotoUrl = featProvider.getPhotoUrlsByPlayers(requestPlayerId)
                        if (responsePhotoUrl.code() in 200..299) {
                            emit(
                                Result.Success(
                                    data = ResponseDetailEvent(
                                        event = responseEvent.body()!!,
                                        playersSuggested = responsePlayersSuggested.body()
                                            ?: listOf(),
                                        playersApplied = responsePlayersApplied.body() ?: listOf(),
                                        playersConfirmed = responsePlayersConfirmed.body()
                                            ?: listOf(),
                                        players = responsePlayer.body() ?: listOf(),
                                        playersPhotoUrl = responsePhotoUrl.body() ?: listOf()
                                    )
                                )
                            )
                        } else if (responsePhotoUrl.code() in 400..499) {
                            emit(
                                Result.Success(
                                    data = ResponseDetailEvent(
                                        event = responseEvent.body()!!,
                                        playersSuggested = responsePlayersSuggested.body()
                                            ?: listOf(),
                                        playersApplied = responsePlayersApplied.body() ?: listOf(),
                                        playersConfirmed = responsePlayersConfirmed.body()
                                            ?: listOf(),
                                        players = responsePlayer.body() ?: listOf(),
                                        playersPhotoUrl = listOf()
                                    )
                                )
                            )
                        }
                    } else {
                        emit(
                            Result.Success(
                                data = ResponseDetailEvent(
                                    event = responseEvent.body()!!,
                                    playersSuggested = responsePlayersSuggested.body() ?: listOf(),
                                    playersApplied = responsePlayersApplied.body() ?: listOf(),
                                    playersConfirmed = responsePlayersConfirmed.body() ?: listOf(),
                                    players = responsePlayer.body() ?: listOf(),
                                    playersPhotoUrl = listOf()
                                )
                            )
                        )
                    }


                } else {
                    loggingSingle(message = "Request", obj = uId)
                    loggingSingle(message = "Request", obj = idEvent)
                    loggingSingle(message = "Response", obj = responseEvent.raw())
                    loggingSingle(message = "Response", obj = responsePlayersConfirmed.raw())
                    loggingSingle(message = "Response", obj = responsePlayersApplied.raw())
                    loggingSingle(message = "Response", obj = responsePlayersSuggested.raw())
                    loggingSingle(message = "Response", obj = responsePlayer.raw())

                    emit(Result.Error(message = Messages.UNKNOW_ERROR))
                }
            } catch (e: Exception) {
                logging(e.localizedMessage!!.toString())
                emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
            }
        }


    override fun getDataSearchEvent(
        idEvent: Int,
        uId: String
    ): Flow<Result<ResponseDataSearchEvent>> = flow {
        try {
            emit(Result.Loading())

            val responseEvent = featProvider.getEventById(idEvent)
            val responsePlayersConfirmed = featProvider.getAllPlayersConfirmedByEvent(idEvent)
            val responsePlayers = featProvider.getPlayersByUser(uId)

            if (responseEvent.code() in 200..299 && responsePlayersConfirmed.code() in 200..299 && responsePlayers.code() in 200..299) {
                emit(
                    Result.Success(
                        data = ResponseDataSearchEvent(
                            event = responseEvent.body()!!,
                            playersConfirmed = responsePlayersConfirmed.body() ?: listOf(),
                            playersUser = responsePlayers.body() ?: listOf()
                        )
                    )
                )
            } else {
                loggingSingle(message = "Request", obj = idEvent)
                loggingSingle(message = "Request", obj = uId)
                loggingSingle(message = "Response", obj = responsePlayers.raw())
                loggingSingle(message = "Response", obj = responseEvent.raw())
                loggingSingle(message = "Response", obj = responsePlayersConfirmed.raw())
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun getDataSportScreen(
        sportGenericId: Int
    ): Flow<Result<ResponseDataSport>> = flow {
        try {
            emit(Result.Loading())
            val responseLevels = featProvider.getAllLevelsBySportGeneric(sportGenericId)
            val responseValuations = featProvider.getValuations()
            val responsePositions =
                featProvider.getAllPositionsBySportGeneric(sportGenericId)


            if (responseLevels.code() in 200..299 && responseValuations.code() in 200..299 && responsePositions.code() in 200..299) {
                emit(
                    Result.Success(
                        data = ResponseDataSport(
                            levelList = responseLevels.body() ?: listOf(),
                            positionList = responsePositions.body() ?: listOf(),
                            valuationList = responseValuations.body() ?: listOf()
                        )
                    )
                )
            } else {
                loggingSingle(message = "Request", obj = sportGenericId)
                loggingSingle(message = "Response", obj = responseLevels.raw())
                loggingSingle(message = "Response", obj = responsePositions.raw())
                loggingSingle(message = "Response", obj = responseValuations.raw())
                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }
//<editor-fold desc="Multiple EndPoints">


    //<editor-fold desc="Addresses">
    override fun getAddressesByUser(uId: String): Flow<Result<List<Address>>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.getAddressesByUser(uId)
            if (response.code() in 200..299) {
                emit(Result.Success(data = response.body() ?: listOf()))
            } else {
                loggingSingle(message = "Request", obj = uId)
                loggingSingle(message = "Request", obj = response.raw())

                emit(Result.Error(message = Messages.UNKNOW_ERROR))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun updateAddress(req: RequestAddress): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.updateAddress(req)
            if (response.code() in 200..299) {
                emit(Result.Success(data = "Actualizado con exito"))
            } else {
                loggingSingle("Request", req)
                loggingSingle("Response", response.raw())
                emit(Result.Error("Algo malo ocurrio."))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun createAddress(req: RequestAddress): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading())
            val response = featProvider.createAddress(req).code()
            if (response in 200..299) emit(Result.Success(data = "Creado con exito")) else emit(
                Result.Error("Algo malo ocurrio.")
            )
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun getDetailProfile(uId: String): Flow<Result<ResponseDetailProfile>> = flow {
        try {
            emit(Result.Loading())
            delay(600)
            val person = featProvider.getPerson(uId).body()
            val players = featProvider.getPlayersByUser(uId).body() ?: emptyList()
            val addresses = featProvider.getAddressesByUser(uId).body() ?: emptyList()
            if (person != null) {
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

        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun qualifyPlayers(requestQualifyPlayers: RequestQualifyPlayers): Flow<Result<String>> =
        flow {
            try {
                emit(Result.Loading())
                val response = featProvider.qualifyPlayers(requestQualifyPlayers)
                if (response.code() in 200..299) {
                    emit(Result.Success(data = "Actualizado con exito"))
                } else {
                    loggingSingle("Request", requestQualifyPlayers)
                    loggingSingle("Response", response.raw())
                    emit(Result.Error("Algo malo ocurrio."))
                }
            } catch (e: Exception) {
                logging(e.localizedMessage!!.toString())
                emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
            }
        }

    override fun findAllQualificationsByPlayer(
        id: String,
        uId: String
    ): Flow<Result<ResponseInfoPlayer>> =
        flow {
            try {
                emit(Result.Loading())
                val responseQualifications = featProvider.findAllQualificationsByPlayer(id)
                val responseUser = featProvider.getPerson(uId)
                if (responseQualifications.code() in 200..299 && responseUser.code() in 200..299) {
                    emit(
                        Result.Success(
                            data = ResponseInfoPlayer(
                                qualifications = responseQualifications.body(),
                                person = responseUser.body()
                            )
                        )
                    )
                } else {
                    loggingSingle("Request", id)
                    loggingSingle("Request", uId)
                    loggingSingle("Response", responseQualifications.raw())
                    loggingSingle("Response", responseUser.raw())
                    emit(Result.Error(Messages.UNKNOW_ERROR))
                }
            } catch (e: Exception) {
                logging(e.localizedMessage!!.toString())
                emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
            }
        }

    override fun findAllQualificationsByUser(id: String): Flow<Result<List<Qualification>>> {
        TODO("Not yet implemented")
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
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun getDataHomeEvent(uId: String): Flow<Result<ResponseDataHomeEvent>> = flow {
        try {
            emit(Result.Loading())
            val responseEventOfTheWeek = featProvider.getAllEventsOfTheWeek(uId)
            val responseEventConfirmedOrApplied =
                featProvider.getAllConfirmedOrAppliedByUser(uId)
            val responsePlayer = featProvider.getPlayersByUser(uId)


            if (responseEventOfTheWeek.code() in 200..299 && responseEventConfirmedOrApplied.code() in 200..299 && responsePlayer.code() in 200..299) {
                val players = responsePlayer.body()
                val eventWeek = responseEventOfTheWeek.body()
                var eventOfWeek: MutableList<Event> = mutableListOf()

                players?.forEach { player ->
                    eventWeek?.forEach { event ->
                        if (event.sport.description.contains(player.sport.description )) {
                            eventOfWeek.add(event)
                        }
                    }
                }

                emit(
                    Result.Success(
                        data = ResponseDataHomeEvent(
                            eventOfTheWeek =  eventOfWeek.toList(),
                            eventConfirmedOrApplied = responseEventConfirmedOrApplied.body()!!,
                        )
                    )
                )
            } else {
                emit(Result.Error(message = "Unknown Error"))
            }
        } catch (e: Exception) {
            logging(e.localizedMessage!!.toString())
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun getSearchEvent(uId: String): Flow<Result<ResponseDataSearch>> = flow {
        try {
            emit(Result.Loading())
            val responseGetSuggestedEvent = featProvider.getEventsSuggestedForUser(uId);
            val responseGetPlayer = featProvider.getPlayersByUser(uId)
            val responseListSport = featProvider.getGenericsSports()

            if (responseGetPlayer.code() in 200..299 && responseGetSuggestedEvent.code() in 200..299 && responseListSport.code() in 200..299) {
                emit(
                    Result.Success(
                        data = ResponseDataSearch(
                            events = responseGetSuggestedEvent.body()!!,
                            players = responseGetPlayer.body()!!,
                            sportList = responseListSport.body()!!
                        )
                    )
                )
            } else {
                emit(Result.Error(message = "Unknown Error"))
            }


        } catch (e: Exception) {
            logging(e.localizedMessage)
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun getFilterSearchEvent(
        uId: String,
        req: RequestFilterEvent
    ): Flow<Result<ResponseDataFilterSearch>> = flow {
        try {
            emit(Result.Loading())
            val filterEvents = featProvider.getfilterEventForUser(req)

            if (filterEvents.code() in 200..299) {
                emit(
                    Result.Success(
                        data = ResponseDataFilterSearch(
                            events = filterEvents.body()!!,
                        )
                    )
                )
            } else {
                emit(Result.Error(message = "Unknown Error"))
            }


        } catch (e: Exception) {
            logging(e.localizedMessage)
            emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
        }
    }

    override fun getPlayersUserAndSportList(uId: String): Flow<Result<ResponsePlayersUserSportList>> =
        flow {
            try {
                emit(Result.Loading())
                val responseGetPlayer = featProvider.getPlayersByUser(uId)
                val responseGetSportList = featProvider.getGenericsSports()
                val responseGetPerson = featProvider.getPerson(uId)

                if (responseGetPlayer.code() in 200..299 && responseGetSportList.code() in 200..299) {
                    emit(
                        Result.Success(
                            data = ResponsePlayersUserSportList(
                                players = responseGetPlayer.body()!!,
                                sportGenericList = responseGetSportList.body()!!,
                                person = responseGetPerson.body()!!
                            )
                        )
                    )
                } else {
                    emit(Result.Error(message = "Unknown Error"))
                }


            } catch (e: Exception) {
                logging(e.localizedMessage)
                emit(Result.Error(message = e.localizedMessage ?: Messages.UNKNOW_ERROR))
            }
        }


//</editor-fold desc="Multiple EndPoints">
}


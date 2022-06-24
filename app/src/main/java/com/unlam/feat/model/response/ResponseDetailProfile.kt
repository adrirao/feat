package com.unlam.feat.model.response

import android.net.InetAddresses
import com.unlam.feat.model.Address
import com.unlam.feat.model.Event
import com.unlam.feat.model.Person
import com.unlam.feat.model.Player

data class ResponseDetailProfile(
    val person: Person,
    val addresses: List<Address>,
    val players : List<Player>
)
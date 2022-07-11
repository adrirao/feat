package com.unlam.feat.ui.util

sealed class TypeClick {
    object GoToRegister : TypeClick()
    object GoToLogin : TypeClick()
    object GoToHome : TypeClick()
    object GoToTakePhoto: TypeClick()
    object GoToInfoEvent : TypeClick()
    object GoToNewEvent : TypeClick()
    object GoToEvent:TypeClick()
    object GoToDetailEvent:TypeClick()
    object GoToSuggestedPlayers:TypeClick()

    object Login : TypeClick()
    object Register : TypeClick()
    object Submit : TypeClick()

    object DismissDialog : TypeClick()

    object ToggledPassword : TypeClick()
    object ToggledRePassword : TypeClick()

    object SaveSoccerData : TypeClick()
    object SaveBasketballData : TypeClick()
    object SaveTennisData : TypeClick()
    object SavePadelData : TypeClick()
    object SaveRecreationalActivityData : TypeClick()

    object Event : TypeClick(){
        sealed class TypleClickEvent{
            object Confirm : TypleClickEvent()
            object Cancel : TypleClickEvent()
        }
    }
}

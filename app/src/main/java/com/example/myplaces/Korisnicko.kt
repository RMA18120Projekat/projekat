package com.example.myplaces

import androidx.lifecycle.ViewModel

class Korisnicko: ViewModel() {
    var ime:String=""
    var img:String=""
    var izabranoMesto=""
    var longituda=""
    var latituda=""
    var user:User=User()
}
package com.smi.test.presentation.entites

class User {
    var username: String? = null
    var email: String? = null
    var password: String? = null
    var uid: String? = null

    constructor() {}
    constructor(email: String?, password: String?) {
        this.email = email
        this.password = password
    }

    constructor(
        username: String?,
        email: String?,
        password: String?,
    ) {
        this.username = username
        this.email = email
        this.password = password
        //this.uid = uid;
    }
}
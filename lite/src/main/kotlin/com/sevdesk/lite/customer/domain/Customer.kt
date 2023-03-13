package com.sevdesk.lite.customer.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "CUSTOMERS")
data class Customer (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long,

    @Column(name = "givenname")
    val givenname: String,

    @Column(name = "surname")
    val surname: String,
)

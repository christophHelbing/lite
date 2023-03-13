package com.sevdesk.lite.invoice.domain

import com.sevdesk.lite.customer.domain.Customer
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.OffsetDateTime
import javax.persistence.*
import kotlin.jvm.Transient

@Entity
@Table(name = "INVOICES")
data class Invoice(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null,

    @Column(name = "status", length = 50)
    val status: InvoiceState = InvoiceState.OPEN,

    @Column(name = "creation_date")
    val creationDate: OffsetDateTime = OffsetDateTime.now(),

    @Column(name = "due_date")
    val dueDate: LocalDate,

    @Column(name = "paid_date")
    val paidDate: OffsetDateTime? = null,

    @Column(name = "paid_amount")
    val paidAmount: BigDecimal = BigDecimal.ZERO,

    @Column(name = "invoice_number")
    val invoiceNumber: String,

    @Column(name = "quantity")
    val quantity: BigDecimal,

    @Column(name = "price_net")
    val priceNet: BigDecimal,

    /*
        Transient calculated values are not serialized via jackson, that's why Iam using postLoad.
        Tried using Annotation JsonProperty or JsonInclude to solve this better, but this was not working.

        I doesn't like this solution with var and setting the values in the postLoad method.
     */
    @Transient
    var priceGross: BigDecimal = BigDecimal.ZERO,

    @Transient
    var totalPrice: BigDecimal = BigDecimal.ZERO,

    @ManyToOne(cascade = [CascadeType.ALL])
    val customer: Customer,
) {
    @PostLoad
    fun postLoad() {
        priceGross = priceNet.plus(priceNet.times(BigDecimal.valueOf(0.19))).setScale(2, RoundingMode.FLOOR)
        totalPrice = priceGross.times(quantity).setScale(2, RoundingMode.FLOOR)
    }
}

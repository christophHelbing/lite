package com.sevdesk.lite.invoice.domain

import com.fasterxml.jackson.annotation.JsonGetter
import com.sevdesk.lite.customer.domain.Customer
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.OffsetDateTime
import javax.persistence.*

// TODO A domain model should never be dependent on third-party libraries. Remove the spring part.
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

    @ManyToOne(cascade = [CascadeType.ALL])
    val customer: Customer,
) {
    @JsonGetter("priceGross")
    fun calculatePriceGross() =  priceNet.plus(priceNet.times(BigDecimal.valueOf(0.19))).setScale(2, RoundingMode.FLOOR)

    @JsonGetter("totalPrice")
    fun calculateTotalPrice() = calculatePriceGross().times(quantity).setScale(2, RoundingMode.FLOOR)

    fun isPaid(): Boolean {
        return paidAmount >= calculateTotalPrice()
    }
}

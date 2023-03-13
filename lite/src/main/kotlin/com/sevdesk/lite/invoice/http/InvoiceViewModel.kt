package com.sevdesk.lite.invoice.http

import com.sevdesk.lite.customer.domain.Customer
import com.sevdesk.lite.invoice.domain.Invoice
import java.math.BigDecimal
import java.time.LocalDate

data class InvoiceViewModel(
    val dueDate: LocalDate,
    val invoiceNumber: String,
    val quantity: BigDecimal,
    val priceNet: BigDecimal,
    val customer: Customer,
)

fun InvoiceViewModel.toDomain() =
    Invoice(
        dueDate = dueDate,
        invoiceNumber = invoiceNumber,
        quantity = quantity,
        priceNet = priceNet,
        customer = customer,
    )

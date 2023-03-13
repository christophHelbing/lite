package com.sevdesk.lite.invoice.port

import arrow.core.Either
import com.sevdesk.lite.failure.Failure
import com.sevdesk.lite.invoice.domain.Invoice
import java.math.BigDecimal

interface InvoiceService {
    fun getAllInvoices(): Either<Failure, List<Invoice>>

    suspend fun getInvoice(id: Long): Either<Failure, Invoice>

    fun saveInvoice(invoice: Invoice): Either<Failure, Invoice>

    suspend fun closeInvoice(id: Long): Either<Failure, Unit>

    suspend fun payForInvoice(id: Long, amount: BigDecimal): Either<Failure, Unit>
}
package com.sevdesk.lite.invoice.service

import arrow.core.Either
import arrow.core.computations.either
import arrow.core.computations.ensureNotNull
import arrow.core.left
import com.sevdesk.lite.failure.Failure
import com.sevdesk.lite.failure.trap
import com.sevdesk.lite.invoice.domain.Invoice
import com.sevdesk.lite.invoice.domain.InvoiceState
import com.sevdesk.lite.invoice.port.InvoiceRepository
import com.sevdesk.lite.invoice.port.InvoiceService
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDate
import java.time.OffsetDateTime

@Service
class InvoiceServiceImpl(private val invoiceRepository: InvoiceRepository) : InvoiceService {

    override fun getAllInvoices(): Either<Failure, List<Invoice>> =
        trap {
            invoiceRepository.findAll().toList()
        }


    override suspend fun getInvoice(id: Long): Either<Failure, Invoice> =
        either {
            val possibleInvoice = trap {
                invoiceRepository.findById(id).orElseGet { null }
            }.bind()
            ensureNotNull(possibleInvoice) { Failure.NotFoundFailure(id, Invoice::class) }
        }

    override fun saveInvoice(invoice: Invoice): Either<Failure, Invoice> {
        return if (invoice.priceNet.signum() < 0) {
            Failure.ValidationError("The price should never be negative.").left()
        } else if (invoice.dueDate.isBefore(LocalDate.now())) {
            Failure.ValidationError("Due date should never been in the past").left()
        } else {
            trap {
                invoiceRepository.save(invoice)
            }
        }
    }

    override suspend fun closeInvoice(id: Long): Either<Failure, Unit> =
        either {
            val possibleInvoice = getInvoice(id = id).bind()
            possibleInvoice.copy(status = InvoiceState.CLOSED).let {
                saveInvoice(it).bind()
            }
        }

    override suspend fun payForInvoice(id: Long, amount: BigDecimal): Either<Failure, Unit> =
        either {
            val possibleInvoice = getInvoice(id = id).bind()

            /* Checking whether the invoice is already fully paid */
            Either.catch { require(!possibleInvoice.isPaid()) }.mapLeft {
                Failure.ValidationError("The invoice with the id: $id is already fully paid")
            }.bind()

            /* Getting payment state by calculating the present paid amount and compare it to the totalPrice */
            val getPaymentState: (Invoice, BigDecimal) -> InvoiceState = { invoice, amount ->
                if (amount + invoice.paidAmount >= invoice.calculateTotalPrice()) InvoiceState.TOTAL_PAID else InvoiceState.PARTLY_PAID
            }

            /* Calculate new paid amount */
            val getPaidAmount: (Invoice, BigDecimal) -> BigDecimal = { invoice, amount ->
                amount.plus(invoice.paidAmount)
            }

            possibleInvoice.copy(
                status = getPaymentState(possibleInvoice, amount),
                paidDate = OffsetDateTime.now(),
                paidAmount = getPaidAmount(possibleInvoice, amount),
            ).let {
                saveInvoice(it).bind()
            }
        }
}

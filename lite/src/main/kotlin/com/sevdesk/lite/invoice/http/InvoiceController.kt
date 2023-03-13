package com.sevdesk.lite.invoice.http

import com.sevdesk.lite.failure.toResponseEntity
import com.sevdesk.lite.invoice.domain.Invoice
import com.sevdesk.lite.invoice.port.InvoiceService
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/invoices")
@Validated
class InvoiceController(
    private val invoiceService: InvoiceService
) {

    @GetMapping
    fun getAllInvoices(page: Pageable = Pageable.unpaged()): ResponseEntity<List<Invoice>> =
        /*
        Arrow's either is running in suspendable context. So we need a coroutine scope here.
        For a better solution we have to add Spring Webflux which supports reactive programming.
         */
        runBlocking {
            invoiceService.getAllInvoices().fold(
                {
                    it.toResponseEntity(logger = logger)
                }
            ) {
                ResponseEntity.ok(it)
            }
        }

    @GetMapping("/{id}")
    fun getInvoice(@PathVariable("id") id: Long): ResponseEntity<Invoice> =
        /*
        Arrow's either is running in suspendable context. So we need a coroutine scope here.
        For a better solution we have to add Spring Webflux which supports reactive programming.
         */
        runBlocking {
            invoiceService.getInvoice(id = id).fold(
                {
                    it.toResponseEntity(logger = logger)
                }
            ) {
                ResponseEntity.ok(it)
            }
        }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun addInvoice(@RequestBody invoiceViewModel: InvoiceViewModel): ResponseEntity<Invoice> =
        /*
        Arrow's either is running in suspendable context. So we need a coroutine scope here.
        For a better solution we have to add Spring Webflux which supports reactive programming.
         */
        runBlocking {
            invoiceService.saveInvoice(invoiceViewModel.toDomain()).fold(
                {
                    it.toResponseEntity(logger = logger)
                }
            ) {
                ResponseEntity.ok(it)
            }
        }


    @PutMapping("/{id}")
    fun closeInvoice(@PathVariable("id") id: Long): ResponseEntity<Unit> =
        /*
        Arrow's either is running in suspendable context. So we need a coroutine scope here.
        For a better solution we have to add Spring Webflux which supports reactive programming.
         */
        runBlocking {
            invoiceService.closeInvoice(id).fold(
                {
                    it.toResponseEntity(logger = logger)
                }
            ) {
                ResponseEntity.ok().build()
            }
        }

    @PostMapping("/pay", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun payForInvoice(@RequestBody payViewModel: PayViewModel): ResponseEntity<Unit> =
        /*
        Arrow's either is running in suspendable context. So we need a coroutine scope here.
        For a better solution we have to add Spring Webflux which supports reactive programming.
         */
        runBlocking {
            invoiceService.payForInvoice(payViewModel.id, payViewModel.payingAmount).fold(
                {
                    it.toResponseEntity(logger = logger)
                }
            ) {
                ResponseEntity.ok().build()
            }
        }

    companion object {
        private val logger = LoggerFactory.getLogger(InvoiceController::class.java)
    }
}

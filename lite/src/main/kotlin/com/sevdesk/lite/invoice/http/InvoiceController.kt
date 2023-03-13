package com.sevdesk.lite.invoice.http

import com.sevdesk.lite.invoice.service.InvoiceServiceImpl
import com.sevdesk.lite.invoice.domain.Invoice
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/invoices")
@Validated
class InvoiceController(
    private val invoiceService: InvoiceServiceImpl
) {

    @GetMapping
    fun getAllInvoices(
        page: Pageable = Pageable.unpaged()
    ): List<Invoice> {
        return invoiceService.getAllInvoices()
    }

    @GetMapping("/{id}")
    fun getInvoice(
        @PathVariable("id") id: Long
    ): Invoice {
        return invoiceService.getInvoice(id)
    }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun addInvoice(
        @RequestBody invoice: Invoice
    ): Invoice {
        return invoiceService.saveInvoice(invoice)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun deleteInvoice(
        @PathVariable("id") id: Long
    ) {
//        invoiceRepository.deleteById(id)
    }
}

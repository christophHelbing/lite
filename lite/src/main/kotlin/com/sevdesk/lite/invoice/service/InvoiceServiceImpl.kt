package com.sevdesk.lite.invoice.service

import com.sevdesk.lite.invoice.domain.Invoice
import com.sevdesk.lite.invoice.port.InvoiceRepository
import com.sevdesk.lite.invoice.port.InvoiceService
import org.springframework.stereotype.Service

@Service
class InvoiceServiceImpl(private val invoiceRepository: InvoiceRepository) : InvoiceService {

    override fun getAllInvoices(): List<Invoice> {
        return invoiceRepository.findAll().toList()
    }

    override fun getInvoice(id: Long): Invoice {
        return invoiceRepository.findById(id).orElseGet { null }
    }

    override fun saveInvoice(invoice: Invoice): Invoice {
        return invoiceRepository.save(invoice)
    }
}

package com.sevdesk.lite.invoice.port

import com.sevdesk.lite.invoice.domain.Invoice

interface InvoiceService {
    fun getAllInvoices(): List<Invoice>

    fun getInvoice(id: Long): Invoice

    fun saveInvoice(invoice: Invoice): Invoice
}
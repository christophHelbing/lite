package com.sevdesk.lite.invoice.port

import com.sevdesk.lite.invoice.domain.Invoice
import org.springframework.data.repository.CrudRepository

interface InvoiceRepository : CrudRepository<Invoice, Long>

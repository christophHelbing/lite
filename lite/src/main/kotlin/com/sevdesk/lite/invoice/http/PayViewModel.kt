package com.sevdesk.lite.invoice.http

import java.math.BigDecimal

data class PayViewModel(
    val id: Long,
    val payingAmount: BigDecimal,
)

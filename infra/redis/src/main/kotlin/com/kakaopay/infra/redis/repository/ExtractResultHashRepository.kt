package com.kakaopay.infra.redis.repository

import org.springframework.data.repository.CrudRepository

interface ExtractResultHashRepository : CrudRepository<ExtractResultHash, String>
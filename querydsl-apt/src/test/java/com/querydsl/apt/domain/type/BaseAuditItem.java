package com.querydsl.apt.domain.type;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Each audit item for AuditEntity
 */
public abstract class BaseAuditItem {

  private Integer actorId;

  private String action;

  private Date actionAt = Date.from(Instant.now());

  private Map<String, Object> oldEntity = new HashMap<>();

  private Map<String, Object> newEntity = new HashMap<>();

  private Map<String, Object> metadata = new HashMap<>();
}

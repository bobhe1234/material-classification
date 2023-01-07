package com.jswy.domain.support;

/**
 * 实体类接口：Entity是一个可识别的概念，自带序列ID，这意味着它需要公开其标识符
 * 
 * @author admin
 *
 * @param <T>
 * @param <ID>
 */
public interface Entity<ID extends Identifier> extends Identifiable<ID> {
}
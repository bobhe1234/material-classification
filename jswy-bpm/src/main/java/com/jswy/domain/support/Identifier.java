package com.jswy.domain.support;

import java.io.Serializable;

/**
 * 标识符接口Identifier：只是为标识符类型配备的标识接口<br>
 * 使用专用类型来描述聚合的标识符。这样做的主要目的是避免每个实体都由一个通用类型（例如Long或UUID）来标识
 * 
 * @author admin
 *
 */
public interface Identifier extends Serializable {

}

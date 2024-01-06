package com.yunbyte.dbtest.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 配置文件参数读取配置类
 * 
 * @author Rongkai Wang
 * @Date: 2020-9-3
 */
@Component
public class ReaderValueConfig {

  @Value("${confreader.nodeCount}")
  public Integer nodeCount;

}

/**
 * Copyright © 2016 Jeremy Custenborder (jcustenborder@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jcustenborder.kafka.connect.solr;

import com.github.jcustenborder.kafka.connect.utils.config.ConfigKeyBuilder;
import org.apache.kafka.common.config.ConfigDef;

import java.util.Map;

class HttpSolrSinkConnectorConfig extends SolrSinkConnectorConfig {
  public static final String SOLR_URL_CONFIG = "solr.url";
  public static final String SOLR_QUEUE_SIZE_CONFIG = "solr.queue.size";
  public static final String SOLR_THREAD_COUNT_CONFIG = "solr.thread.count";
  private static final String SOLR_URL_DOC = "Url to connect to solr with.";
  private static final String SOLR_QUEUE_SIZE_DOC = "The number of documents to batch together before sending to Solr. See " +
      "`ConcurrentUpdateSolrClient.Builder.withQueueSize(int) <https://lucene.apache.org/solr/6_3_0/solr-solrj/org/apache/solr/client/solrj/impl/ConcurrentUpdateSolrClient.Builder.html#withQueueSize-int->`_";
  private static final String SOLR_THREAD_COUNT_DOC = "The number of threads used to empty ConcurrentUpdateSolrClients queue. See " +
      "`ConcurrentUpdateSolrClient.Builder.withThreadCount(int) <https://lucene.apache.org/solr/6_3_0/solr-solrj/org/apache/solr/client/solrj/impl/ConcurrentUpdateSolrClient.Builder.html#withThreadCount-int->`_";


  public final String solrUrl;
  public final int queueSize;
  public final int threadCount;


  public HttpSolrSinkConnectorConfig(Map<String, String> props) {
    super(config(), props);
    this.solrUrl = this.getString(SOLR_URL_CONFIG);
    this.queueSize = this.getInt(SOLR_QUEUE_SIZE_CONFIG);
    this.threadCount = this.getInt(SOLR_THREAD_COUNT_CONFIG);
  }

  public static ConfigDef config() {
    return SolrSinkConnectorConfig.config()
        .define(
            ConfigKeyBuilder.of(SOLR_URL_CONFIG, ConfigDef.Type.STRING)
                .importance(ConfigDef.Importance.HIGH)
                .documentation(SOLR_URL_DOC)
                .group(SolrSinkConnectorConfig.CONNECTION_GROUP)
                .build()
        ).define(
            ConfigKeyBuilder.of(SOLR_QUEUE_SIZE_CONFIG, ConfigDef.Type.INT)
                .importance(ConfigDef.Importance.HIGH)
                .defaultValue(100)
                .documentation(SOLR_QUEUE_SIZE_DOC)
                .group(SolrSinkConnectorConfig.INDEXING_GROUP)
                .build()
        ).define(
            ConfigKeyBuilder.of(SOLR_THREAD_COUNT_CONFIG, ConfigDef.Type.INT)
                .importance(ConfigDef.Importance.MEDIUM)
                .defaultValue(1)
                .documentation(SOLR_THREAD_COUNT_DOC)
                .group(SolrSinkConnectorConfig.INDEXING_GROUP)
                .build()
        );
  }
}
